# GraphQL Client для системы управления учебными материалами

Этот проект содержит GraphQL клиент на Java для получения данных об учебных дисциплинах и связанных с ними материалах.

## Структура проекта

```
src/main/java/org/example/
├── graphql/
│   ├── client/
│   │   └── GraphQLClient.java          # Основной GraphQL клиент
│   ├── models/
│   │   ├── AcademicDiscipline.java     # Модель учебной дисциплины
│   │   ├── Data.java                   # Контейнер для данных
│   │   ├── Viewer.java                 # Модель пользователя
│   │   └── GraphQLResponse.java        # Модель GraphQL ответа
│   └── queries/
│       └── AcademicDisciplineQueries.java # Готовые GraphQL запросы
└── GraphQLClientExample.java           # Пример использования
```

## Зависимости

- **graphql-java** (v20.3) - Библиотека для работы с GraphQL
- **okhttp3** (v4.11.0) - HTTP клиент для выполнения запросов
- **gson** (v2.10.1) - Библиотека для сериализации/десериализации JSON
- **slf4j** (v2.0.7) - Логирование

## Использование

### Базовое использование

```java
import org.example.graphql.client.GraphQLClient;
import org.example.graphql.models.GraphQLResponse;
import org.example.graphql.models.Data;
import org.example.graphql.queries.AcademicDisciplineQueries;

// Создание клиента
GraphQLClient client = new GraphQLClient("https://api.example.com/graphql");

// Выполнение запроса
GraphQLResponse<Data> response = client.executeQuery(
    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES
);

// Обработка результата
if (!response.hasErrors()) {
    Data data = response.getData();
    data.getViewer().getAcademicDisciplines().forEach(discipline ->
        System.out.println("Дисциплина: " + discipline.getUid())
    );
}
```

### Использование переменных в запросе

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Object> variables = new HashMap<>();
variables.put("first", 10);  // Первые 10 элементов
variables.put("after", "cursor_value");  // После курсора для пагинации

GraphQLResponse<Data> response = client.executeQuery(
    AcademicDisciplineQueries.GET_ACADEMIC_DISCIPLINES_PAGINATED,
    variables
);
```

### Обработка ошибок

```java
try {
    GraphQLResponse<Data> response = client.executeQuery(query);
    
    if (response.hasErrors()) {
        System.err.println("Ошибки GraphQL: " + response.getErrors());
    } else {
        // Обработка данных
        System.out.println(response.getData());
    }
} catch (IOException e) {
    System.err.println("Ошибка сети: " + e.getMessage());
}
```

## Доступные запросы

### 1. GET_ACADEMIC_DISCIPLINES
Получает все учебные дисциплины для текущего пользователя.

```graphql
query {
  viewer {
    id
    academicDisciplines {
      uid
      id
    }
  }
}
```

### 2. GET_ACADEMIC_DISCIPLINES_PAGINATED
Получает учебные дисциплины с поддержкой пагинации.

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
        }
      }
    }
  }
}
```

### 3. GET_LEARNING_GROUPS
Получает группы обучения для выбранной дисциплины со всеми связанными данными.

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

## Структура данных JSON

Клиент десериализует ответ JSON в следующую иерархию объектов:

```
GraphQLResponse
├── data
│   └── viewer
│       ├── id
│       └── academicDisciplines[] (List)
│           ├── uid
│           └── id
└── errors (если есть)
```

## Сборка и запуск

### Сборка проекта
```bash
mvn clean install
```

### Запуск примера
```bash
mvn exec:java -Dexec.mainClass="org.example.GraphQLClientExample"
```

### Запуск тестов
```bash
mvn test
```

## Расширение функциональности

### Добавление новых моделей

Если нужно получить больше данных (например, полную информацию о занятиях), создайте новые модели:

```java
public class Lesson {
    private String id;
    private String title;
    private List<Homework> homeworks;
    // getters/setters...
}
```

### Добавление новых запросов

Добавьте новые запросы в `AcademicDisciplineQueries.java`:

```java
public static String GET_LESSONS_WITH_DETAILS = """
    query($lessonId: ID!) {
      lesson(id: $lessonId) {
        id
        title
        description
        homeworks {
          id
          title
        }
      }
    }
    """;
```

## Особенности реализации

- **Асинхронные запросы**: Используются синхронные запросы OkHttp, но могут быть легко адаптированы для асинхронных
- **Логирование**: Все запросы и ответы логируются через SLF4J
- **Обработка ошибок**: Поддержка обработки ошибок GraphQL и сетевых ошибок
- **Типизация**: Полная типизация данных через POJO классы
- **Гибкость**: Поддержка переменных в запросах для динамических значений

## Лицензия

MIT

