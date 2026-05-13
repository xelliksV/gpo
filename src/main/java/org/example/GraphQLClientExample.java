package org.example;

import org.example.graphql.client.GraphQLClient;
import org.example.graphql.models.*;
import org.example.graphql.queries.AcademicDisciplineQueries;
import org.example.services.DataPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GraphQL Client Example for Educational Management System
 * Demonstrates how to query academic disciplines and related data
 */
@Component
public class GraphQLClientExample {
    private static final Logger logger = LoggerFactory.getLogger(GraphQLClientExample.class);

    @Autowired
    private DataPersistenceService dataPersistenceService;

    public void runExample() {
        // Initialize GraphQL client with endpoint
        String endpoint = "https://api.soholms.com/master/graphql";  // Replace with your actual endpoint
        GraphQLClient client = new GraphQLClient(endpoint);
        try {
            // Example 1: Get all academic disciplines
            logger.info("Fetching academic disciplines...");
            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_USER_LEARNING_GROUPS
            );
            Data data = response.getData();

            if (data != null && data.getViewer() != null) {
                logger.info("Successfully retrieved {} disciplines",
                        data.getViewer().getAcademicDisciplines().size());
                data.getViewer().getAcademicDisciplines().forEach(discipline ->
                        logger.info("Discipline - UID: {}, Name: {}, ID: {}",
                                discipline.getUid(),
                                discipline.getName(),
                                discipline.getId())
                );
            }

            // Save data to database
            dataPersistenceService.saveGraphQLData(response);

            // Example 2: Get full learning group configuration
            logger.info("\nFetching full learning group configuration...");
            Map<String, Object> variables = new HashMap<>();
            variables.put("groupId", "TGVhcm5pbmdHcm91cDoyNjc5NTI=");  // Example ID from JSON file

            GraphQLResponse groupResponse = client.executeQuery(
                    AcademicDisciplineQueries.GET_FULL_LEARNING_GROUP,
                    variables
            );

            Data groupData = groupResponse.getData();
            if (groupData != null && groupData.getLearningGroup() != null) {
                LearningGroup group = groupData.getLearningGroup();
                logger.info("Learning Group ID: {}", group.getId());

                if (group.getLearningConfiguration() != null) {
                    // Log syllabus disciplines
                    Syllabus syllabus = group.getLearningConfiguration().getSyllabus();
                    if (syllabus != null && syllabus.getDisciplines() != null) {
                        logger.info("Found {} disciplines in syllabus", syllabus.getDisciplines().size());
                        syllabus.getDisciplines().forEach(discipline -> {
                            if (discipline.getAcademicDiscipline() != null) {
                                AcademicDiscipline ad = discipline.getAcademicDiscipline();
                                logger.info("  - Discipline: {} (UID: {})", ad.getName(), ad.getUid());
                            }
                        });
                    }

                    // Log teachers
                    Teachers teachers = group.getLearningConfiguration().getTeachers();
                    if (teachers != null && teachers.getItems() != null) {
                        logger.info("Found {} teachers", teachers.getItems().size());
                        teachers.getItems().forEach(teacherItem -> {
                            if (teacherItem.getTeacher() != null) {
                                Teacher teacher = teacherItem.getTeacher();
                                if (teacher.getHuman() != null) {
                                    HumanProfile human = teacher.getHuman();
                                    logger.info("  - Teacher: {} {} {} (Roles: {})",
                                            human.getFirstName(),
                                            human.getMiddleName(),
                                            human.getLastName(),
                                            teacherItem.getRoles());
                                }
                            }
                        });
                    }

                    // Log lessons and homeworks
                    Lesson lessons = group.getLearningConfiguration().getLesson();
                    if (lessons != null && lessons.getItems() != null) {
                        logger.info("Found {} lessons", lessons.getItems().size());
                        lessons.getItems().forEach(lesson -> {
                            if (lesson.getHomeWork() != null) {
                                HomeWork homeWork = lesson.getHomeWork();
                                logger.info("  - HomeWork ID: {}, Tasks: {}",
                                        homeWork.getId(),
                                        homeWork.getTasks() != null ? homeWork.getTasks().size() : 0);

                                if (homeWork.getTasks() != null) {
                                    homeWork.getTasks().forEach(task -> {
                                        logger.info("    - Task UID: {}, Kind: {}",
                                                task.getUid(),
                                                task.getKind());
                                    });
                                }
                            }
                        });
                    }
                }
            }

            // Save data to database
            dataPersistenceService.saveGraphQLData(groupResponse);

        } catch (IOException e) {
            logger.error("Failed to execute GraphQL query", e);
        }
    }
}
