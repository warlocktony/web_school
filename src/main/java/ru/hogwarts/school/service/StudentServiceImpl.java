package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private final Map<Long, Student> studentMap = new HashMap<>();

    private long counter;

    public Student create(Student student){
        if(studentMap.containsValue(student)){
            throw new StudentException("this student already added in base!");
        }

        student.setId(++counter);
        studentMap.put(student.getId(), student);

        return student;
    }
    public Student read(long id){
        if(!studentMap.containsKey(id)){
            throw new StudentException("Student not found!");
        }

        return studentMap.get(id);
    }
    public Student update(Student student){
        if(!studentMap.containsKey(student.getId())){
            throw new StudentException("Student not found!");
        }

        studentMap.put(student.getId(), student );

        return student;
    }
    public Student delete(long id){
       Student student= studentMap.remove(id);
       if(student == null){
           throw new StudentException("Student not found!");
       }
        return student;
    }
    public List<Student> readAll(int age){
        return studentMap.values().stream().filter(stud -> stud.getAge() == age)
            .collect(Collectors.toUnmodifiableList());
    }
}
