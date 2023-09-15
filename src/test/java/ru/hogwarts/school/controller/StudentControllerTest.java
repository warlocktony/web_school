package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();
    }


    Student student = new Student(0L, "Velen", 100_000);
    Faculty faculty = new Faculty(0L, "Slizerine", "Green");


    @Test
    void create__returnStatus200AndStudent() {

        ResponseEntity<Student> studentResponseEntity =
                restTemplate.postForEntity(
                        "http://localhost:" + port + "/student", student, Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());

    }

    @Test
    void read_studentNotInDB_returnStatus400AndExceptionTest() {
        ResponseEntity<String> studentResponseEntity =
                restTemplate.getForEntity(
                        "http://localhost:" + port + "/student/" + student.getId(), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("Student not found!", studentResponseEntity.getBody());
    }

    @Test
    void update_studentInDB_returnStatus200AndStudent() {
        Student thisStudent = studentRepository.save(student);
        ResponseEntity<Student> update = restTemplate.exchange(
                "http://localhost:" + port + "/student",
                HttpMethod.PUT, new HttpEntity<>(thisStudent), Student.class);

        assertEquals(HttpStatus.OK, update.getStatusCode());
        assertEquals(thisStudent, update.getBody());
    }

    @Test
    void delete_studentNotInDB_returnStatus400() {
        ResponseEntity<String> delete = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.BAD_REQUEST, delete.getStatusCode());
        assertEquals("Student not found!", delete.getBody());
    }

    @Test
    void readAll__returnStatus200AndStudentList() {
        studentRepository.save(student);

        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/age/" + student.getAge(),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());

    }

    @Test
    void readBetween__returnStatus200AndStudentList() {
        studentRepository.save(student);


        ResponseEntity<List<Student>> between = restTemplate.exchange(
                "http://localhost:" + port + "/student/ageBetween/?minAge="
                        + student.getAge() + "&maxAge=" + (student.getAge() + 1),
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, between.getStatusCode());
        assertEquals(List.of(student), between.getBody());
    }

    @Test
    void findStudentFaculty__returnStatus200AndStudentFaculty() {
        Faculty resultFacultySave = facultyRepository.save(faculty);
        student.setFaculty(resultFacultySave);
        Student resultStudentSave = studentRepository.save(student);


        ResponseEntity<Faculty> studentFaculty = restTemplate.getForEntity("http://localhost:"
                + port + "/student/" + resultStudentSave.getId() + "/studentFaculty", Faculty.class);

        assertEquals(HttpStatus.OK, studentFaculty.getStatusCode());
        assertEquals(resultFacultySave, studentFaculty.getBody());
    }

}
