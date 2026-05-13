package org.example.graphql.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProsaContent {
    private String json;
    private String html;
    private ContentExtra extra;
}

