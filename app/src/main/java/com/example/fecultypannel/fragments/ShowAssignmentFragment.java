package com.example.fecultypannel.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fecultypannel.R;
import com.example.fecultypannel.adapter.f_show_assignment_adapter;
import com.example.fecultypannel.models.f_show_assignment_dataClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class ShowAssignmentFragment extends Fragment {

    RecyclerView f_show_assi_recycl;
    LinearLayoutManager linearLayoutManager;
    String f_email;

    ArrayList<f_show_assignment_dataClass> arrayList = new ArrayList<>();

    public ShowAssignmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_assignment, container, false);

        f_show_assi_recycl = view.findViewById(R.id.f_show_assi_recycl);
        f_email = sharedPreferences.getString("f_email", null);

        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Show Assignment", "Loding...", false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://adkkda34.atwebpages.com/f_show_assignment.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("show_assignment", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String PdfURL = jsonObject.getString("PdfURL");
                            String pdf_title = jsonObject.getString("pdf_title");
                            String s_div = jsonObject.getString("s_div");
                            String upload_date = jsonObject.getString("upload_date");
                            String submission_date = jsonObject.getString("submission_date");
                            String sub_name = jsonObject.getString("sub_name");
                            String f_name = jsonObject.getString("f_name");
                            String f_email = jsonObject.getString("f_email");

                            f_show_assignment_dataClass fShowAssignmentDataClass = new f_show_assignment_dataClass(id, PdfURL, pdf_title, s_div, upload_date, submission_date, sub_name, f_name, f_email);
                            arrayList.add(fShowAssignmentDataClass);
                        }
                        linearLayoutManager =new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                        f_show_assi_recycl.setLayoutManager(linearLayoutManager);

                        f_show_assignment_adapter f_show_assignment_adapter = new f_show_assignment_adapter(getActivity(),arrayList);
                        f_show_assi_recycl.setAdapter(f_show_assignment_adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("show_assignment1", "" + error);
                Toast.makeText(getActivity(), "Something Went Wrong...", Toast.LENGTH_SHORT).show();
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
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}