package cn.edu.xjtu.se.remind.alarm;
/**
 * Created by qh on 2016/8/22.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import cn.edu.xjtu.se.bookgamma.MainActivity;

public class AlarmActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //显示对话框
        new AlertDialog.Builder(AlarmActivity.this).
                setTitle("读书提醒").//设置标题
                setMessage("来看书吧！总有一天你能像我一样谈笑风生！").//设置内容
                setPositiveButton("好的长者", new DialogInterface.OnClickListener(){//设置按钮
            public void onClick(DialogInterface dialog, int which) {
                Intent readingIntent = new Intent(AlarmActivity.this, MainActivity.class);
                startActivity(readingIntent);
                AlarmActivity.this.finish();//关闭Activity
            }
        }).create().show();





    }



}
