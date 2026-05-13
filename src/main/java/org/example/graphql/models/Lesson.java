package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private List<AcademicLesson> items;
    private String id;
}

