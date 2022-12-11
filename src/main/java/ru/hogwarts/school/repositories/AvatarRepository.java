package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar,Long> {

    Optional<Avatar> findByStudentId(Long studentId);
}
