package com.example.fecultypannel.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.fecultypannel.adapter.f_show_notice_adapter;
import com.example.fecultypannel.models.f_show_notice_dataClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowNoticeFragment extends Fragment {
    RecyclerView show_notice_recycl;
    LinearLayoutManager linearLayoutManager;
    ArrayList<f_show_notice_dataClass> arrayList = new ArrayList<>();

    public ShowNoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_show_notice, container, false);

        show_notice_recycl = view.findViewById(R.id.show_notice_recycl);

        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Notice Loding...", "Please Wait", false);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://adkkda34.atwebpages.com/show_notice.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("show_notice", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String id = jsonObject.getString("id");
                            String notice_title = jsonObject.getString("notice_title");
                            String notice_pdf_URL = jsonObject.getString("notice_pdf_URL");
                            String date = jsonObject.getString("date");

                            f_show_notice_dataClass fShowNoticeDataClass = new f_show_notice_dataClass(id,notice_title,notice_pdf_URL,date);;
                            arrayList.add(fShowNoticeDataClass);
                        }
                        linearLayoutManager =new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                        show_notice_recycl.setLayoutManager(linearLayoutManager);

                        f_show_notice_adapter f_show_notice_adapter = new f_show_notice_adapter(getActivity(),arrayList);
                        show_notice_recycl.setAdapter(f_show_notice_adapter);
                    } else {
                        Toast.makeText(getActivity(), "No Data Found :(", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("show_notice1", "" + error);
                Toast.makeText(getActivity(), "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
        return view;
    }
}