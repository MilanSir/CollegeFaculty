package com.example.fecultypannel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_f_ShowAllAttendence extends RecyclerView.Adapter<Adapter_f_ShowAllAttendence.ViewHolder> {
    FinnalShowAllAttendenceStudActivity finnalShowAllAttendenceStudActivity;
    ArrayList<AttendenceData> arrayList;

    public Adapter_f_ShowAllAttendence(FinnalShowAllAttendenceStudActivity finnalShowAllAttendenceStudActivity, ArrayList<AttendenceData> arrayList) {
        this.finnalShowAllAttendenceStudActivity = finnalShowAllAttendenceStudActivity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(finnalShowAllAttendenceStudActivity).inflate(R.layout.layout_fshowattendence, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AttendenceData data = arrayList.get(position);

        String subName = data.getSub();
        String fName = data.getFeculty_name();
        String attendence_result = data.getAttendence_result();
        String div = data.getS_div();
        String date = data.getAttendence_date();
        String rollno = data.getRollno();


        holder.sub_name.setText("Subject : " + subName);
        holder.f_name.setText("Feculty : " + fName);
        holder.div.setText("Div : " + div);
        holder.date.setText("Date : " + date);
        holder.rollno.setText("Roll no : " + rollno);
        holder.result.setText(attendence_result);

        if (holder.result.getText().toString().equals("Present")) {
            holder.result.setBackground(finnalShowAllAttendenceStudActivity.getResources().getDrawable(R.drawable.bg_present));
        }
        if (holder.result.getText().toString().equals("Absent")) {
            holder.result.setBackground(finnalShowAllAttendenceStudActivity.getResources().getDrawable(R.drawable.bg_absent));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_name, f_name, div, date, rollno;
        Button result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_name = itemView.findViewById(R.id.sub_name);
            f_name = itemView.findViewById(R.id.f_name);
            div = itemView.findViewById(R.id.div);
            date = itemView.findViewById(R.id.date);
            rollno = itemView.findViewById(R.id.rollno);
            result = itemView.findViewById(R.id.result);
        }
    }
}
