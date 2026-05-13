package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicUnitIds {
    private List<AcademicDisciplineUnit> disciplines;
    private String selectionBehaviour;
    private FlattenUnitIds flattenUnitIds;
}

