package cn.edu.xjtu.se.remind.alarm;
/**
 * Created by qh on 2016/8/22.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import cn.edu.xjtu.se.bookgamma.MainActivity;
import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.remind.ReadingRemindActivity;

public class AlarmActivity extends Activity {
    private MediaPlayer mp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mp = MediaPlayer.create(AlarmActivity.this, R.raw.music);
        if (mp != null) {
            mp.start();//开始播放
        }

        Intent intent = getIntent();
        String bookName = intent.getStringExtra("bookName");
        //显示对话框
        new AlertDialog.Builder(AlarmActivity.this).
                setTitle("读书提醒").//设置标题
                setMessage("是时候阅读 " + bookName + " 了！").//设置内容
                setPositiveButton("好的", new DialogInterface.OnClickListener(){//设置按钮
            public void onClick(DialogInterface dialog, int which) {
                Intent readingIntent = new Intent(AlarmActivity.this, ReadingRemindActivity.class);
                startActivity(readingIntent);
                AlarmActivity.this.finish();//关闭Activity
            }
        }).create().show();

    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mp.stop();//停止播放
        mp.release();//释放资源
    }

}
