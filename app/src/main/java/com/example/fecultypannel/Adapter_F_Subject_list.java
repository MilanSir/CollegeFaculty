package com.example.fecultypannel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.fecultypannel.AddTimeTableActivity.ttDivsArrayList;
import static com.example.fecultypannel.AttendenceFecultyActivity.divsArrayList;
import static com.example.fecultypannel.MainActivity.sharedPreferences;
import static com.example.fecultypannel.ResultFecultyActivity.ResultDivsArrayList;

public class Adapter_F_Subject_list extends RecyclerView.Adapter<Adapter_F_Subject_list.Viewholder> {

    Context context;
    //ArrayList<HashMap> arrayList;
    String[] diff_sub;
    String showSubject;

    String hhhh_sub_name = "";
    ArrayList arrayList = new ArrayList();

    LinearLayoutManager linearLayoutManager;
    RecyclerView div_recycl;

    public Adapter_F_Subject_list(Context context, String[] diff_sub, String showSubject) {
        this.context = context;
        this.diff_sub = diff_sub;
        this.showSubject = showSubject;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_f_sub_list_layout, parent, false);
        Viewholder Viewholder = new Viewholder(view);
        return Viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //HashMap hashMap = arrayList.get(position);
        //String sub_name = hashMap.get("f_sub").toString();

        String f_email = sharedPreferences.getString("f_email", null);

        if (showSubject.equals("resultShowSubject")){
            holder.tv_sub_name.setText(diff_sub[position]);
            holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hhhh_sub_name = diff_sub[position];
                    //divsArrayList.get(position);
                    arrayList.clear();
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.divison_dialog_layout);
                    dialog.setCanceledOnTouchOutside(false);
                    div_recycl = dialog.findViewById(R.id.div_recycl);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "http://adkkda34.atwebpages.com/f_select_div.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("div_dialog", response);
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                            Log.e("log", "" + ResultDivsArrayList);

                            String finnal_div[] = ResultDivsArrayList.get(position).toString().split(",");

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject data = jsonObject.getJSONObject("data");
                                String f_sub = data.getString("f_sub");

//                                String[] sub_with_div = f_sub.split("/");
//                                String[] onlydiv = new String[0];

                               /* for (int i = 0; i < sub_with_div.length; i++) {
//                                    Log.e("log", sub_with_div[i].split("_"));
                                    onlydiv= sub_with_div[i].split("_");
                                    Log.e("log", "" + onlydiv[1]);
                                }
                                if (sub_name.equals(onlydiv[0])) {

                                    HashMap hashMap = new HashMap();
                                    hashMap.put("1", onlydiv[1].split(","));

                                    arrayList.add(hashMap);
                                }*/
                                linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                                div_recycl.setLayoutManager(linearLayoutManager);

                                Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "showDivResult");
                                div_recycl.setAdapter(adapter_f_div_list);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("div_dialog1", "" + error);
                            Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
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

                    /*dialog.findViewById(R.id.btn_div1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("1");

                        }
                    });

                    dialog.findViewById(R.id.btn_div2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("2");
                        }
                    });

                    dialog.findViewById(R.id.btn_div3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("3");
                        }
                    });
                    dialog.findViewById(R.id.btn_div4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("4");
                        }
                    });*/
                    dialog.show();
                }
            });
        }

        if (showSubject.equals("showSemsAddAttendence")) {
            holder.tv_sub_name.setText(diff_sub[position]);

            holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hhhh_sub_name = diff_sub[position];
                    //divsArrayList.get(position);
                    arrayList.clear();
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.divison_dialog_layout);
                    dialog.setCanceledOnTouchOutside(false);
                    div_recycl = dialog.findViewById(R.id.div_recycl);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    String url = "http://adkkda34.atwebpages.com/f_select_div.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("div_dialog", response);
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                            Log.e("log", "" + divsArrayList);

                            String finnal_div[] = divsArrayList.get(position).toString().split(",");

                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject data = jsonObject.getJSONObject("data");
                                String f_sub = data.getString("f_sub");

