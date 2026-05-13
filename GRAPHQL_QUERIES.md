# GraphQL Запросы для образовательной системы

## Структура данных

### Основные типы:
- **Viewer** - пользователь системы с списком доступных дисциплин
- **LearningGroup** - учебная группа с конфигурацией
- **LearningConfiguration** - конфигурация группы (программа, учителя, уроки)
- **Syllabus** - учебная программа с предметами
- **Teachers** - список учителей
- **Lesson** - уроки и домашние задания
- **Task** - задачи в домашних заданиях

---

## 1. GET_ACADEMIC_DISCIPLINES - Получить все предметы пользователя

```graphql
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
```

**Описание:** Получает список всех академических дисциплин, доступных текущему пользователю.

**Возвращает:**
- `uid` - уникальный номер дисциплины
- `id` - закод��рованный ID (Base64)
- `name` - название дисциплины
- `attendanceSettings.isEnabled` - включена ли отметка посещения

---

## 2. GET_VIEWER_WITH_DISCIPLINES - Информация о пользователе с дисциплинами

```graphql
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
```

**Описание:** То же самое что GET_ACADEMIC_DISCIPLINES

---

## 3. GET_FULL_LEARNING_GROUP - Полные данные учебной группы

```graphql
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
```

**Переменные:**
```json
{
  "groupId": "TGVhcm5pbmdHcm91cDoyNjc5NTI="
}
```

**Описание:** Получает ВСЕ д��нные учебной группы: программа, учителя, уроки, задачи.

---

## 4. GET_ACADEMIC_DISCIPLINES_PAGINATED - Дисциплины с пагинацией

```graphql
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
```

**Переменные:**
```json
{
  "first": 10,
  "after": null
}
```

**Описание:** Получает дисциплины с пагинацией (по 10 штук).

---

## 5. GET_SYLLABUS_AND_TEACHERS - Программа и учителя

```graphql
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
```

**Переменные:**
```json
{
  "groupId": "TGVhcm5pbmdHcm91cDoyNjc5NTI="
}
```

**Описание:** Получает учебную программу и список у��ителей для группы.

---

## 6. GET_LESSONS_WITH_TASKS - Уроки с задачами

```graphql
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
```

**Переменные:**
```json
{
  "groupId": "TGVhcm5pbmdHcm91cDoyNjc5NTI="
}
```

**Описание:** Получает все уроки и связанные с ними домашние задания.

---

## 7. GET_TEACHER_UNITS - Учебные единицы учителей

```graphql
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
```

**Переменные:**
```json
{
  "groupId": "TGVhcm5pbmdHcm91cDoyNjc5NTI="
}
```

**Описание:** Получает информацию об учебных единицах, назначенных каждому учителю.

---

## 8. GET_LEARNING_GROUPS - Учебные группы по дисциплине

```graphql
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
```

**Переменные:**
```json
{
  "disciplineId": "QWNhZGVtaWNEaXNjaXBsaW5lOjM3MzE3"
}
```

**Описание:** Получает все учебные группы для конкретной дисциплины.

---

## Примеры использования в Java

### Запуск с использованием GraphQLServerDataFetcher

```java
// Инициализация клие��та
String endpoint = "https://api.soholms.com/master/graphql";
GraphQLServerDataFetcher fetcher = new GraphQLServerDataFetcher(endpoint);

// Получить все дисциплины
fetcher.fetchAcademicDisciplines();

// Получить программу и учителей
String learningGroupId = "TGVhcm5pbmdHcm91cDoyNjc5NTI=";
fetcher.fetchSyllabusAndTeachers(learningGroupId);

// Получить уроки с задачами
fetcher.fetchLessonsWithTasks(learningGroupId);

// Получить информацию об учебных единицах
fetcher.fetchTeacherUnits(learningGroupId);

// Получить все данные в одном запросе
fetcher.fetchFullLearningGroup(learningGroupId);

// Получить дисциплины с пагинацией
fetcher.fetchPaginatedDisciplines(10, null);
```

### Прямое использование GraphQLClient

```java
GraphQLClient client = new GraphQLClient("https://api.soholms.com/master/graphql");

// Выполнить запрос без переменных
GraphQLResponse response = client.executeQuery(
    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES
);

// Выполнить запрос с переменными
Map<String, Object> variables = new HashMap<>();
variables.put("groupId", "TGVhcm5pbmdHcm91cDoyNjc5NTI=");

GraphQLResponse response = client.executeQuery(
    AcademicDisciplineQueries.GET_FULL_LEARNING_GROUP,
    variables
);

// Получить данные
Data data = response.getData();
if (data.getViewer() != null) {
    System.out.println("Disciplines: " + 
        data.getViewer().getAcademicDisciplines().size());
}
```

---

## ��труктура ответа

Все запросы возвращают объект `GraphQLResponse`:

```java
public class GraphQLResponse {
    private Data data;
}

public class Data {
    private Viewer viewer;
    private LearningGroup learningGroup;
}
```

---

## Примечания

1. **Авторизация**: Токен авторизации передается в заголовке `Authorization: Bearer {token}` (проверьте GraphQLClient)
2. **Пагинация**: Используйте `first` и `after` для получения данных с пагинацией
3. **Переменные**: Все динамические значения передаются как переменные (не в самом запросе)
4. **Кодирование ID**: ID в GraphQL часто закодированы в Base64
5. **Nullable поля**: Указывайте только нужные вам поля, чтобы оптимизировать запросы

---

## Компиляция и запуск

```bash
# Компилировать проект
mvn clean compile

# Запустить GraphQL Server Data Fetcher
mvn exec:java -Dexec.mainClass="org.example.GraphQLServerDataFetcher"

# Запустить Advanced Processor
mvn exec:java -Dexec.mainClass="org.example.AdvancedGraphQLProcessor"

# Запустить JSON Analyzer
mvn exec:java -Dexec.mainClass="org.example.JsonAnalyzerApp"
```


