package com.example.fecultypannel.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fecultypannel.NoticeActivity;
import com.example.fecultypannel.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class AddNoticeFragment extends Fragment {

    ImageView add_notice;
    TextView etNoticeTitle, tvNoticeUploadDate;
    Button btn_notice_submit;

    String notice_pdf_url, encodePdf = "";
    String notice_upload_date, notice_title;

    public AddNoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_notice, container, false);

        add_notice = view.findViewById(R.id.add_notice);
        etNoticeTitle = view.findViewById(R.id.etNoticeTitle);
        tvNoticeUploadDate = view.findViewById(R.id.tvNoticeUploadDate);
        btn_notice_submit = view.findViewById(R.id.btn_notice_submit);

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String currDate = simpleDateFormat.format(d);
        tvNoticeUploadDate.setText(currDate);

        add_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Choosea file"), 1);
            }
        });

        btn_notice_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notice_upload_date = tvNoticeUploadDate.getText().toString();
                notice_title = etNoticeTitle.getText().toString();

                ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Add Notice.", "Please Wait...", true);
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                String url = "http://adkkda34.atwebpages.com/f_add_notice.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("notice", response);
                        Toast.makeText(getActivity(), "Notice Upload Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), NoticeActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("notice", "" + error);
                        Toast.makeText(getActivity(), "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("notice_title", notice_title);
                        hashMap.put("noticePdfURL", encodePdf);
                        hashMap.put("upload_date", notice_upload_date);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodePdf = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);
                Log.e("sdfds", "" + pdfInBytes);

                add_notice.setImageResource(R.drawable.ic_pdf_icon);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}