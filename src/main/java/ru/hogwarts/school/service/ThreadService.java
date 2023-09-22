package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class ThreadService {
    private Logger logger = LoggerFactory.getLogger(ThreadService.class);

    private StudentRepository studentRepository;

    public ThreadService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public void thread(){
        List<Student> allStudents = studentRepository.findAll();
        logStudent(allStudents.get(0));
        logStudent(allStudents.get(1));

        new Thread(()-> {
            logStudent(allStudents.get(2));
            logStudent(allStudents.get(3));
        }).start();

        new Thread(()->{
            logStudent(allStudents.get(4));
            logStudent(allStudents.get(5));
        }).start();

    }

    public void threadTwo() {
        List<Student> all = studentRepository.findAll();
        logStudentTwo(all.get(0));
        logStudentTwo(all.get(1));

        new Thread(() -> {
            logStudentTwo(all.get(2));
            logStudentTwo(all.get(3));
        }).start();

        new Thread(() -> {
            logStudentTwo(all.get(4));
            logStudentTwo(all.get(5));
        }).start();
    }

    private void logStudent(Student student){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException exception){
            exception.printStackTrace();
        }
        logger.info(student.toString());
    }
    private synchronized void logStudentTwo(Student student) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        logger.info(student.toString());
    }


}