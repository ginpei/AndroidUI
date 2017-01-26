package info.ginpei.androidui;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains some data about an user
 * who is displayed on the list view.
 */
public class User implements Serializable {
    public static final int ID_DEFAULT_ICON = android.R.drawable.sym_def_app_icon;

    public String name;
    public int age;
    public String description;
    public int imageId = ID_DEFAULT_ICON;

    public User(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
    }

    public User(String name, int age, String description, int imageId) {
        this(name, age, description);
        this.imageId = imageId;
    }

    static ArrayList<User> getDummyList() {
        ArrayList<User> users = new ArrayList<User>(Arrays.asList(
                new User("Alice", 10, "Nice guy.", android.R.drawable.star_big_on),
                new User("Bob", 22, "Cool guy.", android.R.drawable.ic_lock_power_off),
                new User("Charlie", 33, "Good guy."),
                new User("Dan", 44, "Great guy."),
                new User("Fred", 55, "Awesome guy.", android.R.drawable.star_big_off),
                new User("George", 55, "Awesome guy."),
                new User("Hin", 21, "A guy."),
                new User("Jim", 22, "A guy."),
                new User("Kim", 23, "A guy."),
                new User("Lin", 24, "A guy."),
                new User("Mike", 24, "A guy."),
                new User("Nick", 24, "A guy."),
                new User("O'railly", 24, "A guy."),
                new User("Pumpkin Joe", 24, "A guy."),
                new User("Queen", 24, "A guy.", android.R.drawable.sym_contact_card)
        ));

        return users;
    }
}