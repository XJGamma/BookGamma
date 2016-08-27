package cn.edu.xjtu.se.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by DUAN Yufei on 2016/6/16.
 */
public class XGApplication extends Application {
    //Global context
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
