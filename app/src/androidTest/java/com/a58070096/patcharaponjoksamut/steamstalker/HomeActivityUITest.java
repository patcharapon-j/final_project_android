package com.a58070096.patcharaponjoksamut.steamstalker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.a58070096.patcharaponjoksamut.steamstalker.Activity.HomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule(HomeActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.a58070096.patcharaponjoksamut.steamstalker", appContext.getPackageName());
    }


    @Test
    public void homeActivityTestSelectNewsTabIsDisplayed() {
        Espresso.onView(withText("News")).perform(click());
        Espresso.onView(withId(R.id.newsTitle)).check(matches(isCompletelyDisplayed()));
    }


    @Test
    public void homeActivityTestSelectGameTabIsDisplayed() {
        Espresso.onView(withText("Game")).perform(click());
        Espresso.onView(withId(R.id.game_fragment)).check(matches(isCompletelyDisplayed()));
    }


    @Test
    public void homeActivityTestSelectHotTabIsDisplayed() {
        Espresso.onView(withText("Hot")).perform(click());
        Espresso.onView(withId(R.id.hot_fragment)).check(matches(isCompletelyDisplayed()));
    }


    @Test
    public void homeActivityTestSelectProfileTabIsDisplayed() {
        Espresso.onView(withText("Profile")).perform(click());
        Espresso.onView(withId(R.id.profile_fragment)).check(matches(isCompletelyDisplayed()));
    }

}
