package com.example.studentsRegistration.Student;

import lombok.Data;

@Data
public class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;
}
