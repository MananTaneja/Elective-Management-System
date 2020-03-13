package net.smallacademy.authenticatorapp;

class students {
    private String pref1;
    private String pref2;
    private String pref3;

    public students()
    {

    }

    public students(String pref1, String pref2, String pref3) {
        this.pref1 = pref1;
        this.pref2 = pref2;
        this.pref3 = pref3;
    }

    public String getPref1() {
        return pref1;
    }

    public void setPref1(String pref1) {
        this.pref1 = pref1;
    }

    public String getPref2() {
        return pref2;
    }

    public void setPref2(String pref2) {
        this.pref2 = pref2;
    }

    public String getPref3() {
        return pref3;
    }

    public void setPref3(String pref3) {
        this.pref3 = pref3;
    }
}
