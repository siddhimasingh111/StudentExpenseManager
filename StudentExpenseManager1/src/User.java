/**
 * User.java
 *
 * Represents the student using the application.
 * Demonstrates: Encapsulation (private field with public getter/setter).
 */
public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}