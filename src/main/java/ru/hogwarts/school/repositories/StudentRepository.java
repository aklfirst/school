package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    public Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    Collection<Student> findByFacultyId(long id);

    Student findStudentByName(String name);

    @Query (value = "select count(name) from student",nativeQuery = true)
    Integer getStudentsQty();

    @Query (value = "select avg(age) from student",nativeQuery = true)
    Double getAverageAgeStudents();

    @Query (value = "select * from student order by id desc LIMIT 5",nativeQuery = true)
    List<Student> getStudentsByIdLastFive();

}
