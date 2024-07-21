package com.example.studentsRegistration.Student;
import com.example.studentsRegistration.Event.Holder.AddStudentEventHolder;
import com.example.studentsRegistration.Event.Holder.RemoveStudentEventHolder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ShellComponent
public class StudentWorker {

    private final Map<Integer, Student> studentsList = new HashMap<>();
    private final ApplicationEventPublisher publisher;

    private final Random idGenerator = new Random();
    @Value("${app.studentAdder.enabled}")
    private boolean isAddStudentEnable;
    @Autowired
    public StudentWorker(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @ShellMethod(key = "show")
    public void showStudents() {
        StringBuilder builder = new StringBuilder();
        studentsList.values().forEach(s -> builder.append(s.toString()).append("\n"));
        System.out.println(builder);
    }
    @ShellMethod(key = "add")
    @ShellMethodAvailability(value = {"canAddStudent"})
    public void addStudent(String firstName, String lastName, int age){
        String studentData = firstName + " " + lastName + " " + age;
        String regex = "^[A-Za-zа-яёА-ЯЁ]+\\s[A-Za-zа-яёА-ЯЁ]+\\s\\d+$";
        if(!studentData.matches(regex)) {
            System.out.println("invalid student's data format");
        }
        else {
            int studentId = idGenerator.nextInt(10_000);
            Student student = new Student(studentId, firstName, lastName, age);
            studentsList.put(studentId, student);
            publisher.publishEvent(new AddStudentEventHolder(this, student));
        }
    }
    @ShellMethod(key = "remove")
    public void removeStudent(int id) {
        if(studentsList.keySet().stream().noneMatch(k -> k==id))
            System.out.println("there isn't that id in student's list");
        else {
            Student student = studentsList.get(id);
            studentsList.remove(id);
            publisher.publishEvent(new RemoveStudentEventHolder(this, student));
        }
    }
    @ShellMethod(key = "clearlist")
    public void clearStudentList() {
        studentsList.clear();
    }

    @ShellMethod(key = "instruction")
    public void printInstruction() {
        System.out.println( "Приложение \"Учет студентов\"\n" +
                "\tдоступные команды:\n" +
                "\t\tshow - показывает полный список студентов\n" +
                "\t\tadd * ** *** - добавляет в список нового студента\n" +
                "\t\tremove **** - удаляет из списка студента\n" +
                "\t\tclearlist - очищает список\n" +
                "\t* - имя, ** - фамилия, *** - возраст, **** id студента");

    }

    private Availability canAddStudent() {
            return isAddStudentEnable ? Availability.available()
                : Availability.unavailable("property app.studentCreater.enabled is false");
    }
}
