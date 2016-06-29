package cn.edu.xjtu.se.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.xjtu.se.util.XGApplication;

/**
 * Created by DUAN Yufei on 2016/6/23.
 */
public class DBDao {

    static public long addBook(String bookName, int pages, Date finish_time, String isbn, String image) {
        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", bookName);
        values.put("image", image);
        values.put("isbn", isbn);
        values.put("pages", pages);
        values.put("current_page", 0);
        values.put("finish_time", dateFormat.format(finish_time));
        values.put("total_reading_time", 0);
        db.close();
        dbHelper.close();
        return db.insert("Books", null, values);
    }

//    public List<Book> findAll(){
//        DBHelper dbHelper = new DBHelper(XGApplication.getContext());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from Books",null);
//        List<Book> list = new ArrayList<Book>();
//        while(cursor.moveToNext()){
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            String text = cursor.getString(cursor.getColumnIndex("text"));
//            long mdate  = cursor.getLong(cursor.getColumnIndex("mdate"));
//            cal.setTimeInMillis(mdate);
//            Book m = new Book(id,text,cal.getTime());
//            list.add(m);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
}
