package net.smallacademy.authenticatorapp;

public class PrefStudent{
    String Pref1;
    String Pref2;
    String Pref3;

    public PrefStudent()
    {

    }

    public PrefStudent(String preference1, String preference2, String preference3) {
        Pref1 = preference1;
        Pref2 = preference2;
        Pref3 = preference3;
    }

    public String getPref1() {
        return Pref1;
    }

    public String getPref2() {
        return Pref2;
    }

    public String getPref3() {
        return Pref3;
    }


}
