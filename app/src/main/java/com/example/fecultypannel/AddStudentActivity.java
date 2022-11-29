package com.example.fecultypannel;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddStudentActivity extends AppCompatActivity {

    ImageView stud_pic;
    TextInputEditText iet_stud_name, iet_dob, iet_address, iet_stud_contact, iet_parent_contact, iet_stud_email, iet_enrlno, iet_spid, iet_grno, iet_rollno, iet_sem, iet_div;
    Button btn_confirm;
    String s_photo;
    TextInputLayout il_stud_name,il_stud_email,il_dob,il_address,il_parent_contact,il_stud_contact,il_enrlno,il_spid,il_grno,il_rollno,il_sem,il_div;

    RadioGroup s_radioGroup;
    RadioButton iet_student_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        il_stud_name = findViewById(R.id.il_stud_name);
        il_stud_email = findViewById(R.id.il_stud_email);
        il_dob = findViewById(R.id.il_dob);
        il_address = findViewById(R.id.il_address);
        il_parent_contact = findViewById(R.id.il_parent_contact);
        il_stud_contact = findViewById(R.id.il_stud_contact);
        il_enrlno = findViewById(R.id.il_enrlno);
        il_spid = findViewById(R.id.il_spid);
        il_grno = findViewById(R.id.il_grno);
        il_rollno = findViewById(R.id.il_rollno);
        il_sem = findViewById(R.id.il_sem);
        il_div = findViewById(R.id.il_div);

        stud_pic = findViewById(R.id.stud_pic);
        iet_stud_name = findViewById(R.id.iet_stud_name);
        s_radioGroup = findViewById(R.id.s_radioGroup);
        iet_dob = findViewById(R.id.iet_dob);
        iet_address = findViewById(R.id.iet_address);
        iet_stud_contact = findViewById(R.id.iet_stud_contact);
        iet_parent_contact = findViewById(R.id.iet_parent_contact);
        iet_stud_email = findViewById(R.id.iet_stud_email);

        iet_enrlno = findViewById(R.id.iet_enrlno);
        iet_spid = findViewById(R.id.iet_spid);
        iet_grno = findViewById(R.id.iet_grno);
        iet_rollno = findViewById(R.id.iet_rollno);
        iet_sem = findViewById(R.id.iet_sem);
        iet_div = findViewById(R.id.iet_div);

        btn_confirm = findViewById(R.id.btn_confirm);

        iet_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddStudentActivity.this);
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            Toast.makeText(AddStudentActivity.this, "" + i2 + "/" + (i1 + 1) + "/" + i, Toast.LENGTH_SHORT).show();

                            iet_dob.setText(i2 + "/" + (i1 + 1) + "/" + i);
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });

        stud_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AddStudentActivity.this);
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

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_name = iet_stud_name.getText().toString();
                String s_email = iet_stud_email.getText().toString();
                String s_dob = iet_dob.getText().toString();
                String s_addr = iet_address.getText().toString();
                String s_contact = iet_stud_contact.getText().toString();
                String p_contact = iet_parent_contact.getText().toString();
                String s_enrlno = iet_enrlno.getText().toString();
                String s_spid = iet_spid.getText().toString();
                String s_grno = iet_grno.getText().toString();
                String s_rollno = iet_rollno.getText().toString();
                String s_sem = iet_sem.getText().toString();
                String s_div = iet_div.getText().toString();
                String MobilePattern = "[0-9]{10}";
                String dob = "[0-9/]";

                iet_student_gender = findViewById(s_radioGroup.getCheckedRadioButtonId());
                String s_gender = iet_student_gender.getText().toString();
