package cn.edu.xjtu.se.util;

import android.content.SharedPreferences;
import android.widget.Toast;

import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-8-27.
 */
public class XGUserInfo {
    public static int getStatus() {
        SharedPreferences userInfo = XGApplication.getContext().getSharedPreferences("userInfo", 0);
        int status = userInfo.getInt("status", -1);
        return status;
    }

    public static void setStatus() {
        SharedPreferences userInfo = XGApplication.getContext().getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();
        int status = userInfo.getInt("status", -1);
        if (status == 1) {
            editor.putInt("status", 0);
            Toast.makeText(XGApplication.getContext(), R.string.tip_logout_succeed, Toast.LENGTH_SHORT).show();

        } else {
            editor.putInt("status", 1);
            Toast.makeText(XGApplication.getContext(), R.string.tip_login_succeed, Toast.LENGTH_SHORT).show();
        }
        editor.commit();
    }

}
