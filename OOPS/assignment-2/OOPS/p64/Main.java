import java.util.ArrayList;
import java.util.List;

class Post {
    private String content;
    private String author;
    private String timestamp;

    public Post(String content, String author, String timestamp) {
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getTimestamp(){
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + author + ": " + content;
    }
}

class Friendship {
    private User user1;
    private User user2;

    public Friendship(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }
}

class User {
    private String username;
    private List<Post> posts;
    private List<Friendship> friends;

    public User(String username) {
        this.username = username;
        this.posts = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public void postMessage(String content) {
        Post post = new Post(content, username, getCurrentTimestamp());
        posts.add(post);
        System.out.println("Message posted: " + post.toString());
    }

    public void addFriend(User friend) {
        Friendship friendship = new Friendship(this, friend);
        friends.add(friendship);
        System.out.println(username + " added " + friend.getUsername() + " as a friend.");
    }

    public List<Post> getTimeline() {
        List<Post> timeline = new ArrayList<>(posts);
        for (Friendship friendship : friends) {
            User friend = friendship.getUser2();
            timeline.addAll(friend.posts);
        }
        timeline.sort((post1, post2) -> post2.getTimestamp().compareTo(post1.getTimestamp()));
        return timeline;
    }

    public String getUsername() {
        return username;
    }

    private String getCurrentTimestamp() {
        
        return String.valueOf(System.currentTimeMillis());
    }
}

public class Main {
    public static void main(String[] args) {
        
        User alice = new User("Alice");
        User bob = new User("Bob");
        User charlie = new User("Charlie");

        
        alice.postMessage("Hello, everyone!");

        
        bob.addFriend(alice);

        
        charlie.postMessage("Enjoying the day!");

        
        bob.addFriend(charlie);

        
        List<Post> bobTimeline = bob.getTimeline();
        System.out.println("\nBob's Timeline:");
        for (Post post : bobTimeline) {
            System.out.println(post);
        }
    }
}

