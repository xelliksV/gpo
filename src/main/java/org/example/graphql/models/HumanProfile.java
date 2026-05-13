package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HumanProfile {
    private String firstName;
    private String middleName;
    private String lastName;
    private String id;
}

