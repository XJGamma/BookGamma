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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.xjtu.se.dao.DBDao;

public class AddBookActivity extends AppCompatActivity implements OnClickListener{

    public static final int TAKE_PHOTO = 0;
    public static final int CHOOSE_ALBUM = 1;
    public static final int CROP_PHOTO = 2;

    private Uri imageUri;
    private Calendar finish_time;

    //弹出选择框
    private String[] select_pic = new String[]{"拍照", "从相册选择"};
    private RadioOnClick radioOnClick = new RadioOnClick();

    private EditText et_bookname;
    private EditText et_pages;
    private DatePicker dp;
    private Calendar calendar;
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
    }



    @Override
    public void onClick(View v){
        mlog(v.toString());
        switch (v.getId()) {
            case R.id.btn_finish_time:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(AddBookActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_finish_time.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
                                finish_time.set(year,monthOfYear,dayOfMonth);
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
                //check
                DBDao.addBook(et_bookname.getText().toString(),Integer.valueOf(et_pages.getText().toString()),finish_time.getTime(),"",imageUri.toString());
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

            switch (index) {
                case (TAKE_PHOTO):
                    //拍照
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    Date now = new Date();
                    String fileName = formatter.format(now) + ".jpg";
                    File outputImage = new File(Environment.getExternalStorageDirectory(),
                            fileName);
                    try {
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUri = Uri.fromFile(outputImage);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, TAKE_PHOTO);
                    break;
                case (CHOOSE_ALBUM):

                    //Choose from Album Image Selector
                    SimpleDateFormat formatter_c = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                    Date now_c = new Date();
                    String fileName_c = formatter_c.format(now_c) + ".jpg";
                    File outputImage_c = new File(Environment.getExternalStorageDirectory(),
                            fileName_c);
                    try {
                        outputImage_c.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUri = Uri.fromFile(outputImage_c);
                    Intent intent_c = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent_c.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

                    //intent_c.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent_c, CHOOSE_ALBUM);
                    break;

            }
            //Toast.makeText(AddBookActivity.this,index+":"+select_pic[index],Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mlog("resultCode=" + resultCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                mlog("Take a Pic");
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                    mlog("imageurl=" + imageUri.toString());
                }
                break;

            case CHOOSE_ALBUM:
                mlog("Choose a Pic");
                if (resultCode == RESULT_OK) {
                    Uri tmp_uri = data.getData();
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(tmp_uri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                    mlog("imageurl=" + imageUri.toString());
                }

            case CROP_PHOTO:
                mlog("Crop the Pic");
                mlog("imageUri=" + imageUri.toString());
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
            default:
                break;
        }
    }

    private void mlog(String str) {
        Log.i("AddBookActivity", str);
    }
}
