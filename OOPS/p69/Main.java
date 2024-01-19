import java.util.ArrayList;
import java.util.List;

class Event {
    private String eventName;
    private Organizer organizer;
    private List<Attendee> attendees;

    public Event(String eventName, Organizer organizer) {
        this.eventName = eventName;
        this.organizer = organizer;
        this.attendees = new ArrayList<>();
    }

    public String getEventName() {
        return eventName;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public List<Attendee> getAttendees() {
        return attendees;
    }

    public void registerAttendee(Attendee attendee) {
        if (!attendees.contains(attendee)) {
            attendees.add(attendee);
            System.out.println(attendee.getName() + " registered for " + eventName);
        } else {
            System.out.println(attendee.getName() + " is already registered for " + eventName);
        }
    }

    public void sendNotification(String message) {
        System.out.println("Notification sent to attendees of " + eventName + ": " + message);
        for (Attendee attendee : attendees) {
            attendee.receiveNotification(eventName, message);
        }
    }
}

class Attendee {
    private String name;

    public Attendee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void receiveNotification(String eventName, String message) {
        System.out.println(name + " received notification for " + eventName + ": " + message);
    }
}

class Organizer {
    private String name;
    List<Event> l = new ArrayList<>();

    public Organizer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void organizeEvent(String eventName) {
        Event event = new Event(eventName, this);
        System.out.println("Event '" + eventName + "' organized by " + name);
        l.add(event);

    }

    public List<Event> getOrganizedEvents() {
        return l;
    }
}

public class Main {
    public static void main(String[] args) {

        Organizer organizer = new Organizer("Organizer1");

        organizer.organizeEvent("Java Meetup");

        Attendee attendee1 = new Attendee("Attendee1");
        Attendee attendee2 = new Attendee("Attendee2");

        Event javaMeetup = organizer.getOrganizedEvents().get(0);
        javaMeetup.registerAttendee(attendee1);
        javaMeetup.registerAttendee(attendee2);

        javaMeetup.sendNotification("Reminder: Java Meetup tomorrow at 3 PM");
    }
}
