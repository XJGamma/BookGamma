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
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        setContentView(R.layout.activity_login);
        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_register);
        final SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
        final String user = userInfo.getString("user", "");
        if (!user.isEmpty()) {
            etUser.setText(user);
            etUser.setFocusableInTouchMode(false);
        } else {
            etUser.setFocusableInTouchMode(true);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = userInfo.getString("pwd", "");
                if (etUser.getText().toString().equals(user)) {
                    String pwdE = "";
                    try {
                        byte[] bPwd = etPassword.getText().toString().getBytes("UTF-8");
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        pwdE = new String(md.digest(bPwd));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (pwdE.equals(pwd)) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        XGUserInfo.setStatus();
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "用户未注册", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
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
