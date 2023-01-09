package ru.hogwarts.school.controller;


import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController

public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@RequestParam MultipartFile avatarFile) throws IOException {
        avatarService.uploadAvatar(avatarFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{studentId}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatarFromDb(@PathVariable Long studentId) {
        return readAvatar(avatarService.readAvatarFromDb(studentId));
    }

    @GetMapping(value = "/{studentId}/avatar-from-file")
    public ResponseEntity<byte[]> downloadAvatarFromFile(@PathVariable Long studentId) throws IOException {
        return readAvatar(avatarService.readAvatarFromFs(studentId));
    }

    private ResponseEntity<byte[]> readAvatar(Pair<String,byte[]> pair) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(pair.getLeft()))
                .contentLength(pair.getRight().length)
                .body(pair.getRight());
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Collection<Avatar>> getAllAvatars(@RequestParam("page") Integer pageNumber,
                                                            @RequestParam("size") Integer pageSize) {
     return avatarService.getAll(pageNumber,pageSize);
    }



}
