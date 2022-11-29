package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TimeTableDataActivity extends AppCompatActivity {

    ImageView iv_tt_photo;
    FloatingActionButton add_timetable;
    TextView tv_sem_title_name;
    String tt_photo = "";
    String sem, div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_data);

        iv_tt_photo = findViewById(R.id.iv_tt_photo);
        add_timetable = findViewById(R.id.add_timetable);
        tv_sem_title_name = findViewById(R.id.tv_sem_title_name);

        String sem_name1 = getIntent().getStringExtra("sem1");
        div = getIntent().getStringExtra("div");
        tv_sem_title_name.setText("Semester : "+sem_name1);
        sem = sem_name1.substring(sem_name1.length() - 1);

       /* String sem_name2 = getIntent().getStringExtra("sem2");
        tv_sem_title_name.setText(sem_name2);
        String sem_name3 = getIntent().getStringExtra("sem3");
        tv_sem_title_name.setText(sem_name3);
        String sem_name4 = getIntent().getStringExtra("sem4");
        tv_sem_title_name.setText(sem_name4);
        String sem_name5 = getIntent().getStringExtra("sem5");
        tv_sem_title_name.setText(sem_name5);
        String sem_name6 = getIntent().getStringExtra("sem6");
        tv_sem_title_name.setText(sem_name6);*/

        String show_url = "http://adkkda34.atwebpages.com/f_show_time_table.php";
        ProgressDialog progressDialog = new ProgressDialog(TimeTableDataActivity.this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, show_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TimeTableDataActivity.this, "Img Url", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String img_link = jsonObject.getString("tt_img");

                            Glide.with(TimeTableDataActivity.this).load(img_link).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(iv_tt_photo);
                        }
                    } else {
                        tv_sem_title_name.setText("No Time Table Added");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimeTableDataActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("div", div);
                hashMap.put("sem_title_name", sem);
                return hashMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(TimeTableDataActivity.this);
        requestQueue.add(stringRequest);

        add_timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TimeTableDataActivity.this);
                bottomSheetDialog.setContentView(R.layout.choose_photo_dialog);
                bottomSheetDialog.setTitle("Choose Photo");

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            iv_tt_photo.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            tt_photo = Base64.encodeToString(bytes, Base64.DEFAULT);
            addImage();
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                iv_tt_photo.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();
                tt_photo = Base64.encodeToString(bytes, Base64.DEFAULT);

                addImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void addImage() {
        //String sem_title_name = tv_sem_title_name.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(TimeTableDataActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(TimeTableDataActivity.this);
        String url = "http://adkkda34.atwebpages.com/f_insert_time_table.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("timetable", response);
                Toast.makeText(TimeTableDataActivity.this, "Response ni andar aavyo", Toast.LENGTH_SHORT).show();

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String img_link = jsonObject.getString("tt_img");

                            Glide.with(TimeTableDataActivity.this).load(img_link).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(iv_tt_photo);
                        }
                    } else {
                        tv_sem_title_name.setText("No Time Table Added");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("timetable2", "" + error);
                Toast.makeText(TimeTableDataActivity.this, "Something Went To Wrong..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("sem_title_name", sem);
                hashMap.put("tt_photo", tt_photo);
                hashMap.put("div", div);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}