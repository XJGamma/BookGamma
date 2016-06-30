package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.dao.DBDao;

public class CommentActivity extends AppCompatActivity {

    public static final String TAG = CommentActivity.class.getSimpleName();

    private TextView tv_msg_comment;
    private RecyclerView rv_comment;

    private StaggeredGridLayoutManager layoutManager;

    private List<Comment> comments;
    private int book_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_msg_comment = (TextView) findViewById(R.id.tv_msg_comment);
        rv_comment = (RecyclerView) findViewById(R.id.rv_comment);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rv_comment.setLayoutManager(layoutManager);
        rv_comment.setHasFixedSize(true);
        Intent getIntent = getIntent();
        book_id = getIntent.getIntExtra("book_id", 0);
        if (book_id == 0) {
            mToast(R.string.tip_err_comment);
            CommentActivity.this.finish();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.action_add_comment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add a comment
                Intent addCommentIntent = new Intent(CommentActivity.this, AddCommentActivity.class);
                addCommentIntent.putExtra("book_id",book_id);
                startActivity(addCommentIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        comments = DBDao.getCommentByBook(book_id);
        if (comments.isEmpty()) {
            tv_msg_comment.setText(R.string.msg_no_comment);
            tv_msg_comment.setVisibility(View.VISIBLE);
            rv_comment.setVisibility(View.GONE);
        } else {
            tv_msg_comment.setVisibility(View.GONE);
            rv_comment.setVisibility(View.VISIBLE);
//            bookAdapter = new BookAdapter(books, BookCommentActivity.this);
//            rv_comment.setAdapter(bookAdapter);
//            bookAdapter.setOnItemClickListener(new BookAdapter.OnRecyclerViewItemClickListener() {
//                @Override
//                public void onItemClick(View view, int id) {
//                    //jump to comment activity
//                }
//            });
        }
    }

    private void mLog(String msg) {
        Log.i(TAG, msg);
    }

    private void mToast(int str) {
        Toast.makeText(CommentActivity.this, str, Toast.LENGTH_LONG).show();
    }

}