package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ShowStudentListActivity extends AppCompatActivity {

    RecyclerView studentListrecycler;
    ArrayList<StudentData> arrayList = new ArrayList<>();

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_student_list);

//        String s_name = sharedPreferences.getString("s_name",null);
//        String s_email = sharedPreferences.getString("s_email",null);
//        String s_gender = sharedPreferences.getString("s_gender",null);
//        String s_dob = sharedPreferences.getString("s_dob",null);
//        String s_addr = sharedPreferences.getString("s_addr",null);
//        String s_contact = sharedPreferences.getString("s_contact",null);
//        String p_contact = sharedPreferences.getString("p_contact",null);
//        String s_enrlno = sharedPreferences.getString("s_enrlno",null);
//        String s_spid = sharedPreferences.getString("s_spid",null);
//        String s_grno = sharedPreferences.getString("s_grno",null);
//        String s_rollno = sharedPreferences.getString("s_rollno",null);
//        String s_sem = sharedPreferences.getString("s_sem",null);
//        String s_div = sharedPreferences.getString("s_div",null);
//        String s_photo = sharedPreferences.getString("s_photo",null);

        studentListrecycler = findViewById(R.id.studentListrecycler);

        ProgressDialog progressDialog = new ProgressDialog(ShowStudentListActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ShowStudentListActivity.this);
        String url = "http://adkkda34.atwebpages.com/show_student_list.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("studentlist", response);

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
                        Adapter_Student_list adapter_student_list = new Adapter_Student_list(ShowStudentListActivity.this, arrayList, "ShowStudentList");
                        studentListrecycler.setAdapter(adapter_student_list);

                        linearLayoutManager = new LinearLayoutManager(ShowStudentListActivity.this, RecyclerView.VERTICAL, false);
                        studentListrecycler.setLayoutManager(linearLayoutManager);
                    }
                } catch (JSONException e) {
                    Log.e("catch", "" + e);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("studentlist2", "" + error);
                Toast.makeText(ShowStudentListActivity.this, "Something Went To Wrong..", Toast.LENGTH_SHORT).show();
            }
        }) /*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();

                hashMap.put("s_name", "s_name");
                hashMap.put("s_gender", s_gender);
                hashMap.put("s_dob", s_dob);
                hashMap.put("s_addr", s_addr);
                hashMap.put("s_contact", s_contact);
                hashMap.put("p_contact", p_contact);
                hashMap.put("s_email", s_email);

                hashMap.put("s_enrlno", s_enrlno);
                hashMap.put("s_spid", s_spid);
                hashMap.put("s_grno", s_grno);
                hashMap.put("s_rollno", s_rollno);
                hashMap.put("s_sem", s_sem);
                hashMap.put("s_div", s_div);
                hashMap.put("s_photo",s_photo);

                return hashMap;
            }
        }*/;
        requestQueue.add(stringRequest);
    }
}