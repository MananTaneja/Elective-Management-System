package net.smallacademy.authenticatorapp;

public class AddStudent {
    String roll;
    String name;
    String email;
    String pref1;
    String pref2;
    String pref3;

    public AddStudent()
    {

    }

    public AddStudent(String roll, String name, String email, String pref1, String pref2, String pref3) {
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.pref1 = pref1;
        this.pref2 = pref2;
        this.pref3 = pref3;
    }

    public String getRoll() {
        return roll;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPref1() {
        return pref1;
    }

    public String getPref2() {
        return pref2;
    }

    public String getPref3() {
        return pref3;
    }
}


