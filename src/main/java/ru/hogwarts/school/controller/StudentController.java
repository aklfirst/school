package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}") // GET localhost:8080/student/8
    public ResponseEntity <Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(student);
    }

    @GetMapping("/age/{age}") // GET localhost:8080/student/age/8
    public ResponseEntity <Collection<Student>> getStudentByAge(@PathVariable Integer age) {
        return  ResponseEntity.ok(studentService.getStudentsByAge(age));
      }

      @GetMapping // GET localhost:8080/student/
      public Collection<Student> printAllStudents() {

        return studentService.getAllStudents();
      }

    @DeleteMapping("{id}") // DELETE localhost:8080/student/8
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
        }

    @PutMapping // PUT localhost:8080/student/
    public ResponseEntity <Student> editStudent (@RequestBody Student student) {
        Student studentToEdit = studentService.editStudent(student);
        if (studentToEdit == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(studentToEdit);
    }


    @PostMapping // POST localhost:8080/student/
    public ResponseEntity <Student> createStudentList(@RequestBody Student student) {
        Student studentToCreate = studentService.createStudent(student);
        if (studentToCreate == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(studentToCreate);
    }


}
