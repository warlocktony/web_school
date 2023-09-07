package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;

import java.util.Optional;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }


    public Faculty create(Faculty faculty) {
        if (facultyRepository.findByNameAndColor(faculty.getName(),
                faculty.getColor()).isPresent()) {
            throw new FacultyException("this faculty already added in base!");
        }

        return facultyRepository.save(faculty);


    }

    public Faculty read(long id) {

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("faculty not found!");
        }

        return faculty.get();
    }

    public Faculty update(Faculty faculty) {

        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("faculty not found!");
        }

        return facultyRepository.save(faculty);
    }

    public Faculty delete(long id) {

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("faculty not found!");
        }

        facultyRepository.deleteById(id);
        return faculty.get();

    }

    public List<Faculty> readAll(String color) {
        return facultyRepository.findByColor(color);
    }
}
