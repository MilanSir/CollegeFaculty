package com.example.fecultypannel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class RegistrationFecultyActivity extends AppCompatActivity {

    ImageView faculty_pic;
    TextInputEditText iet_faculty_name, iet_faculty_email, iet_subject, iet_faculty_contact, iet_password, iet_confirm_pass;
    Button btn_registration;
    TextInputLayout il_faculty_name, il_faculty_email, il_subject, il_faculty_contact, il_password, il_confirm_pass;

    RadioGroup f_radioGroup;
    RadioButton iet_faculty_gender;

    String f_photo;

    String f_sub = "", f_div = "";
    CheckBox[] cb = new CheckBox[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_feculty);

        il_faculty_name = findViewById(R.id.il_faculty_name);
        il_faculty_email = findViewById(R.id.il_faculty_email);
        il_subject = findViewById(R.id.il_subject);
        il_faculty_contact = findViewById(R.id.il_faculty_contact);
        il_password = findViewById(R.id.il_password);
        il_confirm_pass = findViewById(R.id.il_confirm_pass);

        iet_faculty_name = findViewById(R.id.iet_faculty_name);
        iet_faculty_email = findViewById(R.id.iet_faculty_email);
        iet_subject = findViewById(R.id.iet_subject);

        //iet_faculty_gender = findViewById(R.id.iet_faculty_gender);
        f_radioGroup = findViewById(R.id.f_radioGroup);

        iet_faculty_contact = findViewById(R.id.iet_faculty_contact);
        iet_password = findViewById(R.id.iet_password);
        iet_confirm_pass = findViewById(R.id.iet_confirm_pass);

        faculty_pic = findViewById(R.id.faculty_pic);
        btn_registration = findViewById(R.id.btn_registration);

        iet_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button add;

                Dialog dialog = new Dialog(RegistrationFecultyActivity.this);
                dialog.setContentView(R.layout.sub_chackbox_dialog_layout);
                dialog.show();

                add = dialog.findViewById(R.id.ok);
                for (int i = 1; i < cb.length; i++) {
                    int cbid = getResources().getIdentifier("cb" + i, "id", getPackageName());
                    cb[i] = dialog.findViewById(cbid);
                }

                for (int j = 1; j < cb.length; j++) {

                    int finalJ = j;
                    cb[j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Dialog dialog1 = new Dialog(RegistrationFecultyActivity.this);
                            dialog1.setContentView(R.layout.sub_wise_div_select);
                            dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                            dialog1.show();

                            EditText et_div = dialog1.findViewById(R.id.et_div);
                            Button div_ok = dialog1.findViewById(R.id.div_ok);

                            div_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog1.dismiss();
                                    f_div = et_div.getText().toString();
                                    Toast.makeText(RegistrationFecultyActivity.this, "" + cb[finalJ].getText().toString() + "_" + f_div, Toast.LENGTH_SHORT).show();
                                    f_sub += cb[finalJ].getText().toString() + "_" + f_div + "/";
                                }
                            });
                        }
                    });
                }

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        iet_subject.setText(f_sub);
                        /*for (int i = 0; i < cb.length; i++) {
                            try {
                                if (cb[i].isChecked()) {
                                    f_sub += cb[i].getText().toString() + ",";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        iet_subject.setText(f_sub);*/
                    }
                });
                Log.e("subjects", "" + f_sub);
            }
        });

        faculty_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(RegistrationFecultyActivity.this);
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

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String f_name = iet_faculty_name.getText().toString();
                String f_email = iet_faculty_email.getText().toString();
                //String f_sub = iet_subject.getText().toString();

                iet_faculty_gender = findViewById(f_radioGroup.getCheckedRadioButtonId());
                String f_gender = iet_faculty_gender.getText().toString();  // TODO: 3/21/2021 gender inpotent che..radiobutton mathi km value ly ne database ma pass karavvi em..e jova 63 no. ni line joy levi

                String f_contact = iet_faculty_contact.getText().toString();
                String f_pass = iet_password.getText().toString();
                String f_conf_pass = iet_password.getText().toString();
                String MobilePattern = "[0-9]{10}";

                ProgressDialog progressDialog = new ProgressDialog(RegistrationFecultyActivity.this);
                progressDialog.setTitle("Please Wait..");
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                RequestQueue queue = Volley.newRequestQueue(RegistrationFecultyActivity.this);
                String url = "http://adkkda34.atwebpages.com/f_registration.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (f_photo.isEmpty()) {
                                    Toast.makeText(RegistrationFecultyActivity.this, "Insert photo", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (f_name.isEmpty()) {
                                    il_faculty_name.setError("Fill the field");
                                    progressDialog.dismiss();
                                    return;
                                }
                                il_faculty_name.setError(null);
                                if (f_email.isEmpty()) {
                                    il_faculty_email.setError("Fill the field");
                                    progressDialog.dismiss();
                                    return;
                                }
                                il_faculty_email.setError(null);
                                if (!isValidEmailId(f_email)) {
                                    il_faculty_email.setError("Invalid email");
                                    progressDialog.dismiss();
                                    return;
                                }
                                if (f_contact.isEmpty()) {
                                    il_faculty_contact.setError("Fill the field");
                                    progressDialog.dismiss();
                                    return;
                                }
                                il_faculty_contact.setError(null);
                                if (!MobilePattern.matches(f_contact)) {
                                    il_faculty_contact.setError("10 Digits only");
                                    progressDialog.dismiss();
                                    return;
                                }
                                if (f_pass.isEmpty()) {
                                    il_password.setError("Fill the field");
                                    progressDialog.dismiss();
                                    return;
                                }
                                il_password.setError(null);
                                if (!f_conf_pass.equals(f_pass)&& f_conf_pass.isEmpty()) {
                                    il_confirm_pass.setError("Fill the field");
                                    progressDialog.dismiss();
                                    return;
                                }

                                il_confirm_pass.setError(null);

                                Toast.makeText(RegistrationFecultyActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegistrationFecultyActivity.this, "" + f_gender, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistrationFecultyActivity.this, LoginFecultyActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("check2", "" + error);
                        Toast.makeText(RegistrationFecultyActivity.this, "Something Went To Wrong...", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();

                        hashMap.put("f_name", f_name);
                        hashMap.put("f_email", f_email);
                        hashMap.put("f_sub", f_sub);
                        hashMap.put("f_gender", f_gender);
                        hashMap.put("f_contact", f_contact);
                        hashMap.put("f_pass", f_pass);
                        hashMap.put("f_photo", f_photo);

                        return hashMap;
                    }
                };

                queue.add(stringRequest);
            }
        });

        f_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (R.id.male == i || R.id.female == i || R.id.other == i) {
                    f_radioGroup.setBackground(getResources().getDrawable(R.drawable.radio_btn_ring_shape));
                } else {
                    Toast.makeText(RegistrationFecultyActivity.this, "unfocus", Toast.LENGTH_SHORT).show();
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
            faculty_pic.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();
            f_photo = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                faculty_pic.setImageBitmap(bitmap);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();
                f_photo = Base64.encodeToString(bytes, Base64.DEFAULT);
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