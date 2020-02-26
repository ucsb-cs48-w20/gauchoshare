package com.ucsb.integration;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MessageActivityTest {
    @Rule
    public ActivityTestRule<StartActivity> mActivityTestRule = new ActivityTestRule<>(StartActivity.class);

    @Test
    public void messageActivityTest() throws Exception{
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.start), withText("Start"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("kylestubbs102@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("123456"), closeSoftKeyboard());

        TimeUnit.SECONDS.sleep(2);

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.login), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        TimeUnit.SECONDS.sleep(8);

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.message), withText("Message"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        TimeUnit.SECONDS.sleep(2);

        ViewInteraction tabView = onView(
                allOf(withContentDescription("Users"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tab_layout),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        TimeUnit.SECONDS.sleep(2);

        ViewInteraction relativeLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler_view),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1)),
                        5),
                        isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.text_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("Users Test Message"), closeSoftKeyboard());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        TimeUnit.SECONDS.sleep(1);

        pressBack();

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.search_users),
                        childAtPosition(
                                withParent(withId(R.id.view_pager)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("kyle"), closeSoftKeyboard());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction relativeLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler_view),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1)),
                        0),
                        isDisplayed()));
        relativeLayout2.perform(click());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.text_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Search Users Test Message"), closeSoftKeyboard());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        TimeUnit.SECONDS.sleep(1);

        pressBack();

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction tabView2 = onView(
                allOf(withContentDescription("Chats"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.tab_layout),
                                        0),
                                0),
                        isDisplayed()));
        tabView2.perform(click());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction relativeLayout3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.recycler_view),
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0)),
                                0),
                        isDisplayed()));
        relativeLayout3.perform(click());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.text_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("Chat Test Message"), closeSoftKeyboard());

        TimeUnit.SECONDS.sleep(1);

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.btn_send),
                        childAtPosition(
                                allOf(withId(R.id.bottom),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                2)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        TimeUnit.SECONDS.sleep(1);

        pressBack();
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
