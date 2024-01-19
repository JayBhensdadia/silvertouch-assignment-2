import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author: " + name;
    }
}

class Book {
    private String title;
    private Author author;
    private boolean checkedOut;
    private LocalDate dueDate;

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
        this.checkedOut = false;
        this.dueDate = null;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void checkOut(int daysToReturn) {
        if (!checkedOut) {
            checkedOut = true;
            dueDate = LocalDate.now().plusDays(daysToReturn);
            System.out.println("Book checked out: " + title);
        } else {
            System.out.println("Book is already checked out.");
        }
    }

    public void returnBook() {
        if (checkedOut) {
            checkedOut = false;
            dueDate = null;
            System.out.println("Book returned: " + title);
        } else {
            System.out.println("Book is not checked out.");
        }
    }

    public boolean isOverdue() {
        if (checkedOut && LocalDate.now().isAfter(dueDate)) {
            System.out.println("Book is overdue!");
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Book: " + title + " by " + author.getName() + " - Checked Out: " + checkedOut;
    }
}

class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchBooksByAuthor(Author author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public void displayBooks() {
        System.out.println("Library Books:");
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");

        Book book1 = new Book("Book1", author1);
        Book book2 = new Book("Book2", author1);
        Book book3 = new Book("Book3", author2);

        Library library = new Library();
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        library.displayBooks();

        Author searchAuthor = author1;
        List<Book> booksByAuthor = library.searchBooksByAuthor(searchAuthor);
        System.out.println("\nBooks by " + searchAuthor.getName() + ":");
        for (Book book : booksByAuthor) {
            System.out.println(book.toString());
        }

        book1.checkOut(14);
        book1.returnBook();

        book2.checkOut(7);
        book2.isOverdue();
    }
}
