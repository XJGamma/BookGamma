package cn.edu.xjtu.se.bookgamma;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.xjtu.se.dao.DBHelper;

public class AddBookActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    public static final int TAKE_PHOTO = 0;
    public static final int CHOOSE_ALBUM = 1;
    public static final int CROP_PHOTO = 2;


    private Uri imageUri;

    //弹出选择框
    private String[] select_pic = new String[]{"拍照", "从相册选择"};
    private RadioOnClick radioOnClick = new RadioOnClick();

    private EditText et_bookname;
    private EditText et_pages;
    private ImageView iv_bookimage;
    private ListView lv_select_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new DBHelper(this);

        et_bookname = (EditText) findViewById(R.id.et_bookname);
        et_pages = (EditText) findViewById(R.id.et_pages);
        iv_bookimage = (ImageView) findViewById(R.id.iv_bookimage);

        iv_bookimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad = new AlertDialog.Builder(AddBookActivity.this)
                        .setTitle(R.string.title_choose_pic)
                        .setSingleChoiceItems(select_pic, radioOnClick.getIndex(), radioOnClick)
                        .create();
                lv_select_pic = ad.getListView();
                ad.show();
            }


        });
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
                    Intent intent_c = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent_c.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

//                    intent_c.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
