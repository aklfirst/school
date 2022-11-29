package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class SchoolApplicationTests {


	long lastId = 0;

	@Test
	void checkValuesInTheMap() {

		//final HashMap<Long, Student> studentsForTest = new HashMap<>();

		//Student student1 = new Student(0L, "Alexander Klepov", 48);
		//Student student2 = new Student(3L, "Dmitry Frolov", 48);
		//Student studentForCheck = new Student(3L, "Dmitry Frolov", 41);

		//studentsForTest.put((++lastId),student1);
		//studentsForTest.put((++lastId),student2);
		//studentsForTest.put((++lastId),student2);

		//System.out.println(studentsForTest.values());



		//System.out.println(checkDuplicate);


		//public Student createStudent1(Student student) {
		//student.setId(++lastId);
		//Set<Map.Entry<Long,Student>> lookForDouble = students.entrySet();
		//for (Map.Entry<Long,Student> entry: lookForDouble) {
		//if (students.containsValue(student)) {
		//	throw new RuntimeException("Такой студент уже есть в базе!");
		//}
		//students.put(lastId, student);
		//return student;
	}

}


