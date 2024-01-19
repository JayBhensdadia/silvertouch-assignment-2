import java.util.ArrayList;
import java.util.List;

class Activity {
    private String name;
    private double duration; // in minutes
    private double distance; // in kilometers

    public Activity(String name, double duration, double distance) {
        this.name = name;
        this.duration = duration;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }
}

class User {
    private String username;
    private List<Activity> activities;
    private double totalDuration;
    private double totalDistance;

    public User(String username) {
        this.username = username;
        this.activities = new ArrayList<>();
        this.totalDuration = 0;
        this.totalDistance = 0;
    }

    public String getUsername() {
        return username;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        totalDuration += activity.getDuration();
        totalDistance += activity.getDistance();
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public double getTotalDistance() {
        return totalDistance;
    }
}

class Tracker {
    private List<User> users;

    public Tracker() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void trackActivity(User user, Activity activity) {
        user.addActivity(activity);
        System.out.println(user.getUsername() + " completed " + activity.getName() +
                " - Duration: " + activity.getDuration() + " minutes, Distance: " + activity.getDistance() + " km");
    }

    public void generateReport(User user) {
        System.out.println("Activity Report for " + user.getUsername());
        System.out.println("Total Duration: " + user.getTotalDuration() + " minutes");
        System.out.println("Total Distance: " + user.getTotalDistance() + " km");
        System.out.println("------------------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a fitness tracker
        Tracker fitnessTracker = new Tracker();

        // Create users
        User user1 = new User("raman");
        User user2 = new User("boman");

        // Add users to the tracker
        fitnessTracker.addUser(user1);
        fitnessTracker.addUser(user2);

        // Track activities
        Activity running = new Activity("Running", 30, 5);
        Activity cycling = new Activity("Cycling", 45, 10);

        fitnessTracker.trackActivity(user1, running);
        fitnessTracker.trackActivity(user1, cycling);
        fitnessTracker.trackActivity(user2, running);

        // Generate reports
        fitnessTracker.generateReport(user1);
        fitnessTracker.generateReport(user2);
    }
}
