package net.smallacademy.authenticatorapp;

import android.service.autofill.Validator;
import android.view.View;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.StringRes;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static android.service.autofill.Validators.not;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static net.smallacademy.authenticatorapp.R.string.error_login_failed;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Test
    public void emailIsEmpty() {
        onView(withId(R.id.Email)).perform(clearText());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.Email)).check(matches(withError(getString(R.string.error_field_required))));
    }
    private String getString(@StringRes int resourceId) {
        return mActivityTestRule.getActivity().getString(resourceId);
    }
    @Test
    public void passwordIsEmpty() {
        onView(withId(R.id.Email)).perform(typeText("email@email.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(clearText());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(getString(R.string.error_field_required))));
    }

    @Test
    public void emailIsInvalid() {
        onView(withId(R.id.Email)).perform(typeText("invalid"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.Email)).check(matches(withError(getString(R.string.error_invalid_email))));
    }

    @Test
    public void passwordIsTooShort() {
        onView(withId(R.id.Email)).perform(typeText("email@email.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("1234"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withId(R.id.password)).check(matches(withError(getString(R.string.error_invalid_password))));
    }

    @Test
    public void loginFailed() {
        onView(withId(R.id.Email)).perform(typeText("v@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123457"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());
        onView(withText(getString(error_login_failed)))
                .inRoot(withDecorView((Matcher<View>) not((Validator) mActivityTestRule.getActivity().getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }
    @Test
    public void logincrct() {
        onView(withId(R.id.Email)).perform(typeText("v@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.loginBtn)).perform(click());

    }


    private static Matcher<View> withError(final String expected) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item instanceof EditText) {
                    return ((EditText)item).getError().toString().equals(expected);
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("Not found error message" + expected + ", find it!");
            }
        };
    }}
