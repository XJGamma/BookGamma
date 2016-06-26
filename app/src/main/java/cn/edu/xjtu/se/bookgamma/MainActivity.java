package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.xjtu.se.dao.DBDao;

public class MainActivity extends AppCompatActivity {

    private List<Book> bookList = new ArrayList<Book>();
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });

        initBook();//初始化书籍信息
        BookAdapter adapter = new BookAdapter(MainActivity.this, R.layout.book_item, bookList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);
                Toast.makeText(MainActivity.this, book.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initBook() {

        dbHelper = new MyDatabaseHelper(this, "bookgamma.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                String name = cursor.getString(cursor. getColumnIndex("name"));
                String image = cursor.getString(cursor. getColumnIndex("image"));
                int pages = cursor.getInt(cursor. getColumnIndex("pages"));
                int current_page = cursor.getInt(cursor. getColumnIndex("current_page"));
                Log.d("MainActivity", "book name is " + name);
                Log.d("MainActivity", "book image is " + image);
                Log.d("MainActivity", "pages is " + pages);
                Log.d("MainActivity", "current_page is " + current_page);
                Book book_element = new Book(name,image,pages);
                bookList.add(book_element);
            }while (cursor.moveToNext());

        }


//        Book santi = new Book("三体", R.drawable.santi, "100");
//        bookList.add(santi);
//
//        Book xianjian = new Book("仙剑奇侠传",R.drawable.xianjian, "191");
//        bookList.add(xianjian);
//
//        Book bingyuhuo = new Book("冰与火之歌",R.drawable.ice_and_fire, "192");
//        bookList.add(bingyuhuo);
//
//        Book douluo = new Book("斗罗大陆",R.drawable.douluo, "193");
//        bookList.add(douluo);
//
//        Book aQ = new Book("阿Q正传",R.drawable.aq, "194");
//        bookList.add(aQ);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case (R.id.action_addbook):
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
                break;
            case (R.id.action_settings):
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
