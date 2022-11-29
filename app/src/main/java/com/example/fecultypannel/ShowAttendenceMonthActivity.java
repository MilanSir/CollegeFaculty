package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class ShowAttendenceMonthActivity extends AppCompatActivity {

    RecyclerView month_recycl;
    GridLayoutManager gridLayoutManager;
    public static String s_enrlno, attendence_date;
    //String month;

    public static TextView presen_lec, total_lec;

    ArrayList dates = new ArrayList();
    ArrayList months = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendence_month);

        month_recycl = findViewById(R.id.month_recycl);
        s_enrlno = getIntent().getStringExtra("s_enrlno");

        presen_lec = findViewById(R.id.presen_lec);
        total_lec = findViewById(R.id.total_lec);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(ShowAttendenceMonthActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_show_attendence_monthWise.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("monthShow", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            attendence_date = jsonObject.getString("attendence_date");

                            Log.e("attendence_date", "" + attendence_date);
                            String split_date[] = attendence_date.split("/");

                            Log.e("attendence_date1", "" + split_date[0]);
                            Log.e("attendence_date2", "" + split_date[1]);
                            Log.e("attendence_date3", "" + split_date[2]);

                            dates.add(attendence_date);
                            if (!months.contains(split_date[1])) {
                                months.add(split_date[1]);
                            }
                            Log.e("ajay", "Date ni ArrayList " + dates);
                            Log.e("ajay", "Months ni ArrayList " + months);
                        }

                        gridLayoutManager = new GridLayoutManager(ShowAttendenceMonthActivity.this, 3, RecyclerView.VERTICAL, false);
                        month_recycl.setLayoutManager(gridLayoutManager);

                        Adapter_showAttendenceMonths adapter_showAttendenceMonths = new Adapter_showAttendenceMonths(ShowAttendenceMonthActivity.this, months, dates);
                        month_recycl.setAdapter(adapter_showAttendenceMonths);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("monthShow1", "" + error);
                Toast.makeText(ShowAttendenceMonthActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("s_enrlno", s_enrlno);
                hashMap.put("sub_name", ShowAttendenceShowStudSDActivity.a_sub_name);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}