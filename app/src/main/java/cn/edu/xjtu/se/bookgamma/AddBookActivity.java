package cn.edu.xjtu.se.bookgamma;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.xjtu.se.dao.DBDao;
import cn.edu.xjtu.se.util.XGFile;

public class AddBookActivity extends AppCompatActivity implements OnClickListener {

    public static final int TAKE_PHOTO = 0;
    public static final int CHOOSE_ALBUM = 1;
    public static final int CROP_PHOTO = 2;

    private static final String FOLDER = "/BookGamma/image/";

    private Uri imageUri;
    private Calendar finish_time;

    //弹出选择框
    private String[] select_pic = new String[]{"拍照", "从相册选择"};
    private RadioOnClick radioOnClick = new RadioOnClick();

    private EditText et_bookname;
    private EditText et_pages;
    private ImageView iv_bookimage;
    private ListView lv_select_pic;
    private Button btn_finish_time;
    private Button btn_save;
    private TextView tv_finish_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        et_bookname = (EditText) findViewById(R.id.et_bookname);
        et_pages = (EditText) findViewById(R.id.et_pages);
        iv_bookimage = (ImageView) findViewById(R.id.iv_bookimage);
        btn_finish_time = (Button) findViewById(R.id.btn_finish_time);
        btn_save = (Button) findViewById(R.id.btn_addbook);
        tv_finish_time = (TextView) findViewById(R.id.tv_finish_time);
        finish_time = Calendar.getInstance();
        String str_finish_time = finish_time.get(Calendar.YEAR) + "年"
                + finish_time.get(Calendar.MONTH) + "月"
                + finish_time.get(Calendar.DAY_OF_MONTH) + "日";
        tv_finish_time.setText(str_finish_time);

        btn_finish_time.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        iv_bookimage.setOnClickListener(this);

        if (!XGFile.createPath(FOLDER)) {
            mToast(R.string.tip_err_create_folder);
        }
    }


    @Override
    public void onClick(View v) {
        mLog(v.toString());
        switch (v.getId()) {
            case R.id.btn_finish_time:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(AddBookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_finish_time.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
                                finish_time.set(year, monthOfYear, dayOfMonth);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.iv_bookimage:
                AlertDialog ad = new AlertDialog.Builder(AddBookActivity.this)
                        .setTitle(R.string.title_choose_pic)
                        .setSingleChoiceItems(select_pic, radioOnClick.getIndex(), radioOnClick)
                        .create();
                lv_select_pic = ad.getListView();
                ad.show();
                break;
            case R.id.btn_addbook:
                //完整性检查
                if (et_bookname.getText().length() == 0) {
                    mToast(R.string.tip_no_book_name);
                    break;
                }
                if (et_pages.getText().length() == 0) {
                    mToast(R.string.tip_no_book_pages);
                    break;
                }
                if (imageUri == null) {
                    mToast(R.string.tip_no_book_image);
                    break;
                }
                //完成日期不能晚于当前日期
                Calendar now = Calendar.getInstance();
                now.set(Calendar.HOUR_OF_DAY, 0);
                now.set(Calendar.MINUTE, 0);
                now.set(Calendar.SECOND, 0);
                mLog(now.getTime().toString());
                if (finish_time.before(now)) {
                    mToast(R.string.tip_err_finish_time);
                    break;
                }
                long row = DBDao.addBook(et_bookname.getText().toString(), Integer.valueOf(et_pages.getText().toString()), finish_time.getTime(), "", imageUri.toString());
                mLog("rowID=" + row);
                mToast(R.string.tip_add_book_succeed);
                AddBookActivity.this.finish();
                break;
            default:
                break;
        }
    }

    class RadioOnClick implements DialogInterface.OnClickListener {
        private int index;

        public RadioOnClick() {
            this.index = 0;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            setIndex(which);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String fileName = FOLDER + formatter.format(now) + ".jpg";
            File outputImage = new File(Environment.getExternalStorageDirectory(), fileName);
            try {
                outputImage.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageUri = Uri.fromFile(outputImage);

            switch (index) {
                case (TAKE_PHOTO):
                    //拍照
                    Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intentPhoto, TAKE_PHOTO);
                    break;
                case (CHOOSE_ALBUM):
                    //Choose from Album Image Selector
                    Intent intentAlbum = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intentAlbum.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intentAlbum, CHOOSE_ALBUM);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_ALBUM:
                if (resultCode == RESULT_OK) {
                    imageUri = data.getData();
                }

            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageUri));
                        iv_bookimage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void mLog(String str) {
        Log.i("AddBookActivity", str);
    }

    private void mToast(int str) {
        Toast.makeText(AddBookActivity.this, getResources().getString(str), Toast.LENGTH_LONG).show();
    }
}
