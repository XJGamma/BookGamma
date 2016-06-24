package cn.edu.xjtu.se.dao;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Test;

/**
 * Created by DUAN Yufei on 2016/6/16.
 */
public class DBHelperTest extends AndroidTestCase {

    private SQLiteDatabase db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void testHelper() throws Exception {
        assertEquals(true, db.isOpen());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        db.close();
    }


}