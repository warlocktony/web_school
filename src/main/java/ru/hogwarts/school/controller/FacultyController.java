package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/({id})")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }

    @DeleteMapping("/({id})")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }

    @GetMapping("/color/{color}")
    public Collection<Faculty> readAll(@PathVariable String color) {
        return facultyService.readAll(color);
    }
    @GetMapping("/nameOrColor")
    public Collection<Faculty> readByNameOrColor(@RequestParam String name,@RequestParam String color){
        return facultyService.readAllByNameOrColor(name,color);
    }
    @GetMapping("/{id}/studentsOfFaculty")
    public  Collection<Student> findStudentsOfFaculty(@PathVariable long id) {
        return facultyService.findById(id);
    }
    }
