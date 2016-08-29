package cn.edu.xjtu.se.bookgamma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class RegActivity extends AppCompatActivity {
    public static final String TAG = RegActivity.class.getName();

    private EditText etUser;
    private EditText etPasswd;
    private EditText etPasswdA;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etUser = (EditText) findViewById(R.id.et_user);
        etPasswd = (EditText) findViewById(R.id.et_password);
        etPasswdA = (EditText) findViewById(R.id.et_password_a);
        btnReg = (Button) findViewById(R.id.btn_register);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etPasswd.getText().toString().equals(etPasswdA.getText().toString())) {
                    Toast.makeText(RegActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    String pwdE;
                    try {
                        byte[] bPwd = etPasswd.getText().toString().getBytes("UTF-8");
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        pwdE = new String(md.digest(bPwd));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.putString("user", etUser.getText().toString());
                    editor.putString("pwd", pwdE);
                    editor.commit();
//                    Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    XGUserInfo.setStatus();
                    Intent intent = new Intent(RegActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void signup(final String name, String password) {
        final XGAPI.SignupParameter sp = new XGAPI.SignupParameter(name, password);
        String json = XGAPI.gson.toJson(sp);
        XGAPI.xgHttp.post(XGAPI.SIGNUP_URL, json, new XGHttp.MOkCallBack() {
            @Override
            public void onSuccess(String str) {
                XGAPI.SignupReturn sr = XGAPI.getReturn(RegActivity.this, str, XGAPI.SignupReturn.class);
                UtilAction.toast.s(RegActivity.this, sr.getMsg());
                if (sr.getCode() == 0) {
                    XGUserInfo.login(name, sr.getToken());
                    finish();
                } else if (sr.getCode() == 1) {
                    // TODO: 用户名已占用
                } else {
                    Log.e(TAG, "Sign Up: undefined code!");
                }
            }

            @Override
            public void onError() {
                UtilAction.toast.s(RegActivity.this, "注册遇到问题，请重试！");
            }
        });
    }
}
