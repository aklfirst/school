package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class FacultyService {

    private final HashMap<Long, Faculty> faculties = new HashMap<>();

    private long facultyId = 0;

    public Faculty createFaculty(Faculty faculty){
        faculty.setId(++facultyId);
        if (checkDuplicateFaculty(faculty).isEmpty()) {
            faculties.put(facultyId,faculty);
            return faculty;
        }
        return null;
    }

    public Faculty findFacultyById (long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        if(faculties.containsKey(faculty.getId())) {
            faculties.put(faculty.getId(),faculty);
            return faculty;
        }
        return null;
    }

    public Faculty deleteFaculty(long id) {
        faculties.remove(id);
        return faculties.get(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties.entrySet().stream()
                .filter(e -> e.getValue().getColor().equals(color))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }


    public Collection<Faculty> checkDuplicateFaculty (Faculty faculty) {
        return faculties.entrySet().stream()
                .filter(e -> e.getValue().getName().equals(faculty.getName()))
                .filter(e -> e.getValue().getColor().equals(faculty.getColor()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())).values();
    }

}
