package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bookgamma.adapter.BookAdapter;
import cn.edu.xjtu.se.dao.DBDao;

public class BookCommentActivity extends AppCompatActivity {

    public static final String TAG = BookCommentActivity.class.getSimpleName();


    private RecyclerView rv_book;
    private StaggeredGridLayoutManager layoutManager;
    private BookAdapter bookAdapter;

    private Toolbar toolbar;


    private List<Book> books;
    private int book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rv_book = (RecyclerView) findViewById(R.id.rv_book);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv_book.setLayoutManager(layoutManager);
        rv_book.setHasFixedSize(true);
        books = DBDao.findBooksAll();

        bookAdapter = new BookAdapter(books, BookCommentActivity.this);
        rv_book.setAdapter(bookAdapter);
        bookAdapter.setOnItemClickListener(new BookAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int id) {
                //jump to comment activity
                Intent commentIntent = new Intent(BookCommentActivity.this, CommentActivity.class);
                commentIntent.putExtra("book_id", id);
                startActivity(commentIntent);
            }
        });
    }

    private void mLog(String msg) {
        Log.i(TAG, msg);
    }

    private void mToast(int str) {
        Toast.makeText(BookCommentActivity.this, str, Toast.LENGTH_LONG).show();
    }
}
