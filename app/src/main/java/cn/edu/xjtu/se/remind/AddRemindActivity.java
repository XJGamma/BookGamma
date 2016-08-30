package cn.edu.xjtu.se.remind;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.edu.xjtu.se.bean.Book;
import cn.edu.xjtu.se.bookgamma.MainActivity;
import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.remind.alarm.WheelView;

public class AddRemindActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button btn = null;
    private AlarmManager alarmManager = null;
    Calendar cal = Calendar.getInstance();
    final int DIALOG_TIME = 0;    //设置对话框id
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final List<Book> BOOK_LIST = new ArrayList<>();
    private static final List<String> BOOK_NAME = new ArrayList<>();
    private static String CurrentItemName = "";
    private static int CurrentItemId = 0;
    private static String CurrentItemImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_reading_remind);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        btn = (Button) findViewById(R.id.set_alarm_clock);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(DIALOG_TIME);//显示时间选择对话框
            }
        });

        BOOK_LIST.clear();
        BOOK_NAME.clear();
        List<Book> list = DBDao.findBooksAll();
        if (list.size() > 0) {
            for (Book book : list) {
                BOOK_LIST.add(book);
                BOOK_NAME.add(book.getName());
            }

            WheelView wva = (WheelView) findViewById(R.id.main_wv);
            wva.setOffset(1);
            wva.setItems(BOOK_NAME);
            CurrentItemName = BOOK_NAME.get(0);
            CurrentItemId = BOOK_LIST.get(0).getId();
            CurrentItemImage = BOOK_LIST.get(0).getImage();
            wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
                @Override
                public void onSelected(int selectedIndex, String item) {
                    Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
                    CurrentItemName = item;
                    CurrentItemId = BOOK_LIST.get(selectedIndex - 1).getId();
                    CurrentItemImage = BOOK_LIST.get(selectedIndex - 1).getImage();
                }
            });

        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_TIME:
                dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();//获取日期对象
                                c.setTimeInMillis(System.currentTimeMillis());        //设置Calendar对象
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);        //设置闹钟小时数
                                c.set(Calendar.MINUTE, minute);            //设置闹钟的分钟数
                                c.set(Calendar.SECOND, 0);                //设置闹钟的秒数
                                c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
                                /*
                                Intent intent = new Intent(AddRemindActivity.this, AlarmReceiver.class);    //创建Intent对象
                                PendingIntent sender = PendingIntent.getBroadcast(AddRemindActivity.this, 0, intent, 0);    //创建PendingIntent
                                AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                                am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);        //设置闹钟，到指定时间唤醒
                                */
                                //Log.d(TAG, "当前时间 : " + new  java.util.Date(System.currentTimeMillis()));
                                //Log.d(TAG, "闹钟时间 : " + new  java.util.Date(c.getTimeInMillis()));
                                //Toast.makeText(AddRemindActivity.this, CurrentItemName + "的闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户
                                //long row = DBDao.addBook(et_bookname.getText().toString(), Integer.valueOf(et_pages.getText().toString()), finish_time.getTime(), "", imageUri.toString());

//                                if(c.getTimeInMillis() < System.currentTimeMillis()){
//                                    c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
//                                }
                                long row = DBDao.addReadingRemind(CurrentItemId, CurrentItemName, CurrentItemImage, Long.toString(c.getTimeInMillis()), 1);
                                //Log.d(TAG, "row =  : "  + Long.toString(row));
                                if (row > 0) {
                                    Toast.makeText(AddRemindActivity.this, CurrentItemName + "的闹钟设置成功", Toast.LENGTH_LONG).show();//提示用户

                                } else {
                                    Toast.makeText(AddRemindActivity.this, CurrentItemName + "的闹钟设置失败", Toast.LENGTH_LONG).show();//提示用户
                                }
                                AddRemindActivity.this.finish();
//                                Intent readingIntent = new Intent(AddRemindActivity.this, ReadingRemindActivity.class);
//                                startActivity(readingIntent);

                            }
                        },
                        cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        false);

                break;
        }
        return dialog;
    }

}
