package cn.edu.xjtu.se.bookgamma;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

//import android.support.test.espresso.contrib.PickerActions;

/**
 * Created by DUAN Yufei on 16-6-24.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddBookActivityTest {
    @Rule
    public ActivityTestRule<AddBookActivity> mActivityRule
            = new ActivityTestRule<AddBookActivity>(AddBookActivity.class);

    @Test
    public void testAddBookByCamera() {
        onView(withId(R.id.et_bookname))
                .perform(typeText("Espresso book"), closeSoftKeyboard());
        onView(withId(R.id.et_pages))
                .perform(typeText("600"), closeSoftKeyboard());
        onView(withId(R.id.btn_finish_time)).perform(click());
//        onView(withClassName(Matchers.equalTo(DatePickerDialog.class.getName())))
//                .perform(PickerActions.setDate(2016,10,1));

    }


}
