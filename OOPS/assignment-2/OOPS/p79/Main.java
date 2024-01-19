import java.util.ArrayList;
import java.util.List;

class Guest {
    private String guestName;
    private String contactNumber;

    public Guest(String guestName, String contactNumber) {
        this.guestName = guestName;
        this.contactNumber = contactNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}

class Room {
    private int roomNumber;
    private boolean isReserved;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isReserved = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserveRoom() {
        isReserved = true;
    }

    public void unreserveRoom() {
        isReserved = false;
    }
}

class Reservation {
    private Guest guest;
    private Room room;
    private int numberOfGuests;
    private int numberOfNights;

    public Reservation(Guest guest, Room room, int numberOfGuests, int numberOfNights) {
        this.guest = guest;
        this.room = room;
        this.numberOfGuests = numberOfGuests;
        this.numberOfNights = numberOfNights;
        room.reserveRoom();
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public int getNumberOfNights() {
        return numberOfNights;
    }

    public double calculateTotalCost(double costPerNight) {
        return numberOfNights * costPerNight;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private double costPerNight;

    public Hotel(int numberOfRooms, double costPerNight) {
        this.costPerNight = costPerNight;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();

        // Initialize rooms
        for (int i = 1; i <= numberOfRooms; i++) {
            rooms.add(new Room(i));
        }
    }

    public boolean checkAvailability() {
        return rooms.stream().anyMatch(room -> !room.isReserved());
    }

    public Room bookRoom(Guest guest, int numberOfGuests, int numberOfNights) {
        if (checkAvailability()) {
            Room availableRoom = rooms.stream()
                    .filter(room -> !room.isReserved())
                    .findFirst()
                    .orElse(null);

            if (availableRoom != null) {
                Reservation reservation = new Reservation(guest, availableRoom, numberOfGuests, numberOfNights);
                reservations.add(reservation);
                return availableRoom;
            }
        }

        return null;
    }

    public void displayReservations() {
        System.out.println("Hotel Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println("Guest: " + reservation.getGuest().getGuestName() +
                    ", Room: " + reservation.getRoom().getRoomNumber() +
                    ", Nights: " + reservation.getNumberOfNights() +
                    ", Total Cost: " + reservation.calculateTotalCost(costPerNight));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(10, 100.0);

        Guest guest1 = new Guest("raman", "123-456-7890");
        Guest guest2 = new Guest("bamn", "987-654-3210");

        Room room1 = hotel.bookRoom(guest1, 2, 3);
        Room room2 = hotel.bookRoom(guest2, 1, 2);

        hotel.displayReservations();
    }
}