//                                String[] sub_with_div = f_sub.split("/");
//                                String[] onlydiv = new String[0];

                               /* for (int i = 0; i < sub_with_div.length; i++) {
//                                    Log.e("log", sub_with_div[i].split("_"));
                                    onlydiv= sub_with_div[i].split("_");
                                    Log.e("log", "" + onlydiv[1]);
                                }
                                if (sub_name.equals(onlydiv[0])) {

                                    HashMap hashMap = new HashMap();
                                    hashMap.put("1", onlydiv[1].split(","));

                                    arrayList.add(hashMap);
                                }*/
                                linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                                div_recycl.setLayoutManager(linearLayoutManager);

                                Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "showDivAddAttendance");
                                div_recycl.setAdapter(adapter_f_div_list);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("div_dialog1", "" + error);
                            Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
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

                    /*dialog.findViewById(R.id.btn_div1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("1");

                        }
                    });

                    dialog.findViewById(R.id.btn_div2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("2");
                        }
                    });

                    dialog.findViewById(R.id.btn_div3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("3");
                        }
                    });
                    dialog.findViewById(R.id.btn_div4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            chooseDate("4");
                        }
                    });*/
                    dialog.show();
                }
            });
        } else if (showSubject.equals("showSemsShowAttendence")) {

            holder.tv_sub_name.setText(diff_sub[position]);
            holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hhhh_sub_name = diff_sub[position];

                    arrayList.clear();
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.divison_dialog_layout);
                    dialog.setCanceledOnTouchOutside(false);
                    div_recycl = dialog.findViewById(R.id.div_recycl);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


                    String finnal_div[] = divsArrayList.get(position).toString().split(",");
                    linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    div_recycl.setLayoutManager(linearLayoutManager);

                    Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "showDivShowAttendence");
                    div_recycl.setAdapter(adapter_f_div_list);

                    /*dialog.findViewById(R.id.btn_div1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            showStudDivSemWise("1");
                        }
                    });
                    dialog.findViewById(R.id.btn_div2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            showStudDivSemWise("2");
                        }
                    });
                    dialog.findViewById(R.id.btn_div3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            showStudDivSemWise("3");
                        }
                    });
                    dialog.findViewById(R.id.btn_div4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            showStudDivSemWise("4");
                        }
                    });*/
                    dialog.show();
                }
            });
        } else if (showSubject.equals("ShowSubjectAddAssig")) {
            holder.tv_sub_name.setText(diff_sub[position]);
            holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    hhhh_sub_name = diff_sub[position];

                    arrayList.clear();
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.divison_dialog_layout);
                    dialog.setCanceledOnTouchOutside(false);
                    div_recycl = dialog.findViewById(R.id.div_recycl);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


                    String finnal_div[] = divsArrayList.get(position).toString().split(",");
                    linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    div_recycl.setLayoutManager(linearLayoutManager);

                    Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "showDivAddAssignment");
                    div_recycl.setAdapter(adapter_f_div_list);

                    //showSubjectAddAssig();
                    /*hhhh_sub_name = diff_sub[position];
                    Intent intent = new Intent(context, AddAssignmentDetails.class);
                    intent.putExtra("sub_name", hhhh_sub_name);
                    context.startActivity(intent);*/
                    dialog.show();
                }
            });
        } else if (showSubject.equals("showSemTimeTable")) {
            holder.tv_sub_name.setText(diff_sub[position].substring(0, 1));
            if (!holder.tv_sub_name.getText().toString().contains(diff_sub[position].substring(0, 1))) {
                holder.tv_sub_name.setText(diff_sub[position].substring(0, 1));

                holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //showSubjectAddAssig();
                        hhhh_sub_name = diff_sub[position];

                        arrayList.clear();
                        Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.divison_dialog_layout);
                        dialog.setCanceledOnTouchOutside(false);
                        div_recycl = dialog.findViewById(R.id.div_recycl);
                        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        String finnal_div[] = ttDivsArrayList.get(position).toString().split(",");
                        linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                        div_recycl.setLayoutManager(linearLayoutManager);

                        Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "showDivShowTimetable");
                        div_recycl.setAdapter(adapter_f_div_list);

                        dialog.show();
                    }
                });
            }
        }
        else if (showSubject.equals("addResultShowSem")){
            holder.tv_sub_name.setText(diff_sub[position]);
            holder.sub_linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    hhhh_sub_name = diff_sub[position];

                    arrayList.clear();
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.divison_dialog_layout);
                    dialog.setCanceledOnTouchOutside(false);
                    div_recycl = dialog.findViewById(R.id.div_recycl);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


                    String finnal_div[] = ResultDivsArrayList.get(position).toString().split(",");
                    linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    div_recycl.setLayoutManager(linearLayoutManager);

                    Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(context, finnal_div, hhhh_sub_name, "addResultShowDiv");
                    div_recycl.setAdapter(adapter_f_div_list);

                    //showSubjectAddAssig();
                    /*hhhh_sub_name = diff_sub[position];
                    Intent intent = new Intent(context, AddAssignmentDetails.class);
                    intent.putExtra("sub_name", hhhh_sub_name);
                    context.startActivity(intent);*/
                    dialog.show();
                }
            });
        }
    }

    /*public void chooseDate(String div) {
        @SuppressLint({"NewApi", "LocalSuppress"}) DatePickerDialog datePickerDialog = new DatePickerDialog(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String selectedDate = i2 + "/" + (i1 + 1) + "/" + i;

                            */
    /*Date d = new Date();

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
                            String currentDate = simpleDateFormat.format(d);

                            Log.e("date", selectedDate);
                            Log.e("date curr", "" + d); //aa aakhi date aapshe tarikh,var and time sathe.
                            Log.e("date cur", "" + currentDate); // and aa ema thi aapde joti hoy etli format ma convert kari ex.18/3/2021

                            if (selectedDate.equals(currentDate)) {
                                Intent intent = new Intent(attendenceFecultyActivity, AttendenceListStudAPActivity.class);
                                intent.putExtra("currDate", currentDate);
                                intent.putExtra("sub_name", sub_name);
                                attendenceFecultyActivity.startActivity(intent);
                            } else {
                                Toast.makeText(attendenceFecultyActivity, "Please Choose Current Date.", Toast.LENGTH_SHORT).show();
                            }*/
    /*

                    String selectdates[] = selectedDate.split("/");

                    int selectDay = Integer.parseInt(selectdates[0]);
                    int selectMonth = Integer.parseInt(selectdates[1]);
                    int selectYear = Integer.parseInt(selectdates[2]);

                    Date d = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
                    String currentDate = simpleDateFormat.format(d);

                    String currentdates[] = currentDate.split("/");
                    int currentDay = Integer.parseInt(currentdates[0]);
                    int currentMonth = Integer.parseInt(currentdates[1]);
                    int currentYear = Integer.parseInt(currentdates[2]);

                    Log.e("ajay", "currentDate " + currentDate + " SelectedDate " + selectedDate);

                    // TODO: 02-Apr-21  jo fit 1 year pachal ni date select karse to previous date select aevu nathi thatu
                    // TODO: 02-Apr-21 previous date mate
                    if (currentYear > selectYear || currentYear == selectYear) {
                        Log.e("ajay", "year vala if ni anader");

                        // TODO: 02-Apr-21  jyare current month and select month sarkha hoy ATHAVA current month karta select month nano hoy tyrae if part execute thase
                        if (currentMonth == selectMonth || currentMonth > selectMonth) {
                            Log.e("ajay", "month vala if ni anader");

                            if (currentMonth == selectMonth) {
                                // TODO: 02-Apr-21 jyare current date karta select date nani hase tyare if part execute thase
                                if (currentDay > selectDay) {
                                    Toast.makeText(context, "you select previous date", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, AttendenceListStudAPActivity.class);
                                    intent.putExtra("div", div);
                                    intent.putExtra("selectdates", selectdates);
                                    intent.putExtra("sub_name", sub_name);
                                    intent.putExtra("month", String.valueOf(currentMonth));
                                    context.startActivity(intent);
                                }
                                // TODO: 02-Apr-21 current date mate
                                else if (currentDay == selectDay) {
                                    Toast.makeText(context, "you select current date", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, AttendenceListStudAPActivity.class);
                                    intent.putExtra("div", div);
                                    intent.putExtra("currDate", currentDate);
                                    intent.putExtra("sub_name", sub_name);
                                    intent.putExtra("month", String.valueOf(currentMonth));
                                    context.startActivity(intent);
                                }
                                // TODO: 02-Apr-21 jyare current month ni furure ni date select kare tyare else part execute thase
                                else {
                                    Toast.makeText(context, "You select future date", Toast.LENGTH_LONG).show();
                                }
                            }
                            // TODO: 02-Apr-21  jyare current month karta select month nano hoy tyare aa execute thase
                            else if (currentMonth > selectMonth) {
                                Toast.makeText(context, "you select previous date", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, AttendenceListStudAPActivity.class);
                                intent.putExtra("div", div);
                                intent.putExtra("selectdates", selectdates);
                                intent.putExtra("sub_name", sub_name);
                                intent.putExtra("month", String.valueOf(currentMonth));
                                context.startActivity(intent);
                            }
                        }
                        // TODO: 02-Apr-21  jyare current month karta select month vadhare hase tyare else part execute thase
                        else {
                            Toast.makeText(context, "you select future date", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // TODO: 02-Apr-21 jyare current year karta select year vadhare hase tyare else part execute thase
                    else {
                        Toast.makeText(context, "you select future date", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        datePickerDialog.show();
    }*/

    public void showStudDivSemWise(String div) {
        Intent intent = new Intent(context, ShowAttendenceShowStudSDActivity.class);
        intent.putExtra("div", div);
        intent.putExtra("sub_name", hhhh_sub_name);
        context.startActivity(intent);
    }

    public void showSubjectAddAssig() {

    }

    @Override
    public int getItemCount() {
        return diff_sub.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_sub_name;
        LinearLayout sub_linear;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tv_sub_name = itemView.findViewById(R.id.tv_sub_name);
            sub_linear = itemView.findViewById(R.id.sub_linear);
        }
    }
}