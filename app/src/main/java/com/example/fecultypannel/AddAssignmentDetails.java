package com.example.fecultypannel;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class AddAssignmentDetails extends AppCompatActivity {

    EditText etAssignmentTitle;
    TextView tvUploadDate, tvSubmissionDate, tvDiv;
    ImageView add_assignment;
    Button btn_assi_submit;

    String encodePdf, assi_title, div, upload_date, submission_date;
    String sub_name, f_name, f_email, assi_div;
    String path;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment_details);

        sub_name = getIntent().getStringExtra("sub_name");
        assi_div = getIntent().getStringExtra("assi_div");
        f_name = sharedPreferences.getString("f_name", null);
        f_email = sharedPreferences.getString("f_email", null);
        Log.e("sub_name", sub_name);

        etAssignmentTitle = findViewById(R.id.etAssignmentTitle);
        tvDiv = findViewById(R.id.tvDiv);
        tvUploadDate = findViewById(R.id.tvUploadDate);
        tvSubmissionDate = findViewById(R.id.tvSubmissionDate);
        add_assignment = findViewById(R.id.add_assignment);
        btn_assi_submit = findViewById(R.id.btn_assi_submit);

        tvDiv.setText(assi_div);

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String currentDate = simpleDateFormat.format(d);
        tvUploadDate.setText(currentDate);

        tvSubmissionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAssignmentDetails.this);
                datePickerDialog.show();
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String submissionDate = i2 + "/" + (i1 + 1) + "/" + i;
                        tvSubmissionDate.setText(submissionDate);
                    }
                });
            }
        });

        add_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(Intent.createChooser(intent, "Choosea file"), 1);
            }
        });

        btn_assi_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = ProgressDialog.show(AddAssignmentDetails.this, "Uploading", "Please Wait...", false);

                assi_title = etAssignmentTitle.getText().toString();
                div = tvDiv.getText().toString();
                upload_date = tvUploadDate.getText().toString();
                submission_date = tvSubmissionDate.getText().toString();

                String url = "http://adkkda34.atwebpages.com/f_add_assignment.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("response", response);
                        Toast.makeText(AddAssignmentDetails.this, "PDF Successfully Uploaded", Toast.LENGTH_SHORT).show();

                        add_assignment.setImageResource(R.drawable.ic_add_pdf);
                        etAssignmentTitle.setText("");
                        tvUploadDate.setText("");
                        tvSubmissionDate.setText("");

                        Intent intent = new Intent(AddAssignmentDetails.this, AddAssignmentActivity.class);
                        startActivity(intent);
                        finish();

                        /*try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    path = jsonObject.getString("url");
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AddAssignmentDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.e("error", "" + error.getMessage());
                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("PdfURL", encodePdf);
                        hashMap.put("assi_title", assi_title);
                        hashMap.put("s_div", div);
                        hashMap.put("upload_date", upload_date);
                        hashMap.put("submission_date", submission_date);
                        hashMap.put("sub_name", sub_name);
                        hashMap.put("f_name", f_name);
                        hashMap.put("f_email", f_email);
                        return hashMap;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(AddAssignmentDetails.this);
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = AddAssignmentDetails.this.getContentResolver().openInputStream(uri);
                byte[] pdfInBytes = new byte[inputStream.available()];
                inputStream.read(pdfInBytes);
                encodePdf = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);
                Log.e("sdfds", "" + pdfInBytes);

                add_assignment.setImageResource(R.drawable.ic_pdf_icon);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}