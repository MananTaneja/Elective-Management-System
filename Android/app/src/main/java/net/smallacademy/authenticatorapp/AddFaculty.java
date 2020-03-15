package net.smallacademy.authenticatorapp;

public class AddFaculty {
    String id;
    String name;
    String email;
    String preference;

    public AddFaculty(String roll, String name, String email, String pref1, String pref2, String pref3)
    {

    }

    public AddFaculty(String id, String name, String email, String preference) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.preference = preference;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPreference() {
        return preference;
    }
}
