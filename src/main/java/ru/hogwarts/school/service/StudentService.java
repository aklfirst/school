package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class StudentService {

    //private final HashMap<Long, Student> students = new HashMap<>();

    //private long lastId = 0;
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public Student findStudentById (long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
      return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

   // public Collection<Student> checkDuplicate (Student student) {
   //       return students.entrySet().stream()
   //           .filter(e -> e.getValue().getName().equals(student.getName()))
   //         .filter(e -> e.getValue().getAge() == student.getAge())
   //       .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())).values();
   //}

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
