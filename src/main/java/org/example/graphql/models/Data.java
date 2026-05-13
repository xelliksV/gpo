package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    private Viewer viewer;
    private LearningGroup learningGroup;
}

