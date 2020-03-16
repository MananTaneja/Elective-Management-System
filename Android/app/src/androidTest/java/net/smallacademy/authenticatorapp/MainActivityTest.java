package net.smallacademy.authenticatorapp;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class MainActivityTest {
    @Rule
    public ActivityTestRule<Register> mActivityTestRule = new ActivityTestRule<>(Register.class);

    private String UName1 = "abcd";
    private String email1 = "";
    private String id1 = "CSE185";
    private String pref1 = "Biometrics";
    private String pwd1 = "123456";

    private String UName2 = "";
    private String email2 = "abcd@gmail.com";
    private String id2 = "CSE185";
    private String pref2 = "Biometrics";
    private String pwd2 = "123456";

    private String UName3 = "abcd";
    private String email3 = "abcd@gmail.com";
    private String id3 = "CSE185";
    private String pref3 = "Biometrics";
    private String pwd3 = "123";

    private String UName4 = "abcd";
    private String email4 = "abcd@gmail.com";
    private String id4 = "CSE185";
    private String pref4 = "Biometrics";
    private String pwd4 = "123456";

    private String UName5 = "abcd";
    private String email5 = "abcd@gmail.com";
    private String id5 = "CSE185";
    private String pref5 = "Biometrics";
    private String pwd5= "";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testUserInputScenario()
    {
        Espresso.onView(withId(R.id.fullName)).perform(typeText(UName1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Email)).perform(typeText(email1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.ID)).perform(typeText(id1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Preference)).perform(typeText(pref1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(typeText(pwd1));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());
    }
    @Test
    public void testUserInputScenario1()
    {
        Espresso.onView(withId(R.id.fullName)).perform(typeText(UName2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Email)).perform(typeText(email2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.ID)).perform(typeText(id2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Preference)).perform(typeText(pref2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(typeText(pwd2));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());
    }
    @Test
    public void testUserInputScenario2()
    {
        Espresso.onView(withId(R.id.fullName)).perform(typeText(UName3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Email)).perform(typeText(email3));

        Espresso.onView(withId(R.id.ID)).perform(typeText(id3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Preference)).perform(typeText(pref3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(typeText(pwd3));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());
    }

    @Test
    public void testUserInputScenario3()
    {
        Espresso.onView(withId(R.id.fullName)).perform(typeText(UName4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Email)).perform(typeText(email4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.ID)).perform(typeText(id4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Preference)).perform(typeText(pref4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(typeText(pwd4));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());
    }

    @Test
    public void testUserInputScenario4()
    {
        Espresso.onView(withId(R.id.fullName)).perform(typeText(UName5));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Email)).perform(typeText(email5));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.ID)).perform(typeText(id5));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.Preference)).perform(typeText(pref5));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.password)).perform(typeText(pwd5));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.registerBtn)).perform(click());
    }


    @After
    public void tearDown() throws Exception {

    }
}
