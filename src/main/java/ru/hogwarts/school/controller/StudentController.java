package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

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
    public Collection<Student> readBetween(@RequestParam int minAge, @RequestParam int maxAge ){
        return studentService.readBetween(minAge,maxAge);
    }
    @GetMapping("/{id}/studentFaculty")
    public Faculty findStudentFaculty(@PathVariable long id){
        return studentService.getStudentFaculty(id);
    }
    }

