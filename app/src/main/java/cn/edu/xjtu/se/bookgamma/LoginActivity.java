package cn.edu.xjtu.se.bookgamma;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.security.MessageDigest;

import cn.edu.xjtu.se.util.UtilAction;
import cn.edu.xjtu.se.util.XGAPI;
import cn.edu.xjtu.se.util.XGHttp;
import cn.edu.xjtu.se.util.XGUserInfo;

/**
 * Created by DUAN Yufei on 2016/6/16.
 */
public class LoginActivity extends Activity {
    public static final String TAG = LoginActivity.class.getName();

    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (XGUserInfo.getStatus()) {
            UtilAction.toast.s(this, "用户已登录！");
            finish();
        }

        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_register);

        etUser.setFocusableInTouchMode(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUser.getText().toString();
                String password = etPassword.getText().toString();
                login(name, password);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void login(final String name, String password) {
        XGAPI.LoginParameter lp = new XGAPI.LoginParameter(name, password);
        String json = XGAPI.gson.toJson(lp);
        XGAPI.xgHttp.post(XGAPI.LOGIN_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                XGAPI.LoginReturn lr = XGAPI.getReturn(LoginActivity.this, str, XGAPI.LoginReturn.class);
                UtilAction.toast.s(LoginActivity.this, lr.getMsg());
                if (lr.getCode() == 0) {
                    XGUserInfo.login(name, lr.getToken());
                    finish();
                } else if (lr.getCode() == 1) {
                    // TODO: 用户名或密码错误
                } else {
                    Log.e(TAG, "Login: undefined code!");
                }
            }

            @Override
            public void onError() {
                UtilAction.toast.s(LoginActivity.this, "登录遇到问题，请重试！");
            }
        });
    }
}
