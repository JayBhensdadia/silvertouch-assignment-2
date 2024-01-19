import java.util.HashMap;
import java.util.Map;

enum VehicleType {
    CAR, MOTORCYCLE, TRUCK
}

class Vehicle {
    private String licensePlate;
    private VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

class Ticket {
    private Vehicle vehicle;
    private long entryTime;

    public Ticket(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.entryTime = System.currentTimeMillis();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getEntryTime() {
        return entryTime;
    }
}

class ParkingLot {
    private Map<String, Ticket> ticketMap;
    private Map<VehicleType, Integer> availableSpaces;

    public ParkingLot(int carSpaces, int motorcycleSpaces, int truckSpaces) {
        this.ticketMap = new HashMap<>();
        this.availableSpaces = new HashMap<>();
        availableSpaces.put(VehicleType.CAR, carSpaces);
        availableSpaces.put(VehicleType.MOTORCYCLE, motorcycleSpaces);
        availableSpaces.put(VehicleType.TRUCK, truckSpaces);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        if (availableSpaces.get(vehicle.getType()) > 0) {
            Ticket ticket = new Ticket(vehicle);
            ticketMap.put(vehicle.getLicensePlate(), ticket);
            availableSpaces.put(vehicle.getType(), availableSpaces.get(vehicle.getType()) - 1);
            System.out.println("Vehicle with license plate " + vehicle.getLicensePlate() +
                    " parked successfully.");
            return ticket;
        } else {
            System.out.println("No available space for vehicle type " + vehicle.getType());
            return null;
        }
    }

    public void retrieveVehicle(String licensePlate) {
        if (ticketMap.containsKey(licensePlate)) {
            Ticket ticket = ticketMap.get(licensePlate);
            long duration = System.currentTimeMillis() - ticket.getEntryTime();
            double parkingFee = calculateParkingFee(duration, ticket.getVehicle().getType());
            System.out.println("Vehicle with license plate " + licensePlate +
                    " retrieved. Parking fee: $" + parkingFee);
            ticketMap.remove(licensePlate);
            availableSpaces.put(ticket.getVehicle().getType(),
                    availableSpaces.get(ticket.getVehicle().getType()) + 1);
        } else {
            System.out.println("No ticket found for vehicle with license plate " + licensePlate);
        }
    }

    private double calculateParkingFee(long duration, VehicleType vehicleType) {

        double rate = 0.0;
        switch (vehicleType) {
            case CAR:
                rate = 0.05;
                break;
            case MOTORCYCLE:
                rate = 0.03;
                break;
            case TRUCK:
                rate = 0.08;
                break;
        }
        return duration * rate;
    }

    public void displayAvailableSpaces() {
        System.out.println("Available spaces:");
        for (Map.Entry<VehicleType, Integer> entry : availableSpaces.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {

        ParkingLot parkingLot = new ParkingLot(10, 5, 3);

        Vehicle car = new Vehicle("ABC123", VehicleType.CAR);
        Vehicle motorcycle = new Vehicle("XYZ456", VehicleType.MOTORCYCLE);
        Vehicle truck = new Vehicle("123DEF", VehicleType.TRUCK);

        Ticket carTicket = parkingLot.parkVehicle(car);
        Ticket motorcycleTicket = parkingLot.parkVehicle(motorcycle);
        Ticket truckTicket = parkingLot.parkVehicle(truck);

        parkingLot.displayAvailableSpaces();

        parkingLot.retrieveVehicle(car.getLicensePlate());
        parkingLot.retrieveVehicle(motorcycle.getLicensePlate());
        parkingLot.retrieveVehicle(truck.getLicensePlate());

        parkingLot.displayAvailableSpaces();
    }
}
