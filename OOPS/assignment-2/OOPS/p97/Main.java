import java.util.ArrayList;
import java.util.List;

class User {
    private String userId;
    private String username;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}

class Message {
    private String messageId;
    private String content;
    private User sender;
    private long timestamp;

    public Message(String messageId, String content, User sender, long timestamp) {
        this.messageId = messageId;
        this.content = content;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

class ChatRoom {
    private String roomId;
    private List<User> users;
    private List<Message> messageHistory;

    public ChatRoom(String roomId) {
        this.roomId = roomId;
        this.users = new ArrayList<>();
        this.messageHistory = new ArrayList<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void sendMessage(User sender, String content) {
        long timestamp = System.currentTimeMillis();
        Message message = new Message("M" + timestamp, content, sender, timestamp);
        messageHistory.add(message);

        for (User user : users) {
            if (!user.getUserId().equals(sender.getUserId())) {
                System.out.println("New Message from " + sender.getUsername() + ": " + content);
            }
        }
    }

    public List<Message> getMessageHistory() {
        return messageHistory;
    }
}

public class Main {
    public static void main(String[] args) {
        User user1 = new User("A1", "Amit");
        User user2 = new User("A1", "Naman");

        ChatRoom generalChat = new ChatRoom("C001");

        generalChat.addUser(user1);
        generalChat.addUser(user2);

        generalChat.sendMessage(user1, "Hello, everyone!");
        generalChat.sendMessage(user2, "Hi, Amit!");

        List<Message> messageHistory = generalChat.getMessageHistory();
        System.out.println("Message History in Chat Room " + generalChat.getRoomId() + ":");
        for (Message message : messageHistory) {
            System.out.println(
                    message.getSender().getUsername() + ": " + message.getContent()
                            
            );
        }
    }
}
