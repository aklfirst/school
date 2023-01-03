package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")

public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}") // GET localhost:8080/faculty/2
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return  ResponseEntity.ok(faculty);
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> printAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/color") // GET localhost:8080/faculty/color/
    public ResponseEntity<Collection<Faculty>> findFacultyByColor(@RequestParam String partColor) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(partColor));
    }

    @GetMapping("/students") // GET localhost:8080/faculty/color/
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@RequestParam long id) {
        return ResponseEntity.ok(facultyService.findStudentsByFaculty(id));
    }

    @DeleteMapping("{id}") // DELETE localhost:8080/faculty/2
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
        }

    @PutMapping // PUT localhost:8080/faculty/
    public ResponseEntity <Faculty> editFaculty (@RequestBody Faculty faculty) {
        Faculty facultyToEdit = facultyService.editFaculty(faculty);
        if (facultyToEdit == null) {
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(facultyToEdit);
    }


    @PostMapping // POST localhost:8080/faculty/
    public ResponseEntity <Faculty> createFacultyList(@RequestBody Faculty faculty) {
        Faculty facultyToCreate = facultyService.createFaculty(faculty);
        if (facultyToCreate == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyToCreate);
    }

}
