import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Movie {
    private String title;
    private int availableSeats;

    public Movie(String title, int availableSeats) {
        this.title = title;
        this.availableSeats = availableSeats;
    }

    public String getTitle() {
        return title;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        if (numSeats > 0 && numSeats <= availableSeats) {
            availableSeats -= numSeats;
            System.out.println("Booking successful for movie " + title + ". Enjoy the show!");
        } else {
            System.out.println("Booking failed. Not enough available seats.");
        }
    }
}

class BookingSystem {
    private Map<String, Movie> movies;

    public BookingSystem() {
        this.movies = new HashMap<>();
    }

    public void addMovie(String title, int availableSeats) {
        Movie movie = new Movie(title, availableSeats);
        movies.put(title, movie);
        System.out.println("Movie added successfully.");
    }

    public void displayMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            System.out.println("Available Movies:");
            for (Movie movie : movies.values()) {
                System.out.println("Title: " + movie.getTitle());
                System.out.println("Available Seats: " + movie.getAvailableSeats());
                System.out.println("--------------------------");
            }
        }
    }

    public void bookSeats(String movieTitle, int numSeats) {
        Movie movie = movies.get(movieTitle);
        if (movie != null) {
            movie.bookSeats(numSeats);
        } else {
            System.out.println("Movie not found.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem bookingSystem = new BookingSystem();

        while (true) {
            System.out.println("1. Add a new movie");
            System.out.println("2. Display available movies");
            System.out.println("3. Book seats");
            System.out.println("4. Exit");
            System.out.print("Enter your Choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); 
                    System.out.print("Enter movie title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter available seats: ");
                    int availableSeats = scanner.nextInt();
                    bookingSystem.addMovie(title, availableSeats);
                    break;
                case 2:
                    bookingSystem.displayMovies();
                    break;
                case 3:
                    scanner.nextLine(); 
                    System.out.print("Enter movie title: ");
                    String movieTitle = scanner.nextLine();
                    System.out.print("Enter number of seats to book: ");
                    int numSeats = scanner.nextInt();
                    bookingSystem.bookSeats(movieTitle, numSeats);
                    break;
                case 4:
                    System.out.println("Exiting the movie ticket booking system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}

