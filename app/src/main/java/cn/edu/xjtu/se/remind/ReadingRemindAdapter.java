package cn.edu.xjtu.se.remind;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.List;

import cn.edu.xjtu.se.bookgamma.R;
import cn.edu.xjtu.se.dao.DBHelper;
import cn.edu.xjtu.se.remind.alarm.AlarmReceiver;
import cn.edu.xjtu.se.util.UtilAction;


/**
 * Created by asus on 2016/8/23.
 */
public class ReadingRemindAdapter extends ArrayAdapter<ReadingRemind> {
    private int resourceId;
    private List<ReadingRemind> ReadingRemindList;
    static private cn.edu.xjtu.se.dao.DBHelper dbHelper;
    private Context context;
    WeakReference<ReadingRemindActivity> weak;

    private ImageLoader imageLoader;

    class ViewHolder {

        ImageView bookImage;
        TextView bookName;
        TextView remindTime;
        Switch switcher;

    }

    public ReadingRemindAdapter(Context context, int textViewResourceId, List<ReadingRemind> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        resourceId = textViewResourceId;
        this.ReadingRemindList = objects;
        this.weak = new WeakReference<ReadingRemindActivity>((ReadingRemindActivity) context);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReadingRemind readingRemind = getItem(position);//获取当前Book实例
//        int selectID = position;

        View view;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.bookImage = (ImageView) convertView.findViewById(R.id.remind_book_image);
            viewHolder.bookName = (TextView) convertView.findViewById(R.id.remind_book_name);
            viewHolder.remindTime = (TextView) convertView.findViewById(R.id.remind_time);
            viewHolder.switcher = (Switch) convertView.findViewById(R.id.remind_switch);

            final ViewHolder finalViewHolder = viewHolder;


            if (readingRemind.getStatus() == 1) {
                viewHolder.switcher.setChecked(true);
            } else {
                viewHolder.switcher.setChecked(false);
            }

            viewHolder.switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        ReadingRemind info = (ReadingRemind) finalViewHolder.switcher.getTag();
                        info.setStatus(1);

                        dbHelper = new DBHelper(context);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("status", 1);
                        db.update("ReadingRemind", values, "id = ?", new String[]{String.valueOf(readingRemind.getId())});
                        db.close();

                        final ReadingRemindActivity activity = weak.get();
                        activity.startAlarmClock(readingRemind.getId(), readingRemind.getRemindTime());
                        Toast.makeText(context, readingRemind.getBookName() + "的提醒已开启", Toast.LENGTH_SHORT).show();
                    } else {
                        ReadingRemind info = (ReadingRemind) finalViewHolder.switcher.getTag();
                        info.setStatus(0);

                        dbHelper = new DBHelper(context);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("status", 0);
                        db.update("ReadingRemind", values, "id = ?", new String[]{String.valueOf(readingRemind.getId())});
                        db.close();

                        final ReadingRemindActivity activity = weak.get();
                        activity.closeAlarmClock(readingRemind.getId());

                        Toast.makeText(context, readingRemind.getBookName() + "的提醒已关闭", Toast.LENGTH_SHORT).show();

                    }
                }
            });
            convertView.setTag(viewHolder);
            viewHolder.switcher.setTag(readingRemind);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.switcher.setTag(readingRemind);
        }

        imageLoader.displayImage(readingRemind.getImage(), viewHolder.bookImage, UtilAction.getDisplayImageOptions());
        viewHolder.bookName.setText(readingRemind.getBookName());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String startTime = sdf.format(new java.util.Date(Long.parseLong(readingRemind.getRemindTime())));
        viewHolder.remindTime.setText(startTime);
        return convertView;
    }


}

