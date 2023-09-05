package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FacultyServiceImplTest {

    FacultyServiceImpl underTest = new FacultyServiceImpl();

    Faculty faculty1 = new Faculty(0L,"Slizerine", "Green");
    Faculty faculty2 = new Faculty(1L,"Grifindor", "Red");
    Collection<Faculty> faculties = List.of(faculty1,faculty2);

    @Test
    void create_facultyInMap_throwsFacultyException(){
        underTest.create(faculty1);
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.create(faculty1));
        assertEquals("this faculty already added in base!", ex.getMessage());
    }
    @Test
    void create_facultyNotInMap_facultyAddedAndReturned(){
        Faculty result = underTest.create(faculty2);
        assertEquals(faculty2, result);
        assertEquals(1, result.getId());
    }
    @Test
    void read_facultyNotInMap_throwsFacultyException(){
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.read(1));
        assertEquals("faculty not found", ex.getMessage());
    }
    @Test
    void read_facultyInMap_facultyAddedAndReturned() {
        underTest.create(faculty1);
        Faculty result = underTest.read(faculty1.getId());
        assertEquals(faculty1, result);
    }
    @Test
    void update_facultyNotInMap_throwsFacultyException(){
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.update(faculty1));
        assertEquals("faculty not found", ex.getMessage());
    }
    @Test
    void update_facultyInMap_facultyUpdatedAndReturned() {
        underTest.create(faculty1);
        Faculty result = underTest.update(faculty1);
        assertEquals(faculty1, result);
    }
    @Test
    void delete_facultyNotInMap_throwsFacultyException() {
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.delete(1));
        assertEquals("faculty not found", ex.getMessage());
    }
    @Test
    void delete_facultyInMap_facultyUpdatedAndReturned() {
        underTest.create(faculty1);
        Faculty result = underTest.delete(faculty1.getId());
        assertEquals(faculty1, result);
    }
    @Test
    void readAll__returnCollectionOfFaculty(){
        Collection<Faculty> result = faculties;
        assertEquals(faculties,result);


    }


}
