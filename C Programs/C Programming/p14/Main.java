import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private List<String> posts;

    public User(String username) {
        this.username = username;
        this.posts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addPost(String post) {
        posts.add(post);
    }

    public List<String> getPosts() {
        return posts;
    }
}

class SocialNetwork {
    private List<User> users;

    public SocialNetwork() {
        this.users = new ArrayList<>();
    }

    public void addUser(String username) {
        User user = new User(username);
        users.add(user);
        System.out.println("User added successfully.");
    }

    public void addPost(String username, String post) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.addPost(post);
                System.out.println("Post added successfully.");
                return;
            }
        }
        System.out.println("User not found.");
    }

    public void displayPosts(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                List<String> posts = user.getPosts();
                if (posts.isEmpty()) {
                    System.out.println("No posts available for user " + username);
                } else {
                    System.out.println("Posts for user " + username + ":");
                    for (String post : posts) {
                        System.out.println(post);
                        System.out.println("--------------------------");
                    }
                }
                return;
            }
        }
        System.out.println("User not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SocialNetwork socialNetwork = new SocialNetwork();

        while (true) {
            System.out.println("1. Add a new user");
            System.out.println("2. Add a post");
            System.out.println("3. Display user posts");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); 
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    socialNetwork.addUser(username);
                    break;
                case 2:
                    scanner.nextLine(); 
                    System.out.print("Enter username: ");
                    String userForPost = scanner.nextLine();
                    System.out.print("Enter post: ");
                    String post = scanner.nextLine();
                    socialNetwork.addPost(userForPost, post);
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Enter username: ");
                    String userToDisplayPosts = scanner.nextLine();
                    socialNetwork.displayPosts(userToDisplayPosts);
                    break;
                case 4:
                    System.out.println("Exiting the social networking application. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
