package cn.edu.xjtu.se.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by DUAN Yufei on 2016/6/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "bookgamma.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_BOOK = "create table Books ( "
            + "id integer primary key autoincrement, "
            + "name text, "
            + "image text, "
            + "isbn text, "
            + "pages integer, "
            + "current_page integer, "
            + "finish_time date, "
            + "total_reading_time integer)";

    public static final String CREATE_BOOKCOMMENT = "create table BookComments( "
            + "id integer primary key autoincrement, "
            + "book_id integer, "
            + "foreign key(book_id) references Book(id), "
            + "content text, "
            + "created_time timestamp)";

    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_BOOKCOMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
