package org.example.graphql.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningGroup {
    @JsonProperty("__typename")
    private String typename;
    private LearningConfiguration learningConfiguration;
    private String id;
    private String name;
}
