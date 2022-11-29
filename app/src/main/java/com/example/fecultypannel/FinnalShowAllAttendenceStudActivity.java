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

public class FinnalShowAllAttendenceStudActivity extends AppCompatActivity {

    RecyclerView finnal_recycl;
    LinearLayoutManager linearLayoutManager;
    ArrayList<AttendenceData> arrayList = new ArrayList<>();
    String month, enrolno = ShowAttendenceMonthActivity.s_enrlno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finnal_show_all_attendence_stud);

        finnal_recycl = findViewById(R.id.finnal_recycl);

        month = getIntent().getStringExtra("month");

        ProgressDialog progressDialog = ProgressDialog.show(FinnalShowAllAttendenceStudActivity.this, "", "Please Wait...", false);

        RequestQueue requestQueue = Volley.newRequestQueue(FinnalShowAllAttendenceStudActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_finnal_show_full_attendence.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("finnal", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String rollno = jsonObject.getString("rollno");
                            String name = jsonObject.getString("name");
                            String attendence_date = jsonObject.getString("attendence_date");
                            String attendence_result = jsonObject.getString("attendence_result");
                            String sub = jsonObject.getString("sub");
                            String feculty_name = jsonObject.getString("feculty_name");
                            String s_div = jsonObject.getString("s_div");
                            String s_enrlno = jsonObject.getString("s_enrlno");
                            String month = jsonObject.getString("month");

                            AttendenceData attendenceData = new AttendenceData(id, rollno, name, attendence_date, attendence_result, sub, feculty_name, s_div, s_enrlno);
                            arrayList.add(attendenceData);
                        }
                        linearLayoutManager = new LinearLayoutManager(FinnalShowAllAttendenceStudActivity.this, RecyclerView.VERTICAL, false);
                        finnal_recycl.setLayoutManager(linearLayoutManager);

                        Adapter_f_ShowAllAttendence adapter_f_showAllAttendence = new Adapter_f_ShowAllAttendence(FinnalShowAllAttendenceStudActivity.this, arrayList);
                        finnal_recycl.setAdapter(adapter_f_showAllAttendence);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("finnal1", "" + error);
                Toast.makeText(FinnalShowAllAttendenceStudActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
//                hashMap.put("attendence_date", attendence_date);
                hashMap.put("month", month);
                hashMap.put("s_enrlno", enrolno);
                hashMap.put("sub_name", ShowAttendenceShowStudSDActivity.a_sub_name);

                Log.e("jjj", month + "...." + enrolno + ShowAttendenceShowStudSDActivity.a_sub_name);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}