package com.a58070096.patcharaponjoksamut.steamstalker;

import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.a58070096.patcharaponjoksamut.steamstalker.Activity.HomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by patcharaponjoksamut on 8/12/2017 AD.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileFragmentUITest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule(HomeActivity.class);

    @Before
    public void prepareUIForTest() {
        Espresso.onView(withText("Profile")).perform(click());
    }

    @Test
    public void logoutDialogTest() {
        Espresso.onView(withId(R.id.logout_button)).perform(click());
        Espresso.onView(withText("Are you sure you want to Logout?")).check(matches(isCompletelyDisplayed()));
    }


}
