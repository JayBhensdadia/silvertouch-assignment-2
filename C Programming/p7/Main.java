import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Post {
    private String title;
    private String content;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}

class Blog {
    private List<Post> posts;

    public Blog() {
        this.posts = new ArrayList<>();
    }

    public void createPost(String title, String content) {
        Post post = new Post(title, content);
        posts.add(post);
        System.out.println("New Post created successfully!");
    }

    public void viewAllPosts() {
        if (posts.isEmpty()) {
            System.out.println("No posts available.");
        } else {
            System.out.println("All Posts:");
            for (Post post : posts) {
                System.out.println("Title: " + post.getTitle());
                System.out.println("Content: " + post.getContent());
                System.out.println("--------------------------");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Blog myBlog = new Blog();

        while (true) {
            System.out.println("1. Create a new post");
            System.out.println("2. View all posts");
            System.out.println("3. Exit \n");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();
                    System.out.print("Enter post title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter post content: ");
                    String content = scanner.nextLine();
                    myBlog.createPost(title, content);
                    break;
                case 2:
                    myBlog.viewAllPosts();
                    break;
                case 3:
                    System.out.println("Exiting the blogging platform. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
