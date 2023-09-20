package ru.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    private final FacultyRepository facultyRepository;

    public StudentServiceImpl(StudentRepository studentRepository,
                              FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student create(Student student) {
        logger.info("was called method create with data" + student);

        if (studentRepository.findByNameAndAge(student.getName(), student.getAge()).isPresent()) {
            throw new StudentException("this student already added in base!");
        }
        Student saveStudent = studentRepository.save(student);

       logger.info("from method create return" + student);

       return saveStudent;


    }

    public Student read(long id) {
        logger.info("was called method read with data" + id );

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }

        Student getStudent = student.get();

        logger.info("from method read return" + getStudent);

        return getStudent;
    }

    public Student update(Student student) {
        logger.info("was called method update with data" + student );

        if (studentRepository.findById(student.getId()).isEmpty()) {
            throw new StudentException("Student not found!");
        }

        Student saveUpdateStudent = studentRepository.save(student);

        logger.info("from method update return" + student);

        return saveUpdateStudent;
    }

    public Student delete(long id) {
        logger.info("was called method delete with data" + id );

        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }

        studentRepository.deleteById(id);

        Student studentGetDelete = student.get();

        logger.info("from method delete return" + studentGetDelete);

        return studentGetDelete;

    }

    public List<Student> readAll(int age) {
        logger.info("was called method readAll with data" + age );

        List<Student> findByAge = studentRepository.findByAge(age);

        logger.info("from method readAll return" + findByAge);

        return findByAge;
    }

    public List<Student> readBetween(int minAge, int maxAge) {
        logger.info("was called method readBetween with data" + minAge + maxAge );

        List<Student> findByAgeBetween = studentRepository.findByAgeBetween(minAge, maxAge);

        logger.info("from method readBetween return" + findByAgeBetween);

        return findByAgeBetween;
    }

    public Faculty getStudentFaculty(long id) {
        logger.info("was called method getStudentFaculty with data" + id );

        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentException("Student not found!");
        }

        Faculty getStudentGetFaculty = student.get().getFaculty();

        logger.info("from method getStudentFaculty return" + getStudentGetFaculty);


        return getStudentGetFaculty;
    }

    public Integer findStudentsOfSchool() {
        logger.info("was called method findStudentsOfSchool");

        return studentRepository.findStudentsOfSchool();
    }

    public Integer findAvgOfStudentAge() {
        logger.info("was called method findAvgOfStudentAge");

        return studentRepository.findAvgOfStudentAge();
    }

    @Override
    public List<Student> findFiveLastStudents() {
        logger.info("was called method findFiveLastStudents");

        return studentRepository.getLast(5);
    }
    public List<String> findNameStartsWithLetterA(){
       return studentRepository.findAll().stream()
                .map(student -> student.getName())
                .filter(name -> StringUtils.startsWithIgnoreCase(name,"a"))
                .map(name ->name.toUpperCase())
                .sorted()
                .collect(Collectors.toList());
    }
    public Double findAvgOfStudentByStream(){
        return studentRepository.findAll().stream()
                .mapToInt(student -> student.getAge())
                .average()
                .orElse(0);
    }



}
