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
import java.util.stream.Collectors;

@Service

public class StudentService {

    private final StudentRepository studentRepository;

    private final AvatarRepository avatarRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

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

    public List<String> getStudentsByNameStartsWith(String letter) {
        logger.debug("Calling method to get students by letter (letter = {})", letter);
        return studentRepository.findAll().stream()
                .map(user -> user.getName())
                .filter(s -> s.startsWith(letter))
                .sorted((s1, s2) -> s1.compareTo(s2))
                .map(s -> s.toUpperCase())
                .collect(Collectors.toList());
    }

    public Double getAverageAgeWithStream() {
        logger.info("Calling method to get average age of students with stream");
        return studentRepository.findAll().stream()
                .mapToDouble(user -> user.getAge())
                .average()
                .orElse(Double.NaN);
    }

    //public void printStudentsNonSyncronizedThread(Integer mainThreadQty, Integer secondThreadQty) {
        //logger.info("Calling method to get list of students in non-syncronized threads");
        //List<Student> students = studentRepository.findAll();
        //for (int i = 0; i < mainThreadQty; i++) {
        //    System.out.println("Student # " + i + " " + students.get(i).getId() + " " + students.get(i).getName());
        //}

        //new Thread(() -> {
        //    for (int i = mainThreadQty; i < (mainThreadQty + secondThreadQty); i++) {
        //        System.out.println("Student # " + i + " " + students.get(i).getId() + " " + students.get(i).getName());
        //    }
        //}).start();

        //new Thread(() -> {
        //    for (int i = (mainThreadQty + secondThreadQty); i < students.size(); i++) {
        //        System.out.println("Student # " + i + " " + students.get(i).getId() + " " + students.get(i).getName());
        //    }
        //}).start();}

    public void printStudentsNonSyncronizedThread() {
        logger.info("Calling method to get list of students in non-syncronized threads");
        List<String> studentNames = studentRepository.findAll().stream()
                .map(Student::getName).toList();

        printNonSyncronized(studentNames.get(0));
        printNonSyncronized(studentNames.get(1));

        new Thread(() -> {
            printNonSyncronized(studentNames.get(4));
            printNonSyncronized(studentNames.get(5));
        }).start();

        new Thread(() -> {
            printNonSyncronized(studentNames.get(2));
            printNonSyncronized(studentNames.get(3));
            printNonSyncronized(studentNames.get(6));
            printNonSyncronized(studentNames.get(7));
            printNonSyncronized(studentNames.get(8));
            printNonSyncronized(studentNames.get(9));
        }).start();

    }

    public void printStudentsSyncronizedThread(){
        logger.info("Calling method to get list of students in Syncronized Threads");
        List<String> studentNames = studentRepository.findAll().stream()
                .map(Student::getName).toList();

        printSyncronized(studentNames.get(0));
        printSyncronized(studentNames.get(1));

        new Thread(() -> {
            printSyncronized(studentNames.get(2));
            printSyncronized(studentNames.get(3));
        }).start();

        new Thread(() -> {
            printSyncronized(studentNames.get(4));
            printSyncronized(studentNames.get(5));
            printSyncronized(studentNames.get(6));
            printSyncronized(studentNames.get(7));
            printSyncronized(studentNames.get(8));
            printSyncronized(studentNames.get(9));
        }).start();

    }

    private void printNonSyncronized(String studentName) {
        System.out.println(studentName);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void printSyncronized(String studentName) {
        System.out.println(studentName);

    }
}
