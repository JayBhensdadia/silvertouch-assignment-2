import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student {
    private String name;
    private int age;
    private String rollNumber;

    public Student(String name, int age, String rollNumber) {
        this.name = name;
        this.age = age;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getRollNumber() {
        return rollNumber;
    }
}

class StudentInformationSystem {
    private List<Student> students;

    public StudentInformationSystem() {
        this.students = new ArrayList<>();
    }

    public void addStudent(String name, int age, String rollNumber) {
        Student student = new Student(name, age, rollNumber);
        students.add(student);
        System.out.println("Student added successfully.");
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("All Students:");
            for (Student student : students) {
                System.out.println("Name: " + student.getName());
                System.out.println("Age: " + student.getAge());
                System.out.println("Roll Number: " + student.getRollNumber());
                System.out.println("--------------------------");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentInformationSystem sis = new StudentInformationSystem();

        while (true) {
            System.out.println("1. Add a new student");
            System.out.println("2. Display all students");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); 
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter student age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter student roll number: ");
                    String rollNumber = scanner.nextLine();
                    sis.addStudent(name, age, rollNumber);
                    break;
                case 2:
                    sis.displayAllStudents();
                    break;
                case 3:
                    System.out.println("Exiting the student information system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
