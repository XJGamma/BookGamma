package cn.edu.xjtu.se.remind.alarm;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;

import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.dao.DBHelper;

/**
 * Created by asus on 2016/8/22.
 */
public class AlarmReceiver extends BroadcastReceiver {
    static private cn.edu.xjtu.se.dao.DBHelper dbHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        int remindId = bundle.getInt("remindId");
        System.out.println("remind Id = " + remindId);
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status",0);
        db.update("ReadingRemind", values ,"id = ?", new String[]{String.valueOf(remindId)});
        db.close();


        Intent i=new Intent(context, AlarmActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}