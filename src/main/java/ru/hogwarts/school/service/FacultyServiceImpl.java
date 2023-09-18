package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    private final FacultyRepository facultyRepository;

    private final StudentRepository studentRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {

        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }


    public Faculty create(Faculty faculty) {
        logger.info("was called method create with data" + faculty);


        if (facultyRepository.findByNameAndColor(faculty.getName(),
                faculty.getColor()).isPresent()) {
            throw new FacultyException("this faculty already added in base!");
        }

        Faculty saveFaculty = facultyRepository.save(faculty);

        logger.info("from method create return" + faculty);

        return saveFaculty;


    }

    public Faculty read(long id) {
        logger.info("was called method read with data" + id);

        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("faculty not found!");
        }
        Faculty getFaculty = faculty.get();

        logger.info("from method read return" + getFaculty);

        return getFaculty;
    }

    public Faculty update(Faculty faculty) {
        logger.info("was called method update with data" + faculty);

        if (facultyRepository.findById(faculty.getId()).isEmpty()) {
            throw new FacultyException("faculty not found!");
        }

        Faculty sveFaculty = facultyRepository.save(faculty);

        logger.info("from method update return" + faculty);

        return sveFaculty;
    }

    public Faculty delete(long id) {
        logger.info("was called method delete with data" + id);


        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            throw new FacultyException("faculty not found!");
        }

        facultyRepository.deleteById(id);

        Faculty getDeleteFaculty = faculty.get();

        logger.info("from method delete return" + getDeleteFaculty);

        return getDeleteFaculty;

    }

    public List<Faculty> readAll(String color) {
        logger.info("was called method readAll with data" + color);

        List<Faculty> findByColorIgnoreCase = facultyRepository.findByColorIgnoreCase(color);

        logger.info("from method readAll return" + findByColorIgnoreCase);

        return findByColorIgnoreCase;
    }

    public List<Faculty> readAllByNameOrColor(String searchName, String searchColor) {
        logger.info("was called method readAllByNameOrColor with data" + searchName + searchColor);

        List<Faculty> findByNameContainingIgnoreCaseOrColorContainingIgnoreCase =
                facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase(searchName, searchColor);

        logger.info("from method readAllByNameOrColor return" + findByNameContainingIgnoreCaseOrColorContainingIgnoreCase);

        return findByNameContainingIgnoreCaseOrColorContainingIgnoreCase;
    }

    public List<Student> findById(long id) {
        logger.info("was called method findById with data" + id);

        List<Student> findByFacultyId =
                studentRepository.findByFacultyId(id);

        logger.info("from method findById return" + findByFacultyId);

        return findByFacultyId;
    }
}


