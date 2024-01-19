import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Author {
    private String authorName;

    public Author(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return authorName;
    }
}

class Book {
    private String bookTitle;
    private Author author;
    private boolean available;

    public Book(String bookTitle, Author author) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.available = true;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrowBook() {
        if (available) {
            available = false;
            System.out.println("Book '" + bookTitle + "' borrowed successfully.");
        } else {
            System.out.println("Sorry, the book '" + bookTitle + "' is currently not available.");
        }
    }

    public void returnBook() {
        available = true;
        System.out.println("Book '" + bookTitle + "' returned successfully.");
    }
}

class Member {
    private String memberId;
    private String memberName;
    private List<Book> borrowedBooks;

    public Member(String memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (!book.isAvailable()) {
            System.out.println("Sorry, the book '" + book.getBookTitle() + "' is currently not available.");
        } else if (borrowedBooks.size() >= 3) {
            System.out.println("You have reached the maximum limit of borrowed books (3). Please return some books before borrowing more.");
        } else {
            book.borrowBook();
            borrowedBooks.add(book);
            System.out.println("Book '" + book.getBookTitle() + "' added to your borrowed books.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.returnBook();
            borrowedBooks.remove(book);
        } else {
            System.out.println("You did not borrow the book '" + book.getBookTitle() + "'.");
        }
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book '" + book.getBookTitle() + "' added to the library.");
    }

    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member '" + member.getMemberName() + "' added to the library.");
    }

    public void displayBooks() {
        System.out.println("Books available in the library:");
        for (Book book : books) {
            String status = book.isAvailable() ? "Available" : "Not Available";
            System.out.println("Title: " + book.getBookTitle() + ", Author: " + book.getAuthor().getAuthorName() + ", Status: " + status);
        }
    }

    public void displayMembers() {
        System.out.println("Members in the library:");
        for (Member member : members) {
            System.out.println("ID: " + member.getMemberId() + ", Name: " + member.getMemberName());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Author author1 = new Author("Author One");
        Author author2 = new Author("Author Two");

        Book book1 = new Book("Book One", author1);
        Book book2 = new Book("Book Two", author2);
        Book book3 = new Book("Book Three", author1);

        Member member1 = new Member("M001", "Member One");
        Member member2 = new Member("M002", "Member Two");

        Library library = new Library();

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.addMember(member1);
        library.addMember(member2);

        library.displayBooks();
        library.displayMembers();

        member1.borrowBook(book1);
        member1.borrowBook(book2);
        member1.borrowBook(book3);
        member1.borrowBook(book3);  

        member1.returnBook(book2);

        library.displayBooks();
    }
}
