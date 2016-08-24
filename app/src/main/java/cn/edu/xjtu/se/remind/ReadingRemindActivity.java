package cn.edu.xjtu.se.remind;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.edu.xjtu.se.bookgamma.MainActivity;
import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.booklistview.Book;
import cn.edu.xjtu.se.booklistview.BookAdapter;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.dao.DBHelper;
import cn.edu.xjtu.se.remind.alarm.AlarmReceiver;

public class ReadingRemindActivity extends AppCompatActivity{
    private Toolbar toolbar;
    private cn.edu.xjtu.se.dao.DBHelper dbHelper;
    private List<ReadingRemind> remindList = new ArrayList<ReadingRemind>();
    private static final String TAG = MainActivity.class.getSimpleName();
    public static List<RunningClock> clockList = new ArrayList<>();
    Calendar cal = Calendar.getInstance();

    class RunningClock {
        int id;
        PendingIntent sender;
        String remindTime;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reading_remind);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initRemind();//初始化书籍信息
        ReadingRemindAdapter adapter = new ReadingRemindAdapter(ReadingRemindActivity.this, R.layout.remind_item,remindList);
        ListView listView = (ListView) findViewById(R.id.remind_list_view);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        remindList.clear();
        initRemind();
        ReadingRemindAdapter adapter = new ReadingRemindAdapter(ReadingRemindActivity.this, R.layout.remind_item, remindList);
        ListView listView = (ListView) findViewById(R.id.remind_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                final ReadingRemind readRemind = remindList.get(position);
                //Toast.makeText(ReadingRemindActivity.this, remindList.get(position).getBookName() + "的闹钟设置成功", Toast.LENGTH_LONG).show();
                final Calendar c=Calendar.getInstance();//获取日期对象
                c.setTimeInMillis(System.currentTimeMillis());
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                new TimePickerDialog(ReadingRemindActivity.this,new TimePickerDialog.OnTimeSetListener()
                {

                    @Override

                    public void onTimeSet(TimePicker view, int hourOfDay ,int minute)
                    {
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);

                        if (readRemind.getStatus() == 1){
                            closeAlarmClock(readRemind.getId());
                            startAlarmClock(readRemind.getId(),Long.toString(c.getTimeInMillis()));
                        }

                        Log.d(TAG, "当前时间 : " + new  java.util.Date(System.currentTimeMillis()));
                        Log.d(TAG, "闹钟时间 : " + new  java.util.Date(c.getTimeInMillis()));

//                        if(c.getTimeInMillis() < System.currentTimeMillis()){
//                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
//                        }

                        long row = DBDao.updateReadingRemindTime(remindList.get(position).getId(),Long.toString(c.getTimeInMillis()));
                        if (row > 0) {
                            Toast.makeText(ReadingRemindActivity.this, remindList.get(position).getBookName() + "的闹钟修改成功", Toast.LENGTH_LONG).show();//提示用户

                        } else {
                            Toast.makeText(ReadingRemindActivity.this, remindList.get(position).getBookName() + "的闹钟修改失败", Toast.LENGTH_LONG).show();//提示用户
                        }

                        onStart();
                    }

                }, hour, minute, false).show();

            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final ReadingRemind readRemind = remindList.get(position);
                new AlertDialog.Builder(ReadingRemindActivity.this).setTitle("系统提示")//设置对话框标题

                        .setMessage("您确定要删除这本 " + readRemind.getBookName() + " 的提醒吗?")//设置显示的内容

                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                dbHelper = new DBHelper(ReadingRemindActivity.this);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete("ReadingRemind", "id = ?",new String[]{String.valueOf(readRemind.getId())});
                                db.close();
                                // TODO Auto-generated method stub

                                //finish();
                                onStart();

                            }

                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮

                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        // TODO Auto-generated method stub


                    }

                }).show();//在按键响应事件中显示此对话框

                return true;
            }
        });
    }

    private void initRemind() {

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("ReadingRemind", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(cursor. getColumnIndex("id"));
                int bookId = cursor.getInt(cursor. getColumnIndex("book_id"));
                String bookName = cursor.getString(cursor. getColumnIndex("book_name"));
                String image = cursor.getString(cursor. getColumnIndex("image"));
                String remindTime = cursor.getString(cursor. getColumnIndex("remind_time"));
                int status = cursor.getInt(cursor. getColumnIndex("status"));
//                Log.d(TAG, "remind_id is " + id);
//                Log.d(TAG, "book_id is " + bookId);
//                Log.d(TAG, "book name is " + bookName);
//                Log.d(TAG, "book image is " + image);
//                Log.d(TAG, "remind_time is " + remindTime);
//                Log.d(TAG, "status is " + status);
                ReadingRemind remind_element = new ReadingRemind(id, bookId, bookName, image, remindTime, status);
                remindList.add(remind_element);
                if (status == 1){
                    startAlarmClock(id, remindTime);
                }
            }while (cursor.moveToNext());

        }
        db.close();

    }

    public void startAlarmClock(int remindId, String remindTime) {

        RunningClock clockItem = new RunningClock();
        int flag = 0;
        for (RunningClock c:clockList){
            if(c.id == remindId){
                flag = 1;
            }
        }

        if(Long.parseLong(remindTime) >= System.currentTimeMillis() && flag == 0){
            Intent intent = new Intent(ReadingRemindActivity.this, AlarmReceiver.class);    //创建Intent对象
            PendingIntent sender = PendingIntent.getBroadcast(ReadingRemindActivity.this, remindId, intent, 0);    //创建PendingIntent
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

            am.set(AlarmManager.RTC_WAKEUP, Long.parseLong(remindTime), sender);        //设置闹钟，到指定时间唤醒
            Log.d(TAG, "当前时间 : " + new  java.util.Date(System.currentTimeMillis()));
            Log.d(TAG, "以下时间的闹钟已开启 : " + new  java.util.Date(Long.parseLong(remindTime)));
            clockItem.id = remindId;
            clockItem.sender = sender;
            clockItem.remindTime = remindTime;
            clockList.add(clockItem);
        }

    }

    public void closeAlarmClock(int remindId) {
        RunningClock object = new RunningClock();
        for (RunningClock c:clockList){
            if(c.id == remindId){
                Intent intent = new Intent(ReadingRemindActivity.this, AlarmReceiver.class);
                //PendingIntent sender = PendingIntent.getBroadcast(ReadingRemindActivity.this, 0, intent, 0);
                // And cancel the alarm.
                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.cancel(c.sender);
                Log.d(TAG, "以下时间的闹钟已关闭 : " + new  java.util.Date(Long.parseLong(c.remindTime)));
                object = c;
            }
        }
        clockList.remove(object);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reading_remind, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Books", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            Intent addBookIntent = new Intent(ReadingRemindActivity.this, AddRemindActivity.class);
            startActivity(addBookIntent);
        }
        else{
            new AlertDialog.Builder(ReadingRemindActivity.this).setTitle("系统提示")//设置对话框标题
                    .setMessage("你需要先添加一本书籍！ ")//设置显示的内容
                    .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            // TODO Auto-generated method stub
                        }
                    }).show();//在按键响应事件中显示此对话框
        }
        return super.onOptionsItemSelected(item);
    }



}