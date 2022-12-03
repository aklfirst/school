select * from student;

select * from student
where age > 40 and age <46;

select name from student;

select * from student
where name like '%o%';

select * from student
where age/10 > id;

select s.name, s.age from student as s
order by age, name;

select s.*, f.name from student as s, faculty as f
where s.faculty_id = f.id
  and f.name like '%1%'
order by f.name,s.name;


select s.name, f.name from student as s, faculty as f
where s.faculty_id = f.id
  and s.faculty_id in (2,4)
order by f.name,s.name;