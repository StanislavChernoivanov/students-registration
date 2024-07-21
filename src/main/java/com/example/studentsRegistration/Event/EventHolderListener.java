package com.example.studentsRegistration.Event;

import com.example.studentsRegistration.Event.Holder.AddStudentEventHolder;
import com.example.studentsRegistration.Event.Holder.RemoveStudentEventHolder;
import com.example.studentsRegistration.Event.Holder.StudentEventHolder;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHolderListener {
    @EventListener
    public void listenAddStudent(AddStudentEventHolder eventHolder) {
        System.out.println(eventHolder.getStudent().toString());
    }
    @EventListener
    public void listenRemoveStudent(RemoveStudentEventHolder eventHolder) {
        System.out.println(eventHolder.getStudent().getId());
    }
}
