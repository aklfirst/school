package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service

public class StudentService {

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        }

    public Student createStudent(Student student){
        logger.debug("Calling method createStudent");
        student.setId(null);
        return studentRepository.save(student);
    }

    public Student findStudentById(long id) {
        logger.debug("Calling method findStudentById (studentId = {})", id);
        return studentRepository.findById(id).orElse(null);
    }

    public Student editStudent(Student student) {
        logger.debug("Calling method editStudent (studentId = {})", student.getId());
        if (studentRepository.findById(student.getId()).orElse(null) == null) {
            return null;
        }
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.debug("Calling method deleteStudent (studentId = {})", id);
        studentRepository.deleteById(id);
        }

    public Collection<Student> getStudentsByAge(int age) {
        logger.debug("Calling method getStudentsByAge (age = {})", age);
        return studentRepository.findByAge(age);
    }

    public ResponseEntity<Collection<Student>> getStudentsByAgeInRange(int ageMin, int ageMax) {
        logger.debug("Calling method findByAgeInRange (ageMin = {}, ageMax = {})", ageMin, ageMax);
        Collection<Student> studentsSelection = studentRepository.findByAgeBetween(ageMin,ageMax);
        return ResponseEntity.ok(studentsSelection);
    }

    public ResponseEntity<Collection<Student>> getAllStudents() {
        logger.info("Calling method to get all students");
        Collection<Student> studentsList = studentRepository.findAll();
        if (studentsList.isEmpty()) {
            logger.error("No students exist in DB!");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsList);
    }

    public String findStudentFaculty(Long id) {
        logger.debug("Calling method findStudentFaculty (studentId = {})", id);
        Student student = findStudentById(id);
        return student.getName() +": " + student.getFaculty();
    }

    public List<Student> getStudentsByIdLastFive() {
        logger.info("Calling method to get last 5 students");
        return studentRepository.getStudentsByIdLastFive();
    }

    public Integer getStudentsQty(){
        logger.info("Calling method to get all students quantity");
        return studentRepository.getStudentsQty();
    };

    public Double getAverageAgeStudents() {
        logger.info("Calling method to get average age of students");
        return studentRepository.getAverageAgeStudents();
    }

    public Student patchStudentAvatar(long studentId, long avatarId) {
        logger.debug("Calling method patchStudentAvatar (studentId = {}, avatarId = {})", studentId, avatarId);
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional <Avatar> optionalAvatar = avatarRepository.findById(avatarId);

        if(optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }
        if(optionalAvatar.isEmpty()) {
            throw new AvatarNotFoundException(avatarId);
        }
        Student student = optionalStudent.get();
        Avatar avatar = optionalAvatar.get();

        student.setAvatar(avatar);
        return studentRepository.save(student);
    }

}
