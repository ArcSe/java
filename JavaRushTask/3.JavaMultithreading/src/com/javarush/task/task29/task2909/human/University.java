package com.javarush.task.task29.task2909.human;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class University {

    private List<Student> students ;
    private String name;
    private int age;
    public University(String name, int age) {
        students = new ArrayList<>();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        for(Student student: students){
            if(student.getAverageGrade() == averageGrade){
                return student;
            }
        }
        return students.get(0);
    }

    public Student getStudentWithMaxAverageGrade() {
        Comparator<Student> comparator = (s1, s2) -> Double.compare(s1.getAverageGrade(), s2.getAverageGrade());
        students.sort(comparator);
        return students.get(students.size()-1);
    }

    public Student getStudentWithMinAverageGrade() {
        Comparator<Student> comparator = (s1, s2) -> Double.compare(s1.getAverageGrade(), s2.getAverageGrade());
        students.sort(comparator);
         return students.get(0);
    }

    public void expel(Student student){
        students.remove(student);
    }
}