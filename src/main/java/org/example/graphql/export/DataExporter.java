package org.example.graphql.export;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.graphql.models.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Export educational data to various formats
 */
public class DataExporter {
    private final ObjectMapper objectMapper;

    public DataExporter() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Export teachers list to JSON file
     */
    public void exportTeachersToJson(List<TeacherItem> teachers, String filePath) throws IOException {
        List<Map<String, Object>> teachersList = new ArrayList<>();

        for (TeacherItem item : teachers) {
            Map<String, Object> teacherMap = new LinkedHashMap<>();
            teacherMap.put("id", item.getId());
            teacherMap.put("teacherId", item.getTeacherId());
            teacherMap.put("roles", item.getRoles());
            teacherMap.put("disabled", item.isDisabled());

            if (item.getTeacher() != null && item.getTeacher().getHuman() != null) {
                HumanProfile human = item.getTeacher().getHuman();
                Map<String, Object> nameMap = new LinkedHashMap<>();
                nameMap.put("firstName", human.getFirstName());
                nameMap.put("middleName", human.getMiddleName());
                nameMap.put("lastName", human.getLastName());
                teacherMap.put("name", nameMap);
            }

            teachersList.add(teacherMap);
        }

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(filePath), teachersList);
    }

    /**
     * Export disciplines to CSV format
     */
    public void exportDisciplinesToCsv(List<AcademicDiscipline> disciplines, String filePath) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("UID,Name,ID,Attendance Enabled\n");

        for (AcademicDiscipline d : disciplines) {
            csv.append(d.getUid()).append(",");
            csv.append("\"").append(d.getName() != null ? d.getName() : "").append("\",");
            csv.append(d.getId()).append(",");
            csv.append(d.getAttendanceSettings() != null ? d.getAttendanceSettings().isEnabled() : false)
                    .append("\n");
        }

        java.nio.file.Files.write(java.nio.file.Paths.get(filePath), csv.toString().getBytes());
    }

    /**
     * Export homework summary
     */
    public void exportHomeworkSummary(List<HomeWork> homeworks, String filePath) throws IOException {
        List<Map<String, Object>> hwList = new ArrayList<>();

        for (HomeWork hw : homeworks) {
            Map<String, Object> hwMap = new LinkedHashMap<>();
            hwMap.put("id", hw.getId());
            hwMap.put("uid", hw.getUid());
            hwMap.put("taskCount", hw.getTasks() != null ? hw.getTasks().size() : 0);

            if (hw.getTasks() != null) {
                List<String> taskKinds = new ArrayList<>();
                for (Task task : hw.getTasks()) {
                    if (task.getKind() != null && !taskKinds.contains(task.getKind())) {
                        taskKinds.add(task.getKind());
                    }
                }
                hwMap.put("taskKinds", taskKinds);
            }

            hwList.add(hwMap);
        }

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(filePath), hwList);
    }
}


