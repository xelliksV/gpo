package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademicDiscipline {
    private long uid;
    private String name;
    private AttendanceSettings attendanceSettings;
    private String id;
}

