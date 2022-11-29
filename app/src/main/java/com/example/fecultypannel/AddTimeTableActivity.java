package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.fecultypannel.adapter.TimeTableShowSemAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class AddTimeTableActivity extends AppCompatActivity {
    CardView sem1, sem2, sem3, sem4, sem5, sem6;
    TextView tv_sem1, tv_sem2, tv_sem3, tv_sem4, tv_sem5, tv_sem6;
    RecyclerView timetable_sub_recycl;
    GridLayoutManager gridLayoutManager;
    String f_email;
    public static ArrayList ttDivsArrayList = new ArrayList();
    ArrayList compare_sem = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);

       /* sem1 = findViewById(R.id.sem1);
        sem2 = findViewById(R.id.sem2);
        sem3 = findViewById(R.id.sem3);
        sem4 = findViewById(R.id.sem4);
        sem5 = findViewById(R.id.sem5);
        sem6 = findViewById(R.id.sem6);

        tv_sem1 = findViewById(R.id.tv_sem1);
        tv_sem2 = findViewById(R.id.tv_sem2);
        tv_sem3 = findViewById(R.id.tv_sem3);
        tv_sem4 = findViewById(R.id.tv_sem4);
        tv_sem5 = findViewById(R.id.tv_sem5);
        tv_sem6 = findViewById(R.id.tv_sem6);*/

        timetable_sub_recycl = findViewById(R.id.timetable_sub_recycl);

        f_email = sharedPreferences.getString("f_email", null);

        ProgressDialog progressDialog = new ProgressDialog(AddTimeTableActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(AddTimeTableActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_attendence_sub_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("attendence1", response);
                Toast.makeText(AddTimeTableActivity.this, "Success...", Toast.LENGTH_SHORT).show();

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
                        ttDivsArrayList.add(onlydiv[1]);

                        if (!compare_sem.contains(onlydiv[0].substring(0, 1))) {
                            compare_sem.add(onlydiv[0].substring(0, 1));
                        }
                    }
                    TimeTableShowSemAdapter timeTableShowSemAdapter = new TimeTableShowSemAdapter(AddTimeTableActivity.this, compare_sem);
                    timetable_sub_recycl.setAdapter(timeTableShowSemAdapter);

                    gridLayoutManager = new GridLayoutManager(AddTimeTableActivity.this, 2, RecyclerView.VERTICAL, false);
                    timetable_sub_recycl.setLayoutManager(gridLayoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AddTimeTableActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
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

        /*sem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem1 = tv_sem1.getText().toString();
                fun_Dialog(tvsem1);
            }
        });
        sem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem2 = tv_sem2.getText().toString();
                fun_Dialog(tvsem2);
            }
        });
        sem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem3 = tv_sem3.getText().toString();
                fun_Dialog(tvsem3);
            }
        });
        sem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem4 = tv_sem4.getText().toString();
                fun_Dialog(tvsem4);
            }
        });
        sem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem5 = tv_sem5.getText().toString();
                fun_Dialog(tvsem5);
            }
        });
        sem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tvsem6 = tv_sem6.getText().toString();
                fun_Dialog(tvsem6);
            }
        });*/
    }

    public void fun_Dialog(String tvsem) {

        /*dialog.findViewById(R.id.btn_div1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddTimeTableActivity.this, TimeTableDataActivity.class);
                intent.putExtra("sem1", tvsem);
                intent.putExtra("div", "1");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimeTableActivity.this, TimeTableDataActivity.class);
                intent.putExtra("sem1", tvsem);
                intent.putExtra("div", "2");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimeTableActivity.this, TimeTableDataActivity.class);
                intent.putExtra("sem1", tvsem);
                intent.putExtra("div", "3");
                startActivity(intent);
            }
        });
        dialog.findViewById(R.id.btn_div4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTimeTableActivity.this, TimeTableDataActivity.class);
                intent.putExtra("sem1", tvsem);
                intent.putExtra("div", "4");
                startActivity(intent);
            }
        });*/
    }

}