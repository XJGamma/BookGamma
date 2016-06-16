package cn.edu.xjtu.se.dao;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fay on 2016/6/16.
 */
public class DBHelperTest extends AndroidTestCase{

    private SQLiteDatabase db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        DBHelper xgdbHelper = new DBHelper(context);
        db = xgdbHelper.getWritableDatabase();
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    //@Test
    public void testAddEntry(){
    }
}