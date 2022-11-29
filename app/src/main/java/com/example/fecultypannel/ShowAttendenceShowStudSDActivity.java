package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

public class ShowAttendenceShowStudSDActivity extends AppCompatActivity {

    RecyclerView show_stud_recycl;
    LinearLayoutManager linearLayoutManager;
    String a_div,finnal_sem_name;
    public static String a_sub_name;
    ArrayList<StudentData> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendence_show_stud_s_d);

        show_stud_recycl = findViewById(R.id.show_stud_recycl);
        a_div = getIntent().getStringExtra("div");
        a_sub_name = getIntent().getStringExtra("sub_name");

        if (a_sub_name.startsWith("1")) {
            finnal_sem_name = "1";
        } else if (a_sub_name.startsWith("2")) {
            finnal_sem_name = "2";
        } else if (a_sub_name.startsWith("3")) {
            finnal_sem_name = "3";
        } else if (a_sub_name.startsWith("4")) {
            finnal_sem_name = "4";
        } else if (a_sub_name.startsWith("5")) {
            finnal_sem_name = "5";
        } else if (a_sub_name.startsWith("6")) {
            finnal_sem_name = "6";
        }

        ProgressDialog progressDialog = new ProgressDialog(ShowAttendenceShowStudSDActivity.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ShowAttendenceShowStudSDActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_Show_SemWiseStud.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("showstud_show", response);

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
                        Adapter_Student_list adapterStudentList = new Adapter_Student_list(ShowAttendenceShowStudSDActivity.this, arrayList, "showAttendenceShowStud");
                        show_stud_recycl.setAdapter(adapterStudentList);

                        linearLayoutManager = new LinearLayoutManager(ShowAttendenceShowStudSDActivity.this, RecyclerView.VERTICAL, false);
                        show_stud_recycl.setLayoutManager(linearLayoutManager);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("showstud_show1", "" + error);
                Toast.makeText(ShowAttendenceShowStudSDActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("semester", finnal_sem_name);
                hashMap.put("div", a_div);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}