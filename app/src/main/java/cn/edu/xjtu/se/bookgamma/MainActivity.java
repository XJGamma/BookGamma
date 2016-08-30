package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.booklistview.BookAdapter;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.remind.ReadingRemindActivity;
import cn.edu.xjtu.se.util.DoActionListener;
import cn.edu.xjtu.se.util.UpdateTask;
import cn.edu.xjtu.se.util.UtilAction;
import cn.edu.xjtu.se.util.XGUserInfo;


public class MainActivity extends AppCompatActivity {


    private List<Book> bookList;
    private BookAdapter adapter;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookList = DBDao.findBooksAll();
        adapter = new BookAdapter(MainActivity.this, R.layout.book_item, bookList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);
                Intent intent = new Intent(MainActivity.this, ViewBookActivity.class);
                intent.putExtra("book_id", book.getId());
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookList.get(position);
                UtilAction.book.delete(MainActivity.this, book.getId(), new DoActionListener() {
                    @Override
                    public void doAction(Context context) {
                        // TODO: 此处应为局部更新
                        onResume();
                    }
                });
                return true;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.action_logout);
        if (XGUserInfo.getStatus()) {
            menuItem.setTitle(R.string.action_logout);
        } else {
            menuItem.setTitle(R.string.action_login);
        }
        return true;
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
            case (R.id.action_account):
                Intent accountIntent = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(accountIntent);
                break;
            case (R.id.action_addbook):
                Intent addBookIntent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(addBookIntent);
                break;
            case (R.id.action_comment):
                Intent commentIntent = new Intent(MainActivity.this, BookCommentActivity.class);
                startActivity(commentIntent);
                break;
            case (R.id.action_reading):
                Intent readingIntent = new Intent(MainActivity.this, ReadingRemindActivity.class);
                startActivity(readingIntent);
                break;
            case (R.id.action_update):
                new UpdateTask(MainActivity.this).update();
                break;
            case (R.id.action_logout):
                if (XGUserInfo.getStatus()) {
                    XGUserInfo.setStatus();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
