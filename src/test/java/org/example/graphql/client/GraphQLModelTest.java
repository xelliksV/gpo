package org.example.graphql.client;

import org.example.graphql.models.AcademicDiscipline;
import org.example.graphql.models.Data;
import org.example.graphql.models.GraphQLResponse;
import org.example.graphql.models.Viewer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for GraphQL models
 */
public class GraphQLModelTest {

    private AcademicDiscipline discipline;
    private Viewer viewer;
    private Data data;
    private GraphQLResponse<Data> response;

    @Before
    public void setUp() {
        // Setup test data
        discipline = new AcademicDiscipline(123L, "test-id");

        List<AcademicDiscipline> disciplines = new ArrayList<>();
        disciplines.add(discipline);

        viewer = new Viewer(disciplines, "viewer-id");
        data = new Data(viewer);
        response = new GraphQLResponse<>(data, null);
    }

    @Test
    public void testAcademicDisciplineCreation() {
        assertEquals(123L, discipline.getUid());
        assertEquals("test-id", discipline.getId());
    }

    @Test
    public void testAcademicDisciplineSetters() {
        discipline.setUid(456L);
        discipline.setId("new-id");

        assertEquals(456L, discipline.getUid());
        assertEquals("new-id", discipline.getId());
    }

    @Test
    public void testViewerCreation() {
        assertNotNull(viewer.getAcademicDisciplines());
        assertEquals(1, viewer.getAcademicDisciplines().size());
        assertEquals("viewer-id", viewer.getId());
    }

    @Test
    public void testDataCreation() {
        assertNotNull(data.getViewer());
        assertEquals("viewer-id", data.getViewer().getId());
    }

    @Test
    public void testGraphQLResponseWithoutErrors() {
        assertFalse(response.hasErrors());
        assertNotNull(response.getData());
        assertNull(response.getErrors());
    }

    @Test
    public void testGraphQLResponseWithErrors() {
        GraphQLResponse<Data> errorResponse = new GraphQLResponse<>(null, "Error occurred");
        assertTrue(errorResponse.hasErrors());
        assertEquals("Error occurred", errorResponse.getErrors());
    }

    @Test
    public void testDisciplineToString() {
        String str = discipline.toString();
        assertTrue(str.contains("123"));
        assertTrue(str.contains("test-id"));
    }
}

