package cn.edu.xjtu.se.bookgamma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import cn.edu.xjtu.se.bookgamma.adapter.BookAdapter;

public class BookCommentActivity extends AppCompatActivity {

    public static final String TAG = BookCommentActivity.class.getSimpleName();

    private RecyclerView rv_book;
    private RecyclerView.LayoutManager layoutManager;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_comment);
        rv_book = (RecyclerView) findViewById(R.id.rv_book);
        layoutManager = new LinearLayoutManager(this);
        rv_book.setLayoutManager(layoutManager);
        rv_book.setHasFixedSize(true);

        String[] temp = new String[]{"a", "b", "c"};
        mLog(temp.toString());

        bookAdapter = new BookAdapter(temp);
        rv_book.setAdapter(bookAdapter);

        mLog("load");
    }

    private void mLog(String msg) {
        Log.i(TAG, msg);
    }


}
