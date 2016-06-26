package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by qh on 2016/6/24.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Books ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "pages integer, "
            + "name text)";

    public static final String CREATE_PERSON = "create table Person ("
            + "id integer primary key autoincrement, "
            + "name text, "
            + "age integer, "
            + "password text)";

//    public static final String CREATE_CATEGORY = "create table Category ("
//            + "id integer primary key autoincrement, "
//            + "category_name text, "
//            + "category_code integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory
            factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_PERSON);
//        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists person");
        onCreate(db);
    }
}

