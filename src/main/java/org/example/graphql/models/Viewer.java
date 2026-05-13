package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Viewer {
    private List<AcademicDiscipline> academicDisciplines;
    private List<LearningGroup> learningGroups;
    private String id;
}
