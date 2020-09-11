package monika.library.server;

import monika.library.server.data.Author;
import monika.library.server.data.Book;
import monika.library.server.data.Genre;
import monika.library.server.data.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class CommandMenu {
    private final BufferedReader inputStream;
    private final PrintStream outputStream;
    private final Library library;
    private User loggedUser;

    public CommandMenu(BufferedReader input, PrintStream output, Library library) {
        this.inputStream = input;
        this.outputStream = output;
        this.library = library;
    }

    User login() throws IOException {
        outputStream.println("Type your login");
        String login = inputStream.readLine();
        System.out.println(login);
        return library.getUserByLogin(login);
    }

    void loop() throws IOException {
        boolean shouldEnd = false;

        loggedUser = login();
        if (loggedUser == null) {
            outputStream.println("Unknown user");
            System.out.println("# Client disconnected");
            return;
        } else {
            outputStream.format("Logged in as %s\n", loggedUser.toString());
        }

        while (!shouldEnd) {
            String input = inputStream.readLine();
            if (input == null || input.equals("exit")) {
                shouldEnd = true;
                System.out.println("# Client disconnected");
            } else {
                System.out.println(input);
                handleCommands(input);
                outputStream.flush();
            }
        }
    }

    void handleCommands(String command) throws IOException {
        switch (command.trim()) {
            case "list books": {
                outputStream.println(library.getBooksListString());
                break;
            }
            case "get book": {
                outputStream.println("What is the book ID?");
                String bookId = inputStream.readLine();
                Book foundBook = library.findBookById(Integer.parseInt(bookId));
                if (foundBook == null) {
                    outputStream.println("Book not found");
                } else {
                    outputStream.println(foundBook.toString());
                }
                break;
            }
            case "borrow": {
                outputStream.println("What is the ID of the book you want to borrow?");
                String bookId = inputStream.readLine();
                Book foundBook = library.findBookById(Integer.parseInt(bookId));
                if (foundBook == null) {
                    outputStream.println("Book not found");
                } else if (foundBook.isBorrowed()) {
                    outputStream.println("Book is already borrowed!");
                } else {
                    foundBook.borrow(loggedUser);
                    outputStream.println("Book successfully borrowed");
                }
                break;
            }
            case "return": {
                outputStream.println("What is the ID of the book you want to return?");
                String bookId = inputStream.readLine();
                Book foundBook = library.findBookById(Integer.parseInt(bookId));
                if (foundBook == null) {
                    outputStream.println("Book not found");
                } else if (foundBook.returnBook(loggedUser)) {
                    outputStream.println("Book successfully returned");
                } else {
                    outputStream.println("You either don't own this book or the book is free to borrow!");
                }
                break;
            }
            case "add book": {
                outputStream.println("Title?");
                String title = inputStream.readLine();

                Author author = getAuthorFromClient();
                if (author == null) {
                    outputStream.println("Invalid author name!");
                    return;
                }

                outputStream.println("Genre?");
                Genre genre = Genre.fromString(inputStream.readLine());

                Book book = new Book(title, genre, author);
                library.addBook(book);
                outputStream.println("Added new book with id " + book.getId());
                break;
            }
            case "remove book": {
                outputStream.println("What is the ID of the book you want to return?");
                String bookId = inputStream.readLine();
                boolean bookRemoved = library.removeBookById(Integer.parseInt(bookId));
                if (bookRemoved) {
                    outputStream.println("Book successfully removed");
                } else {
                    outputStream.println("Book not found");
                }
                break;
            }
            case "list authors": {
                outputStream.println(library.getAuthorsListString());
                break;
            }
            case "list books by author": {
                Author author = getAuthorFromClient();
                if (author == null) {
                    outputStream.println("Invalid author name!");
                    return;
                }
                outputStream.println(library.getBooksListStringByAuthor(author));
                break;
            }
            default:
                outputStream.println("Unknown command. Please try again.\n" +
                        "AVAILABLE COMMANDS\n" +
                        "* list books\n" +
                        "* list books by author\n" +
                        "* add book\n" +
                        "* remove book\n" +
                        "* get book\n" +
                        "* list authors\n" +
                        "* borrow\n" +
                        "* return");
        }
    }

    private Author getAuthorFromClient() throws IOException {
        outputStream.println("Author (lastname, firstname)?");
        String[] authorName = inputStream.readLine().split(",");
        if (authorName.length != 2) {
            return null;
        }
        Author author = library.findAuthorByNames(authorName[1].trim(), authorName[0].trim());
        if (author == null) {
            author = new Author(authorName[1].trim(), authorName[0].trim());
        }
        return author;
    }
}
