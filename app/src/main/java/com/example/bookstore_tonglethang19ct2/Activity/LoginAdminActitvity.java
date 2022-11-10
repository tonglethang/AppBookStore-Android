package com.example.bookstore_tonglethang19ct2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookstore_tonglethang19ct2.R;
import com.example.bookstore_tonglethang19ct2.Utils.CheckConnection;

public class LoginAdminActitvity extends AppCompatActivity {
    EditText username, password;
    Button login, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_actitvity);
        mapping();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().length() == 0 || password.getText().toString().length() == 0){
                    CheckConnection.showToast_Short(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin !");
                }
                else{
                    if(username.getText().toString().equals("admin") && password.getText().toString().equals("123")){
                        Intent intent = new Intent(getApplicationContext(), AdminActitvity.class);
                        startActivity(intent);
                    }
                    else{
                        CheckConnection.showToast_Short(getApplicationContext(), "Thông tin đăng nhập không chính xác !");
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void mapping() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        back = findViewById(R.id.back);
    }
}