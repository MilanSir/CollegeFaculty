package com.example.fecultypannel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.MainActivity.sharedPreferences;

public class Adapter_Student_list extends RecyclerView.Adapter<Adapter_Student_list.ViewHolder> {

    Context context;
    ArrayList<StudentData> arrayList;
    String showStudentList;
    //String AttendenceListSemWiseStud;

    public Adapter_Student_list(Context context, ArrayList<StudentData> arrayList, String showStudentList) {
        this.context = context;
        this.arrayList = arrayList;
        this.showStudentList = showStudentList;
        //this.AttendenceListSemWiseStud = AttendenceListSemWiseStud;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_studentlist_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        if (showStudentList.equals("AttendenceListSemWiseStud")) {
            view = LayoutInflater.from(context).inflate(R.layout.adepter_layout_attendence_sheet, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        /*if (showStudentList.equals("ShowStudentList") || showStudentList.equals("ShowStudentSemWise")) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_studentlist_layout, parent, false);
            viewHolder = new ViewHolder(view);
            return viewHolder;
        }*/

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        StudentData studentData = arrayList.get(position);

        String s_id = studentData.getS_id();
        String s_photo = studentData.getS_photo();
        String s_name = studentData.getS_name();
        String s_email = studentData.getS_email();
        String s_gender = studentData.getS_gender();
        String s_dob = studentData.getS_dob();
        String s_addr = studentData.getS_addr();
        String s_contact = studentData.getS_contact();
        String p_contact = studentData.getP_contact();
        String s_enrlno = studentData.getS_enrlno();
        String s_spid = studentData.getS_spid();
        String s_grno = studentData.getS_grno();
        String s_rollno = studentData.getS_rollno();
        String s_sem = studentData.getS_sem();
        String s_div = studentData.getS_div();

        if (showStudentList.equals("ShowStudentList") || showStudentList.equals("ShowStudentSemWise") || showStudentList.equals("showAttendenceShowStud")) {
            holder.tv_stud_name.setText("Name : " + s_name);
            holder.tv_rollno.setText("Roll No : " + s_rollno);
            holder.tv_sem.setText("Sem : " + s_sem);
            holder.tv_div.setText("Div : " + s_div);

            Glide.with(context).load(s_photo).into(holder.iv_stud_photo);
        }

        if (showStudentList.equals("ShowStudentList")) {
            holder.main_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StudentDetailActivity.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("s_id", s_id);
                    intent.putExtra("s_photo", s_photo);
                    intent.putExtra("s_name", s_name);
                    intent.putExtra("s_email", s_email);
                    intent.putExtra("s_gender", s_gender);
                    intent.putExtra("s_dob", s_dob);
                    intent.putExtra("s_addr", s_addr);
                    intent.putExtra("s_contact", s_contact);
                    intent.putExtra("p_contact", p_contact);
                    intent.putExtra("s_enrlno", s_enrlno);
                    intent.putExtra("s_spid", s_spid);
                    intent.putExtra("s_grno", s_grno);
                    intent.putExtra("s_rollno", s_rollno);
                    intent.putExtra("s_sem", s_sem);
                    intent.putExtra("s_div", s_div);
                    context.startActivity(intent);
                }
            });
        } else if (showStudentList.equals("ShowStudentSemWise")) {
            String sem = s_sem.substring(s_sem.length() - 1);
            holder.main_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (sem.equals("1")) {
                        Intent intent = new Intent(context, CreateResultSem1Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                    if (sem.equals("2")) {
                        Intent intent = new Intent(context, CreateResultSem2Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                    if (sem.equals("3")) {
                        Intent intent = new Intent(context, CreateResultSem3Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", s_sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                    if (sem.equals("4")) {
                        Intent intent = new Intent(context, CreateResultSem4Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", s_sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                    if (sem.equals("5")) {
                        Intent intent = new Intent(context, CreateResultSem5Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", s_sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                    if (sem.equals("6")) {
                        Intent intent = new Intent(context, CreateResultSem6Activity.class);
                        intent.putExtra("s_photo", s_photo);
                        intent.putExtra("s_name", s_name);
                        intent.putExtra("s_enrlno", s_enrlno);
                        intent.putExtra("s_grno", s_grno);
                        intent.putExtra("s_rollno", s_rollno);
                        intent.putExtra("s_sem", s_sem);
                        intent.putExtra("s_div", s_div);
                        context.startActivity(intent);
                    }
                }
            });
        } else if (showStudentList.equals("AttendenceListSemWiseStud")) {

            holder.attendence_name.setSelected(true);   //aa marquee mate che
            holder.attendence_rollno.setText(s_rollno);
            holder.attendence_name.setText(s_name);

            String rollno = holder.attendence_rollno.getText().toString();
            String name = holder.attendence_name.getText().toString();

            holder.absent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.absent.setTextColor(context.getResources().getColor(R.color.black));
                    holder.present.setTextColor(context.getResources().getColor(R.color.black));
                    holder.absent.setBackground(context.getResources().getDrawable(R.drawable.border_absent));
                    holder.present.setBackground(context.getResources().getDrawable(R.drawable.border_present));

                    holder.absent.setBackground(context.getResources().getDrawable(R.drawable.fill_bg_absent_touch));
                    holder.absent.setTextColor(context.getResources().getColor(R.color.white));
                    fun_attendenceVolly("Absent", rollno, name, s_enrlno);
                }
            });
            holder.present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.absent.setTextColor(context.getResources().getColor(R.color.black));
                    holder.present.setTextColor(context.getResources().getColor(R.color.black));
                    holder.absent.setBackground(context.getResources().getDrawable(R.drawable.border_absent));
                    holder.present.setBackground(context.getResources().getDrawable(R.drawable.border_present));

                    holder.present.setBackground(context.getResources().getDrawable(R.drawable.fill_bg_present_touch));
                    holder.present.setTextColor(context.getResources().getColor(R.color.white));
                    fun_attendenceVolly("Present", rollno, name, s_enrlno);
                }
            });
        } else if (showStudentList.equals("showAttendenceShowStud")) {
            /*holder.tv_stud_name.setText(s_name);
            holder.tv_rollno.setText(s_rollno);
            holder.tv_sem.setText(s_sem);
            holder.tv_div.setText(s_div);

            Glide.with(context).load(s_photo).into(holder.iv_stud_photo);*/

            holder.main_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowAttendenceMonthActivity.class);
                    intent.putExtra("s_enrlno", s_enrlno);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_stud_photo;
        TextView tv_stud_name, tv_rollno, tv_sem, tv_div, attendence_rollno, attendence_name;
        LinearLayout main_linear;
        Button absent, present;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_stud_photo = itemView.findViewById(R.id.iv_stud_photo); // TODO: 3/23/2021 and aa bija bey layout ni FVB ID 
            tv_stud_name = itemView.findViewById(R.id.tv_stud_name);
            tv_rollno = itemView.findViewById(R.id.tv_rollno);
            tv_sem = itemView.findViewById(R.id.tv_sem);
            tv_div = itemView.findViewById(R.id.tv_div);

            main_linear = itemView.findViewById(R.id.main_linear);

            attendence_rollno = itemView.findViewById(R.id.attendence_rollno); // TODO: 3/23/2021 aa attendence na layout ni FVB ID che 
            attendence_name = itemView.findViewById(R.id.attendence_name);
            absent = itemView.findViewById(R.id.absent);
            present = itemView.findViewById(R.id.present);
        }
    }

    public void fun_attendenceVolly(String attendence_result, String rollno, String name, String s_enrlno) {

        String sub = AttendenceListStudAPActivity.sub_name;
        String attendence_date = AttendenceListStudAPActivity.current_date;
        String div = AttendenceListStudAPActivity.div;
        String month = AttendenceListStudAPActivity.month;
        String feculty_name = sharedPreferences.getString("f_name", null);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://adkkda34.atwebpages.com/feculty_add_attendence.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("attendenc_result", response);
                Toast.makeText(context, "Attendence Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("attendenc_result1", "" + error);
                Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("rollno", rollno);
                hashMap.put("name", name);
                hashMap.put("attendence_date", attendence_date);
                hashMap.put("attendence_result", attendence_result);
                hashMap.put("sub", sub);
                hashMap.put("feculty_name", feculty_name);
                hashMap.put("div", div);
                hashMap.put("s_enrlno", s_enrlno);
                hashMap.put("month", month);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}