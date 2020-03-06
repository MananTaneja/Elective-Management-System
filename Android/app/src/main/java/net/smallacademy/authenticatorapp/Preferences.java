package net.smallacademy.authenticatorapp;

public class Preferences {
    String Preference1;
    String Preference2;
    String Preference3;

    public Preferences()
    {

    }

    public Preferences(String preference1, String preference2, String preference3) {
        Preference1 = preference1;
        Preference2 = preference2;
        Preference3 = preference3;
    }

    public String getPreference1() {
        return Preference1;
    }

    public String getPreference2() {
        return Preference2;
    }

    public String getPreference3() {
        return Preference3;
    }


}
