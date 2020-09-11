package monika.library.server;

import monika.library.server.data.Author;
import monika.library.server.data.Book;
import monika.library.server.data.Genre;
import monika.library.server.data.User;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private final List<Book> bookList = new ArrayList<>();
    private final List<Author> authorList = new ArrayList<>();
    private final List<User> registeredUsers = new ArrayList<>();

    void initLibrary() {
        registeredUsers.add(new User("John", "Doe", "johndoe"));
        registeredUsers.add(new User("Monika", "Kucharczyk", "monika"));

        Author johnMaloney = new Author("John", "Maloney");
        Author umberto = new Author("Umberto", "Eco");
        Author tolkien = new Author("J.R.R.", "Tolkien");
        Author cormac = new Author("Cormac", "McCarthy");
        authorList.add(johnMaloney);

        bookList.add(new Book("Something", Genre.Drama, johnMaloney));

        bookList.add(new Book("The Name of the Rose", Genre.Mystery, umberto));
        bookList.add(new Book("Foucault's Pendulum", Genre.Mystery, umberto));
        bookList.add(new Book("The Prague Cemetery", Genre.Historical, umberto));
        bookList.add(new Book("The Island of the Day Before", Genre.Fantasy, umberto));

        bookList.add(new Book("The Hobbit, or There and Back again", Genre.Fantasy, tolkien));
        bookList.add(new Book("The Hobbit, or There and Back again", Genre.Fantasy, tolkien));
        bookList.add(new Book("The Hobbit, or There and Back again", Genre.Fantasy, tolkien));
        bookList.add(new Book("The Children of Húrin", Genre.Fantasy, tolkien));
        bookList.add(new Book("The Silmarillion", Genre.Fantasy, tolkien));

        bookList.add(new Book("No Country for Old Men", Genre.Thriller, cormac));
    }

    String getBooksListString() {
        StringBuilder builder = new StringBuilder();
        for (Book book : this.bookList) {
            builder.append(book.getId())
                    .append(")")
                    .append(" '")
                    .append(book.getTitle())
                    .append("' ")
                    .append(book.getAuthor().toString());
            if (book.isBorrowed()) {
                builder.append(" (borrowed)");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    String getAuthorsListString() {
        StringBuilder builder = new StringBuilder();
        for (Author author : this.authorList) {
            builder.append(author.toString())
                    .append("\n");
        }
        return builder.toString();
    }

    boolean removeBookById(int id) {
        return this.bookList.removeIf(book -> book.getId() == id);
    }

    void addBook(Book book) {
        this.bookList.add(book);
    }

    Book findBookById(int id) {
        for (Book book : bookList) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    User getUserByLogin(String login) {
        for (User user : this.registeredUsers) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    Author findAuthorByNames(String firstName, String lastName) {
        for (Author author : authorList) {
            if (author.getFirstName().equals(firstName) && author.getLastname().equals(lastName)) {
                return author;
            }
        }
        return null;
    }

    String getBooksListStringByAuthor(Author author) {
        StringBuilder builder = new StringBuilder();
        for (Book book : this.bookList) {
            if (book.getAuthor() == author) {
                builder.append(book.getId())
                        .append(")")
                        .append(" ")
                        .append(book.getTitle())
                        .append(" ")
                        .append(book.getAuthor().toString())
                        .append("\n");
            }
        }
        return builder.toString();
    }
}
