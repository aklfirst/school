package ru.hogwarts.school.controller;


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
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(faculty);
    }

    @GetMapping("/color/{color}") // GET localhost:8080/faculty/color/red
    public Collection<Faculty> getFacultyByColor(@PathVariable String color) {
        return  facultyService.getFacultiesByColor(color);
    }

    @DeleteMapping("{id}") // DELETE localhost:8080/faculty/2
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
        }

    @PutMapping // PUT localhost:8080/faculty/
    public ResponseEntity <Faculty> editFaculty (@RequestBody Faculty faculty) {
        Faculty facultyToEdit = facultyService.editFaculty(faculty);
        if (facultyToEdit == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(facultyToEdit);
    }


    @PostMapping // POST localhost:8080/faculty/
    public ResponseEntity <Faculty> createStudentList(@RequestBody Faculty faculty) {
        Faculty facultyToCreate = facultyService.createFaculty(faculty);
        if (facultyToCreate == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(facultyToCreate);
    }

}
