package com.example.fecultypannel;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.fecultypannel.MainActivity.editor;

public class LoginFecultyActivity extends AppCompatActivity {

    Button btn_login;
    TextInputEditText iet_login_email, iet_login_password;
    TextInputLayout il_login_password, il_login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_feculty);

        btn_login = findViewById(R.id.btn_login);
        iet_login_email = findViewById(R.id.iet_login_email);
        iet_login_password = findViewById(R.id.iet_login_password);
        il_login_email = findViewById(R.id.il_login_email);
        il_login_password = findViewById(R.id.il_login_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String f_email = iet_login_email.getText().toString();
                String f_pass = iet_login_password.getText().toString();


                if (f_email.isEmpty()) {
                    il_login_email.setError("Fill the field");
                    return;
                }
                il_login_email.setError(null);
                if(!isValidEmailId(f_email)){
                    il_login_email.setError("Invalid email");
                    return;
                }

                if (f_pass.isEmpty()) {
                    il_login_password.setError("Fill the field");
                    return;
                }
                il_login_password.setError(null);
                ProgressDialog progressDialog = new ProgressDialog(LoginFecultyActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(LoginFecultyActivity.this);
                String url = "http://adkkda34.atwebpages.com/f_login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("login1", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int result = jsonObject.getInt("result");

                            if (result == 1) {
                                JSONObject data = jsonObject.getJSONObject("data");

                                String f_id = data.getString("f_id");
                                String f_name = data.getString("f_name");
                                String f_email = data.getString("f_email");
                                String f_sub = data.getString("f_sub");
                                String f_gender = data.getString("f_gender");
                                String f_contact = data.getString("f_contact");
                                String f_pass = data.getString("f_pass");
                                String f_photo = data.getString("f_photo");

                                editor.putString("f_id", f_id);
                                editor.putString("f_name", f_name);
                                editor.putString("f_email", f_email);
                                editor.putString("f_sub", f_sub);
                                editor.putString("f_gender", f_gender);
                                editor.putString("f_contact", f_contact);
                                editor.putString("f_pass", f_pass);
                                editor.putString("f_photo", f_photo);
                                editor.commit();

                                Intent intent = new Intent(LoginFecultyActivity.this, DashbordFacultyActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginFecultyActivity.this, "User Not Found :(", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("login2", "" + error);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap hashMap = new HashMap();

                        hashMap.put("f_email", f_email);
                        hashMap.put("f_pass", f_pass);

                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    public void signin(View view) {
        Intent intent = new Intent(LoginFecultyActivity.this, RegistrationFecultyActivity.class);
        startActivity(intent);
    }

    public void forget_password(View view) {
        Dialog dialog = new Dialog(LoginFecultyActivity.this);
        dialog.setContentView(R.layout.forgetpass_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextInputEditText iet_email = dialog.findViewById(R.id.iet_email);
        TextInputLayout il_username = dialog.findViewById(R.id.il_username1);

        Button next = dialog.findViewById(R.id.btn_next_pass);
        ProgressBar progressBar = dialog.findViewById(R.id.forget_progress);

        dialog.findViewById(R.id.btn_next_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                next.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                RequestQueue requestQueue = Volley.newRequestQueue(LoginFecultyActivity.this);
                String url = "http://adkkda34.atwebpages.com/f_email_check_forget_pass.php";
                String f_email = iet_email.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("OK")) {
                            Intent intent = new Intent(LoginFecultyActivity.this, ChangePasswordActivity.class);
                            intent.putExtra("f_email", f_email);
                            startActivity(intent);
                            finish();
                        } else {
                            il_username.setError(response);
                            next.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginFecultyActivity.this, "Something want to wrong", Toast.LENGTH_SHORT).show();
                        next.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
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
        });
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