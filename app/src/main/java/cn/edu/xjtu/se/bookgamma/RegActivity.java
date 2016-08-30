package cn.edu.xjtu.se.bookgamma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                String name = etUser.getText().toString();
                String password = etPasswd.getText().toString();
                String passwordAgain = etPasswdA.getText().toString();
                if (name.length() == 0) {
                    UtilAction.toast.s(RegActivity.this, "用户名不能为空！");
                } else if (password.length() == 0) {
                    UtilAction.toast.s(RegActivity.this, "密码不能为空！");
                } else if (password.length() < 6) {
                    UtilAction.toast.s(RegActivity.this, "密码需大于6位！");
                } else if (password.compareTo(passwordAgain) != 0) {
                    UtilAction.toast.s(RegActivity.this, "两次密码不一致");
                } else {
                    signup(name, password);
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
