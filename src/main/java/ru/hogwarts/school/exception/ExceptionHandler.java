package ru.hogwarts.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(StudentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Студент с id = %d не найден!",e.getId()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(FacultyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Факультет с id = %d не найден!",e.getId()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<String> handleStudentNotFoundException(AvatarNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Аватар с id = %d не найден!",e.getId()));
    }

}
