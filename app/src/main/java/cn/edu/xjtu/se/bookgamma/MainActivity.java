package cn.edu.xjtu.se.bookgamma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.booklistview.BookAdapter;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.remind.ReadingRemindActivity;
import cn.edu.xjtu.se.util.UpdateTask;
import cn.edu.xjtu.se.util.XGUserInfo;


public class MainActivity extends AppCompatActivity {


    private List<Book> bookList;

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
        BookAdapter adapter = new BookAdapter(MainActivity.this, R.layout.book_item, bookList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Book book = bookList.get(position);
                final EditText currentPage = new EditText(MainActivity.this);
                currentPage.setInputType(InputType.TYPE_CLASS_NUMBER);
                currentPage.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("请输入当前页数")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(currentPage)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (TextUtils.isEmpty(currentPage.getText())) {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题
                                            .setMessage("当前页数不能为空！ ")//设置显示的内容
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                    // TODO Auto-generated method stub
                                                }
                                            }).show();//在按键响应事件中显示此对话框
                                } else if (Integer.parseInt(currentPage.getText().toString()) > book.getPages()) {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题
                                            .setMessage("当前页数不能超过总页数！ ")//设置显示的内容
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                    // TODO Auto-generated method stub
                                                }
                                            }).show();//在按键响应事件中显示此对话框
                                } else if (0 > Integer.parseInt(currentPage.getText().toString())) {
                                    new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题
                                            .setMessage("当前页数不能小于零！ ")//设置显示的内容
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                                    // TODO Auto-generated method stub
                                                }
                                            }).show();//在按键响应事件中显示此对话框
                                } else {
                                    DBDao.updateCurrentPage(book.getId(), currentPage.getText().toString());
                                    onStart();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Book book = bookList.get(position);
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")//设置对话框标题

                        .setMessage("您确定要删除这本 " + book.getName() + " 吗?")//设置显示的内容

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                int ret = DBDao.delBook(book.getId());
                                if (ret > 0) {
                                    mToast(R.string.tip_del_book_succeed);
                                } else {
                                    mToast(R.string.tip_del_book_failed);
                                }
                                int ret2 = DBDao.delReadingRemindByBook(book.getId());
                                if (ret2 > 0) {
                                    mToast(R.string.tip_del_remind_succeed);
                                } else {
                                    mToast(R.string.tip_del_remind_failed);
                                }
                                // TODO Auto-generated method stub
                                onStart();

                            }

                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        // TODO Auto-generated method stub


                    }

                }).show();//在按键响应事件中显示此对话框

                return true;
            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuItem = menu.findItem(R.id.action_logout);
        switch (XGUserInfo.getStatus()) {
            case 0:
                menuItem.setTitle(R.string.action_login);
                break;
            case 1:
                menuItem.setTitle(R.string.action_logout);
                break;
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
            case (R.id.action_addbook):
                Intent addBookIntent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(addBookIntent);
                break;
            case (R.id.action_comment):
                Intent commentIntent = new Intent(MainActivity.this, BookCommentActivity.class);
                startActivity(commentIntent);
            case (R.id.action_settings):
                break;
            case (R.id.action_reading):
                Intent readingIntent = new Intent(MainActivity.this, ReadingRemindActivity.class);
                startActivity(readingIntent);
                break;
            case (R.id.action_update):
                new UpdateTask(MainActivity.this).update();
                break;
            case (R.id.action_logout):
                switch (XGUserInfo.getStatus()) {
                    case 0:
                    case -1:
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case 1:
                        XGUserInfo.setStatus();
                        break;
                }
        }

        return super.onOptionsItemSelected(item);
    }

    private void mToast(int str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
    }
}
