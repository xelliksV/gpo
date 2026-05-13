package org.example.graphql.parser;

import org.example.graphql.models.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Parser for educational data structures
 * Provides convenient methods for extracting and organizing data
 */
public class EducationalDataParser {

    /**
     * Extract all unique academic disciplines from viewer
     */
    public static Set<AcademicDiscipline> getUniqueDisciplines(Viewer viewer) {
        if (viewer == null || viewer.getAcademicDisciplines() == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(viewer.getAcademicDisciplines());
    }

    /**
     * Get all teachers from learning group
     */
    public static List<TeacherItem> getAllTeachers(LearningGroup group) {
        if (group == null || group.getLearningConfiguration() == null) {
            return Collections.emptyList();
        }
        
        Teachers teachers = group.getLearningConfiguration().getTeachers();
        if (teachers == null || teachers.getItems() == null) {
            return Collections.emptyList();
        }
        
        return teachers.getItems();
    }

    /**
     * Get teacher by name
     */
    public static Optional<TeacherItem> getTeacherByName(LearningGroup group, String firstName, String lastName) {
        return getAllTeachers(group).stream()
                .filter(t -> t.getTeacher() != null && t.getTeacher().getHuman() != null)
                .filter(t -> firstName.equals(t.getTeacher().getHuman().getFirstName()) &&
                            lastName.equals(t.getTeacher().getHuman().getLastName()))
                .findFirst();
    }

    /**
     * Get all disciplines from syllabus
     */
    public static List<Discipline> getSyllabusDisciplines(LearningGroup group) {
        if (group == null || group.getLearningConfiguration() == null) {
            return Collections.emptyList();
        }
        
        Syllabus syllabus = group.getLearningConfiguration().getSyllabus();
        if (syllabus == null || syllabus.getDisciplines() == null) {
            return Collections.emptyList();
        }
        
        return syllabus.getDisciplines();
    }

    /**
     * Get all lessons from learning group
     */
    public static List<AcademicLesson> getAllLessons(LearningGroup group) {
        if (group == null || group.getLearningConfiguration() == null) {
            return Collections.emptyList();
        }
        
        Lesson lesson = group.getLearningConfiguration().getLesson();
        if (lesson == null || lesson.getItems() == null) {
            return Collections.emptyList();
        }
        
        return lesson.getItems();
    }

    /**
     * Get all homeworks from lessons
     */
    public static List<HomeWork> getAllHomeworks(LearningGroup group) {
        return getAllLessons(group).stream()
                .filter(lesson -> lesson.getHomeWork() != null)
                .map(AcademicLesson::getHomeWork)
                .collect(Collectors.toList());
    }

    /**
     * Get total number of tasks across all homeworks
     */
    public static int getTotalTaskCount(LearningGroup group) {
        return getAllHomeworks(group).stream()
                .mapToInt(hw -> hw.getTasks() != null ? hw.getTasks().size() : 0)
                .sum();
    }

    /**
     * Group teachers by role
     */
    public static Map<String, List<TeacherItem>> groupTeachersByRole(LearningGroup group) {
        return getAllTeachers(group).stream()
                .collect(Collectors.groupingBy(teacher -> {
                    List<String> roles = teacher.getRoles();
                    return roles != null && !roles.isEmpty() ? roles.get(0) : "Unknown";
                }));
    }

    /**
     * Count tasks by type
     */
    public static Map<String, Long> countTasksByType(LearningGroup group) {
        return getAllHomeworks(group).stream()
                .flatMap(hw -> hw.getTasks() != null ? hw.getTasks().stream() : java.util.stream.Stream.empty())
                .collect(Collectors.groupingBy(
                        task -> task.getKind() != null ? task.getKind() : "Unknown",
                        Collectors.counting()
                ));
    }

    /**
     * Get discipline names from syllabus
     */
    public static List<String> getSyllabusDisciplineNames(LearningGroup group) {
        return getSyllabusDisciplines(group).stream()
                .filter(d -> d.getAcademicDiscipline() != null)
                .map(d -> d.getAcademicDiscipline().getName())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}

