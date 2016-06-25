package cn.edu.xjtu.se.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by DUAN Yufei on 16-6-25.
 */
public class XGFile {

    //在SD卡中创建目录
    public static boolean createPath(String path) {
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File sdDir=Environment.getExternalStorageDirectory();
            File newFolder = new File(sdDir.getPath()+path);
            Log.i("XGFile",newFolder.getAbsolutePath());
            if(!newFolder.exists()){
                return newFolder.mkdirs();
            }else{
                return true;
            }
        }
        return false;
    }
}
