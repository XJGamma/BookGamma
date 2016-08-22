package cn.edu.xjtu.se.bookgamma;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.contrib.PickerActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static cn.edu.xjtu.se.bookgamma.ImageViewHasDrawableMatcher.hasDrawable;

/**
 * Created by DUAN Yufei on 16-6-24.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddBookActivityTest extends ActivityInstrumentationTestCase2<AddBookActivity> {

    public AddBookActivityTest() {
        super(AddBookActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Rule
    public IntentsTestRule<AddBookActivity> mActivityRule
            = new IntentsTestRule<AddBookActivity>(AddBookActivity.class);

    @Test
    public void testAddBookByCamera() {
        onView(withId(R.id.et_bookname))
                .perform(typeText("Espresso book"), closeSoftKeyboard());
        onView(withId(R.id.et_pages))
                .perform(typeText("600"), closeSoftKeyboard());
//        onView(withId(R.id.btn_finish_time)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2016, 7, 26));
        try {
            onView(withText("OK")).perform(click());
        } catch (NoMatchingViewException e) {
        }
        try {
            onView(withText("确定")).perform(click());
        } catch (NoMatchingViewException e) {
        }

        //camera
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);
        Intent retData = new Intent();
        retData.putExtra("data", icon);
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, retData);
        intending(toPackage("com.android.camera")).respondWith(result);
        onView(withId(R.id.iv_bookimage)).perform(click());
        onView(withText("拍照")).perform(click());
        intended(toPackage("com.android.camera"));

        onView(withId(R.id.iv_bookimage)).check(matches(hasDrawable()));
        onView(withId(R.id.btn_addbook)).perform(click());
//        onView(withId(R.id.fab)).perform(click());
    }

}
