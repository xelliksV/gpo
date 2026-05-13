package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicLesson {
    private String uid;
    private String id;
    private String __typename;
    private HomeWork homeWork;
    private Object interactiveLesson;
}

