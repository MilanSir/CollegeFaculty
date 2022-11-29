package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateResultSem1Activity extends AppCompatActivity {

    TextView tv_res_stud_name, tv_res_stud_enroll, tv_res_stud_rollno, tv_res_stud_sem, tv_res_stud_div, tv_res_stud_grno, tv_total, tv_cgpi, tv_grade, tv_per;
    EditText et_sub1, et_sub2, et_sub3, et_sub4, et_sub5, et_sub6;
    Button btnCalculate, btnSubmit;

    String sub1, sub2, sub3, sub4, sub5, sub6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_result_sem1);

        tv_res_stud_name = findViewById(R.id.tv_res_stud_name);
        tv_res_stud_enroll = findViewById(R.id.tv_res_stud_enroll);
        tv_res_stud_rollno = findViewById(R.id.tv_res_stud_rollno);
        tv_res_stud_sem = findViewById(R.id.tv_res_stud_sem);
        tv_res_stud_div = findViewById(R.id.tv_res_stud_div);
        tv_res_stud_grno = findViewById(R.id.tv_res_stud_grno);
        tv_total = findViewById(R.id.tv_total);
        tv_cgpi = findViewById(R.id.tv_cgpi);
        tv_grade = findViewById(R.id.tv_grade);
        tv_per = findViewById(R.id.tv_per);

        et_sub1 = findViewById(R.id.et_sub1);
        et_sub2 = findViewById(R.id.et_sub2);
        et_sub3 = findViewById(R.id.et_sub3);
        et_sub4 = findViewById(R.id.et_sub4);
        et_sub5 = findViewById(R.id.et_sub5);
        et_sub6 = findViewById(R.id.et_sub6);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setSelected(true);

        String s_photo = getIntent().getStringExtra("s_photo");
        String s_name = getIntent().getStringExtra("s_name");
        String s_enrlno = getIntent().getStringExtra("s_enrlno");
        String s_grno = getIntent().getStringExtra("s_grno");
        String s_rollno = getIntent().getStringExtra("s_rollno");
        String s_sem = getIntent().getStringExtra("s_sem");
        String s_div = getIntent().getStringExtra("s_div");

        tv_res_stud_name.setText(s_name);
        tv_res_stud_enroll.setText(s_enrlno);
        tv_res_stud_rollno.setText(s_rollno);
        tv_res_stud_sem.setText(s_sem);
        tv_res_stud_div.setText(s_div);
        tv_res_stud_grno.setText(s_grno);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sub1 = et_sub1.getText().toString();
                sub2 = et_sub2.getText().toString();
                sub3 = et_sub3.getText().toString();
                sub4 = et_sub4.getText().toString();
                sub5 = et_sub5.getText().toString();
                sub6 = et_sub6.getText().toString();

                int subject1 = Integer.parseInt(sub1);
                int subject2 = Integer.parseInt(sub2);
                int subject3 = Integer.parseInt(sub3);
                int subject4 = Integer.parseInt(sub4);
                int subject5 = Integer.parseInt(sub5);
                int subject6 = Integer.parseInt(sub6);

                int total = subject1 + subject2 + subject3 + subject4 + subject5 + subject6;
                tv_total.setText("" + total + "/310");

                float cgpi = (float) total * 10 / 310;
                tv_cgpi.setText("" + cgpi);

                if (cgpi > 9 && cgpi <= 10) {
                    tv_grade.setText("Distinction");
                } else if (cgpi > 8 && cgpi <= 9) {
                    tv_grade.setText("Distinction");
                } else if (cgpi > 7 && cgpi <= 8) {
                    tv_grade.setText("Distinction");
                } else if (cgpi > 6 && cgpi <= 7) {
                    tv_grade.setText("First Class");
                } else if (cgpi > 5 && cgpi <= 6) {
                    tv_grade.setText("Second Class");
                } else if (cgpi > 4 && cgpi <= 5) {
                    tv_grade.setText("Pass Class");
                } else if (cgpi < 4) {
                    tv_grade.setText("Fail..");
                }

                float per = (float) (total / 3.1);
                tv_per.setText("" + per);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String total = tv_total.getText().toString();
                String cgpi = tv_cgpi.getText().toString();
                String grade = tv_grade.getText().toString();
                String per = tv_per.getText().toString();
                String enrlno = tv_res_stud_enroll.getText().toString();

                ProgressDialog progressDialog = new ProgressDialog(CreateResultSem1Activity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(CreateResultSem1Activity.this);
                String url = "http://adkkda34.atwebpages.com/f_add_result_sem1.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(CreateResultSem1Activity.this, "Response", Toast.LENGTH_SHORT).show();
                        Log.e("sem1", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("sem1error", "" + error);
                        Toast.makeText(CreateResultSem1Activity.this, "Something Went wrong...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("sub1", sub1);
                        hashMap.put("sub2", sub2);
                        hashMap.put("sub3", sub3);
                        hashMap.put("sub4", sub4);
                        hashMap.put("sub5", sub5);
                        hashMap.put("sub6", sub6);

                        hashMap.put("total", total);
                        hashMap.put("cgpi", cgpi);
                        hashMap.put("grade", grade);
                        hashMap.put("per", per);
                        hashMap.put("enrlno", enrlno);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }
}