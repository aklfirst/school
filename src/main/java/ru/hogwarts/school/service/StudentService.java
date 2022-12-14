package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service

public class StudentService {

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

    public Collection<Student> getStudentsByAgeInRange(int ageMin, int ageMax) {
        return studentRepository.findByAgeBetween(ageMin,ageMax);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public String findStudentFaculty(Long id) {
        Student student = findStudentById(id);
        return student.getName() +": " + student.getFaculty();
    }

    public List<Student> getStudentsByIdLastFive() {
        return studentRepository.getStudentsByIdLastFive();
    }

    public Integer getStudentsQty(){
        return studentRepository.getStudentsQty();
    };

    public Double getAverageAgeStudents() {
        return studentRepository.getAverageAgeStudents();
    }

}
