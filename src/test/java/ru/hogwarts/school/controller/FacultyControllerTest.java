package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FacultyController facultyController;

    @SpyBean
    FacultyServiceImpl facultyService;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    FacultyRepository facultyRepository;

    Faculty faculty = new Faculty(0L, "Slizerine", "Green");

    Student student = new Student(0L, "pupa", 10);
    Student student1 = new Student(1L, "lupa", 11);


    List<Student> students = List.of(student, student1);
    List<Faculty> faculties = List.of(faculty);



    @Test
    void create__status200AndSavedToDB() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }

    @Test
    void read__status200AndReturnFaculty() throws Exception {
        when(facultyRepository.findById(0L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/" + faculty.getId()))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }


    @Test
    void readAll__status200AndReturnListFaculty() throws Exception {
        when(facultyRepository.findByColorIgnoreCase(faculty.getColor()))
                .thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }

    @Test
    void update__status200AndReturnFaculty() throws Exception {
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        when(facultyRepository.findById(0L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(put("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }

    @Test
    void delete__status200AndReturnDeleteFaculty() throws Exception {
        when(facultyRepository.findById(0L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(delete("/faculty/" + faculty.getId()))

                .andExpect(status().isOk());


    }

    @Test
    void readByNameOrColor__status200AndReturnListOfFaculty() throws Exception {
        when(facultyRepository.findByNameContainingIgnoreCaseOrColorContainingIgnoreCase
                (faculty.getName(), faculty.getColor()))
                .thenReturn(List.of(faculty));
        mockMvc.perform(get("/faculty/nameOrColor/?name="
                        + faculty.getName() + "&color=" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));

    }

    @Test
    void findStudentsOfFaculty__status200AndReturnListOfStudents() throws Exception {
        when(studentRepository.findByFacultyId(0L)).thenReturn(students);

        mockMvc.perform(get("/faculty/" + faculty.getId() + "/studentsOfFaculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));
    }
    @Test
    void findByLongestNameFaculty__status200AndReturnStringName() throws Exception{
        when(facultyRepository.findAll()).thenReturn(faculties);

        mockMvc.perform(get("/faculty/longest-name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(faculty.getName()));






    }


}
