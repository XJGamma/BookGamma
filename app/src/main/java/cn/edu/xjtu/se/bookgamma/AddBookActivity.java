package cn.edu.xjtu.se.bookgamma;

import android.app.Activity;
import android.os.Bundle;

import cn.edu.xjtu.se.dao.DBHelper;

/**
 * Created by DUAN Yufei on 2016/6/17.
 */
public class AddBookActivity extends Activity{
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        dbHelper = new DBHelper(this);
    }
}
