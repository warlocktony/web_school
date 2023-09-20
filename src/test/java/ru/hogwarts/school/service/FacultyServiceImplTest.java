package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.shortThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceImplTest {

    @Mock
    FacultyRepository facultyRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    FacultyService facultyService;

    @InjectMocks
    FacultyServiceImpl underTest;


    Faculty faculty1 = new Faculty(0L, "Slizerine", "Green");
    Faculty faculty2 = new Faculty(1L, "Grifindor", "Red");
    Student student1 = new Student(0L, "Voland De Mord", 10_000);
    List<Faculty> facultyes = List.of(faculty1, faculty2);





    @Test
    void create_facultyInRepository_throwsFacultyException() {
        when(facultyRepository.findByNameAndColor("Slizerine", "Green"))
                .thenReturn(Optional.of(faculty1));

        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.create(faculty1));
        assertEquals("this faculty already added in base!", ex.getMessage());
    }

    @Test
    void create_facultyNotInRepository_facultyAddedAndReturned() {

        when(facultyRepository.findByNameAndColor("Slizerine", "Green"))
                .thenReturn(Optional.empty());
        when(facultyRepository.save(faculty1))
                .thenReturn(faculty1);

        Faculty result = underTest.create(faculty1);
        assertEquals(faculty1, result);
        assertEquals(0, result.getId());

    }

    @Test
    void read_facultyNotInRepository_throwsFacultyException() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.empty());
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.read(0));
        assertEquals("faculty not found!", ex.getMessage());
    }

    @Test
    void read_facultyInRepository_facultyAddedAndReturned() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.of(faculty1));
        Faculty result = underTest.read(0);
        assertEquals(faculty1, result);

    }

    @Test
    void update_facultyNotInRepository_throwsFacultyException() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.empty());


        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.update(faculty1));
        assertEquals("faculty not found!", ex.getMessage());
    }

    @Test
    void update_facultyInRepository_facultyUpdatedAndReturned() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.of(faculty1));
        when(facultyRepository.save(faculty1))
                .thenReturn(faculty1);

        Faculty result = underTest.update(faculty1);
        assertEquals(faculty1, result);
    }


    @Test
    void delete_facultyNotInRepository_throwsFacultyException() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.empty());
        FacultyException ex =
                assertThrows(FacultyException.class,
                        () -> underTest.delete(0));
        assertEquals("faculty not found!", ex.getMessage());
    }

    @Test
    void delete_facultyInRepository_facultyUpdatedAndReturned() {
        when(facultyRepository.findById(0L))
                .thenReturn(Optional.of(faculty1));
        Faculty rasult = underTest.delete(0);
        assertEquals(faculty1, rasult);
    }


    @Test
    void readAll__returnCollectionOfFaculty() {
        when(facultyRepository.findByColorIgnoreCase("Green"))
                .thenReturn(facultyes);

        Collection<Faculty> result = underTest.readAll("Green");
        assertEquals(facultyes,result);

    }
    @Test
    void readAllByNameOrColor_nameColor_returnFaculty(){
        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase("Slizerine",""))
                .thenReturn(facultyes);

        Collection<Faculty> result = underTest.readAllByNameOrColor("Slizerine","");
        assertEquals(facultyes,result);

    }

    @Test
    void findById_id_returnCollectionStudent(){
        List<Student> students = List.of(student1);

        when(studentRepository.findByFacultyId(0L)).thenReturn(students);

        List<Student> result = underTest.findById(0L);

        assertEquals(students,result);


    }
    @Test
    void findByLongestNameFaculty__returnNameOfFaculty(){
        Faculty facultySaveRes = facultyRepository.save(faculty1);
        when(facultyService.findByLongestNameFaculty()).thenReturn(facultySaveRes.getName());

        String res = underTest.findByLongestNameFaculty();

        assertEquals(facultySaveRes.getName(),res);

    }


}



