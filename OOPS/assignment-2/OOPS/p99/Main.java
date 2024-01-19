import java.util.ArrayList;
import java.util.List;

class Course {
    private String courseId;
    private String courseName;
    private List<Assignment> assignments;

    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.assignments = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}

class Student {
    private String studentId;
    private String studentName;
    private List<Course> enrolledCourses;

    public Student(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void completeAssignment(Assignment assignment) {
        System.out.println("Assignment completed by student: " + studentName);
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
}

class Instructor {
    private String instructorId;
    private String instructorName;

    public Instructor(String instructorId, String instructorName) {
        this.instructorId = instructorId;
        this.instructorName = instructorName;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void gradeAssignment(Assignment assignment, Student student, int score) {
        System.out.println("Assignment graded for student: " + student.getStudentName() +
                ", Score: " + score);
    }
}

class Assignment {
    private String assignmentId;
    private String assignmentName;

    public Assignment(String assignmentId, String assignmentName) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }
}

public class Main {
    public static void main(String[] args) {
        Course javaCourse = new Course("C001", "Java Programming");
        Course pythonCourse = new Course("C002", "Python Programming");

        Assignment javaAssignment = new Assignment("A001", "Java Assignment 1");
        Assignment pythonAssignment = new Assignment("A002", "Python Assignment 1");

        javaCourse.addAssignment(javaAssignment);
        pythonCourse.addAssignment(pythonAssignment);

        Student student1 = new Student("S001", "ramesh");
        Student student2 = new Student("S002", "manih");

        Instructor instructor = new Instructor("I001", "Professor mahesh");

        student1.enrollInCourse(javaCourse);
        student2.enrollInCourse(pythonCourse);

        student1.completeAssignment(javaAssignment);
        student2.completeAssignment(pythonAssignment);

        instructor.gradeAssignment(javaAssignment, student1, 78);
        instructor.gradeAssignment(pythonAssignment, student2, 85);
    }
}
