package cn.edu.xjtu.se.bookgamma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;

public class RegActivity extends AppCompatActivity {

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
                    try{
                        byte[] bPwd = etPasswd.getText().toString().getBytes("UTF-8");
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        pwdE = new String(md.digest(bPwd));
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences userInfo = getSharedPreferences("userInfo", 0);
                    SharedPreferences.Editor editor = userInfo.edit();
                    editor.putString("user",etUser.getText().toString());
                    editor.putString("pwd", pwdE);
                    editor.commit();
                    Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }
}
