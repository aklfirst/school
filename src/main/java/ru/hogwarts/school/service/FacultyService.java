package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;

@Service

public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Calling method createFaculty");
        faculty.setId(null);
        return facultyRepository.save(faculty);
    }

    public Faculty findFacultyById(long id) {
        logger.debug("Calling method findFaculty (facultyId={})", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.debug("Calling method editFaculty (facultyId={})", faculty.getId());
        if (facultyRepository.findById(faculty.getId()).orElse(null) == null) {
            return null;
        }
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("Calling method deleteFaculty(facultyId={})",id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String partColor) {
        logger.debug("Calling method getFacultiesByColor (partColor={})",partColor);
        return facultyRepository.findAllByColorContainsIgnoreCase(partColor);
    }

    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        logger.info("Calling method to get all faculties");
        Collection<Faculty> facultiesList = facultyRepository.findAll();
        if (facultiesList.isEmpty()) {
            logger.error("No faculties exist in DB!");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultiesList);
    }

    public Collection<Student> findStudentsByFaculty(long id) {
        logger.debug("Calling method findStudentsByFaculty (facultyId={})",id);
        return studentRepository.findByFacultyId(id);
    }
}
