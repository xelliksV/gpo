package org.example;

import org.example.graphql.client.GraphQLClient;
import org.example.graphql.models.GraphQLResponse;
import org.example.graphql.models.Data;
import org.example.graphql.queries.AcademicDisciplineQueries;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * GraphQL Queries Examples
 * Demonstrates all available GraphQL queries and how to use them
 */
public class GraphQLQueriesExample {
    private static final Logger logger = LoggerFactory.getLogger(GraphQLQueriesExample.class);

    /**
     * Example 1: Simple query without variables
     * GET_ACADEMIC_DISCIPLINES
     */
    public static void example1_GetAcademicDisciplines() {
        logger.info("\n========== EXAMPLE 1: Get Academic Disciplines ==========");
        logger.info("Query: GET_ACADEMIC_DISCIPLINES");
        logger.info("Type: Simple query without variables");
        logger.info("Description: Returns all available academic disciplines for the current user");

        String query = AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES;
        logger.info("Query:\n{}", query);

        logger.info("Usage:");
        logger.info("  GraphQLClient client = new GraphQLClient(endpoint);");
        logger.info("  GraphQLResponse response = client.executeQuery(query);");
        logger.info("  Data data = response.getData();");
        logger.info("  List<AcademicDiscipline> disciplines = data.getViewer().getAcademicDisciplines();");
    }

    /**
     * Example 2: Query with single variable
     * GET_SYLLABUS_AND_TEACHERS
     */
    public static void example2_GetSyllabusAndTeachers() {
        logger.info("\n========== EXAMPLE 2: Get Syllabus and Teachers ==========");
        logger.info("Query: GET_SYLLABUS_AND_TEACHERS");
        logger.info("Type: Query with variables");
        logger.info("Variable: groupId (ID!)");
        logger.info("Description: Returns syllabus and teachers for a specific learning group");

        String query = AcademicDisciplineQueries.GET_SYLLABUS_AND_TEACHERS;
        logger.info("Query:\n{}", query);

        logger.info("Usage:");
        logger.info("  Map<String, Object> variables = new HashMap<>();");
        logger.info("  variables.put(\"groupId\", \"TGVhcm5pbmdHcm91cDoyNjc5NTI=\");");
        logger.info("  GraphQLResponse response = client.executeQuery(query, variables);");
        logger.info("  LearningGroup group = response.getData().getLearningGroup();");
    }

    /**
     * Example 3: Query with pagination
     * GET_ACADEMIC_DISCIPLINES_PAGINATED
     */
    public static void example3_GetAcademicDisciplinesPaginated() {
        logger.info("\n========== EXAMPLE 3: Get Academic Disciplines Paginated ==========");
        logger.info("Query: GET_ACADEMIC_DISCIPLINES_PAGINATED");
        logger.info("Type: Query with pagination variables");
        logger.info("Variables: first (Int!), after (String)");
        logger.info("Description: Returns paginated list of academic disciplines");

        String query = AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES_PAGINATED;
        logger.info("Query:\n{}", query);

        logger.info("Usage:");
        logger.info("  Map<String, Object> variables = new HashMap<>();");
        logger.info("  variables.put(\"first\", 10);");
        logger.info("  variables.put(\"after\", null); // null for first page");
        logger.info("  GraphQLResponse response = client.executeQuery(query, variables);");
        logger.info("  // Use PageInfo.endCursor for next page query");
    }

    /**
     * Example 4: Complex query with nested data
     * GET_FULL_LEARNING_GROUP
     */
    public static void example4_GetFullLearningGroup() {
        logger.info("\n========== EXAMPLE 4: Get Full Learning Group ==========");
        logger.info("Query: GET_FULL_LEARNING_GROUP");
        logger.info("Type: Complex nested query");
        logger.info("Variable: groupId (ID!)");
        logger.info("Description: Returns all data for a learning group including syllabus, teachers, lessons, tasks");

        String query = AcademicDisciplineQueries.GET_FULL_LEARNING_GROUP;
        logger.info("Query length: {} characters", query.length());
        logger.info("Query complexity: HIGH - includes nested structures up to tasks");

        logger.info("Usage:");
        logger.info("  Map<String, Object> variables = new HashMap<>();");
        logger.info("  variables.put(\"groupId\", \"TGVhcm5pbmdHcm91cDoyNjc5NTI=\");");
        logger.info("  GraphQLResponse response = client.executeQuery(query, variables);");
        logger.info("  LearningGroup group = response.getData().getLearningGroup();");
        logger.info("  LearningConfiguration config = group.getLearningConfiguration();");
        logger.info("  // Access syllabus, teachers, lessons, tasks");
    }

    /**
     * Example 5: Query for lessons with tasks
     * GET_LESSONS_WITH_TASKS
     */
    public static void example5_GetLessonsWithTasks() {
        logger.info("\n========== EXAMPLE 5: Get Lessons With Tasks ==========");
        logger.info("Query: GET_LESSONS_WITH_TASKS");
        logger.info("Type: Nested query for educational content");
        logger.info("Variable: groupId (ID!)");
        logger.info("Description: Returns lessons with homework and tasks");

        String query = AcademicDisciplineQueries.GET_LESSONS_WITH_TASKS;
        logger.info("Query:\n{}", query);

        logger.info("Usage:");
        logger.info("  Map<String, Object> variables = new HashMap<>();");
        logger.info("  variables.put(\"groupId\", \"TGVhcm5pbmdHcm91cDoyNjc5NTI=\");");
        logger.info("  GraphQLResponse response = client.executeQuery(query, variables);");
        logger.info("  List<AcademicLesson> lessons = response.getData()");
        logger.info("      .getLearningGroup().getLearningConfiguration().getLesson().getItems();");
        logger.info("  // Each lesson has homeWork with tasks");
    }

