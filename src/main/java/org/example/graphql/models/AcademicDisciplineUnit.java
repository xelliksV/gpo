package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicDisciplineUnit {
    @JsonProperty("academicDisciplineId")
    private long academicDisciplineId;
    private List<String> unitIds;
}

