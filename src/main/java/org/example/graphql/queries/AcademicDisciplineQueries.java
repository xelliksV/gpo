package org.example.graphql.queries;

public class AcademicDisciplineQueries {

    /**
     * Query to get all academic disciplines for the viewer
     */
    public static String GET_ACADEMIC_DISCIPLINES = """
            query {
              viewer {
                id
                academicDisciplines {
                  uid
                  id
                  name
                  attendanceSettings {
                    isEnabled
                  }
                }
              }
            }
            """;

    /**
     * Query to get viewer with full list of available disciplines
     */
    public static String GET_VIEWER_WITH_DISCIPLINES = """
            query {
              viewer {
                id
                academicDisciplines {
                  uid
                  id
                  name
                  attendanceSettings {
                    isEnabled
                  }
                }
              }
            }
            """;

    /**
     * Query to get full learning group configuration with disciplines, teachers, and lessons
     */
    public static String GET_FULL_LEARNING_GROUP = """
            query($groupId: ID!) {
              learningGroup(id: $groupId) {
                __typename
                id
                learningConfiguration {
                  syllabus {
                    disciplines {
                      academicDiscipline {
                        uid
                        name
                        id
                        attendanceSettings {
                          isEnabled
                        }
                      }
                    }
                  }
                  teachers {
                    items {
                      id
                      teacherId
                      roles
                      isDisabled
                      isInteractiveLessons
                      inheritedFromLearningConfigurationId
                      academicUnitIds {
                        disciplines {
                          academicDisciplineId
                          unitIds
                        }
                        selectionBehaviour
                        flattenUnitIds {
                          disciplines {
                            academicDisciplineId
                            unitIds
                          }
                        }
                      }
                      teacher {
                        uid
                        human {
                          firstName
                          middleName
                          lastName
                          id
                        }
                        id
                      }
                    }
                  }
                  lesson {
                    items {
                      uid
                      id
                      __typename
                      homeWork {
                        uid
                        id
                        __typename
                        tasks {
                          uid
                          processNote
                          question
                          questionProse {
                            json
                            html
                            extra {
                              videoIds
                              fileIds
                            }
                          }
                          scores {
                            kind
                            maxScoresAmount
                            perQuestion {
                              scoresAmount
                            }
                            partialAnswers
                          }
                          extraRewards
                          kind
                          answerParts
                          answersSingleFromTwo
                          answersSingleFromMultiple
                          answersMultipleFromMultiple
                          answersMatchPairs
                          answersWrite {
                            uid
                            index
                            options
                            commentCorrect
                            commentCorrectProse
                            commentIncorrect
                            commentIncorrectProse
                            rewards
                          }
                          answersFillBlanks
                          answersDistributeByGroups
                          answersWordsSequence
                          commentCorrect
                          commentCorrectProse {
                            json
                            html
                            extra {
                              videoIds
                              fileIds
                            }
                          }
                          commentIncorrect
                          commentIncorrectProse {
                            json
                            html
                            extra {
                              videoIds
                              fileIds
                            }
                          }
                          dialogSimulator
                          dragAndDropToArea
                          selectArea
                          interactiveVideo
                        }
                      }
                      interactiveLesson
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get all academic disciplines for the viewer with pagination
     */
    public static String GET_ACADEMIC_DISCIPLINES_PAGINATED = """
            query($first: Int!, $after: String) {
              viewer {
                id
                academicDisciplines(first: $first, after: $after) {
                  pageInfo {
                    hasNextPage
                    endCursor
                  }
                  edges {
                    node {
                      uid
                      id
                      name
                      attendanceSettings {
                        isEnabled
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get learning groups for academic discipline
     */
    public static String GET_LEARNING_GROUPS = """
            query($disciplineId: ID!) {
              academicDiscipline(id: $disciplineId) {
                id
                learningGroups {
                  id
                  lessons {
                    id
                    homeworks {
                      id
                      questions {
                        id
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get syllabus disciplines and teachers for a learning group
     */
    public static String GET_SYLLABUS_AND_TEACHERS = """
            query($groupId: ID!) {
              learningGroup(id: $groupId) {
                id
                learningConfiguration {
                  syllabus {
                    disciplines {
                      academicDiscipline {
                        uid
                        name
                        id
                        attendanceSettings {
                          isEnabled
                        }
                      }
                    }
                  }
                  teachers {
                    items {
                      id
                      teacherId
                      roles
                      isDisabled
                      teacher {
                        uid
                        human {
                          firstName
                          middleName
                          lastName
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get lessons and homeworks with tasks
     */
    public static String GET_LESSONS_WITH_TASKS = """
            query($groupId: ID!) {
              learningGroup(id: $groupId) {
                id
                learningConfiguration {
                  lesson {
                    items {
                      uid
                      id
                      homeWork {
                        uid
                        id
                        tasks {
                          uid
                          kind
                          question
                          questionProse {
                            json
                            html
                          }
                          scores {
                            kind
                            maxScoresAmount
                            perQuestion {
                              scoresAmount
                            }
                          }
                          answerParts
                          answersWrite {
                            uid
                            index
                            options
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get teacher details with academic units
     */
    public static String GET_TEACHER_UNITS = """
            query($groupId: ID!) {
              learningGroup(id: $groupId) {
                id
                learningConfiguration {
                  teachers {
                    items {
                      id
                      teacherId
                      roles
                      academicUnitIds {
                        disciplines {
                          academicDisciplineId
                          unitIds
                        }
                        selectionBehaviour
                        flattenUnitIds {
                          disciplines {
                            academicDisciplineId
                            unitIds
                          }
                        }
                      }
                      teacher {
                        uid
                        human {
                          firstName
                          middleName
                          lastName
                          id
                        }
                      }
                    }
                  }
                }
              }
            }
            """;

    /**
     * Query to get all data in one request (similar to the JSON file)
     * Simplified version - only viewer data since learning group access fails
     */
    public static String GET_ALL_DATA = """
            query {
              viewer {
                id
                academicDisciplines {
                  uid
                  id
                  name
                  attendanceSettings {
                    isEnabled
                  }
                }
              }
            }
            """;

    /**
     * Simple test query to check if learning group exists
     */
    public static String TEST_LEARNING_GROUP = """
            query {
              learningGroup(id: "TGVhcm5pbmdHcm91cDoyNjc5NTI=") {
                id
                __typename
              }
            }
            """;

    /**
     * Query to get user's learning groups
     */
    public static String GET_USER_LEARNING_GROUPS = """
            query {
              viewer {
                id
                learningGroups {
                  id
                  name
                  __typename
                }
              }
            }
            """;
    public static String query = "query GetLearningGroupData {\n" +
            "  viewer {\n" +
            "    academicDisciplines {\n" +
            "      uid\n" +
            "      id\n" +
            "    }\n" +
            "    id\n" +
            "  }\n" +
            "  learningGroups {\n" +
            "    __typename\n" +
            "    id\n" +
            "    learningConfiguration {\n" +
            "      id\n" +
            "      syllabus {\n" +
            "        disciplines {\n" +
            "          academicDiscipline {\n" +
            "            uid\n" +
            "            name\n" +
            "            id\n" +
            "            attendanceSettings {\n" +
            "              isEnabled\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "      teachers {\n" +
            "        items {\n" +
            "          id\n" +
            "          teacherId\n" +
            "          roles\n" +
            "          isDisabled\n" +
            "          isInteractiveLessons\n" +
            "          inheritedFromLearningConfigurationId\n" +
            "          academicUnitIds {\n" +
            "            disciplines {\n" +
            "              academicDisciplineId\n" +
            "              unitIds\n" +
            "            }\n" +
            "            selectionBehaviour\n" +
            "            flattenUnitIds {\n" +
            "              disciplines {\n" +
            "                academicDisciplineId\n" +
            "                unitIds\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "          teacher {\n" +
            "            uid\n" +
            "            id\n" +
            "            human {\n" +
            "              firstName\n" +
            "              middleName\n" +
            "              lastName\n" +
            "              id\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "      attendance {\n" +
            "        periods\n" +
            "      }\n" +
            "    }\n" +
            "    attendance {\n" +
            "      learningDisciplineWithStudentIds {\n" +
            "        learningDisciplineId\n" +
            "        learningStudentIds\n" +
            "      }\n" +
            "      learningAttendancePeriodRecords\n" +
            "      academicLearningLessons {\n" +
            "        uid {\n" +
            "          academicLessonId\n" +
            "          interactiveLessonId\n" +
            "        }\n" +
            "        learningDisciplineLessons {\n" +
            "          studentIds\n" +
            "          learningDisciplineLessonId\n" +
            "          userRights\n" +
            "          attendanceRecord {\n" +
            "            learningDisciplineId\n" +
            "            learningDisciplineLessonId\n" +
            "            statusId\n" +
            "            gradeLesson\n" +
            "            gradeHomeworksAssignment\n" +
            "            gradeHomeworksExamination\n" +
            "            gradeHomeworksIndependentWork\n" +
            "            isAbonnementUsed\n" +
            "            commentTeacher\n" +
            "            commentGroupDirector\n" +
            "            commentTutor\n" +
            "            updatedAt\n" +
            "            deletedAt\n" +
            "            createdAt\n" +
            "            uid\n" +
            "            id\n" +
            "            status {\n" +
            "              name\n" +
            "              iconKind\n" +
            "              color\n" +
            "              isStatsTableColumnDisplayed\n" +
            "              state\n" +
            "              kind\n" +
            "              orderIndex\n" +
            "              deletedAt\n" +
            "              orgId\n" +
            "              uid\n" +
            "            }\n" +
            "          }\n" +
            "          lesson {\n" +
            "            learningDisciplineId\n" +
            "            id\n" +
            "            discipline {\n" +
            "              masterClientId\n" +
            "              id\n" +
            "              human {\n" +
            "                firstName\n" +
            "                middleName\n" +
            "                lastName\n" +
            "                id\n" +
            "              }\n" +
            "            }\n" +
            "            homeworks {\n" +
            "              uid\n" +
            "              academicHomeworkId\n" +
            "              grade\n" +
            "              scores\n" +
            "              progressStatus\n" +
            "              gradedResult {\n" +
            "                grade\n" +
            "                scores\n" +
            "                status\n" +
            "                id\n" +
            "                quiz {\n" +
            "                  activeQuestionId\n" +
            "                  questionAnswers {\n" +
            "                    uid\n" +
            "                    answerSelectedIds\n" +
            "                    answerMatchPairs\n" +
            "                    answerWrites {\n" +
            "                      index\n" +
            "                      answer\n" +
            "                    }\n" +
            "                    answerFillBlanks\n" +
            "                    answerTextMistakes\n" +
            "                    answerTextMistakeState {\n" +
            "                      prosemirrorState\n" +
            "                      answers\n" +
            "                    }\n" +
            "                    answerDistributeByGroups\n" +
            "                    answerWordsSequence\n" +
            "                    answerSelectArea\n" +
            "                    answerDragAndDropToArea\n" +
            "                    isCorrect\n" +
            "                    isPickedFromPreviousResults\n" +
            "                    scores\n" +
            "                    rewards\n" +
            "                  }\n" +
            "                  questionIds\n" +
            "                  isPreventProgressWorkflow\n" +
            "                  timeLimitAt\n" +
            "                  quizVersion\n" +
            "                  quizProblemVersion\n" +
            "                }\n" +
            "                id\n" +
            "              }\n" +
            "              id\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "        academicLearningLesson {\n" +
            "          uid {\n" +
            "            academicLessonId\n" +
            "            interactiveLessonId\n" +
            "          }\n" +
            "          academicDiscipline {\n" +
            "            uid\n" +
            "            name\n" +
            "            id\n" +
            "            attendanceSettings {\n" +
            "              isEnabled\n" +
            "            }\n" +
            "          }\n" +
            "          academicLesson {\n" +
            "            uid\n" +
            "            academicDisciplineId\n" +
            "            indexKey\n" +
            "            orderFullIndex\n" +
            "            name\n" +
            "            id\n" +
            "            homeworks {\n" +
            "              id\n" +
            "              problemResolved {\n" +
            "                uid\n" +
            "                name\n" +
            "                indexKeyInternal\n" +
            "                kind\n" +
            "                id\n" +
            "                details {\n" +
            "                  __typename\n" +
            "                  ... on AcademicHomeworkQuizDetails {\n" +
            "                    version\n" +
            "                    grade {\n" +
            "                      positiveGradeBorder\n" +
            "                      scoresToGradeTransformRanges {\n" +
            "                        items {\n" +
            "                          grade\n" +
            "                          minScores\n" +
            "                        }\n" +
            "                      }\n" +
            "                      isDisplayMaxGradeFromTransformRanges\n" +
            "                    }\n" +
            "                    timeLimit {\n" +
            "                      unit\n" +
            "                      amount\n" +
            "                    }\n" +
            "                    attemptsCountLimit\n" +
            "                    questionsCountLimit\n" +
            "                    displayCorrectAnswers\n" +
            "                    retryQuestionsBehaviour\n" +
            "                    answersIndexLabelKind\n" +
            "                    isGradable\n" +
            "                    isTreatScoresAsGrades\n" +
            "                    isDoNotDisplayGrade\n" +
            "                    isRandomQuestionsOrder\n" +
            "                    isRandomAnswersOrder\n" +
            "                    isDisplayCompletedResultsToStudent\n" +
            "                    isInline\n" +
            "                    isDoNotDisplayFirstSlide\n" +
            "                    isDoNotDisplayLastSlide\n" +
            "                    isDisplayAllQuestionsTogether\n" +
            "                    isAnswersAnyOrderAllowed\n" +
            "                    slides {\n" +
            "                      openingSlide {\n" +
            "                        name\n" +
            "                        body\n" +
            "                        bodyProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                      }\n" +
            "                      closingSlide {\n" +
            "                        options {\n" +
            "                          name\n" +
            "                          body\n" +
            "                          bodyProse {\n" +
            "                            json\n" +
            "                            html\n" +
            "                            extra\n" +
            "                          }\n" +
            "                          minGradeScoreToBeDisplayed\n" +
            "                          bgImage\n" +
            "                        }\n" +
            "                      }\n" +
            "                      questions {\n" +
            "                        uid\n" +
            "                        processNote\n" +
            "                        question\n" +
            "                        questionProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        scores {\n" +
            "                          kind\n" +
            "                          maxScoresAmount\n" +
            "                          perQuestion {\n" +
            "                            scoresAmount\n" +
            "                          }\n" +
            "                          partialAnswers\n" +
            "                        }\n" +
            "                        extraRewards\n" +
            "                        kind\n" +
            "                        answerParts\n" +
            "                        answersWrite {\n" +
            "                          uid\n" +
            "                          index\n" +
            "                          options\n" +
            "                          commentCorrect\n" +
            "                          commentCorrectProse\n" +
            "                          commentIncorrect\n" +
            "                          commentIncorrectProse\n" +
            "                          rewards\n" +
            "                        }\n" +
            "                        answersFillBlanks\n" +
            "                        answersDistributeByGroups\n" +
            "                        answersWordsSequence\n" +
            "                        commentCorrect\n" +
            "                        commentCorrectProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        commentIncorrect\n" +
            "                        commentIncorrectProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        dialogSimulator\n" +
            "                        dragAndDropToArea\n" +
            "                        selectArea\n" +
            "                        interactiveVideo\n" +
            "                        answersSingleFromTwo\n" +
            "                        answersSingleFromMultiple\n" +
            "                        answersMultipleFromMultiple\n" +
            "                        answersMatchPairs\n" +
            "                      }\n" +
            "                    }\n" +
            "                  }\n" +
            "                  ... on AcademicHomeworkSimpleDetails {\n" +
            "                    __typename\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "          interactiveLesson {\n" +
            "            uid\n" +
            "            academicDisciplineId\n" +
            "            startsAt\n" +
            "            finishesAt\n" +
            "            indexKey\n" +
            "            name\n" +
            "            id\n" +
            "            teachers {\n" +
            "              uid\n" +
            "              id\n" +
            "              human {\n" +
            "                firstName\n" +
            "                middleName\n" +
            "                lastName\n" +
            "                id\n" +
            "              }\n" +
            "            }\n" +
            "            homeworks {\n" +
            "              id\n" +
            "              problemResolved {\n" +
            "                uid\n" +
            "                name\n" +
            "                indexKeyInternal\n" +
            "                kind\n" +
            "                id\n" +
            "                details {\n" +
            "                  __typename\n" +
            "                  ... on AcademicHomeworkQuizDetails {\n" +
            "                    version\n" +
            "                    grade {\n" +
            "                      positiveGradeBorder\n" +
            "                      scoresToGradeTransformRanges {\n" +
            "                        items {\n" +
            "                          grade\n" +
            "                          minScores\n" +
            "                        }\n" +
            "                      }\n" +
            "                      isDisplayMaxGradeFromTransformRanges\n" +
            "                    }\n" +
            "                    timeLimit {\n" +
            "                      unit\n" +
            "                      amount\n" +
            "                    }\n" +
            "                    attemptsCountLimit\n" +
            "                    questionsCountLimit\n" +
            "                    displayCorrectAnswers\n" +
            "                    retryQuestionsBehaviour\n" +
            "                    answersIndexLabelKind\n" +
            "                    isGradable\n" +
            "                    isTreatScoresAsGrades\n" +
            "                    isDoNotDisplayGrade\n" +
            "                    isRandomQuestionsOrder\n" +
            "                    isRandomAnswersOrder\n" +
            "                    isDisplayCompletedResultsToStudent\n" +
            "                    isInline\n" +
            "                    isDoNotDisplayFirstSlide\n" +
            "                    isDoNotDisplayLastSlide\n" +
            "                    isDisplayAllQuestionsTogether\n" +
            "                    isAnswersAnyOrderAllowed\n" +
            "                    slides {\n" +
            "                      openingSlide {\n" +
            "                        name\n" +
            "                        body\n" +
            "                        bodyProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                      }\n" +
            "                      closingSlide {\n" +
            "                        options {\n" +
            "                          name\n" +
            "                          body\n" +
            "                          bodyProse {\n" +
            "                            json\n" +
            "                            html\n" +
            "                            extra\n" +
            "                          }\n" +
            "                          minGradeScoreToBeDisplayed\n" +
            "                          bgImage\n" +
            "                        }\n" +
            "                      }\n" +
            "                      questions {\n" +
            "                        uid\n" +
            "                        processNote\n" +
            "                        question\n" +
            "                        questionProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        scores {\n" +
            "                          kind\n" +
            "                          maxScoresAmount\n" +
            "                          perQuestion {\n" +
            "                            scoresAmount\n" +
            "                          }\n" +
            "                          partialAnswers\n" +
            "                        }\n" +
            "                        extraRewards\n" +
            "                        kind\n" +
            "                        answerParts\n" +
            "                        answersWrite {\n" +
            "                          uid\n" +
            "                          index\n" +
            "                          options\n" +
            "                          commentCorrect\n" +
            "                          commentCorrectProse\n" +
            "                          commentIncorrect\n" +
            "                          commentIncorrectProse\n" +
            "                          rewards\n" +
            "                        }\n" +
            "                        answersFillBlanks\n" +
            "                        answersDistributeByGroups\n" +
            "                        answersWordsSequence\n" +
            "                        commentCorrect\n" +
            "                        commentCorrectProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        commentIncorrect\n" +
            "                        commentIncorrectProse {\n" +
            "                          json\n" +
            "                          html\n" +
            "                          extra\n" +
            "                        }\n" +
            "                        dialogSimulator\n" +
            "                        dragAndDropToArea\n" +
            "                        selectArea\n" +
            "                        interactiveVideo\n" +
            "                        answersSingleFromTwo\n" +
            "                        answersSingleFromMultiple\n" +
            "                        answersMultipleFromMultiple\n" +
            "                        answersMatchPairs\n" +
            "                      }\n" +
            "                    }\n" +
            "                  }\n" +
            "                  ... on AcademicHomeworkSimpleDetails {\n" +
            "                    __typename\n" +
            "                  }\n" +
            "                }\n" +
            "              }\n" +
            "            }\n" +
            "            attendanceSettings {\n" +
            "              lessonGradeLogic\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
