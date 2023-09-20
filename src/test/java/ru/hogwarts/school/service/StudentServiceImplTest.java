package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @Mock
    StudentRepository studentRepository;
    @Mock
    StudentService studentService;

    @InjectMocks
    StudentServiceImpl underTest;



    Faculty faculty1 = new Faculty(0L, "Slizerine", "Green");
    Student student1 = new Student(0L, "Voland De Mord", 10_000);
    Student student2 = new Student(1L,
            "Albus Persivald Wulfreak Braine Damboldor", 100_000);
    Student student3 = new Student(2L, "Garry Potter", 15);
    Student student4 = new Student(3L, "Ron Uwizly", 14);
    Student student5 = new Student(4L, "Germiona Greindger", 14);


    List<Student> students = List.of(student1, student2, student3, student4, student5);


    @Test
    void create_studentInRepository_throwsStudentException() {
        when(studentRepository.findByNameAndAge("Voland De Mord", 10_000))
                .thenReturn(Optional.of(student1));

        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.create(student1));
        assertEquals("this student already added in base!", ex.getMessage());
    }

    @Test
    void create_studentNotInRepository_studentAddedAndReturned() {
        when(studentRepository.findByNameAndAge("Voland De Mord", 10_000))
                .thenReturn(Optional.empty());
        when(studentRepository.save(student1)).thenReturn(student1);

        Student result = underTest.create(student1);
        assertEquals(student1, result);
        assertEquals(0, result.getId());
    }

    @Test
    void read_studentNotInRepository_throwsStudentException() {
        when(studentRepository.findById(0L))
                .thenReturn(Optional.empty());
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.read(0));
        assertEquals("Student not found!", ex.getMessage());
    }

    @Test
    void read_studentInRepository_studentAddedAndReturned() {
        when(studentRepository.findById(0L))
                .thenReturn(Optional.of(student1));

        Student result = underTest.read(0);
        assertEquals(student1, result);
    }

    @Test
    void update_studentNotInRepository_throwsStudentException() {
        when(studentRepository.findById(0L))
                .thenReturn(Optional.empty());
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.update(student1));
        assertEquals("Student not found!", ex.getMessage());
    }

    @Test
    void update_studentInRepository_studentUpdatedAndReturned() {
        when(studentRepository.findById(0L))
                .thenReturn(Optional.of(student1));
        when(studentRepository.save(student1)).thenReturn(student1);
        Student result = underTest.update(student1);
        assertEquals(student1, result);
    }

    @Test
    void delete_studentNotInRepository_throwsStudentException() {
        when(studentRepository.findById(0L)).thenReturn(Optional.empty());
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.delete(0));
        assertEquals("Student not found!", ex.getMessage());
    }

    @Test
    void delete_studentInRepository_studentUpdatedAndReturned() {
        when(studentRepository.findById(0L)).thenReturn(Optional.of(student1));
        Student result = underTest.delete(0L);
        assertEquals(student1, result);
    }

    @Test
    void readAll__returnCollectionOfStudents() {
        when(studentRepository.findByAge(10_000)).thenReturn(students);

        Collection<Student> result = underTest.readAll(10_000);
        assertEquals(students, result);

    }
    @Test
    void readBetween_minAgeMaxAge_returnCollectionOdStudents(){
        when(studentRepository.findByAgeBetween(10_000, 100_000)).thenReturn(students);

        Collection<Student> result = underTest.readBetween(10_000,100_000);
        assertEquals(students,result);
    }


    @Test
    void getStudentFaculty_id_returnStudentFaculty(){

        student1.setFaculty(faculty1);
        when(studentRepository.findById(0L)).thenReturn(Optional.of(student1));

        Faculty result = underTest.getStudentFaculty(0L);
        assertEquals(faculty1,result);

    }
    @Test
    void findStudentsOfSchool__returnIntegerNumber(){
        when(studentRepository.findStudentsOfSchool()).thenReturn(5);

        Integer result = underTest.findStudentsOfSchool();
        assertEquals(5,result);
    }
    @Test
    void findAvgOfStudentAge__returnIntegerAge(){
        when(studentRepository.findAvgOfStudentAge()).thenReturn(7);

        Integer result = underTest.findAvgOfStudentAge();

        assertEquals(7,result);

    }
    @Test
    void findFiveLastStudents__returnListStudents(){
        when(studentRepository.getLast(5)).thenReturn(students);

        List<Student> result = underTest.findFiveLastStudents();

        assertEquals(students,result);
    }
    @Test
    void findNameStartsWithLetterA__returnListName(){
        List<String> strings = List.of("TEST");
        when(studentService.findNameStartsWithLetterA()).thenReturn(strings);

        List<String> res = studentService.findNameStartsWithLetterA();

        assertEquals(strings,res);

    }
    @Test
    void findAvgOfStudentByStream__returnDoubleAge(){
        when(studentService.findAvgOfStudentByStream()).thenReturn(7.5);

        Double result = studentService.findAvgOfStudentByStream();

        assertEquals(7.5,result);
    }

}
