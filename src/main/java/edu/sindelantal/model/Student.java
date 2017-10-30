package edu.sindelantal.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class Student implements Serializable {

    private Long id;
    private String name;
    private Integer age;

    public Long id() {
        return id;
    }

    public Student withId(Long id) {
        this.id = id;
        return this;
    }

    public String name() {
        return name;
    }

    public Student withName(String name) {
        this.name = name;
        return this;
    }

    public Integer age() {
        return age;
    }

    public Student withAge(Integer age) {
        this.age = age;
        return this;
    }

    public static Student builder(){
        return new Student();
    }

    public Student build(){
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if( !(object instanceof Student) ) return false;

        Student student = (Student) object;

        return new EqualsBuilder()
                .append(id, student.id)
                .append(name, student.name)
                .append(age, student.age)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(name)
                .append(age)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Student {\n" +
                "\tid:" + "\t\t" + id + "\n" +
                "\tname:" + "\t" + name + "\n" +
                "\tage:" + "\t" + age + "\n" +
                "}";
    }

}
