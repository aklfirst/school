package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();

    private long lastId = 0;

    public Student createStudent(Student student){
        student.setId(++lastId);
        if (checkDuplicate(student).isEmpty()) {
            students.put(lastId,student);
            return student;
            }
        return null;
    }

    public Student findStudentById (long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if(students.containsKey(student.getId())) {
            students.put(student.getId(),student);
            return student;
       }
        return null;
    }

    public Student deleteStudent(long id) {
        students.remove(id);
        return students.get(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.entrySet().stream()
                .filter(e -> e.getValue().getAge() == age)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).values();
    }

    public Collection<Student> checkDuplicate (Student student) {
            return students.entrySet().stream()
                .filter(e -> e.getValue().getName().equals(student.getName()))
                .filter(e -> e.getValue().getAge() == student.getAge())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())).values();
    }

}
