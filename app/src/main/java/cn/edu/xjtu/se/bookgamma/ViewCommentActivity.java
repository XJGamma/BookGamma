package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.util.DoActionListener;
import cn.edu.xjtu.se.util.UtilAction;

public class ViewCommentActivity extends AppCompatActivity {

    private int commentId;
    private ImageView bookCover;
    private TextView bookName;
    private TextView commentContent;
    private TextView commentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        commentId = intent.getIntExtra("comment_id", 0);

        bookCover = (ImageView) findViewById(R.id.view_book_cover);
        bookName = (TextView) findViewById(R.id.view_book_name);
        commentContent = (TextView) findViewById(R.id.comment_content);
        commentTime = (TextView) findViewById(R.id.comment_time);
    }

    @Override
    public void onResume() {
        super.onResume();
        Comment comment = DBDao.getComment(commentId);
        Book book = DBDao.getBook(comment.getBook_id());
        bookCover.setImageURI(Uri.parse(book.getImage()));
        bookName.setText(book.getName());
        commentContent.setText(comment.getContent());
        commentTime.setText(new SimpleDateFormat("yyyy年M月d日 HH:mm").format(comment.getCreated_time()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_comment) {
            Intent intent = new Intent(this, AddCommentActivity.class);
            intent.putExtra("comment_id", commentId);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_del_comment) {
            UtilAction.bookComment.delete(this, commentId, new DoActionListener() {
                @Override
                public void doAction(Context context) {
                    finish();
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }
}
