package net.smallacademy.authenticatorapp;

public class AddFaculty {
    String id;
    String name;
    String email;
    String preference;

    public AddFaculty() {

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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
