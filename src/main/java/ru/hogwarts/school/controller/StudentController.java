package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return studentService.update(student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping("/age/{age}")
    public Collection<Student> readAll(@PathVariable int age) {
        return studentService.readAll(age);
    }

    @GetMapping("/ageBetween")
    public Collection<Student> readBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        return studentService.readBetween(minAge, maxAge);
    }

    @GetMapping("/{id}/studentFaculty")
    public Faculty findStudentFaculty(@PathVariable long id) {
        return studentService.getStudentFaculty(id);
    }

    @GetMapping("/count")
    public Integer findStudentsOfSchool(){
        return studentService.findStudentsOfSchool();
    }
    @GetMapping("/age - avg")
    public Integer findAvgOfStudentAge(){
        return studentService.findAvgOfStudentAge();
    }
    @GetMapping("/last - five")
    public List<Student> findFiveLastStudents(){
        return studentService.findFiveLastStudents();
    }

    @GetMapping("/name-start-a")
    public List<String> findNameStartsWithLetterA(){
        return studentService.findNameStartsWithLetterA();
    }
    @GetMapping("age-avg-by-stream")
    public Double findAvgOfStudentByStream(){
        return studentService.findAvgOfStudentByStream();
    }
}







