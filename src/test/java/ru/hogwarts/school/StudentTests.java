package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.FacultyService;


import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


public class StudentTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();

    }

    @Test
     void testCreateStudentList() throws Exception {
        Faculty testFaculty = facultyService.findFacultyById(7L);

        Student testStudent = createTestStudent("Test Testovich",95,testFaculty);

        ResponseEntity<Student> responseTestStudentCreated = createStudentResponse(testStudent);

        assertThat(responseTestStudentCreated.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseTestStudentCreated.getBody()).isNotNull();
        assertThat(responseTestStudentCreated.getBody().getName().equals("Test Testovich"));
        assertThat(responseTestStudentCreated.getBody().getId()).isNotNull();

        }

    @Test
    void testGetStudentInfo() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/student/1",String.class))
                .contains("A Klepov");

    }

    @Test
    void testGetStudentByAge() throws Exception {
        int ageMin = 45;
        int ageMax = 47;
        ResponseEntity<Collection<Student>> response = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/?ageMin=" + ageMin + "&ageMax=" + ageMax,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Collection<Student>>() {
                });

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        if (!response.getBody().isEmpty()) {
            for (Student student : response.getBody()) {
                assertThat(student.getAge()).isBetween(ageMin, ageMax);
            }
        }

        Set<Student> expected = new HashSet<>();
        expected.add(new Student(2L, "D Frolov", 45));
        expected.add(new Student(4L, "A Elkin", 45));
        expected.add(new Student(5L, "M Vitokhin", 45));

        assertThat(response.getBody()).hasSameElementsAs(expected);

    }

    @Test
    void testEditStudent() throws Exception {

        String updateName = "Ivan";
        int updateAge = 55;
        String searchedName ="Test Testovich";

        Student studentForUpdate = studentRepository.findStudentByName(searchedName);
        studentForUpdate.setName(updateName);
        studentForUpdate.setAge(updateAge);

        restTemplate.put(
                "http://localhost:" + port + "/student",
                studentForUpdate);

        ResponseEntity<Student> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentForUpdate.getId(),
                Student.class);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(updateName);
        assertThat(response.getBody().getAge()).isEqualTo(updateAge);
    }


    @Test
    void testGetStudentFaculty() {
        Long studentToFindFaculty = 1L;

        ResponseEntity responseFaculty = this.restTemplate.getForEntity("http://localhost:"+port+"/student/faculty/"+studentToFindFaculty+"/",String.class);

        assertThat(responseFaculty.getBody()).isNotNull();
        assertThat(responseFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    void testDeleteStudent() {

        String searchedName = "Ivan";

        Student studentForDelete = studentRepository.findStudentByName(searchedName);

        restTemplate.delete(
                "http://localhost:" + port + "/student/"+
                studentForDelete.getId());

        ResponseEntity<Student> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentForDelete.getId(),
                Student.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Student createTestStudent(String name, int age,Faculty faculty) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        student.setFaculty(faculty);
        return student;
    }

    private ResponseEntity<Student> createStudentResponse(Student student) {
        return restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class);
    }

}
