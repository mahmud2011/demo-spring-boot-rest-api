package com.example.demospringbootrestapi.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByID(Long id) {
        return studentRepository.findById(id);
    }

    public void addNewStudent(Student student) {
        if (studentRepository.findStudentByEmail(student.getEmail()).isPresent()) {
            throw new IllegalStateException("student exists");
        }

        studentRepository.save(student);
    }

    public void deleteStudentByID(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("student does not exists");
        }
    }

    @Transactional
    public void updateStudentByID(Long id, String name, String email) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("student does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            if (studentRepository.findStudentByEmail(email).isPresent()) {
                throw new IllegalStateException("email already exits");
            }

            student.setEmail(email);
        }
    }
}
