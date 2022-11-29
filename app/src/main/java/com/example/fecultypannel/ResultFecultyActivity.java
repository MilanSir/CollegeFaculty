package com.example.fecultypannel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class ResultFecultyActivity extends AppCompatActivity {

    /*CardView result1, result2, result3, result4, result5, result6;
    TextView tv_result1, tv_result2, tv_result3, tv_result4, tv_result5, tv_result6;*/
    String f_email;
    public static ArrayList ResultDivsArrayList = new ArrayList();
    ArrayList compare_sem = new ArrayList();
    GridLayoutManager gridLayoutManager;
    RecyclerView result_show_subject_recycl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_show_subject);

        result_show_subject_recycl=findViewById(R.id.result_show_subject_recycl);
        /*result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        result4 = findViewById(R.id.result4);
        result5 = findViewById(R.id.result5);
        result6 = findViewById(R.id.result6);

        tv_result1 = findViewById(R.id.tv_result1);
        tv_result2 = findViewById(R.id.tv_result2);
        tv_result3 = findViewById(R.id.tv_result3);
        tv_result4 = findViewById(R.id.tv_result4);
        tv_result5 = findViewById(R.id.tv_result5);
        tv_result6 = findViewById(R.id.tv_result6);*/

        /*result1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult1 = tv_result1.getText().toString();
                compareSemToSub("1");
            }
        });
        result2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult2 = tv_result2.getText().toString();
                compareSemToSub("2");
            }
        });
        result3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult3 = tv_result3.getText().toString();
                compareSemToSub("3");
            }
        });
        result4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult4 = tv_result4.getText().toString();
                compareSemToSub("4");
            }
        });
        result5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult5 = tv_result5.getText().toString();
                compareSemToSub("5");
            }
        });
        result6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvresult6 = tv_result6.getText().toString();
                compareSemToSub("6");
            }
        });
        String sample[] = {tv_result1.toString(), tv_result2.toString(), tv_result3.toString(), tv_result4.toString(), tv_result5.toString(), tv_result6.toString()};*/
        f_email = sharedPreferences.getString("f_email", null);

        ProgressDialog progressDialog = new ProgressDialog(ResultFecultyActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ResultFecultyActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_attendence_sub_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("attendence1", response);
                Toast.makeText(ResultFecultyActivity.this, "Success...", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject data = jsonObject.getJSONObject("data");
                    String f_sub = data.getString("f_sub");

                    String[] diff_sub = f_sub.split("/");
                    String[] onlydiv = new String[diff_sub.length];
                    String[] subs = new String[diff_sub.length];

                    for (int i = 0; i < diff_sub.length; i++) {
                        onlydiv = diff_sub[i].split("_");
                        Log.e("log", "" + onlydiv[0]);
                        subs[i] = onlydiv[0];
                        ResultDivsArrayList.add(onlydiv[1]);
                    }
                   /* HashMap hashMap = new HashMap();
                    hashMap.put("f_sub", f_sub);
                    arrayList.add(hashMap);*/

                    Adapter_F_Subject_list adapterFSubjectList = new Adapter_F_Subject_list(ResultFecultyActivity.this, subs, "resultShowSubject");
                    result_show_subject_recycl.setAdapter(adapterFSubjectList);

                    gridLayoutManager = new GridLayoutManager(ResultFecultyActivity.this, 2, RecyclerView.VERTICAL, false);
                    result_show_subject_recycl.setLayoutManager(gridLayoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ResultFecultyActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("f_email", f_email);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void compareSemToSub(String semName) {


        /*RequestQueue requestQueue = Volley.newRequestQueue(ResultFecultyActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_attendence_sub_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("attendence1", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONObject data = jsonObject.getJSONObject("data");
                    String f_sub = data.getString("f_sub");

                    String[] diff_sub = f_sub.split("/");
                    String[] onlydiv = new String[diff_sub.length];

                    String[] subs = new String[diff_sub.length];
                    ResultDivsArrayList.clear();
                    for (int i = 0; i < diff_sub.length; i++) {
                        onlydiv = diff_sub[i].split("_");
                        Log.e("log", "" + onlydiv[0]);
                        subs[i] = onlydiv[0];
                        ResultDivsArrayList.add(onlydiv[1]);

                        if (!compare_sem.contains(onlydiv[0].substring(0, 1))) {
                            compare_sem.add(onlydiv[0].substring(0, 1));
                        }
                    }
                    Log.e("divvv", "" + ResultDivsArrayList);
                    if (compare_sem.contains(semName)) {
                        Intent intent = new Intent(ResultFecultyActivity.this, ResultShowSubjectActivity.class);
                        intent.putExtra("subs", subs);
                        intent.putExtra("result_semester", semName);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ResultFecultyActivity.this, "Please Select Your Semester.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResultFecultyActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("f_email", f_email);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);*/


    }

    public void funDialog(String tvresult) {
        Dialog dialog = new Dialog(ResultFecultyActivity.this);
        dialog.setContentView(R.layout.divison_dialog_layout);
        dialog.show();

       /* dialog.findViewById(R.id.btn_div1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ResultFecultyActivity.this, ShowStudentSemWiseActivity.class);
                intent.putExtra("tvresult", tvresult);
                intent.putExtra("div", "1");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultFecultyActivity.this, ShowStudentSemWiseActivity.class);
                intent.putExtra("tvresult", tvresult);
                intent.putExtra("div", "2");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultFecultyActivity.this, ShowStudentSemWiseActivity.class);
                intent.putExtra("tvresult", tvresult);
                intent.putExtra("div", "3");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultFecultyActivity.this, ShowStudentSemWiseActivity.class);
                intent.putExtra("tvresult", tvresult);
                intent.putExtra("div", "4");
                startActivity(intent);
            }
        });*/
    }
}