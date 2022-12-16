ALTER table student
ADD CONSTRAINT age_constraint check (age>15);

ALTER table student
ALTER COLUMN name SET NOT NULL,ADD CONSTRAINT name_unique UNIQUE (name);

ALTER table student
ALTER age SET DEFAULT 20;

ALTER table faculty
ADD CONSTRAINT name_color_unique UNIQUE (name,color);

SELECT student.name, student.age, f.name
FROM student
INNER JOIN faculty as f ON student.faculty_id = f.id;

SELECT s.name, a.id
FROM avatar as a
         INNER JOIN student as s ON a.student_id = s.id;

SELECT student.name, avatar.id
FROM avatar
         LEFT JOIN student ON avatar.student_id = student.id;

SELECT student.name, avatar.id
FROM avatar
         RIGHT JOIN student ON avatar.student_id = student.id;

SELECT student.name, avatar.id
FROM avatar
         FULL JOIN student ON avatar.student_id = student.id;