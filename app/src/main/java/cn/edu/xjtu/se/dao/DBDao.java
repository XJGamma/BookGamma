package cn.edu.xjtu.se.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.util.XGApplication;

/**
 * Created by DUAN Yufei on 2016/6/23.
 */
public class DBDao {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static public long addBook(String bookName, int pages, Date finish_time, String isbn, String image) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bookName);
        values.put("image", image);
        values.put("isbn", isbn);
        values.put("pages", pages);
        values.put("current_page", 0);
        values.put("finish_time", dateFormat.format(finish_time));
        values.put("total_reading_time", 0);
        long rowID = db.insert("Books", null, values);
        db.close();
        dbHelper.close();
        return rowID;
    }

    public static List<Book> findAll() {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Books", null);
        List<Book> list = new ArrayList<Book>();
        while (cursor.moveToNext()) {
            if (Cursor2Book(cursor) != null) {
                list.add(Cursor2Book(cursor));
            }
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return list;
    }

    private static Book Cursor2Book(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String image = cursor.getString(cursor.getColumnIndex("image"));
        String isbn = cursor.getString(cursor.getColumnIndex("isbn"));
        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
        int current_page = cursor.getInt(cursor.getColumnIndex("current_page"));
        String finish_time = cursor.getString(cursor.getColumnIndex("finish_time"));
        int total_reading_time = cursor.getInt(cursor.getColumnIndex("total_reading_time"));
        try {
            return new Book(current_page, dateFormat.parse(finish_time), id, image, isbn, name, pages, total_reading_time);
        } catch (Exception e) {
            return null;
        }
    }
}
