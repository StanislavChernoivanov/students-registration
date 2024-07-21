package com.example.studentsRegistration.Event.Holder;

import com.example.studentsRegistration.Student.Student;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
@Getter
public class StudentEventHolder extends ApplicationEvent {
    private final Student student;
    public StudentEventHolder(Object source, Student student) {
        super(source);
        this.student = student;
    }
}
