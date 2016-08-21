package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.xjtu.se.bean.Comment;
import cn.edu.xjtu.se.dao.DBDao;

public class AddCommentActivity extends AppCompatActivity {

    private int bookId;
    private EditText et_add_comment;
    private int commentId;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        bookId = getIntent.getIntExtra("book_id", 0);
        commentId = getIntent.getIntExtra("comment_id", 0);
        setContentView(R.layout.activity_add_comment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_add_comment = (EditText) findViewById(R.id.et_add_comment);
        if (commentId > 0) {
            Comment comment = DBDao.getComment(commentId);
            et_add_comment.setText(comment.getContent());
            getSupportActionBar().setTitle(R.string.upd_comment);
            return;
        }
        getSupportActionBar().setTitle(R.string.add_comment);
        if (bookId == 0) {
            mToast(R.string.tip_err_comment);
            AddCommentActivity.this.finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_finish_comment) {
            //add a comment
            if (commentId > 0) {
                int ret = DBDao.updComment(commentId, et_add_comment.getText().toString());
                if (ret > 0) {
                    Toast.makeText(AddCommentActivity.this, R.string.tip_upd_comment_succeed, Toast.LENGTH_LONG).show();
                    AddCommentActivity.this.finish();
                } else {
                    Toast.makeText(AddCommentActivity.this, R.string.tip_upd_comment_fail, Toast.LENGTH_LONG).show();
                }
            } else {
                long rowID = DBDao.addComment(bookId, et_add_comment.getText().toString());
                if (rowID > 0) {
                    Toast.makeText(AddCommentActivity.this, R.string.tip_add_comment_succeed, Toast.LENGTH_LONG).show();
                    AddCommentActivity.this.finish();
                } else {
                    Toast.makeText(AddCommentActivity.this, R.string.tip_add_comment_fail, Toast.LENGTH_LONG).show();
                }
            }
        }
        return true;
    }

    private void mToast(int str) {
        Toast.makeText(AddCommentActivity.this, str, Toast.LENGTH_LONG).show();
    }

}
