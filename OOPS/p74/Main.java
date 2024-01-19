import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Movie {
    private String movieId;
    private String title;
    private String genre;
    private int durationMinutes;

    public Movie(String movieId, String title, String genre, int durationMinutes) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }
}

class Ticket {
    private String ticketId;
    private Movie movie;
    private String showtime;
    private double price;

    public Ticket(String ticketId, Movie movie, String showtime, double price) {
        this.ticketId = ticketId;
        this.movie = movie;
        this.showtime = showtime;
        this.price = price;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowtime() {
        return showtime;
    }

    public double getPrice() {
        return price;
    }
}

class Customer {
    private String customerId;
    private String name;
    private List<Ticket> bookedTickets;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.bookedTickets = new ArrayList<>();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void bookTicket(Ticket ticket) {
        bookedTickets.add(ticket);
        System.out.println(name + " booked a ticket for " + ticket.getMovie().getTitle() +
                " at " + ticket.getShowtime());
    }

    public List<Ticket> getBookedTickets() {
        return bookedTickets;
    }
}

class Cinema {
    private List<Movie> movies;
    private Map<String, List<Ticket>> showtimes;

    public Cinema() {
        this.movies = new ArrayList<>();
        this.showtimes = new HashMap<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addShowtime(Movie movie, String showtime) {
        if (!showtimes.containsKey(movie.getMovieId())) {
            showtimes.put(movie.getMovieId(), new ArrayList<>());
        }
        showtimes.get(movie.getMovieId()).add(new Ticket(generateTicketId(), movie, showtime, 10.0));
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Ticket> getShowtimes(String movieId) {
        return showtimes.getOrDefault(movieId, new ArrayList<>());
    }

    private String generateTicketId() {
        
        return "T" + System.currentTimeMillis();
    }
}

public class Main {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        Movie movie1 = new Movie("M001", "Inception", "Sci-Fi", 150);
        Movie movie2 = new Movie("M002", "The Dark Knight", "Action", 152);

        cinema.addMovie(movie1);
        cinema.addMovie(movie2);

        cinema.addShowtime(movie1, "2022-02-01 18:00");
        cinema.addShowtime(movie1, "2022-02-01 20:30");
        cinema.addShowtime(movie2, "2022-02-02 15:00");
        cinema.addShowtime(movie2, "2022-02-02 18:30");

        Customer customer1 = new Customer("C001", "Alice");
        Customer customer2 = new Customer("C002", "Bob");

        List<Ticket> showtimesMovie1 = cinema.getShowtimes(movie1.getMovieId());
        List<Ticket> showtimesMovie2 = cinema.getShowtimes(movie2.getMovieId());

        
        customer1.bookTicket(showtimesMovie1.get(0));
        customer2.bookTicket(showtimesMovie2.get(1));

        
        System.out.println("\nBooked tickets for " + customer1.getName() + ":");
        for (Ticket ticket : customer1.getBookedTickets()) {
            System.out.println(ticket.getMovie().getTitle() + " at " + ticket.getShowtime());
        }

        System.out.println("\nBooked tickets for " + customer2.getName() + ":");
        for (Ticket ticket : customer2.getBookedTickets()) {
            System.out.println(ticket.getMovie().getTitle() + " at " + ticket.getShowtime());
        }
    }
}

