package com.example.fecultypannel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_showAttendenceMonths extends RecyclerView.Adapter<Adapter_showAttendenceMonths.Viewholder> {
    ShowAttendenceMonthActivity showAttendenceMonthActivity;
    ArrayList months;
    ArrayList dates;

    int total_lec = 0, present_lec = 0, total_lec_month = 0, present_lec_month = 0;

    public Adapter_showAttendenceMonths(ShowAttendenceMonthActivity showAttendenceMonthActivity, ArrayList months, ArrayList dates) {
        this.showAttendenceMonthActivity = showAttendenceMonthActivity;
        this.months = months;
        this.dates = dates;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(showAttendenceMonthActivity).inflate(R.layout.layout_show_attendence_months, parent, false);
        RecyclerView.ViewHolder viewHolder = new Viewholder(view);
        return (Viewholder) viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        //holder.month_name.setText("" + months.get(position));
        String digit_month = months.get(position).toString();
        if (months.get(position).equals("1")) {
            holder.month_name.setText("January");
        }
        if (months.get(position).equals("2")) {
            holder.month_name.setText("February");
        }
        if (months.get(position).equals("3")) {
            holder.month_name.setText("March");
        }
        if (months.get(position).equals("4")) {
            holder.month_name.setText("April");
        }
        if (months.get(position).equals("5")) {
            holder.month_name.setText("May");
        }
        if (months.get(position).equals("6")) {
            holder.month_name.setText("June");
        }
        if (months.get(position).equals("7")) {
            holder.month_name.setText("July");
        }
        if (months.get(position).equals("8")) {
            holder.month_name.setText("August");
        }
        if (months.get(position).equals("9")) {
            holder.month_name.setText("September");
        }
        if (months.get(position).equals("10")) {
            holder.month_name.setText("October");
        }
        if (months.get(position).equals("11")) {
            holder.month_name.setText("November");
        }
        if (months.get(position).equals("12")) {
            holder.month_name.setText("December");
        }

        holder.month_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(showAttendenceMonthActivity, FinnalShowAllAttendenceStudActivity.class);
                intent.putExtra("month", digit_month);
                showAttendenceMonthActivity.startActivity(intent);
            }
        });
        ProgressDialog progressDialog = ProgressDialog.show(showAttendenceMonthActivity, "", "Please Wait...", false);

        RequestQueue requestQueue = Volley.newRequestQueue(showAttendenceMonthActivity);
        String url = "http://adkkda34.atwebpages.com/s_count_TP_lecture.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.e("lecture", response);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String attendence_result = jsonObject.getString("attendence_result");
                            String sub = jsonObject.getString("sub");
                            String month = jsonObject.getString("month");

                            if (sub.equals(ShowAttendenceShowStudSDActivity.a_sub_name)) {
                                total_lec++;
                                ShowAttendenceMonthActivity.total_lec.setText("/" + total_lec);

                                if (attendence_result.equals("Present")) {
                                    present_lec++;
                                    ShowAttendenceMonthActivity.presen_lec.setText("" + present_lec);
                                }
                            }
                            if (sub.equals(ShowAttendenceShowStudSDActivity.a_sub_name) && month.equals(months.get(position))) {
                                total_lec_month++;
                                holder.total_lec_month.setText("/" + total_lec_month);

                                if (attendence_result.equals("Present")) {
                                    present_lec_month++;
                                    holder.present_lec_month.setText("" + present_lec_month);
                                }
                            }
                        }
                        total_lec = 0;
                        present_lec = 0;
                        total_lec_month = 0;
                        present_lec_month = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("lecture", "" + error);
                Toast.makeText(showAttendenceMonthActivity, "Somethinf Went Wrong...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put("s_enrlno", ShowAttendenceMonthActivity.s_enrlno);
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView month_name, present_lec_month, total_lec_month;
        CardView month_card;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            month_name = itemView.findViewById(R.id.month_name);
            month_card = itemView.findViewById(R.id.month_card);
            present_lec_month = itemView.findViewById(R.id.present_lec_month);
            total_lec_month = itemView.findViewById(R.id.total_lec_month);
        }
    }
}
