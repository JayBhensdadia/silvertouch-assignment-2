import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Question {
    private String text;
    private int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public String getText() {
        return text;
    }

    public int getPoints() {
        return points;
    }
}

class Quiz {
    private List<Question> questions;

    public Quiz(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double takeQuiz(Quiz quiz) {
        Scanner scanner = new Scanner(System.in);
        double totalPoints = 0;

        System.out.println("Quiz for " + name + ":");
        for (Question question : quiz.getQuestions()) {
            System.out.println(question.getText());
            System.out.print("Enter your answer (A, B, C, or D): ");
            String studentAnswer = scanner.nextLine().toUpperCase();

            
            String correctAnswer = "A";
            if (studentAnswer.equals(correctAnswer)) {
                totalPoints += question.getPoints();
            }
        }

        System.out.println(name + "'s quiz is completed. Total points: " + totalPoints);
        return totalPoints;
    }
}

public class Main {
    public static void main(String[] args) {
        
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", 2));
        questions.add(new Question("Who wrote Romeo and Juliet?", 3));
        questions.add(new Question("What is the largest planet in our solar system?", 4));

        
        Quiz quiz = new Quiz(questions);

        
        Student student1 = new Student("Student1");
        Student student2 = new Student("Student2");

        
        Collections.shuffle(questions);

        
        double score1 = student1.takeQuiz(quiz);
        double score2 = student2.takeQuiz(quiz);

        
        if (score1 > score2) {
            System.out.println(student1.getName() + " has a higher score!");
        } else if (score2 > score1) {
            System.out.println(student2.getName() + " has a higher score!");
        } else {
            System.out.println("It's a tie! Both students have the same score.");
        }
    }
}

