package cn.edu.xjtu.se.bookgamma;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;

import cn.edu.xjtu.se.util.UtilAction;
import cn.edu.xjtu.se.util.XGAPI;
import cn.edu.xjtu.se.util.XGHttp;
import cn.edu.xjtu.se.util.XGUserInfo;

public class AccountActivity extends AppCompatActivity {
    private static String Tag = AccountActivity.class.getName();

    private TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvAbout = (TextView) findViewById(R.id.tv_about);
        tvAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void logout() {
        if (!XGUserInfo.getStatus()) {
            UtilAction.toast.s(this, "未登录！");
        }

        String name = XGUserInfo.getName();
        String token = XGUserInfo.getToken();

        XGAPI.LogoutParameter lp = new XGAPI.LogoutParameter(name, token);
        String json = XGAPI.gson.toJson(lp);

        XGAPI.xgHttp.post(XGAPI.LOGOUT_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                XGAPI.LogoutReturn lr = XGAPI.getReturn(AccountActivity.this, str, XGAPI.LogoutReturn.class);
                // 只有一种结果
                // 不看也罢
            }

            @Override
            public void onError() {

            }
        });

        XGUserInfo.logout();
        UtilAction.toast.s(this, "注销成功！");
    }
}
