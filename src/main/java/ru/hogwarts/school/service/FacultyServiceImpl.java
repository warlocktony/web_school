package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class FacultyServiceImpl implements FacultyService{

    private final Map<Long, Faculty> facultyMap = new HashMap<>();

    private long counter;

    public Faculty create(Faculty faculty){
        if(facultyMap.containsValue(faculty)){
            throw new FacultyException("this faculty already added in base!");
        }
        faculty.setId(++counter);
        facultyMap.put(faculty.getId(), faculty);

        return faculty;
    }
    public Faculty read(long id){
        if(!facultyMap.containsKey(id)){
            throw new FacultyException("faculty not found");
        }
        return facultyMap.get(id);
    }
    public Faculty update(Faculty faculty){
        if(!facultyMap.containsKey(faculty.getId())){
            throw new FacultyException("faculty not found");
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }
    public Faculty delete(long id){
        Faculty faculty = facultyMap.remove(id);
        if(faculty == null){
            throw new FacultyException("faculty not found");
        }
        return faculty;
    }
    public List<Faculty> readAll(String color){
        return facultyMap.values().stream().filter(col -> col.getColor().equals(color))
                .collect(Collectors.toUnmodifiableList());
    }
}
