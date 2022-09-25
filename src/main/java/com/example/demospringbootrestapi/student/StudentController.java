package com.example.demospringbootrestapi.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(value = "/{id}")
    public Student getStudentByID(@PathVariable Long id) {
        return studentService.getStudentByID(id).orElse(new Student());
    }

    @PostMapping(value = "/")
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }
}
