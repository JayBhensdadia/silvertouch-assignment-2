import java.util.ArrayList;
import java.util.List;

class Aircraft {
    private String aircraftId;
    private String model;
    private boolean inFlight;

    public Aircraft(String aircraftId, String model) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.inFlight = false;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public String getModel() {
        return model;
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public void takeOff() {
        if (!inFlight) {
            inFlight = true;
            System.out.println("Aircraft " + aircraftId + " has taken off.");
        } else {
            System.out.println("Aircraft " + aircraftId + " is already in flight.");
        }
    }

    public void land() {
        if (inFlight) {
            inFlight = false;
            System.out.println("Aircraft " + aircraftId + " has landed.");
        } else {
            System.out.println("Aircraft " + aircraftId + " is not in flight.");
        }
    }
}

class Airport {
    private String airportCode;
    private List<Aircraft> parkedAircraft;

    public Airport(String airportCode) {
        this.airportCode = airportCode;
        this.parkedAircraft = new ArrayList<>();
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void parkAircraft(Aircraft aircraft) {
        parkedAircraft.add(aircraft);
        System.out.println("Aircraft " + aircraft.getAircraftId() + " parked at " + airportCode);
    }

    public void releaseAircraft(Aircraft aircraft) {
        parkedAircraft.remove(aircraft);
        System.out.println("Aircraft " + aircraft.getAircraftId() + " released from " + airportCode);
    }

    public List<Aircraft> getParkedAircraft() {
        return parkedAircraft;
    }
}

class Flight {
    private String flightNumber;
    private Airport departureAirport;
    private Airport arrivalAirport;

    public Flight(String flightNumber, Airport departureAirport, Airport arrivalAirport) {
        this.flightNumber = flightNumber;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public void scheduleFlight() {
        System.out.println("Flight " + flightNumber + " scheduled from " +
                departureAirport.getAirportCode() + " to " + arrivalAirport.getAirportCode());
    }

    public void cancelFlight() {
        System.out.println("Flight " + flightNumber + " cancelled.");
    }
}

public class Main {
    public static void main(String[] args) {
        Airport airport1 = new Airport("JFK");
        Airport airport2 = new Airport("LAX");

        Aircraft aircraft1 = new Aircraft("A001", "Boeing 737");
        Aircraft aircraft2 = new Aircraft("A002", "Airbus A320");

        airport1.parkAircraft(aircraft1);
        airport2.parkAircraft(aircraft2);

        Flight flight1 = new Flight("F001", airport1, airport2);
        flight1.scheduleFlight();

        aircraft1.takeOff();
        aircraft1.land();

        airport1.releaseAircraft(aircraft1);
        airport2.releaseAircraft(aircraft2);

        flight1.cancelFlight();
    }
}
