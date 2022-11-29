package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowStudentSemWiseActivity extends AppCompatActivity {

    RecyclerView result_recycl;
    TextView tv_result_title;
    String finnal_sem_name;

    LinearLayoutManager linearLayoutManager;
    ArrayList<StudentData> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_sem_wise);

        result_recycl = findViewById(R.id.result_recycl);
        tv_result_title = findViewById(R.id.tv_result_title);

        String result_title_name = getIntent().getStringExtra("tvresult");
        tv_result_title.setText("Semester : " + result_title_name);
        //finnal_sem_name = result_title_name.substring(result_title_name.length() - 1);

        if (result_title_name.startsWith("1")) {
            finnal_sem_name = "1";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }
        if (result_title_name.startsWith("2")) {
            finnal_sem_name = "2";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }
        if (result_title_name.startsWith("3")) {
            finnal_sem_name = "3";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }
        if (result_title_name.startsWith("4")) {
            finnal_sem_name = "4";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }
        if (result_title_name.startsWith("5")) {
            finnal_sem_name = "5";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }
        if (result_title_name.startsWith("6")) {
            finnal_sem_name = "6";
            tv_result_title.setText("Semester : " + finnal_sem_name);
        }

        String div = getIntent().getStringExtra("div");

        ProgressDialog progressDialog = new ProgressDialog(ShowStudentSemWiseActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ShowStudentSemWiseActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_Show_SemWiseStud.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("show_student", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String s_id = jsonObject.getString("s_id");
                            String s_name = jsonObject.getString("s_name");
                            String s_email = jsonObject.getString("s_email");
                            String s_gender = jsonObject.getString("s_gender");
                            String s_dob = jsonObject.getString("s_dob");
                            String s_addr = jsonObject.getString("s_addr");
                            String s_contact = jsonObject.getString("s_contact");
                            String p_contact = jsonObject.getString("p_contact");
                            String s_enrlno = jsonObject.getString("s_enrlno");
                            String s_spid = jsonObject.getString("s_spid");
                            String s_grno = jsonObject.getString("s_grno");
                            String s_rollno = jsonObject.getString("s_rollno");
                            String s_sem = jsonObject.getString("s_sem");
                            String s_div = jsonObject.getString("s_div");
                            String s_photo = jsonObject.getString("s_photo");

                            StudentData studentData = new StudentData(s_id, s_name, s_email, s_gender, s_dob, s_addr, s_contact, p_contact, s_enrlno, s_spid, s_grno, s_rollno, s_sem, s_div, s_photo);
                            arrayList.add(studentData);
                        }
                        Adapter_Student_list adapterStudentList = new Adapter_Student_list(ShowStudentSemWiseActivity.this, arrayList, "ShowStudentSemWise");
                        result_recycl.setAdapter(adapterStudentList);

                        linearLayoutManager = new LinearLayoutManager(ShowStudentSemWiseActivity.this, RecyclerView.VERTICAL, false);
                        result_recycl.setLayoutManager(linearLayoutManager);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("show_student2", "" + error);
                Toast.makeText(ShowStudentSemWiseActivity.this, "Something Went wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("semester", finnal_sem_name);
                hashMap.put("div", div);
                Log.e("aaaaaaaaaa", div + "...." + finnal_sem_name);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}