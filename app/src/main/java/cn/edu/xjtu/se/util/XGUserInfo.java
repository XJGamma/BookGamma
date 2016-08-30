package cn.edu.xjtu.se.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Base64;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.edu.xjtu.se.bookgamma.R;

/**
 * Created by DUAN Yufei on 16-8-27.
 */
public class XGUserInfo {
    private static boolean status;
    private static String name;
    private static String token;
    private static String avatar;

    static {
        initialize();
    }

    private static void initialize() {
        SharedPreferences sp = XGApplication.getContext().getSharedPreferences("userInfo", 0);
        status = sp.getBoolean("status", false);
        name = sp.getString("name", null);
        token = sp.getString("token", null);
        avatar = sp.getString("avatar", null);
    }

    public static String getName() {
        return name;
    }

    public static String getToken() {
        return token;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static boolean getStatus() {
        return status;
    }

    public static void setStatus() {
        SharedPreferences userInfo = XGApplication.getContext().getSharedPreferences("userInfo", 0);
        SharedPreferences.Editor editor = userInfo.edit();
        boolean status = userInfo.getBoolean("status", false);
        if (status) {
            editor.putBoolean("status", false);
            Toast.makeText(XGApplication.getContext(), R.string.tip_logout_succeed, Toast.LENGTH_SHORT).show();
        } else {
            editor.putBoolean("status", true);
            Toast.makeText(XGApplication.getContext(), R.string.tip_login_succeed, Toast.LENGTH_SHORT).show();
        }
        editor.commit();
    }

    public static void login(String name, String token) {
        XGApplication.getContext().getSharedPreferences("userInfo", 0)
                .edit()
                .putBoolean("status", true)
                .putString("name", name)
                .putString("token", token)
                .commit();
        initialize();
    }

    public static void logout() {
        XGApplication.getContext().getSharedPreferences("userInfo", 0)
                .edit()
                .clear()
                .commit();
        initialize();
    }

    private static final String AVATAR_NAME = "avatar.png";
    public static void setAvatar(String encodedAvatar) {
        Context context = XGApplication.getContext();
        try {
            FileOutputStream fos = context.openFileOutput(AVATAR_NAME, Context.MODE_PRIVATE);
            byte[] data = Base64.decode(encodedAvatar, Base64.DEFAULT);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context.getSharedPreferences("userInfo", 0)
                .edit()
                .putString("avatar", context.getFilesDir() + File.separator + AVATAR_NAME);
        initialize();
    }
}
