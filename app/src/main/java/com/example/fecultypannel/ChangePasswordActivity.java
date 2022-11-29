package com.example.fecultypannel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {

    TextInputEditText iet_new_password, iet_con_password;
    Button btn_confirm;
    ProgressBar update_progress;
    String f_email;
    LottieAnimationView lottieAnimationView;
    RelativeLayout r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        f_email = getIntent().getStringExtra("f_email");

        iet_new_password = findViewById(R.id.iet_new_password);
        iet_con_password = findViewById(R.id.iet_con_password);
        btn_confirm = findViewById(R.id.btn_confirm);
        update_progress = findViewById(R.id.update_progress);
        lottieAnimationView = findViewById(R.id.lottie_forget);
        r1=findViewById(R.id.r1);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_confirm.setVisibility(View.GONE);
                update_progress.setVisibility(View.VISIBLE);

                String new_password = iet_new_password.getText().toString();

                RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivity.this);
                String url = "http://adkkda34.atwebpages.com/f_new_pass_update.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        r1.setVisibility(View.GONE);

                        Toast.makeText(ChangePasswordActivity.this, "Your Password Forgeted Successfully", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(ChangePasswordActivity.this, LoginFecultyActivity.class));
                                finish();        
                            }
                        },3000);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePasswordActivity.this, "Something want to wrong", Toast.LENGTH_SHORT).show();
                        btn_confirm.setVisibility(View.VISIBLE);
                        update_progress.setVisibility(View.GONE);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("new_password", new_password);
                        hashMap.put("f_email", f_email);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginFecultyActivity.class);
        startActivity(intent);
        finish();
    }
}