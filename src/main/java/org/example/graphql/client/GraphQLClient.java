package org.example.graphql.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.example.graphql.models.Data;
import org.example.graphql.models.GraphQLResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphQLClient {
    private static final Logger logger = LoggerFactory.getLogger(GraphQLClient.class);
    private final String endpoint;
    private final OkHttpClient httpClient;
    private final ObjectMapper mapper;

    public GraphQLClient(String endpoint) {
        this.endpoint = endpoint;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        this.mapper = new ObjectMapper();
    }

    /**
     * Execute a GraphQL query and return the response
     *
     * @param query GraphQL query string
     * @return GraphQLResponse with data and errors
     */
    public GraphQLResponse executeQuery(String query) throws IOException {
        return executeQuery(query, null);
    }

    /**
     * Execute a GraphQL query with variables
     *
     * @param query     GraphQL query string
     * @param variables Variables map for the query
     * @return GraphQLResponse with data and errors
     */
    public GraphQLResponse executeQuery(String query, Map<String, Object> variables) throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", query);
        if (variables != null) {
            requestBody.put("variables", variables);
        }
        String jsonBody = mapper.writeValueAsString(requestBody);
        logger.debug("Sending GraphQL request to: {}", endpoint);
        logger.debug("Request body: {}", jsonBody);

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(endpoint)
                .post(body)
                .addHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJsaWJpY3JhZnQiLCJzdWIiOiJpZCIsImV4cCI6MTgwNzg4ODM2NiwiaWF0IjoxNzc2MzUyMzY2LCJpZCI6IjMxNTY0MTQiLCJhdXRoSWQiOiIwIiwiZXBvY2giOiIwIiwib3JnSWQiOiI5NDM2NzEifQ.avTbdHMHLEPWFLhxGkBkZIxkb6peJYio2EQnP55Gp0gD8qH5rzNsQRfjA92oIucgdj23R2nMvWlt6DRypva8SQ")
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.code() + ": " + response.message());
            }

            String responseBody = response.body().string();
            logger.debug("Response: {}", responseBody);
            System.out.println(responseBody);
            return mapper.readValue(responseBody, GraphQLResponse.class);
        }
    }

}

