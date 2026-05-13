package org.example.services;

import org.example.graphql.models.*;
import org.example.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataPersistenceService {
    private static final Logger logger = LoggerFactory.getLogger(DataPersistenceService.class);

    @Autowired
    private ViewerRepository viewerRepository;

    @Autowired
    private AcademicDisciplineRepository academicDisciplineRepository;

    @Autowired
    private LearningGroupRepository learningGroupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private HomeWorkRepository homeWorkRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private QuizDetailsRepository quizDetailsRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Transactional
    public void saveGraphQLData(GraphQLResponse response) {
        if (response == null || response.getData() == null) {
            logger.warn("No data to save");
            return;
        }

        Data data = response.getData();

        // Save Viewer and Academic Disciplines
        if (data.getViewer() != null) {
            saveViewer(data.getViewer());
        }

        // Save Learning Group data
        if (data.getLearningGroup() != null) {
            saveLearningGroup(data.getLearningGroup());
        }

        logger.info("Data saved successfully");
    }

    private void saveViewer(Viewer viewer) {
        org.example.entities.Viewer entity = new org.example.entities.Viewer();
        entity.setId(viewer.getId());

        if (viewer.getAcademicDisciplines() != null) {
            List<org.example.entities.AcademicDiscipline> disciplines = viewer.getAcademicDisciplines().stream()
                    .map(this::mapToAcademicDisciplineEntity)
                    .collect(Collectors.toList());
            disciplines.forEach(d -> d.setViewer(entity));
            entity.setAcademicDisciplines(disciplines);
        }

        viewerRepository.save(entity);
        logger.info("Saved viewer with {} disciplines", entity.getAcademicDisciplines().size());
    }

    private org.example.entities.AcademicDiscipline mapToAcademicDisciplineEntity(
            org.example.graphql.models.AcademicDiscipline discipline) {
        org.example.entities.AcademicDiscipline entity = new org.example.entities.AcademicDiscipline();
        entity.setUid(discipline.getUid());
        entity.setId(discipline.getId());
        entity.setName(discipline.getName());
        if (discipline.getAttendanceSettings() != null) {
            // entity.setAttendanceEnabled(discipline.getAttendanceSettings().getIsEnabled());
        }
        return entity;
    }

    private void saveLearningGroup(LearningGroup learningGroup) {
        org.example.entities.LearningGroup entity = new org.example.entities.LearningGroup();
        entity.setId(learningGroup.getId());
        entity.setTypename(learningGroup.getTypename());

        if (learningGroup.getLearningConfiguration() != null) {
            LearningConfiguration config = learningGroup.getLearningConfiguration();

            // Save Teachers
            if (config.getTeachers() != null && config.getTeachers().getItems() != null) {
                List<org.example.entities.Teacher> teachers = config.getTeachers().getItems().stream()
                        .map(this::mapToTeacherEntity)
                        .collect(Collectors.toList());
                teachers.forEach(t -> t.setLearningGroup(entity));
                entity.setTeachers(teachers);
            }

            // Save Lessons
            if (config.getLesson() != null && config.getLesson().getItems() != null) {
                List<org.example.entities.Lesson> lessons = config.getLesson().getItems().stream()
                        .map(this::mapToLessonEntity)
                        .collect(Collectors.toList());
                lessons.forEach(l -> l.setLearningGroup(entity));
                entity.setLessons(lessons);
            }
        }

        learningGroupRepository.save(entity);
        logger.info("Saved learning group with {} teachers and {} lessons",
                entity.getTeachers().size(), entity.getLessons().size());
    }

    private org.example.entities.Teacher mapToTeacherEntity(TeacherItem teacherItem) {
        org.example.entities.Teacher entity = new org.example.entities.Teacher();
        entity.setId(teacherItem.getId());
        // entity.setTeacherId(teacherItem.getTeacherId());
        // entity.setRoles(teacherItem.getRoles());
        // entity.setIsDisabled(teacherItem.getIsDisabled());
        // entity.setIsInteractiveLessons(teacherItem.getIsInteractiveLessons());
        // entity.setInheritedFromLearningConfigurationId(teacherItem.getInheritedFromLearningConfigurationId());

        if (teacherItem.getTeacher() != null) {
            entity.setUid(teacherItem.getTeacher().getUid());
            if (teacherItem.getTeacher().getHuman() != null) {
                HumanProfile human = teacherItem.getTeacher().getHuman();
                entity.setHumanFirstName(human.getFirstName());
                entity.setHumanMiddleName(human.getMiddleName());
                entity.setHumanLastName(human.getLastName());
                entity.setHumanId(human.getId());
            }
        }

        // if (teacherItem.getAcademicUnitIds() != null) {
        //     entity.setSelectionBehaviour(teacherItem.getAcademicUnitIds().getSelectionBehaviour());
        // }

        return entity;
    }

    private org.example.entities.Lesson mapToLessonEntity(org.example.graphql.models.Lesson lesson) {
        org.example.entities.Lesson entity = new org.example.entities.Lesson();
        // entity.setUid(lesson.getUid());
        entity.setId(lesson.getId());
        // entity.setTypename(lesson.getTypename());

        // if (lesson.getHomeWork() != null) {
        //     org.example.entities.HomeWork homeWork = mapToHomeWorkEntity(lesson.getHomeWork());
        //     homeWork.setLesson(entity);
        //     entity.setHomeWorks(List.of(homeWork));
        // }

        return entity;
    }

    private org.example.entities.HomeWork mapToHomeWorkEntity(HomeWork homeWork) {
        org.example.entities.HomeWork entity = new org.example.entities.HomeWork();
        // entity.setUid(homeWork.getUid());
        entity.setId(homeWork.getId());
        // entity.setTypename(homeWork.getTypename());

        // if (homeWork.getTasks() != null) {
        //     List<org.example.entities.Task> tasks = homeWork.getTasks().stream()
        //             .map(this::mapToTaskEntity)
        //             .collect(Collectors.toList());
        //     tasks.forEach(t -> t.setHomeWork(entity));
        //     entity.setTasks(tasks);
        // }

        return entity;
    }

    private org.example.entities.Task mapToTaskEntity(org.example.graphql.models.Task task) {
        org.example.entities.Task entity = new org.example.entities.Task();
        // entity.setUid(task.getUid());
        // entity.setProcessNote(task.getProcessNote());
        entity.setQuestion(task.getQuestion());
        if (task.getQuestionProse() != null) {
            entity.setQuestionJson(task.getQuestionProse().getJson());
            entity.setQuestionHtml(task.getQuestionProse().getHtml());
        }
        // if (task.getScores() != null) {
        //     entity.setScoresKind(task.getScores().getKind());
        //     entity.setMaxScoresAmount(task.getScores().getMaxScoresAmount());
        //     if (task.getScores().getPerQuestion() != null) {
        //         entity.setPerQuestionScoresAmount(task.getScores().getPerQuestion().getScoresAmount());
        //     }
        //     entity.setPartialAnswers(task.getScores().getPartialAnswers());
        // }
        // entity.setExtraRewards(task.getExtraRewards());
        entity.setKind(task.getKind());
        // entity.setAnswerParts(task.getAnswerParts());

        return entity;
    }
}
