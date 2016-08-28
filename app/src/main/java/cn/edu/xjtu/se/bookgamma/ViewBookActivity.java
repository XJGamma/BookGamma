package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.remind.alarm.CircleProgressView;
import cn.edu.xjtu.se.remind.alarm.WheelView;
import cn.edu.xjtu.se.util.DoActionListener;
import cn.edu.xjtu.se.util.UtilAction;

public class ViewBookActivity extends AppCompatActivity {

    private TextView bookName;
    private TextView bookPage;
    private TextView bookCreatedAt;
    private ImageView bookCover;
    private TextView bookCompletedAt;
    private WheelView pageWheel;
    private CircleProgressView readingPercent;
    private int bookId;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        bookId = getIntent().getIntExtra("book_id", 0);

        bookName = (TextView) findViewById(R.id.view_book_name);
        bookPage = (TextView) findViewById(R.id.view_book_page);
        bookCreatedAt = (TextView) findViewById(R.id.view_book_created_at);
        bookCover = (ImageView) findViewById(R.id.view_book_cover);
        bookCompletedAt = (TextView) findViewById(R.id.view_book_completed_at);
        pageWheel = (WheelView) findViewById(R.id.page_wheel);
        pageWheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                DBDao.updateCurrentPage(bookId, item);
                int page = Integer.parseInt(item);
                readingPercent.setProgress(UtilAction.percent(page, book.getPages()));
            }
        });
        readingPercent = (CircleProgressView) findViewById(R.id.reading_percent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        book = DBDao.getBook(bookId);
        setTitle(book.getName());
        bookName.setText(book.getName());
        bookPage.setText(String.valueOf(book.getPages()));
        bookCreatedAt.setText(UtilAction.fmt.date(book.getFinish_time()));
        bookCover.setImageURI(Uri.parse(book.getImage()));
        bookCompletedAt.setText(UtilAction.fmt.date(book.getFinish_time()));
        pageWheel.setItems(UtilAction.srange(1, book.getPages()));
        pageWheel.setSeletion(book.getCurrent_page()-1);
        readingPercent.setProgress(UtilAction.percent(book.getCurrent_page(), book.getPages()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_del_book) {
            UtilAction.book.delete(this, bookId, new DoActionListener() {
                @Override
                public void doAction(Context context) {
                    finish();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
