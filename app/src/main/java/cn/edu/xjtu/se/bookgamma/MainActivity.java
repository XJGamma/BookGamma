package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.edu.xjtu.se.util.UpdateTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import cn.edu.xjtu.se.dao.DBHelper;


public class MainActivity extends AppCompatActivity {


    private  List<Book> bookList = new ArrayList<Book>();
    //private  MyDatabaseHelper dbHelper;
    private cn.edu.xjtu.se.dao.DBHelper dbHelper;
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
    }

    private void initBook() {

        dbHelper = new DBHelper(this);
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
                Log.d("MainActivity", "total_pages is " + pages);
                Log.d("MainActivity", "current_page is " + current_page);
                Book book_element = new Book(name,image,pages,current_page);
                bookList.add(book_element);
            }while (cursor.moveToNext());

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        bookList.clear();
        initBook();
        BookAdapter adapter = new BookAdapter(MainActivity.this, R.layout.book_item, bookList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Book book = bookList.get(position);
                final EditText editText = new EditText(MainActivity.this);
                AlertDialog builder  =  new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请输入当前页数")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(editText)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbHelper = new DBHelper(MainActivity.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put("current_page",editText.getText().toString());
                                db.update("Books", values ,"name = ?", new String[]{book.getName()});
                                onStart();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
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
        int id = item.getItemId();

        switch (item.getItemId()) {
            case (R.id.action_addbook):
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
                break;
            case (R.id.action_settings):
                break;
            case (R.id.action_update):
                new UpdateTask(MainActivity.this).update();
        }

        return super.onOptionsItemSelected(item);
    }
}
