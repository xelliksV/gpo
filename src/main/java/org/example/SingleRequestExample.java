package org.example;

import org.example.graphql.client.GraphQLClient;
import org.example.graphql.models.GraphQLResponse;
import org.example.graphql.queries.AcademicDisciplineQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple example showing how to fetch all data in one GraphQL request
 * This replicates the structure from the JSON file
 */
public class SingleRequestExample {
    private static final Logger logger = LoggerFactory.getLogger(SingleRequestExample.class);

    public static void main(String[] args) {
        logger.info("===============================================");
        logger.info("  Single GraphQL Request Example");
        logger.info("  Fetches all data in one request");
        logger.info("===============================================");

        try {
            // Initialize GraphQL client
            String endpoint = "https://api.soholms.com/master/graphql";
            GraphQLClient client = new GraphQLClient(endpoint);

            logger.info("Testing simple query first...");

            // First test with a simple query
            GraphQLResponse simpleResponse = client.executeQuery(
                    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES
            );

            if (simpleResponse.getData() != null) {
                logger.info("✓ Simple query works! Server is accessible.");
            } else {
                logger.error("✗ Simple query failed. Check authentication.");
                return;
            }

            logger.info("Testing learning group access...");

            // First get user's available learning groups
            GraphQLResponse groupsResponse = client.executeQuery(
                    AcademicDisciplineQueries.GET_USER_LEARNING_GROUPS
            );

            String availableGroupId = null;
            if (groupsResponse.getData() != null && groupsResponse.getData().getViewer() != null) {
                var viewer = groupsResponse.getData().getViewer();
                logger.info("Found {} learning groups for user",
                        viewer.getLearningGroups() != null ? viewer.getLearningGroups().size() : 0);

                if (viewer.getLearningGroups() != null && !viewer.getLearningGroups().isEmpty()) {
                    availableGroupId = viewer.getLearningGroups().get(0).getId();
                    logger.info("Using first available learning group: {}", availableGroupId);
                }
            }

            if (availableGroupId != null) {
                logger.info("✓ Learning group found and accessible.");

                // Now try to get full data with the available group
                Map<String, Object> variables = new HashMap<>();
                variables.put("groupId", availableGroupId);

                GraphQLResponse groupTestResponse = client.executeQuery(
                        AcademicDisciplineQueries.GET_SYLLABUS_AND_TEACHERS,
                        variables
                );

                if (groupTestResponse.getData() != null && groupTestResponse.getData().getLearningGroup() != null) {
                    logger.info("✓ Learning group data accessible.");
                } else {
                    logger.error("✗ Learning group data not accessible. Using only viewer data.");
                    availableGroupId = null;
                }
            } else {
                logger.error("✗ No learning groups available. Using only viewer data.");
            }

            logger.info("Sending GraphQL query to fetch all data...");

            // Execute the single comprehensive query
            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_ALL_DATA
            );

            if (response.getData() != null) {
                logger.info("✓ Successfully received data from GraphQL server");

                // Process Viewer data
                if (response.getData().getViewer() != null) {
                    var viewer = response.getData().getViewer();
                    logger.info("Viewer ID: {}", viewer.getId());
                    logger.info("Academic Disciplines: {}",
                            viewer.getAcademicDisciplines() != null ? viewer.getAcademicDisciplines().size() : 0);
                }

                // Process Learning Group data
                if (response.getData().getLearningGroup() != null) {
                    var group = response.getData().getLearningGroup();
                    logger.info("Learning Group ID: {}", group.getId());
                    logger.info("Group Type: {}", group.getTypename());

                    if (group.getLearningConfiguration() != null) {
                        var config = group.getLearningConfiguration();

                        // Count disciplines in syllabus
                        if (config.getSyllabus() != null && config.getSyllabus().getDisciplines() != null) {
                            logger.info("Syllabus Disciplines: {}", config.getSyllabus().getDisciplines().size());
                        }

                        // Count teachers
                        if (config.getTeachers() != null && config.getTeachers().getItems() != null) {
                            logger.info("Teachers: {}", config.getTeachers().getItems().size());
                        }

                        // Count lessons
                        if (config.getLesson() != null && config.getLesson().getItems() != null) {
                            logger.info("Lessons: {}", config.getLesson().getItems().size());
                        }
                    }
                }

                logger.info("✓ All data processed successfully");
                logger.info("This single request fetched the same data structure as the JSON file");

            } else {
                logger.error("No data received from server");
            }

        } catch (IOException e) {
            logger.error("Error executing GraphQL query: {}", e.getMessage());
            logger.error("Make sure the GraphQL endpoint is accessible and authentication is configured");
        }

        logger.info("===============================================");
        logger.info("  Query Details:");
        logger.info("  - Endpoint: https://api.soholms.com/master/graphql");
        logger.info("  - Query: GET_ALL_DATA");
        logger.info("  - Returns: viewer + learningGroup data");
        logger.info("  - Structure: Matches the JSON file structure");
        logger.info("===============================================");
    }
}
