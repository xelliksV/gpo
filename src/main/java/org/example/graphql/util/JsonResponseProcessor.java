package org.example.graphql.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graphql.models.GraphQLResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Utility class for working with GraphQL JSON responses
 */
public class JsonResponseProcessor {
    private static final Logger logger = LoggerFactory.getLogger(JsonResponseProcessor.class);
    private final ObjectMapper objectMapper;

    public JsonResponseProcessor() {
        this.objectMapper = new ObjectMapper();
        // Configure to ignore unknown properties
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Parse GraphQL response from JSON file
     * @param filePath path to JSON file
     * @return GraphQLResponse object
     */
    public GraphQLResponse parseJsonFile(String filePath) throws IOException {
        logger.info("Parsing JSON file: {}", filePath);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try {
            GraphQLResponse response = objectMapper.readValue(file, GraphQLResponse.class);
            logger.info("Successfully parsed JSON response");
            return response;
        } catch (IOException e) {
            logger.error("Error parsing JSON file", e);
            throw e;
        }
    }

    /**
     * Parse GraphQL response from JSON string
     * @param jsonString JSON string
     * @return GraphQLResponse object
     */
    public GraphQLResponse parseJsonString(String jsonString) throws IOException {
        try {
            return objectMapper.readValue(jsonString, GraphQLResponse.class);
        } catch (IOException e) {
            logger.error("Error parsing JSON string", e);
            throw e;
        }
    }

    /**
     * Convert object to JSON string
     * @param object object to convert
     * @return JSON string representation
     */
    public String toJsonString(Object object) throws IOException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}


