package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(Faculty faculty);

    Faculty delete(long id);

    Collection<Faculty> readAll(String color);

    Collection<Faculty> readAllByNameOrColor(String name, String color);

    Collection<Student> findById(long id);
}
