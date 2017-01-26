package info.ginpei.androidui;


import java.io.Serializable;

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
}