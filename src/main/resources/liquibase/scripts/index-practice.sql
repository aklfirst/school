-- liquibase formatted sql

-- changeset student:1
CREATE INDEX student_name_idx ON student (name);

-- changeset student:2
CREATE INDEX faculty_name_color_idx ON faculty (color,name);




