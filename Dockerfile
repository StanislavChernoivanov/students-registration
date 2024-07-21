FROM openjdk:22

WORKDIR /app

ENV STUDENT_ADDER=true
ENV SHELL_INTERACTIVE=true

COPY target/studentsRegistration-0.0.1-SNAPSHOT.jar /app/students-reg.jar

CMD ["java", "-jar", "students-reg.jar"]

