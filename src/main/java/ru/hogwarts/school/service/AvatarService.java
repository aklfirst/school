package ru.hogwarts.school.service;

import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

import static io.swagger.v3.core.util.AnnotationsUtils.getExtensions;
import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional

public class AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) throws IOException {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(MultipartFile avatarFile) throws IOException {
        logger.info("Calling method uploadAvatar");
        byte[] data = avatarFile.getBytes();
        Avatar avatar = create(avatarFile.getSize(), avatarFile.getContentType(), data);

        String extension = Optional.ofNullable(avatarFile.getOriginalFilename())
                .map(s -> s.substring(avatarFile.getOriginalFilename().lastIndexOf('.')))
                .orElse("");
        Path filePath = Paths.get(avatarsDir).resolve(avatar.getId() + extension);
        Files.write(filePath, data);
        avatar.setFilePath(filePath.toString());
        avatarRepository.save(avatar);
    }

    private Avatar create (long size, String contentType, byte[] data) {
        Avatar avatar = new Avatar();
        avatar.setData(data);
        avatar.setFileSize(size);
        avatar.setMediaType(contentType);
        return avatarRepository.save(avatar);
    }

    public Pair<String, byte[]> readAvatarFromDb (long id) {
        logger.debug("Calling method readAvatarFromDb (id={})", id);
        Avatar avatar = avatarRepository.findById(id).orElseThrow(()-> new AvatarNotFoundException(id));
        return Pair.of(avatar.getMediaType(),avatar.getData());
    }

    public Pair<String, byte[]> readAvatarFromFs (long id) throws IOException {
        logger.debug("Calling method readAvatarFromFs (id={})", id);
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundException(id));
        return Pair.of(avatar.getMediaType(),Files.readAllBytes(Paths.get(avatar.getFilePath())));
    }


    public ResponseEntity<Collection<Avatar>> getAll(Integer pageNumber, Integer pageSize) {
        logger.info("Calling method to get all avatars");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Collection<Avatar> avatarsList = avatarRepository.findAll(pageRequest).getContent();
        if (avatarsList.isEmpty()) {
            logger.error("No avatars exist in DB!");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatarsList);
    }

}