/*

                String s_dob = iet_dob.getText().toString();
                String s_addr = iet_address.getText().toString();
                String s_contact = iet_stud_contact.getText().toString();
                String p_contact = iet_parent_contact.getText().toString();

                String s_enrlno = iet_enrlno.getText().toString();
                String s_spid = iet_spid.getText().toString();
                String s_grno = iet_grno.getText().toString();
                String s_rollno = iet_rollno.getText().toString();
                String s_sem = iet_sem.getText().toString();
                String s_div = iet_div.getText().toString();
*/

                ProgressDialog progressDialog = new ProgressDialog(AddStudentActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(AddStudentActivity.this);
                String url = "http://adkkda34.atwebpages.com/s_registration.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();


                        if(s_photo.isEmpty()){
                            Toast.makeText(AddStudentActivity.this, "Select photo", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            return;
                        }
                        if(s_name.isEmpty()){
                            il_stud_name.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_stud_name.setError(null);
                        if(s_email.isEmpty()){
                            il_stud_email.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_stud_email.setError(null);
                        if(!isValidEmailId(s_email)){
                            il_stud_email.setError("Invalid email");
                            progressDialog.dismiss();
                            return;
                        }
                        il_stud_email.setError(null);
                        if(s_dob.isEmpty()){
                            il_dob.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_dob.setError(null);

                        if(s_addr.isEmpty()){
                            il_address.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_address.setError(null);
                        if(s_contact.isEmpty()){
                            il_stud_contact.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_stud_contact.setError(null);
                        if(!s_contact.matches(MobilePattern)){
                            il_stud_contact.setError("Invalid Phone number");
                            progressDialog.dismiss();
                            return;
                        }
                        if(p_contact.isEmpty()){
                            il_parent_contact.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_parent_contact.setError(null);
                        if(!p_contact.matches(MobilePattern)){
                            il_parent_contact.setError("10 Digits Only");
                            progressDialog.dismiss();
                            return;
                        }
                        if(s_enrlno.isEmpty()){
                            il_enrlno.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_enrlno.setError(null);
                        if(s_spid.isEmpty()){
                            il_spid.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_spid.setError(null);
                        if(s_grno.isEmpty()){
                            il_grno.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_grno.setError(null);
                        if(s_rollno.isEmpty()){
                            il_rollno.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_rollno.setError(null);
                        if(s_sem.isEmpty()){
                            il_sem.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_sem.setError(null);
                        if(s_div.isEmpty()){
                            il_div.setError("Fill the field");
                            progressDialog.dismiss();
                            return;
                        }
                        il_div.setError(null);


                        Toast.makeText(AddStudentActivity.this, "Add Student Successfully.", Toast.LENGTH_SHORT).show();

                        /*editor.putString("s_name",s_name);
                        editor.putString("s_email",s_email);
                        editor.putString("s_gender",s_gender);
                        editor.putString("s_dob",s_dob);
                        editor.putString("s_addr",s_addr);
                        editor.putString("s_contact",s_contact);
                        editor.putString("p_contact",p_contact);
                        editor.putString("s_enrlno",s_enrlno);
                        editor.putString("s_spid",s_spid);
                        editor.putString("s_grno",s_grno);
                        editor.putString("s_rollno",s_rollno);
                        editor.putString("s_sem",s_sem);
                        editor.putString("s_div",s_div);
                        editor.putString("s_photo",s_photo);
                        editor.commit();*/

                        Intent intent = new Intent(AddStudentActivity.this, DashbordFacultyActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("check4", "" + error);
                        Toast.makeText(AddStudentActivity.this, "Something Went To Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();

                        hashMap.put("s_name", s_name);
                        hashMap.put("s_gender", s_gender);
                        hashMap.put("s_dob", s_dob);
                        hashMap.put("s_addr", s_addr);
                        hashMap.put("s_contact", s_contact);
                        hashMap.put("p_contact", p_contact);
                        hashMap.put("s_email", s_email);

                        hashMap.put("s_enrlno", s_enrlno);
                        hashMap.put("s_spid", s_spid);
                        hashMap.put("s_grno", s_grno);
                        hashMap.put("s_rollno", s_rollno);
                        hashMap.put("s_sem", s_sem);
                        hashMap.put("s_div", s_div);

                        hashMap.put("s_photo", s_photo);
                        hashMap.put("password", s_spid);

                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        s_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.male == i || R.id.female == i || R.id.other == i) {
                    s_radioGroup.setBackground(getResources().getDrawable(R.drawable.radio_btn_ring_shape));
                } else {
                    Toast.makeText(AddStudentActivity.this, "unfocus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");

            stud_pic.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            s_photo = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                stud_pic.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();
                s_photo = Base64.encodeToString(bytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidEmailId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}