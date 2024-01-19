import java.util.ArrayList;
import java.util.List;

class Destination {
    private String name;
    private String description;

    public Destination(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

class Itinerary {
    private List<Destination> destinations;

    public Itinerary() {
        this.destinations = new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }

    public List<Destination> getDestinations() {
        return destinations;
    }
}

class Traveler {
    private String name;
    private Itinerary itinerary;

    public Traveler(String name) {
        this.name = name;
        this.itinerary = new Itinerary();
    }

    public String getName() {
        return name;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void planTrip(Destination destination) {
        itinerary.addDestination(destination);
        System.out.println(name + " has added " + destination.getName() + " to the itinerary.");
    }

    public void generateItinerary() {
        List<Destination> destinations = itinerary.getDestinations();

        System.out.println("Travel Itinerary for " + name + ":");
        for (int i = 0; i < destinations.size(); i++) {
            System.out.println((i + 1) + ". " + destinations.get(i).getName() + ": " +
                    destinations.get(i).getDescription());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Destination beach = new Destination("Beach Resort", "Relax on the sandy beaches.");
        Destination mountains = new Destination("Mountain Retreat", "Explore scenic mountain trails.");

        Traveler traveler1 = new Traveler("Amit");
        Traveler traveler2 = new Traveler("Bina");

        traveler1.planTrip(beach);
        traveler2.planTrip(mountains);

        traveler1.generateItinerary();
        traveler2.generateItinerary();
    }
}
