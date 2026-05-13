package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherItem {
    private String id;
    private long teacherId;
    private List<String> roles;
    private boolean isDisabled;
    private boolean isInteractiveLessons;
    private String inheritedFromLearningConfigurationId;
    private AcademicUnitIds academicUnitIds;
    private Teacher teacher;
}

