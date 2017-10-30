package edu.sindelantal.mock;

import com.github.javafaker.Faker;
import edu.sindelantal.model.Student;
import edu.sindelantal.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class StudentCreator {

    public static List<Student> createList(int sizeList) {
        List<Student> students = new ArrayList<>();

        for(int i = 0; i < sizeList; i++){
            Student student = create();
            students.add(student);
        }

        students.add(defaultStudent());

        return students;
    }

    public static Student create() {
        Faker faker = new Faker();
        int age = NumberUtils.randomInt(15, 30);

        Student student = Student.builder()
                .withAge(age)
                .withName(faker.name().fullName())
                .withId(NumberUtils.randomLong())
                .build();

        return student;
    }

    public static Student defaultStudent() {
        return Student.builder()
                .withId(1L)
                .withName("Erick Barrera")
                .withAge(24)
                .build();
    }

}
