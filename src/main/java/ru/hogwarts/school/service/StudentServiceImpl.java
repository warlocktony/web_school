package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student create(Student student) {
        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("this student already added in base!");
        }

        return studentRepository.save(student);


    }

    public Student read(long id) {

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }

        return student.get();
    }

    public Student update(Student student) {

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Student not found!");
        }

        return studentRepository.save(student);
    }

    public Student delete(long id) {

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }

        studentRepository.deleteById(id);
        return student.get();

    }

    public List<Student> readAll(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> readBetween(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty getStudentFaculty(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }
        return student.get().getFaculty();
    }
}
