package com.example.fecultypannel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class FecultyProfileActivity extends AppCompatActivity {

    ImageView iv_logo;
    TextView tv_feculty_name, tv_feculty_email, tv_feculty_sub, tv_feculty_gender, tv_feculty_contact, tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feculty_profile);

        String f_photo = sharedPreferences.getString("f_photo", null);
        String f_name = sharedPreferences.getString("f_name", null);
        String f_email = sharedPreferences.getString("f_email", null);
        String f_sub = sharedPreferences.getString("f_sub", null);
        String f_gender = sharedPreferences.getString("f_gender", null);
        String f_contact = sharedPreferences.getString("f_contact", null);
        String f_pass = sharedPreferences.getString("f_pass", null);

        iv_logo = findViewById(R.id.iv_logo);
        //uper_feculty_name = findViewById(R.id.uper_feculty_name);
        tv_feculty_name = findViewById(R.id.tv_feculty_name);
        tv_feculty_email = findViewById(R.id.tv_feculty_email);
        tv_feculty_sub = findViewById(R.id.tv_feculty_sub);
        tv_feculty_gender = findViewById(R.id.tv_feculty_gender);
        tv_feculty_contact = findViewById(R.id.tv_feculty_contact);
        tv_password = findViewById(R.id.tv_password);

        Glide.with(FecultyProfileActivity.this).load(f_photo).into(iv_logo);

        //uper_feculty_name.setText(f_name);
        tv_feculty_name.setText(f_name);
        tv_feculty_email.setText(f_email);
        tv_feculty_sub.setText(f_sub);
        tv_feculty_gender.setText(f_gender);
        tv_feculty_contact.setText(f_contact);
        tv_password.setText(f_pass);
    }
}