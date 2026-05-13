package org.example;

import org.example.graphql.client.GraphQLClient;
import org.example.graphql.models.*;
import org.example.graphql.queries.AcademicDisciplineQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GraphQL Server Data Fetcher
 * Fetches educational data from remote GraphQL server
 */
public class GraphQLServerDataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(GraphQLServerDataFetcher.class);
    private final GraphQLClient client;

    public GraphQLServerDataFetcher(String endpoint) {
        this.client = new GraphQLClient(endpoint);
    }

    /**
     * Fetch all academic disciplines from server
     */
    public void fetchAcademicDisciplines() {
        try {
            logger.info("\n========== FETCHING ACADEMIC DISCIPLINES ==========");

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES
            );

            if (response.getData() != null && response.getData().getViewer() != null) {
                Viewer viewer = response.getData().getViewer();
                logger.info("Viewer ID: {}", viewer.getId());
                logger.info("Total Disciplines: {}", viewer.getAcademicDisciplines().size());

                viewer.getAcademicDisciplines().forEach(d ->
                        logger.info("  - {} (UID: {}, ID: {})",
                                d.getName() != null ? d.getName() : "N/A",
                                d.getUid(),
                                d.getId())
                );
            }
        } catch (IOException e) {
            logger.error("Error fetching academic disciplines", e);
        }
    }

    /**
     * Fetch syllabus and teachers for learning group
     */
    public void fetchSyllabusAndTeachers(String groupId) {
        try {
            logger.info("\n========== FETCHING SYLLABUS AND TEACHERS ==========");

            Map<String, Object> variables = new HashMap<>();
            variables.put("groupId", groupId);

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_SYLLABUS_AND_TEACHERS,
                    variables
            );

            if (response.getData() != null && response.getData().getLearningGroup() != null) {
                LearningGroup group = response.getData().getLearningGroup();
                logger.info("Learning Group ID: {}", group.getId());

                // Log disciplines
                if (group.getLearningConfiguration() != null &&
                    group.getLearningConfiguration().getSyllabus() != null) {
                    var disciplines = group.getLearningConfiguration().getSyllabus().getDisciplines();
                    logger.info("Disciplines in Syllabus: {}", disciplines.size());

                    disciplines.forEach(d -> {
                        if (d.getAcademicDiscipline() != null) {
                            logger.info("  - {} (UID: {})",
                                    d.getAcademicDiscipline().getName(),
                                    d.getAcademicDiscipline().getUid());
                        }
                    });
                }

                // Log teachers
                if (group.getLearningConfiguration() != null &&
                    group.getLearningConfiguration().getTeachers() != null) {
                    var teachers = group.getLearningConfiguration().getTeachers().getItems();
                    logger.info("Teachers: {}", teachers != null ? teachers.size() : 0);

                    if (teachers != null) {
                        teachers.forEach(t -> {
                            if (t.getTeacher() != null && t.getTeacher().getHuman() != null) {
                                HumanProfile human = t.getTeacher().getHuman();
                                logger.info("  - {} {} {} (ID: {})",
                                        human.getFirstName(),
                                        human.getMiddleName(),
                                        human.getLastName(),
                                        t.getId());
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error fetching syllabus and teachers", e);
        }
    }

    /**
     * Fetch lessons with tasks
     */
    public void fetchLessonsWithTasks(String groupId) {
        try {
            logger.info("\n========== FETCHING LESSONS AND TASKS ==========");

            Map<String, Object> variables = new HashMap<>();
            variables.put("groupId", groupId);

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_LESSONS_WITH_TASKS,
                    variables
            );

            if (response.getData() != null && response.getData().getLearningGroup() != null) {
                LearningGroup group = response.getData().getLearningGroup();

                if (group.getLearningConfiguration() != null &&
                    group.getLearningConfiguration().getLesson() != null) {
                    var lessons = group.getLearningConfiguration().getLesson().getItems();
                    logger.info("Total Lessons: {}", lessons != null ? lessons.size() : 0);

                    if (lessons != null) {
                        lessons.stream()
                                .filter(lesson -> lesson.getHomeWork() != null)
                                .limit(5)
                                .forEach(lesson -> {
                                    HomeWork hw = lesson.getHomeWork();
                                    logger.info("  Lesson: {} - HomeWork {}",
                                            lesson.getUid(),
                                            hw.getId());

                                    if (hw.getTasks() != null) {
                                        logger.info("    Tasks: {}", hw.getTasks().size());

                                        hw.getTasks().stream()
                                                .limit(3)
                                                .forEach(task ->
                                                        logger.info("      - Task {}: Kind = {}",
                                                                task.getUid(),
                                                                task.getKind())
                                                );
                                    }
                                });
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error fetching lessons", e);
        }
    }

    /**
     * Fetch full learning group data with all nested information
     */
    public void fetchFullLearningGroup(String groupId) {
        try {
            logger.info("\n========== FETCHING FULL LEARNING GROUP DATA ==========");

            Map<String, Object> variables = new HashMap<>();
            variables.put("groupId", groupId);

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_FULL_LEARNING_GROUP,
                    variables
            );

            if (response.getData() != null && response.getData().getLearningGroup() != null) {
                logger.info("Successfully fetched full learning group data");
                logger.info("Parsing complete structure...");

                LearningGroup group = response.getData().getLearningGroup();
                logger.info("Group Type: {}", group.getTypename());
                logger.info("Group ID: {}", group.getId());

                LearningConfiguration config = group.getLearningConfiguration();
                if (config != null) {
                    // Syllabus
                    if (config.getSyllabus() != null) {
                        logger.info("Syllabus Disciplines: {}",
                                config.getSyllabus().getDisciplines().size());
                    }

                    // Teachers
                    if (config.getTeachers() != null && config.getTeachers().getItems() != null) {
                        logger.info("Teachers: {}", config.getTeachers().getItems().size());
                    }

                    // Lessons
                    if (config.getLesson() != null && config.getLesson().getItems() != null) {
                        logger.info("Lessons: {}", config.getLesson().getItems().size());
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error fetching full learning group", e);
        }
    }

    /**
     * Fetch teacher academic units
     */
    public void fetchTeacherUnits(String groupId) {
        try {
            logger.info("\n========== FETCHING TEACHER ACADEMIC UNITS ==========");

            Map<String, Object> variables = new HashMap<>();
            variables.put("groupId", groupId);

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_TEACHER_UNITS,
                    variables
            );

            if (response.getData() != null && response.getData().getLearningGroup() != null) {
                LearningGroup group = response.getData().getLearningGroup();

                if (group.getLearningConfiguration() != null &&
                    group.getLearningConfiguration().getTeachers() != null) {
                    var teachers = group.getLearningConfiguration().getTeachers().getItems();

                    teachers.forEach(teacher -> {
                        if (teacher.getTeacher() != null && teacher.getTeacher().getHuman() != null) {
                            HumanProfile human = teacher.getTeacher().getHuman();
                            logger.info("Teacher: {} {} {}",
                                    human.getFirstName(),
                                    human.getMiddleName(),
                                    human.getLastName());

                            if (teacher.getAcademicUnitIds() != null) {
                                AcademicUnitIds units = teacher.getAcademicUnitIds();
                                logger.info("  Selection Behaviour: {}", units.getSelectionBehaviour());

                                if (units.getDisciplines() != null) {
                                    units.getDisciplines().forEach(d ->
                                            logger.info("    Discipline: {}, Units: {}",
                                                    d.getAcademicDisciplineId(),
                                                    d.getUnitIds().size())
                                    );
                                }
                            }
                        }
                    });
                }
            }
        } catch (IOException e) {
            logger.error("Error fetching teacher units", e);
        }
    }

    /**
     * Fetch paginated disciplines
     */
    public void fetchPaginatedDisciplines(int first, String after) {
        try {
            logger.info("\n========== FETCHING PAGINATED DISCIPLINES ==========");

            Map<String, Object> variables = new HashMap<>();
            variables.put("first", first);
            if (after != null) {
                variables.put("after", after);
            }

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES_PAGINATED,
                    variables
            );

            if (response.getData() != null && response.getData().getViewer() != null) {
                Viewer viewer = response.getData().getViewer();
                logger.info("Viewer ID: {}", viewer.getId());
                logger.info("Disciplines fetched: {}",
                        viewer.getAcademicDisciplines().size());
            }
        } catch (IOException e) {
            logger.error("Error fetching paginated disciplines", e);
        }
    }

    /**
     * Fetch all data in one request (similar to the JSON file)
     */
    public void fetchAllDataInOneRequest() {
        try {
            logger.info("\n========== FETCHING ALL DATA IN ONE REQUEST ==========");
            logger.info("This query replicates the structure from the JSON file");

            GraphQLResponse response = client.executeQuery(
                    AcademicDisciplineQueries.GET_ALL_DATA
            );

            if (response.getData() != null) {
                logger.info("Successfully fetched all data in one request");

                // Process Viewer data
                if (response.getData().getViewer() != null) {
                    Viewer viewer = response.getData().getViewer();
                    logger.info("Viewer ID: {}", viewer.getId());
                    logger.info("Academic Disciplines: {}",
                            viewer.getAcademicDisciplines() != null ? viewer.getAcademicDisciplines().size() : 0);
                }

                // Process Learning Group data
                if (response.getData().getLearningGroup() != null) {
                    LearningGroup group = response.getData().getLearningGroup();
                    logger.info("Learning Group ID: {}", group.getId());
                    logger.info("Group Type: {}", group.getTypename());

                    if (group.getLearningConfiguration() != null) {
                        LearningConfiguration config = group.getLearningConfiguration();

                        // Syllabus
                        if (config.getSyllabus() != null) {
                            logger.info("Syllabus Disciplines: {}",
                                    config.getSyllabus().getDisciplines().size());
                        }

                        // Teachers
                        if (config.getTeachers() != null && config.getTeachers().getItems() != null) {
                            logger.info("Teachers: {}", config.getTeachers().getItems().size());
                        }

                        // Lessons
                        if (config.getLesson() != null && config.getLesson().getItems() != null) {
                            logger.info("Lessons: {}", config.getLesson().getItems().size());
                        }
                    }
                }

                logger.info("✓ All data fetched successfully in one GraphQL request");
            } else {
                logger.error("No data received in response");
            }

        } catch (IOException e) {
            logger.error("Error fetching all data", e);
        }
    }

    public static void main(String[] args) {
        // Use your GraphQL endpoint here
        String endpoint = "https://api.soholms.com/master/graphql";

        GraphQLServerDataFetcher fetcher = new GraphQLServerDataFetcher(endpoint);

        // Fetch all data in one request (similar to the JSON file)
        fetcher.fetchAllDataInOneRequest();

        // Alternative: Fetch individual parts
        /*
        // Fetch disciplines
        fetcher.fetchAcademicDisciplines();

        // Use a learning group ID from your data
        String learningGroupId = "QWNhZGVtaWNEaXNjaXBsaW5lOjQ4MDgx";

        // Fetch syllabus and teachers
        fetcher.fetchSyllabusAndTeachers(learningGroupId);

        // Fetch lessons with tasks
        fetcher.fetchLessonsWithTasks(learningGroupId);

        // Fetch teacher units
        fetcher.fetchTeacherUnits(learningGroupId);

        // Fetch full learning group
        fetcher.fetchFullLearningGroup(learningGroupId);

        // Fetch paginated disciplines
        fetcher.fetchPaginatedDisciplines(10, null);
        */
    }
}
