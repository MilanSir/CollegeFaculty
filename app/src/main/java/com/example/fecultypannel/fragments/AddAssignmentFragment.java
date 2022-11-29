package com.example.fecultypannel.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fecultypannel.Adapter_F_Subject_list;
import com.example.fecultypannel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.AttendenceFecultyActivity.divsArrayList;
import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class AddAssignmentFragment extends Fragment {

    RecyclerView recycl_assignment_sub;
    GridLayoutManager gridLayoutManager;
    String a_f_email;

    public AddAssignmentFragment() {
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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_assignment, container, false);

        recycl_assignment_sub = view.findViewById(R.id.recycl_assignment_sub);

        a_f_email = sharedPreferences.getString("f_email", null);
        Log.e("eeee", a_f_email);

        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Loadding...", false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        String url = "http://adkkda34.atwebpages.com/f_attendence_sub_list.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("assignment", response);

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

                    gridLayoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
                    recycl_assignment_sub.setLayoutManager(gridLayoutManager);

                    Adapter_F_Subject_list adapterFSubjectList = new Adapter_F_Subject_list(getActivity(), subs, "ShowSubjectAddAssig");
                    recycl_assignment_sub.setAdapter(adapterFSubjectList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("assignment1", "" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("f_email", a_f_email);
                Log.e("email", "Email : " + a_f_email);
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