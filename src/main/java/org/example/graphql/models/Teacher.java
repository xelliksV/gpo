package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private long uid;
    private HumanProfile human;
    private String id;
}

