package info.ginpei.androidui;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains some data about an user
 * who is displayed on the list view.
 */
public class User implements Serializable {
    public String name;
    public int age;
    public String description;

    public User(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    static ArrayList<User> getDummyList() {
        ArrayList<User> users = new ArrayList<User>(Arrays.asList(
                new User("Alice", 10, "Nice guy."),
                new User("Bob", 22, "Cool guy."),
                new User("Charlie", 33, "Good guy."),
                new User("Dan", 44, "Great guy."),
                new User("Fred", 55, "Awesome guy."),
                new User("George", 55, "Awesome guy."),
                new User("Hin", 21, "A guy."),
                new User("Jim", 22, "A guy."),
                new User("Kim", 23, "A guy."),
                new User("Lin", 24, "A guy.")
        ));

        return users;
    }
}