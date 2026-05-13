package org.example;

import org.example.graphql.export.DataExporter;
import org.example.graphql.models.*;
import org.example.graphql.parser.EducationalDataParser;
import org.example.graphql.util.JsonResponseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Advanced GraphQL Data Processor
 * Demonstrates how to parse, analyze, and export educational data
 */
public class AdvancedGraphQLProcessor {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedGraphQLProcessor.class);
    private final JsonResponseProcessor responseProcessor;
    private final DataExporter dataExporter;

    public AdvancedGraphQLProcessor() {
        this.responseProcessor = new JsonResponseProcessor();
        this.dataExporter = new DataExporter();
    }

    /**
     * Process complete educational dataset
     */
    public void processEducationalData(String jsonFilePath) {
        try {
            logger.info("Processing educational data from: {}", jsonFilePath);

            // Parse JSON file
            GraphQLResponse response = responseProcessor.parseJsonFile(jsonFilePath);
            if (response == null || response.getData() == null) {
                logger.error("Invalid response data");
                return;
            }

            var data = response.getData();

            // Process Viewer data
            if (data.getViewer() != null) {
                processDisciplines(data.getViewer());
            }

            // Process Learning Group data
            if (data.getLearningGroup() != null) {
                processLearningGroup(data.getLearningGroup());
            }

            logger.info("Data processing completed successfully");

        } catch (IOException e) {
            logger.error("Error processing data", e);
        }
    }

    private void processDisciplines(Viewer viewer) {
        logger.info("\n========== DISCIPLINES PROCESSING ==========");

        var disciplines = EducationalDataParser.getUniqueDisciplines(viewer);
        logger.info("Total unique disciplines: {}", disciplines.size());

        // Export to CSV
        try {
            dataExporter.exportDisciplinesToCsv(
                    new java.util.ArrayList<>(disciplines),
                    "/tmp/disciplines.csv"
            );
            logger.info("Disciplines exported to: /tmp/disciplines.csv");
        } catch (IOException e) {
            logger.error("Error exporting disciplines", e);
        }
    }

    private void processLearningGroup(LearningGroup group) {
        logger.info("\n========== LEARNING GROUP PROCESSING ==========");
        logger.info("Group ID: {}", group.getId());

        // Process teachers
        processTeachers(group);

        // Process syllabi
        processSyllabus(group);

        // Process lessons and homeworks
        processLessonsAndHomeworks(group);
    }

    private void processTeachers(LearningGroup group) {
        logger.info("\n--- Teachers Processing ---");

        var allTeachers = EducationalDataParser.getAllTeachers(group);
        logger.info("Total teachers: {}", allTeachers.size());

        // Group teachers by role
        Map<String, java.util.List<TeacherItem>> byRole =
                EducationalDataParser.groupTeachersByRole(group);

        byRole.forEach((role, teachers) -> {
            logger.info("Role '{}': {} teachers", role, teachers.size());
        });

        // Export teachers
        try {
            dataExporter.exportTeachersToJson(
                    allTeachers,
                    "/tmp/teachers.json"
            );
            logger.info("Teachers exported to: /tmp/teachers.json");
        } catch (IOException e) {
            logger.error("Error exporting teachers", e);
        }
    }

    private void processSyllabus(LearningGroup group) {
        logger.info("\n--- Syllabus Processing ---");

        var disciplineNames = EducationalDataParser.getSyllabusDisciplineNames(group);
        logger.info("Discipline names in syllabus: {}", disciplineNames.size());

        disciplineNames.forEach(name -> logger.info("  - {}", name));
    }

    private void processLessonsAndHomeworks(LearningGroup group) {
        logger.info("\n--- Lessons & Homeworks Processing ---");

        var allLessons = EducationalDataParser.getAllLessons(group);
        logger.info("Total lessons: {}", allLessons.size());

        var allHomeworks = EducationalDataParser.getAllHomeworks(group);
        logger.info("Total homeworks: {}", allHomeworks.size());

        var taskCount = EducationalDataParser.getTotalTaskCount(group);
        logger.info("Total tasks: {}", taskCount);

        // Analyze task types
        var tasksByType = EducationalDataParser.countTasksByType(group);
        logger.info("Tasks by type:");
        tasksByType.forEach((type, count) ->
                logger.info("  - {}: {} tasks", type, count)
        );

        // Export homework summary
        try {
            dataExporter.exportHomeworkSummary(
                    allHomeworks,
                    "/tmp/homework_summary.json"
            );
            logger.info("Homework summary exported to: /tmp/homework_summary.json");
        } catch (IOException e) {
            logger.error("Error exporting homework summary", e);
        }
    }

    /**
     * Generate data statistics report
     */
    public void generateStatisticsReport(String jsonFilePath) {
        try {
            logger.info("\n========== STATISTICS REPORT ==========");

            GraphQLResponse response = responseProcessor.parseJsonFile(jsonFilePath);
            if (response == null || response.getData() == null) {
                return;
            }

            var data = response.getData();

            // Viewer statistics
            if (data.getViewer() != null) {
                long disciplineCount = data.getViewer().getAcademicDisciplines() != null ?
                        data.getViewer().getAcademicDisciplines().size() : 0;
                logger.info("Viewer Disciplines: {}", disciplineCount);
            }

            // Learning group statistics
            if (data.getLearningGroup() != null) {
                var group = data.getLearningGroup();

                long teacherCount = EducationalDataParser.getAllTeachers(group).size();
                long lessonCount = EducationalDataParser.getAllLessons(group).size();
                long homeworkCount = EducationalDataParser.getAllHomeworks(group).size();
                long taskCount = EducationalDataParser.getTotalTaskCount(group);

                logger.info("Learning Group Statistics:");
                logger.info("  Teachers: {}", teacherCount);
                logger.info("  Lessons: {}", lessonCount);
                logger.info("  Homeworks: {}", homeworkCount);
                logger.info("  Total Tasks: {}", taskCount);
            }

        } catch (IOException e) {
            logger.error("Error generating report", e);
        }
    }

    public static void main(String[] args) {
        String jsonFilePath = "/home/xelliks/IdeaProjects/gpo/2.json";

        AdvancedGraphQLProcessor processor = new AdvancedGraphQLProcessor();

        // Process data
        processor.processEducationalData(jsonFilePath);

        // Generate report
        processor.generateStatisticsReport(jsonFilePath);
    }
}


