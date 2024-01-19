import java.util.ArrayList;
import java.util.List;

class User {
    private String userId;
    private String userName;
    private List<User> friends;
    private List<Post> posts;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.friends = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void createPost(String content) {
        Post post = new Post(this, content);
        posts.add(post);
    }
}

class Post {
    private User author;
    private String content;

    public Post(User author, String content) {
        this.author = author;
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}

class Network {
    private List<User> users;
    private List<Post> posts;

    public Network() {
        this.users = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public List<User> findInfluentialUsers() {
        List<User> influentialUsers = new ArrayList<>();
        int maxFriendsCount = 0;

        for (User user : users) {
            int friendsCount = user.getFriends().size();
            if (friendsCount > maxFriendsCount) {
                influentialUsers.clear();
                influentialUsers.add(user);
                maxFriendsCount = friendsCount;
            } else if (friendsCount == maxFriendsCount) {
                influentialUsers.add(user);
            }
        }

        return influentialUsers;
    }

    public List<Post> findPopularPosts() {
        return posts;
    }
}

public class Main {
    public static void main(String[] args) {
        User user1 = new User("U001", "Alice");
        User user2 = new User("U002", "Bob");
        User user3 = new User("U003", "Charlie");

        Network socialNetwork = new Network();

        socialNetwork.addUser(user1);
        socialNetwork.addUser(user2);
        socialNetwork.addUser(user3);

        user1.addFriend(user2);
        user1.addFriend(user3);
        user2.addFriend(user1);
        user3.addFriend(user1);

        user1.createPost("Hello, world!");
        user2.createPost("Java is fun!");
        user3.createPost("Just finished a coding challenge.");

        List<User> influentialUsers = socialNetwork.findInfluentialUsers();
        System.out.println("Influential Users:");
        for (User user : influentialUsers) {
            System.out.println(user.getUserName());
        }

        List<Post> popularPosts = socialNetwork.findPopularPosts();
        System.out.println("\nPopular Posts:");
        for (Post post : popularPosts) {
            System.out.println(post.getAuthor().getUserName() + ": " + post.getContent());
        }
    }
}
