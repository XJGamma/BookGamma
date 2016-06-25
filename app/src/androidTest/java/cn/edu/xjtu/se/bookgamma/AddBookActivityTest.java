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
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        onView(withId(R.id.btn_finish_time)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2016,7,26));
        try{
            onView(withText("OK")).perform(click());
        }catch (NoMatchingViewException e){
        }
        try{
            onView(withText("确定")).perform(click());
        }catch (NoMatchingViewException e){
        }

        //camera
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        Intent retData = new Intent();
        retData.putExtra("data",icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK,retData);

        intending(toPackage("com.android.camera2")).respondWith(result);
        onView(withId(R.id.iv_bookimage)).perform(click());
        onView(withText("拍照")).perform(click());

        /*Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceTypeName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceEntryName(R.mipmap.ic_launcher));

        Intent resultData = new Intent();
        resultData.setData(imageUri);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(
                Activity.RESULT_OK, resultData);

        Matcher<Intent> expectedIntent = allOf(hasAction(Intent.ACTION_PICK),
                hasData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI));
//        Intents.init();
        intending(expectedIntent).respondWith(result);*/

        //Click the select button
/*        onView(withId(R.id.iv_bookimage)).perform(click());
        onView(withText("拍照")).perform(click());

        intended(expectedIntent);
        Intents.release();*/

        //onView(withId(R.id.iv_bookimage)).perform(click());
//        onView()
    }

}
