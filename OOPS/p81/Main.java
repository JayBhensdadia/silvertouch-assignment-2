import java.util.ArrayList;
import java.util.List;

class Room {
    private String roomId;
    private boolean isOccupied;

    public Room(String roomId) {
        this.roomId = roomId;
        this.isOccupied = false;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupyRoom() {
        isOccupied = true;
        System.out.println("Room " + roomId + " is now occupied.");
    }

    public void vacateRoom() {
        isOccupied = false;
        System.out.println("Room " + roomId + " is now vacant.");
    }
}

class Guest {
    private String guestId;
    private String guestName;

    public Guest(String guestId, String guestName) {
        this.guestId = guestId;
        this.guestName = guestName;
    }

    public String getGuestId() {
        return guestId;
    }

    public String getGuestName() {
        return guestName;
    }
}

class Booking {
    private String bookingId;
    private Guest guest;
    private Room room;

    public Booking(String bookingId, Guest guest, Room room) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
    }

    public String getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }
}

class Hotel {
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel() {
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
        initializeRooms();
    }

    private void initializeRooms() {
        for (int i = 1; i <= 10; i++) {
            Room room = new Room("R" + i);
            rooms.add(room);
        }
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (!room.isOccupied()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void bookRoom(Guest guest, Room room) {
        if (!room.isOccupied()) {
            room.occupyRoom();
            Booking booking = new Booking("B" + System.currentTimeMillis(), guest, room);
            bookings.add(booking);
            System.out.println("Booking successful. Booking ID: " + booking.getBookingId());
        } else {
            System.out.println("Room " + room.getRoomId() + " is already occupied.");
        }
    }

    public void checkOut(Guest guest) {
        for (Booking booking : bookings) {
            if (booking.getGuest().equals(guest)) {
                Room room = booking.getRoom();
                room.vacateRoom();
                bookings.remove(booking);
                System.out.println("Check-out successful for " + guest.getGuestName() +
                        ". Room " + room.getRoomId() + " is now vacant.");
                return;
            }
        }
        System.out.println("No active booking found for " + guest.getGuestName() + ".");
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a hotel
        Hotel hotel = new Hotel();

        // Display available rooms
        List<Room> availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + availableRooms.size());

        // Create guests
        Guest guest1 = new Guest("G001", "raman");
        Guest guest2 = new Guest("G002", "gunit");

        // Book rooms for guests
        hotel.bookRoom(guest1, availableRooms.get(0));
        hotel.bookRoom(guest2, availableRooms.get(1));

        // Display available rooms after bookings
        availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + availableRooms.size());

        // Check-out a guest
        hotel.checkOut(guest1);

        // Display available rooms after check-out
        availableRooms = hotel.getAvailableRooms();
        System.out.println("Available Rooms: " + availableRooms.size());
    }
}
