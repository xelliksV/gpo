package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningConfiguration {
    private Syllabus syllabus;
    private Teachers teachers;
    private Lesson lesson;
}

