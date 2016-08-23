package cn.edu.xjtu.se.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bean.Comment;
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
        values.put("current_page", 1);
        values.put("finish_time", dateFormat.format(finish_time));
        values.put("total_reading_time", 0);
        long rowID = db.insert("Books", null, values);
        db.close();
        dbHelper.close();
        return rowID;
    }

    static public long addReadingRemind(int bookId, String bookName, String image, String remindTime, int status) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", bookId);
        values.put("book_name", bookName);
        values.put("image", image);
        values.put("remind_time",remindTime);
        values.put("status",status);
        long rowID = db.insert("ReadingRemind", null, values);
        db.close();
        dbHelper.close();
        return rowID;
    }

    public static List<Book> findBooksAll() {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
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

    public static long addComment(int book_id, String content) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_id", book_id);
        values.put("content", content);
        values.put("created_time", dateFormat.format(Calendar.getInstance().getTime()));
        long rowID = db.insert("BookComments", null, values);
        db.close();
        dbHelper.close();
        return rowID;
    }

    public static List<Comment> getCommentByBook(int book_id) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from BookComments where book_id = ? order by created_time",
                new String[]{String.valueOf(book_id)});
        List<Comment> list = new ArrayList<Comment>();
        while (cursor.moveToNext()) {
            list.add(Cursor2Comment(cursor));
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return list;
    }

    public static Comment getComment(int id) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from BookComments where id = ?",
                new String[]{String.valueOf(id)});
        cursor.moveToNext();
        Comment comment = Cursor2Comment(cursor);
        cursor.close();
        db.close();
        dbHelper.close();
        return comment;
    }

    public static int updComment(int id, String content) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", content);
        int ret = db.update("BookComments", values, "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return ret;
    }

    public static int delComment(int id){
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete("BookComments", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return ret;
    }

    public static Book getBook(int id){
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Books where id = ?",
                new String[]{String.valueOf(id)});
        cursor.moveToNext();
        Book book = Cursor2Book(cursor);
        cursor.close();
        db.close();
        dbHelper.close();
        return book;
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

    private static Comment Cursor2Comment(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex("id"));
        int book_id = cursor.getInt(cursor.getColumnIndex("book_id"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        String created_time = cursor.getString(cursor.getColumnIndex("created_time"));

        try {
            return new Comment(id, book_id, dateFormat.parse(created_time), content);
        } catch (Exception e) {
            return null;
        }

    }
}
