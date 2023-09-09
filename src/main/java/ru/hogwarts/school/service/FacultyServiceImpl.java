package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.SchoolApplication;
import ru.hogwarts.school.exception.FacultyException;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.List;

import java.util.Optional;


@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {

        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
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
        return facultyRepository.findByColorIgnoreCase(color);
    }

    public List<Faculty> readAllByNameOrColor(String searchName, String searchColor){
        return facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(searchName,searchColor);
    }
    public List<Student> findById(long id){
        return studentRepository.findByFacultyId(id);
    }
}
