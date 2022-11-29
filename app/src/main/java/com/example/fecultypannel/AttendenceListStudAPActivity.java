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

public class AttendenceListStudAPActivity extends AppCompatActivity {

    RecyclerView recyclView_studlist;
    LinearLayoutManager linearLayoutManager;
    public static String sub_name, current_date, div, month, selectdates;
    String finnal_sem_name;

    ArrayList<StudentData> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_list_stud_a_p);

        recyclView_studlist = findViewById(R.id.recyclView_studlist);

        sub_name = getIntent().getStringExtra("sub_name");
        current_date = getIntent().getStringExtra("currDate");
        div = getIntent().getStringExtra("div");
        try {
            selectdates = getIntent().getStringExtra("selectdates");
            Toast.makeText(this, "try" + selectdates, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        month = getIntent().getStringExtra("month");
        Log.e("mghghgh", "" + month);

        if (sub_name.startsWith("1")) {
            finnal_sem_name = "1";
        } else if (sub_name.startsWith("2")) {
            finnal_sem_name = "2";
        } else if (sub_name.startsWith("3")) {
            finnal_sem_name = "3";
        } else if (sub_name.startsWith("4")) {
            finnal_sem_name = "4";
        } else if (sub_name.startsWith("5")) {
            finnal_sem_name = "5";
        } else if (sub_name.startsWith("6")) {
            finnal_sem_name = "6";
        }

        ProgressDialog progressDialog = new ProgressDialog(AttendenceListStudAPActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(AttendenceListStudAPActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_Show_SemWiseStud.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("Astudlist", response);
                Toast.makeText(AttendenceListStudAPActivity.this, "Success.", Toast.LENGTH_SHORT).show();

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
                        linearLayoutManager = new LinearLayoutManager(AttendenceListStudAPActivity.this, RecyclerView.VERTICAL, false);
                        recyclView_studlist.setLayoutManager(linearLayoutManager);

                        Adapter_Student_list adapter_student_list = new Adapter_Student_list(AttendenceListStudAPActivity.this, arrayList, "AttendenceListSemWiseStud");
                        recyclView_studlist.setAdapter(adapter_student_list);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("Astudlist2", "" + error);
                Toast.makeText(AttendenceListStudAPActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("semester", finnal_sem_name);
                hashMap.put("div", div);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}