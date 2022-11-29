package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class AttendenceFecultyActivity extends AppCompatActivity {

    RecyclerView attendence_sub_recycl;
    GridLayoutManager gridLayoutManager;
    String f_email;
    public static ArrayList divsArrayList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_feculty);

        attendence_sub_recycl = findViewById(R.id.attendence_sub_recycl);

        f_email = sharedPreferences.getString("f_email", null);

        ProgressDialog progressDialog = new ProgressDialog(AttendenceFecultyActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(AttendenceFecultyActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_attendence_sub_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("attendence1", response);
                Toast.makeText(AttendenceFecultyActivity.this, "Success...", Toast.LENGTH_SHORT).show();

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
                        divsArrayList.add(onlydiv[1]);
                    }
                   /* HashMap hashMap = new HashMap();
                    hashMap.put("f_sub", f_sub);
                    arrayList.add(hashMap);*/

                    Adapter_F_Subject_list adapterFSubjectList = new Adapter_F_Subject_list(AttendenceFecultyActivity.this, subs, "showSemsAddAttendence");
                    attendence_sub_recycl.setAdapter(adapterFSubjectList);

                    gridLayoutManager = new GridLayoutManager(AttendenceFecultyActivity.this, 2, RecyclerView.VERTICAL, false);
                    attendence_sub_recycl.setLayoutManager(gridLayoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AttendenceFecultyActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
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
}