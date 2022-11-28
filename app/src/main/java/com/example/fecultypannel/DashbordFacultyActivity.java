package com.example.fecultypannel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.AddTimeTableActivity.ttDivsArrayList;
import static com.example.fecultypannel.AttendenceFecultyActivity.divsArrayList;
import static com.example.fecultypannel.MainActivity.editor;
import static com.example.fecultypannel.MainActivity.sharedPreferences;
import static com.example.fecultypannel.ResultFecultyActivity.ResultDivsArrayList;

public class DashbordFacultyActivity extends AppCompatActivity {

    CardView add_stud, show_stud, btn_add_time_table, btn_result, btn_attendence, btn_show_attendence, btn_assignment, notice;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView imageView;
    Toolbar toolbar;
    TextView tv_header_email, tv_feculty_name;
    TextView t1;
    LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord_faculty);

        String f_email = sharedPreferences.getString("f_email", null);
        String f_name = sharedPreferences.getString("f_name", null);
        String f_photo = sharedPreferences.getString("f_photo", null);

        add_stud = findViewById(R.id.add_stud);
        show_stud = findViewById(R.id.show_stud);
        btn_add_time_table = findViewById(R.id.btn_add_time_table);
        btn_result = findViewById(R.id.btn_result);
        btn_attendence = findViewById(R.id.btn_attendence);
        btn_show_attendence = findViewById(R.id.btn_show_attendence);
        btn_assignment = findViewById(R.id.btn_assignment);
        notice = findViewById(R.id.notice);

        t1 = findViewById(R.id.t1);
        t1.setSelected(true);

        navigationView = findViewById(R.id.nev);

        View view = navigationView.getHeaderView(0);
        tv_header_email = view.findViewById(R.id.fac_email);
        tv_feculty_name = view.findViewById(R.id.fac_name);
        main_layout = findViewById(R.id.main_linear);
        imageView = view.findViewById(R.id.fac_image);

        tv_header_email.setText(f_email);
        tv_feculty_name.setText(f_name);
        Glide.with(DashbordFacultyActivity.this).load(f_photo).into(imageView);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(DashbordFacultyActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.item1) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, FecultyProfileActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item3) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, AddStudentActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item4) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, ShowStudentListActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item5) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, NoticeActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item6) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, AddTimeTableActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item7) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, ResultFecultyActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item8) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, AttendenceFecultyActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item9) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, ShowFecultyAttendenceOfStud.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item10) {
                    Intent intent = new Intent(DashbordFacultyActivity.this, AddAssignmentActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (item.getItemId() == R.id.item11) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Dialog dialog = new Dialog(DashbordFacultyActivity.this);
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

                            RequestQueue requestQueue = Volley.newRequestQueue(DashbordFacultyActivity.this);
                            String url = "http://adkkda34.atwebpages.com/f_email_check_forget_pass.php";
                            String f_email = iet_email.getText().toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.equals("OK")) {
                                        Intent intent = new Intent(DashbordFacultyActivity.this, ChangePasswordActivity.class);
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
                                    Toast.makeText(DashbordFacultyActivity.this, "Something want to wrong", Toast.LENGTH_SHORT).show();
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
                if (item.getItemId() == R.id.item2) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DashbordFacultyActivity.this);
                    builder.setIcon(R.drawable.ic_alert);
                    builder.setTitle("Logout");
                    builder.setMessage("Are You Want To Sure Logout ?");

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            editor.clear();
                            editor.commit();
                            divsArrayList.clear();
                            ttDivsArrayList.clear();
                            ResultDivsArrayList.clear();
                            Intent intent = new Intent(DashbordFacultyActivity.this, LoginFecultyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
                return false;
            }
        });

        add_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });
        show_stud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, ShowStudentListActivity.class);
                startActivity(intent);
            }
        });
        btn_add_time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, AddTimeTableActivity.class);
                startActivity(intent);
            }
        });
        btn_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, ResultFecultyActivity.class);
                startActivity(intent);
            }
        });
        btn_attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, AttendenceFecultyActivity.class);
                startActivity(intent);
            }
        });
        btn_show_attendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, ShowFecultyAttendenceOfStud.class);
                startActivity(intent);
            }
        });
        btn_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, AddAssignmentActivity.class);
                startActivity(intent);
            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashbordFacultyActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });
    }

}