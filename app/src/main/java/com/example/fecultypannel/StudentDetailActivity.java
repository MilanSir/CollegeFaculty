package com.example.fecultypannel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentDetailActivity extends AppCompatActivity {

    TextView tv_s_name, tv_stud_name, tv_s_email, tv_stud_gender, tv_stud_dob, tv_stud_addr, tv_stud_phno, tv_father_phno, tv_stud_enrollment, tv_stud_spid, tv_stud_grno, tv_stud_rollno,
            tv_stud_course, tv_stud_sem, tv_stud_div, tv_stud_college;
    ImageView iv_stud_photo;
    Button btn_update, btn_delete;
    ArrayList<StudentData> arrayList = new ArrayList<>();
    String s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        iv_stud_photo = findViewById(R.id.iv_stud_photo);
        tv_s_name = findViewById(R.id.tv_s_name);
        tv_stud_name = findViewById(R.id.tv_stud_name);
        tv_s_email = findViewById(R.id.tv_s_email);
        tv_stud_gender = findViewById(R.id.tv_stud_gender);
        tv_stud_dob = findViewById(R.id.tv_stud_dob);
        tv_stud_addr = findViewById(R.id.tv_stud_addr);
        tv_stud_phno = findViewById(R.id.tv_stud_phno);
        tv_father_phno = findViewById(R.id.tv_father_phno);
        tv_stud_enrollment = findViewById(R.id.tv_stud_enrollment);
        tv_stud_spid = findViewById(R.id.tv_stud_spid);
        tv_stud_grno = findViewById(R.id.tv_stud_grno);
        tv_stud_rollno = findViewById(R.id.tv_stud_rollno);
        tv_stud_course = findViewById(R.id.tv_stud_course);
        tv_stud_sem = findViewById(R.id.tv_stud_sem);
        tv_stud_div = findViewById(R.id.tv_stud_div);
        tv_stud_college = findViewById(R.id.tv_stud_college);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        int pos = getIntent().getIntExtra("pos", 0);
        String s_id = getIntent().getStringExtra("s_id");
        String s_photo = getIntent().getStringExtra("s_photo");
        String s_name = getIntent().getStringExtra("s_name");
        String s_email = getIntent().getStringExtra("s_email");
        String s_gender = getIntent().getStringExtra("s_gender");
        String s_dob = getIntent().getStringExtra("s_dob");
        String s_addr = getIntent().getStringExtra("s_addr");
        String s_contact = getIntent().getStringExtra("s_contact");
        String p_contact = getIntent().getStringExtra("p_contact");
        String s_enrlno = getIntent().getStringExtra("s_enrlno");
        String s_spid = getIntent().getStringExtra("s_spid");
        String s_grno = getIntent().getStringExtra("s_grno");
        String s_rollno = getIntent().getStringExtra("s_rollno");
        String s_sem = getIntent().getStringExtra("s_sem");
        String s_div = getIntent().getStringExtra("s_div");

        Glide.with(StudentDetailActivity.this).load(s_photo).into(iv_stud_photo);
        tv_s_name.setText(s_name);
        tv_stud_name.setText(s_name);
        tv_s_email.setText(s_email);
        tv_stud_gender.setText(s_gender);
        tv_stud_dob.setText(s_dob);
        tv_stud_addr.setText(s_addr);
        tv_stud_phno.setText(s_contact);
        tv_father_phno.setText(p_contact);
        tv_stud_enrollment.setText(s_enrlno);
        tv_stud_spid.setText(s_spid);
        tv_stud_grno.setText(s_grno);
        tv_stud_rollno.setText(s_rollno);
        tv_stud_sem.setText(s_sem);
        tv_stud_div.setText(s_div);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentDetailActivity.this, StudUpdateActivity.class);
                intent.putExtra("pos", pos);
                intent.putExtra("s_id", s_id);
                intent.putExtra("s_photo", s_photo);
                intent.putExtra("s_name", s_name);
                intent.putExtra("s_email", s_email);
                intent.putExtra("s_gender", s_gender);
                intent.putExtra("s_dob", s_dob);
                intent.putExtra("s_addr", s_addr);
                intent.putExtra("s_contact", s_contact);
                intent.putExtra("p_contact", p_contact);
                intent.putExtra("s_enrlno", s_enrlno);
                intent.putExtra("s_spid", s_spid);
                intent.putExtra("s_grno", s_grno);
                intent.putExtra("s_rollno", s_rollno);
                intent.putExtra("s_sem", s_sem);
                intent.putExtra("s_div", s_div);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentDetailActivity.this);
                builder.setTitle("Delete !");
                builder.setMessage("Are You Want to Sure Delete..?");
                builder.setIcon(R.drawable.ic_alert);

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                        ProgressDialog progressDialog = ProgressDialog.show(StudentDetailActivity.this, "Deleting..", "Please Wait...", false);

                        RequestQueue requestQueue = Volley.newRequestQueue(StudentDetailActivity.this);
                        String url = "http://adkkda34.atwebpages.com/delete_student.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Toast.makeText(StudentDetailActivity.this, "Record Deleted Successfully..", Toast.LENGTH_SHORT).show();
                                Log.e("stud_delete", response);

                                Intent intent = new Intent(StudentDetailActivity.this, ShowStudentListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(StudentDetailActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                                Log.e("stud_delete", "" + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap hashMap = new HashMap();
                                hashMap.put("s_id", s_id);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.show();
            }
        });
    }
}