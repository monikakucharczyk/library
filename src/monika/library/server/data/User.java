package monika.library.server.data;

public class User extends Person {
    private String login;

    public User(String firstName, String lastname, String login) {
        super(firstName, lastname);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
