package com.example.fecultypannel;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        permission_dialog();
    }

    private void permission_dialog() {
        ActivityCompat.requestPermissions(MainActivity.this, permission, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (sharedPreferences.getString("f_email", null) != null) {
                            Intent intent = new Intent(MainActivity.this, DashbordFacultyActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(MainActivity.this, LoginFecultyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, 1000);

            } else {
                finishAffinity();
            }
        }
    }
}