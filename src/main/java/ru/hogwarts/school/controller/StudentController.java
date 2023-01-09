package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

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

    @GetMapping("/age/") // GET localhost:8080/student/age/
    public ResponseEntity <Collection<Student>> getStudentByAge(@RequestParam Integer ageMin,
                                                                @RequestParam (required = false) Integer ageMax) {
        if (ageMax == null) {
            return  ResponseEntity.ok(studentService.getStudentsByAge(ageMin));
        }
        return  studentService.getStudentsByAgeInRange(ageMin,ageMax);
      }


    @GetMapping("/faculty/{id}/") // GET localhost:8080/student/faculty/2
    public ResponseEntity getStudentFaculty(@PathVariable Long id) {
        return  ResponseEntity.ok(studentService.findStudentFaculty(id));
    }

    @GetMapping // GET localhost:8080/student/
    public ResponseEntity<Collection<Student>> printAllStudents() {
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

    @GetMapping("/last5/")
    public List<Student> getStudentsByIdLastFive() {
        return studentService.getStudentsByIdLastFive();
    }

    @GetMapping("/qty/")
    public ResponseEntity<Integer> getStudentsQty() {
        return  ResponseEntity.ok(studentService.getStudentsQty());
    }

    @GetMapping("/ageAvg/")
    public ResponseEntity<Double> getAverageAgeStudents() {
        return  ResponseEntity.ok(studentService.getAverageAgeStudents());
    }

    @PatchMapping("/{id}/avatar")

    public Student patchStudentAvatar(@PathVariable long id,
                                      @RequestParam("avatarId") long avatarId) {
        return studentService.patchStudentAvatar(id,avatarId);
    }

    @GetMapping("/average-age-with-stream")
    public ResponseEntity<Double> getAverageAgeWithStream() {
        return ResponseEntity.ok(studentService.getAverageAgeWithStream());
    }

    @GetMapping("/name-starts-with")
    public ResponseEntity<List<String>> getStudentsByNameStartsWith(@RequestParam String letter) {
        return ResponseEntity.ok(studentService.getStudentsByNameStartsWith(letter));
    }

    @GetMapping("/students-nonsync-threads")
    public void printStudentsNonSyncronizedThread() {
        studentService.printStudentsNonSyncronizedThread();
    }

    @GetMapping("/students-sync-threads")
    public void printStudentsSyncronizedThread() {
        studentService.printStudentsSyncronizedThread();
    }


}
