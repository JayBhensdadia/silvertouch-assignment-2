import java.util.ArrayList;
import java.util.List;

class Flight {
    private String flightNumber;
    private int totalSeats;
    private int availableSeats;
    private List<Reservation> reservations;

    public Flight(String flightNumber, int totalSeats) {
        this.flightNumber = flightNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.reservations = new ArrayList<Reservation>();
    }

    public List<Reservation> getReservations(){
        return reservations;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public boolean bookSeat(Passenger passenger) {
        if (availableSeats > 0) {
            Reservation reservation = new Reservation(passenger, this);
            reservations.add(reservation);
            availableSeats--;
            System.out.println("Seat booked for passenger " + passenger.getName() +
                    " on flight " + flightNumber);
            return true;
        } else {
            System.out.println("Sorry, no available seats on flight " + flightNumber);
            return false;
        }
    }

    public boolean cancelReservation(Reservation reservation) {
        if (reservations.contains(reservation)) {
            reservations.remove(reservation);
            availableSeats++;
            System.out.println("Reservation canceled for passenger " +
                    reservation.getPassenger().getName() + " on flight " + flightNumber);
            return true;
        } else {
            System.out.println("Reservation not found for passenger " +
                    reservation.getPassenger().getName() + " on flight " + flightNumber);
            return false;
        }
    }

    public void displayReservations() {
        System.out.println("Reservations for flight " + flightNumber + ":");
        for (Reservation reservation : reservations) {
            System.out.println(reservation.toString());
        }
    }
}

class Passenger {
    private String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Reservation {
    private Passenger passenger;
    private Flight flight;

    public Reservation(Passenger passenger, Flight flight) {
        this.passenger = passenger;
        this.flight = flight;
    }

    public Reservation getReservations(){
        return this;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    @Override
    public String toString() {
        return "Reservation: Passenger " + passenger.getName() +
                " on Flight " + flight.getFlightNumber();
    }
}

public class Main {
    public static void main(String[] args) {
        
        Flight flight1 = new Flight("F001", 100);
        Flight flight2 = new Flight("F002", 50);

        
        Passenger passenger1 = new Passenger("John Doe");
        Passenger passenger2 = new Passenger("Jane Smith");

        
        flight1.bookSeat(passenger1);
        flight1.bookSeat(passenger2);
        flight2.bookSeat(passenger1);

        
        flight1.displayReservations();
        flight2.displayReservations();

        
        Reservation reservationToCancel = flight1.getReservations().get(0);
        flight1.cancelReservation(reservationToCancel);

        
        flight1.displayReservations();
        flight2.displayReservations();
    }
}
