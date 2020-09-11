package monika.library.server.data;

public class Author extends Person {
    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public String toString() {
        return lastname + ", " + firstName;
    }
}