    /**
     * Example 6: Query for teacher academic units
     * GET_TEACHER_UNITS
     */
    public static void example6_GetTeacherUnits() {
        logger.info("\n========== EXAMPLE 6: Get Teacher Units ==========");
        logger.info("Query: GET_TEACHER_UNITS");
        logger.info("Type: Teacher-specific query");
        logger.info("Variable: groupId (ID!)");
        logger.info("Description: Returns academic units assigned to each teacher");

        String query = AcademicDisciplineQueries.GET_TEACHER_UNITS;
        logger.info("Query:\n{}", query);

        logger.info("Usage:");
        logger.info("  Map<String, Object> variables = new HashMap<>();");
        logger.info("  variables.put(\"groupId\", \"TGVhcm5pbmdHcm91cDoyNjc5NTI=\");");
        logger.info("  GraphQLResponse response = client.executeQuery(query, variables);");
        logger.info("  // Access teacher.academicUnitIds for unit assignments");
    }

    /**
     * Example 7: Comprehensive example with error handling
     */
    public static void example7_ComprehensiveWithErrorHandling() {
        logger.info("\n========== EXAMPLE 7: Comprehensive Example With Error Handling ==========");

        try {
            String endpoint = "https://api.soholms.com/master/graphql";
            GraphQLClient client = new GraphQLClient(endpoint);

            // Step 1: Fetch disciplines
            logger.info("Step 1: Fetching academic disciplines...");
            GraphQLResponse response1 = client.executeQuery(
                    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES
            );

            if (response1.getData() != null && response1.getData().getViewer() != null) {
                int disciplineCount = response1.getData().getViewer().getAcademicDisciplines().size();
                logger.info("  ✓ Found {} disciplines", disciplineCount);

                // Step 2: Fetch learning group data
                logger.info("Step 2: Fetching learning group data...");
                Map<String, Object> variables = new HashMap<>();
                variables.put("groupId", "TGVhcm5pbmdHcm91cDoyNjc5NTI=");

                GraphQLResponse response2 = client.executeQuery(
                        AcademicDisciplineQueries.GET_SYLLABUS_AND_TEACHERS,
                        variables
                );

                if (response2.getData() != null && response2.getData().getLearningGroup() != null) {
                    logger.info("  ✓ Successfully fetched learning group");

                    // Step 3: Process data
                    logger.info("Step 3: Processing data...");
                    Data data = response2.getData();
                    // Process as needed
                    logger.info("  ✓ Data processing complete");
                }
            }

        } catch (IOException e) {
            logger.error("Error during query execution: {}", e.getMessage());
            logger.error("Make sure the GraphQL endpoint is accessible and the token is valid");
        }
    }

    /**
     * Print all available queries
     */
    public static void printAllAvailableQueries() {
        logger.info("\n========== ALL AVAILABLE GRAPHQL QUERIES ==========\n");

        logger.info("1. GET_ACADEMIC_DISCIPLINES");
        logger.info("   - Returns all academic disciplines");
        logger.info("   - No variables required");
        logger.info("");

        logger.info("2. GET_VIEWER_WITH_DISCIPLINES");
        logger.info("   - Same as GET_ACADEMIC_DISCIPLINES");
        logger.info("   - No variables required");
        logger.info("");

        logger.info("3. GET_SYLLABUS_AND_TEACHERS");
        logger.info("   - Returns syllabus and teachers for a learning group");
        logger.info("   - Variable: groupId (ID!)");
        logger.info("");

        logger.info("4. GET_LESSONS_WITH_TASKS");
        logger.info("   - Returns lessons with homework and tasks");
        logger.info("   - Variable: groupId (ID!)");
        logger.info("");

        logger.info("5. GET_TEACHER_UNITS");
        logger.info("   - Returns teacher academic units");
        logger.info("   - Variable: groupId (ID!)");
        logger.info("");

        logger.info("6. GET_FULL_LEARNING_GROUP");
        logger.info("   - Returns complete learning group data");
        logger.info("   - Variable: groupId (ID!)");
        logger.info("   - Most comprehensive query");
        logger.info("");

        logger.info("7. GET_ACADEMIC_DISCIPLINES_PAGINATED");
        logger.info("   - Returns paginated disciplines");
        logger.info("   - Variables: first (Int!), after (String)");
        logger.info("");

        logger.info("8. GET_LEARNING_GROUPS");
        logger.info("   - Returns learning groups for a discipline");
        logger.info("   - Variable: disciplineId (ID!)");
    }

    public static void main(String[] args) {
        logger.info("===============================================");
        logger.info("  GraphQL Queries Examples for Education System");
        logger.info("===============================================");

        // Print all available queries
        printAllAvailableQueries();

        // Show examples
        example1_GetAcademicDisciplines();
        example2_GetSyllabusAndTeachers();
        example3_GetAcademicDisciplinesPaginated();
        example4_GetFullLearningGroup();
        example5_GetLessonsWithTasks();
        example6_GetTeacherUnits();
        example7_ComprehensiveWithErrorHandling();

        logger.info("\n========== KEY POINTS ==========");
        logger.info("1. All queries use variables for dynamic inputs");
        logger.info("2. Responses are mapped to Java model classes");
        logger.info("3. Error handling should include IOException");
        logger.info("4. OAuth token must be included in request headers");
        logger.info("5. Use null-safe checks for optional fields");
    }
}

