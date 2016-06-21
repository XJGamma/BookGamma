package cn.edu.xjtu.se.bookgamma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;

import cn.edu.xjtu.se.dao.DBHelper;

public class AddBookActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    public static final int TAKE_PHOTO = 0;
    public static final int CROP_PHOTO = 1;


    private Uri imageUri;

    //弹出选择框
    private String[] select_pic = new String[]{"拍照","从相册选择"};
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

        et_bookname = (EditText)findViewById(R.id.et_bookname);
        et_pages = (EditText)findViewById(R.id.et_pages);
        iv_bookimage = (ImageView)findViewById(R.id.iv_bookimage);

        iv_bookimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad = new AlertDialog.Builder(AddBookActivity.this)
                        .setTitle(R.string.title_choose_pic)
                        .setSingleChoiceItems(select_pic,radioOnClick.getIndex(),radioOnClick)
                        .create();
                lv_select_pic = ad.getListView();
                ad.show();
            }


        });
    }
    class RadioOnClick implements DialogInterface.OnClickListener{
        private int index;

        public RadioOnClick(){
            this.index = 0;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public void onClick(DialogInterface dialog, int which){
            setIndex(which);
            File outputImage;
            Intent intent;
            switch (index){
                case (TAKE_PHOTO):
                    //拍照
                    outputImage = new File(Environment.getExternalStorageDirectory(),
                            "output_image.jpg");
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUri = Uri.fromFile(outputImage);
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent,TAKE_PHOTO);
                    break;
                case (CROP_PHOTO):
                    //从相册选择
                    Log.i("AddBookActivity","点击从相册选择");
                    Intent intent_c = new Intent();
                    intent_c.setAction(Intent.ACTION_PICK);
                    intent_c.setType("image/*");
                    startActivityForResult(intent_c,CROP_PHOTO);
                    Log.i("AddBookActivity","启动相册");


                    /*
                    outputImage = new File(Environment.getExternalStorageDirectory(),
                            "output_image.jpg");
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUri = Uri.fromFile(outputImage);
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, 0);
                    */
                    break;
            }
            //Toast.makeText(AddBookActivity.this,index+":"+select_pic[index],Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                Log.d("AddBookActivity","Take Photo");
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    //Here is bug...
                    Uri uric = data.getData();
                    Log.i("AddBookActivity",uric.toString());
                    iv_bookimage.setImageURI(uric);
                    /*
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imageUri));
                        iv_bookimage.setImageBitmap(bitmap);
                        Log.d("AddBookActivity","Crop Photo");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    */
                }
                break;
            default:
                break;
        }
    }
}
