package monika.library.server.data;

public class Book {
    private static int idCounter = 1;

    private final int id;
    private final String title;
    private final Genre genre;
    private final Author author;
    private User owner;

    public Book(String title, Genre genre, Author author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.id = Book.idCounter;

        Book.idCounter++;
    }

    public boolean isBorrowed() {
        return this.owner != null;
    }

    public void borrow(User user) {
        this.owner = user;
    }

    public boolean returnBook(User user) {
        if (user == owner) {
            this.owner = null;
            return true;
        }
        return false;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String isAvailableText = isBorrowed() ? "No" : "Yes";
        return getId() + ") " + getTitle()
                + "\n* Written by: " + getAuthor().toString()
                + "\n* Genre: " + getGenre().toString()
                + "\n* Available: " + isAvailableText;
    }
}
