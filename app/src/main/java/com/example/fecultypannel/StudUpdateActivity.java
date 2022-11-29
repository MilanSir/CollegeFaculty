package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudUpdateActivity extends AppCompatActivity {

    EditText tv_s_name, tv_stud_name, tv_s_email, tv_stud_gender, tv_stud_dob, tv_stud_addr, tv_stud_phno, tv_father_phno, tv_stud_enrollment, tv_stud_spid, tv_stud_grno, tv_stud_rollno,
            tv_stud_course, tv_stud_sem, tv_stud_div, tv_stud_college;
    ImageView iv_stud_photo;
    Button btn_update_ok;
    String s_photo;
    ArrayList<StudentData> arrayList = new ArrayList<>();
    String s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_update);

        iv_stud_photo = findViewById(R.id.iv_stud_photo);
        tv_s_name = findViewById(R.id.tv_s_name);
        tv_stud_name = findViewById(R.id.tv_stud_name);
        tv_s_email = findViewById(R.id.tv_s_email);
        tv_stud_gender = findViewById(R.id.tv_stud_gender);
        tv_stud_dob = findViewById(R.id.tv_stud_dob);
        tv_stud_addr = findViewById(R.id.tv_stud_addr);
        tv_stud_phno = findViewById(R.id.tv_stud_phno);
        tv_father_phno = findViewById(R.id.tv_father_phno);
        tv_stud_enrollment = findViewById(R.id.tv_stud_enrollment);
        tv_stud_spid = findViewById(R.id.tv_stud_spid);
        tv_stud_grno = findViewById(R.id.tv_stud_grno);
        tv_stud_rollno = findViewById(R.id.tv_stud_rollno);
        tv_stud_course = findViewById(R.id.tv_stud_course);
        tv_stud_sem = findViewById(R.id.tv_stud_sem);
        tv_stud_div = findViewById(R.id.tv_stud_div);
        tv_stud_college = findViewById(R.id.tv_stud_college);
        btn_update_ok = findViewById(R.id.btn_update_ok);

        int pos = getIntent().getIntExtra("pos", 0);
        String s_id = getIntent().getStringExtra("s_id");
        String s_s_photo = getIntent().getStringExtra("s_photo");
        String s_name = getIntent().getStringExtra("s_name");
        String s_email = getIntent().getStringExtra("s_email");
        String s_gender = getIntent().getStringExtra("s_gender");
        String s_dob = getIntent().getStringExtra("s_dob");
        String s_addr = getIntent().getStringExtra("s_addr");
        String s_contact = getIntent().getStringExtra("s_contact");
        String p_contact = getIntent().getStringExtra("p_contact");
        String s_enrlno = getIntent().getStringExtra("s_enrlno");
        String s_spid = getIntent().getStringExtra("s_spid");
        String s_grno = getIntent().getStringExtra("s_grno");
        String s_rollno = getIntent().getStringExtra("s_rollno");
        String s_sem = getIntent().getStringExtra("s_sem");
        String s_div = getIntent().getStringExtra("s_div");

        Glide.with(StudUpdateActivity.this).load(s_s_photo).into(iv_stud_photo);
        tv_s_name.setText(s_name);
        tv_stud_name.setText(s_name);
        tv_s_email.setText(s_email);
        tv_stud_gender.setText(s_gender);
        tv_stud_dob.setText(s_dob);
        tv_stud_addr.setText(s_addr);
        tv_stud_phno.setText(s_contact);
        tv_father_phno.setText(p_contact);
        tv_stud_enrollment.setText(s_enrlno);
        tv_stud_spid.setText(s_spid);
        tv_stud_grno.setText(s_grno);
        tv_stud_rollno.setText(s_rollno);
        tv_stud_sem.setText(s_sem);
        tv_stud_div.setText(s_div);

        iv_stud_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(StudUpdateActivity.this);
                bottomSheetDialog.setContentView(R.layout.choose_photo_dialog);
                bottomSheetDialog.setTitle("Choose Photos");

                ImageView iv_camera, iv_gallery;
                Button btn_cancel;
                iv_camera = bottomSheetDialog.findViewById(R.id.iv_camera);
                iv_gallery = bottomSheetDialog.findViewById(R.id.iv_gallery);
                btn_cancel = bottomSheetDialog.findViewById(R.id.btn_cancel);

                iv_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();

                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera_intent, 1);
                    }
                });
                iv_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        Intent gallery_intent = new Intent(Intent.ACTION_GET_CONTENT);
                        gallery_intent.setType("image/*");
                        startActivityForResult(gallery_intent, 2);
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.show();
            }
        });

        btn_update_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Drawable drawable = iv_stud_photo.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                s_photo = Base64.encodeToString(bytes, Base64.DEFAULT);

                String s_name = tv_s_name.getText().toString();
                String s_email = tv_s_email.getText().toString();
                String s_gender = tv_stud_gender.getText().toString();
                String s_dob = tv_stud_dob.getText().toString();
                String s_addr = tv_stud_addr.getText().toString();
                String s_contact = tv_stud_phno.getText().toString();
                String p_contact = tv_father_phno.getText().toString();
                String s_enrlno = tv_stud_enrollment.getText().toString();
                String s_spid = tv_stud_spid.getText().toString();
                String s_grno = tv_stud_grno.getText().toString();
                String s_rollno = tv_stud_rollno.getText().toString();
                String s_sem = tv_stud_sem.getText().toString();
                String s_div = tv_stud_div.getText().toString();

                ProgressDialog progressDialog = ProgressDialog.show(StudUpdateActivity.this, "Update.", "Please Wait...", false);

                RequestQueue requestQueue = Volley.newRequestQueue(StudUpdateActivity.this);
                String url = "http://adkkda34.atwebpages.com/s_update_detail.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("update_request", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("update_request1", "" + error);
                        Toast.makeText(StudUpdateActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();
                        hashMap.put("s_id", s_id);
                        hashMap.put("s_name", s_name);
                        hashMap.put("s_email", s_email);
                        hashMap.put("s_gender", s_gender);
                        hashMap.put("s_dob", s_dob);
                        hashMap.put("s_addr", s_addr);
                        hashMap.put("s_contact", s_contact);
                        hashMap.put("p_contact", p_contact);
                        hashMap.put("s_enrlno", s_enrlno);
                        hashMap.put("s_spid", s_spid);
                        hashMap.put("s_grno", s_grno);
                        hashMap.put("s_rollno", s_rollno);
                        hashMap.put("s_sem", s_sem);
                        hashMap.put("s_div", s_div);
                        hashMap.put("s_photo", s_photo);
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            iv_stud_photo.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            s_photo = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv_stud_photo.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();
                s_photo = Base64.encodeToString(bytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}