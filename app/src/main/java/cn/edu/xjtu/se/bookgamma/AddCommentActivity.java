package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.xjtu.se.dao.DBDao;

public class AddCommentActivity extends AppCompatActivity {

    private int book_id;
    private EditText et_add_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        book_id = getIntent.getIntExtra("book_id", 0);
        setContentView(R.layout.activity_add_comment);
        et_add_comment = (EditText) findViewById(R.id.et_add_comment);
        if (book_id == 0) {
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
            long rowID = DBDao.addComment(book_id, et_add_comment.getText().toString());
            if (rowID > 0) {
                Toast.makeText(AddCommentActivity.this, R.string.tip_add_comment_succeed, Toast.LENGTH_LONG).show();
                AddCommentActivity.this.finish();
            } else {
                Toast.makeText(AddCommentActivity.this, R.string.tip_add_comment_fail, Toast.LENGTH_LONG).show();
            }
        }
        return true;
    }

    private void mToast(int str) {
        Toast.makeText(AddCommentActivity.this, str, Toast.LENGTH_LONG).show();
    }

}
