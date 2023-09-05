package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceImplTest {

    StudentServiceImpl underTest = new StudentServiceImpl();
    Student student1 = new Student(0L, "Voland De Mord",10_000);
    Student student2 = new Student(1L,
            "Albus Persivald Wulfreak Braine Damboldor",100_000);
    Collection<Student> students = List.of(student1,student2);


    @Test
    void create_studentInMap_throwsStudentException(){
            underTest.create(student1);
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.create(student1));
        assertEquals("this student already added in base!", ex.getMessage());
    }
    @Test
    void create_studentNotInMap_studentAddedAndReturned(){
        Student result = underTest.create(student2);
        assertEquals(student2, result);
        assertEquals(1, result.getId());
    }
    @Test
    void read_studentNotInMap_throwsStudentException(){
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.read(1));
        assertEquals("Student not found!", ex.getMessage());
    }
    @Test
    void read_studentInMap_studentAddedAndReturned() {
        underTest.create(student1);
        Student result = underTest.read(student1.getId());
        assertEquals(student1, result);
    }
    @Test
    void update_studentNotInMap_throwsStudentException(){
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.update(student1));
        assertEquals("Student not found!", ex.getMessage());
    }
    @Test
    void update_studentInMap_studentUpdatedAndReturned() {
        underTest.create(student1);
        Student result = underTest.update(student1);
        assertEquals(student1, result);
    }
    @Test
    void delete_studentNotInMap_throwsStudentException() {
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.delete(1));
        assertEquals("Student not found!", ex.getMessage());
    }
    @Test
    void delete_studentInMap_studentUpdatedAndReturned() {
        underTest.create(student1);
        Student result = underTest.delete(student1.getId());
        assertEquals(student1, result);
    }
    @Test
    void readAll__returnCollectionOfStudents(){
        Collection<Student> result = students;
        assertEquals(students,result);


    }





}
