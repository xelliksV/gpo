package org.example;

import org.example.graphql.models.*;
import org.example.graphql.util.JsonResponseProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Application to process and analyze GraphQL JSON response
 * This demonstrates how to work with the complete JSON structure
 */
public class JsonAnalyzerApp {
    private static final Logger logger = LoggerFactory.getLogger(JsonAnalyzerApp.class);
    private final JsonResponseProcessor processor;

    public JsonAnalyzerApp() {
        this.processor = new JsonResponseProcessor();
    }

    /**
     * Load and analyze the GraphQL response from JSON file
     */
    public void analyzeJsonFile(String filePath) {
        try {
            logger.info("Loading GraphQL response from: {}", filePath);
            GraphQLResponse response = processor.parseJsonFile(filePath);

            if (response == null || response.getData() == null) {
                logger.error("No data found in response");
                return;
            }

            Data data = response.getData();

            // Analyze Viewer data
            if (data.getViewer() != null) {
                analyzeViewer(data.getViewer());
            }

            // Analyze Learning Group data
            if (data.getLearningGroup() != null) {
                analyzeLearningGroup(data.getLearningGroup());
            }

        } catch (IOException e) {
            logger.error("Error loading JSON file", e);
        }
    }

    private void analyzeViewer(Viewer viewer) {
        logger.info("\n=== VIEWER ANALYSIS ===");
        logger.info("Viewer ID: {}", viewer.getId());

        List<AcademicDiscipline> disciplines = viewer.getAcademicDisciplines();
        if (disciplines != null && !disciplines.isEmpty()) {
            logger.info("Total Academic Disciplines: {}", disciplines.size());

            // Group by attendance settings
            long enabledCount = disciplines.stream()
                    .filter(d -> d.getAttendanceSettings() != null && d.getAttendanceSettings().isEnabled())
                    .count();

            logger.info("Attendance Enabled: {}", enabledCount);
            logger.info("Attendance Disabled: {}", disciplines.size() - enabledCount);

            // List all disciplines
            logger.info("\nAll Disciplines:");
            disciplines.forEach(d ->
                    logger.info("  - {} (UID: {}, Attendance: {})",
                            d.getName() != null ? d.getName() : "N/A",
                            d.getUid(),
                            d.getAttendanceSettings() != null && d.getAttendanceSettings().isEnabled() ? "Enabled" : "Disabled")
            );
        }
    }

    private void analyzeLearningGroup(LearningGroup group) {
        logger.info("\n=== LEARNING GROUP ANALYSIS ===");
        logger.info("Group ID: {}", group.getId());
        logger.info("Group Type: {}", group.getTypename());

        LearningConfiguration config = group.getLearningConfiguration();
        if (config == null) {
            logger.warn("No learning configuration found");
            return;
        }

        // Analyze Syllabus
        analyzeSyllabus(config.getSyllabus());

        // Analyze Teachers
        analyzeTeachers(config.getTeachers());

        // Analyze Lessons
        analyzeLessons(config.getLesson());
    }

    private void analyzeSyllabus(Syllabus syllabus) {
        logger.info("\n--- SYLLABUS ---");
        if (syllabus == null || syllabus.getDisciplines() == null) {
            logger.info("No syllabus data");
            return;
        }

        List<Discipline> disciplines = syllabus.getDisciplines();
        logger.info("Total Disciplines in Syllabus: {}", disciplines.size());

        disciplines.forEach(d -> {
            if (d.getAcademicDiscipline() != null) {
                AcademicDiscipline ad = d.getAcademicDiscipline();
                logger.info("  Discipline: {} (UID: {})", ad.getName(), ad.getUid());
            }
        });
    }

    private void analyzeTeachers(Teachers teachers) {
        logger.info("\n--- TEACHERS ---");
        if (teachers == null || teachers.getItems() == null) {
            logger.info("No teacher data");
            return;
        }

        List<TeacherItem> items = teachers.getItems();
        logger.info("Total Teachers: {}", items.size());

        // Group teachers by role
        items.stream()
                .filter(t -> t.getTeacher() != null && t.getTeacher().getHuman() != null)
                .forEach(item -> {
                    Teacher teacher = item.getTeacher();
                    HumanProfile human = teacher.getHuman();
                    logger.info("  Teacher: {} {} {}",
                            human.getFirstName(),
                            human.getMiddleName(),
                            human.getLastName());
                    logger.info("    ID: {}, Roles: {}, Disabled: {}",
                            item.getId(),
                            item.getRoles(),
                            item.isDisabled());

                    // Show academic units
                    if (item.getAcademicUnitIds() != null) {
                        AcademicUnitIds units = item.getAcademicUnitIds();
                        logger.info("    Selection Behaviour: {}", units.getSelectionBehaviour());

                        if (units.getDisciplines() != null) {
                            units.getDisciplines().forEach(d ->
                                    logger.info("      Discipline: {}, Units: {}",
                                            d.getAcademicDisciplineId(),
                                            d.getUnitIds() != null ? d.getUnitIds().size() : 0)
                            );
                        }
                    }
                });
    }

    private void analyzeLessons(Lesson lessons) {
        logger.info("\n--- LESSONS & HOMEWORKS ---");
        if (lessons == null || lessons.getItems() == null) {
            logger.info("No lesson data");
            return;
        }

        List<AcademicLesson> items = lessons.getItems();
        logger.info("Total Lessons: {}", items.size());

        items.stream()
                .filter(lesson -> lesson.getHomeWork() != null)
                .limit(5)  // Limit output to first 5 lessons
                .forEach(lesson -> {
                    HomeWork hw = lesson.getHomeWork();
                    logger.info("  Lesson: {}", lesson.getUid());
                    logger.info("    HomeWork ID: {}", hw.getId());

                    if (hw.getTasks() != null) {
                        logger.info("    Tasks: {}", hw.getTasks().size());

                        // Analyze task types
                        hw.getTasks().stream()
                                .limit(3)
                                .forEach(task ->
                                        logger.info("      Task {}: Kind={}, AnswerParts={}",
                                                task.getUid(),
                                                task.getKind(),
                                                task.getAnswerParts() != null ? task.getAnswerParts() : "N/A")
                                );
                    }
                });

        logger.info("(Showing first 5 lessons with homework)");
    }

    public static void main(String[] args) {
        String jsonFilePath = "/home/xelliks/IdeaProjects/gpo/2.json";

        JsonAnalyzerApp analyzer = new JsonAnalyzerApp();
        analyzer.analyzeJsonFile(jsonFilePath);
    }
}

