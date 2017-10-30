package edu.sindelantal.service;

import edu.sindelantal.mock.StudentCreator;
import edu.sindelantal.model.Student;

import java.util.List;
import java.util.Optional;

public class StudentService {

    private final int SIZE_STUDENTS = 10;

    public List<Student> getStudents() {
        return StudentCreator.createList(SIZE_STUDENTS);
    }

    public Optional<Student> getDefault() {
        return Optional.of(StudentCreator.defaultStudent());
    }

    public void printStudent(Student student) {
        System.out.println(student);
    }

    public void printOnlyName(Student student) {
        System.out.print(student.name() + ". ");
    }

}
