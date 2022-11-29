package com.example.fecultypannel.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fecultypannel.Adapter_f_div_list;
import com.example.fecultypannel.AddTimeTableActivity;
import com.example.fecultypannel.R;

import java.util.ArrayList;

import static com.example.fecultypannel.AddTimeTableActivity.ttDivsArrayList;

public class TimeTableShowSemAdapter extends RecyclerView.Adapter<TimeTableShowSemAdapter.ViewHolder> {

    AddTimeTableActivity addTimeTableActivity;
    ArrayList compare_sem;
    RecyclerView div_recycl;
    LinearLayoutManager linearLayoutManager;

    public TimeTableShowSemAdapter(AddTimeTableActivity addTimeTableActivity, ArrayList compare_sem) {
        this.addTimeTableActivity = addTimeTableActivity;
        this.compare_sem = compare_sem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(addTimeTableActivity).inflate(R.layout.adapter_add_timetable_show_sem_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_sem.setText("Semester : " + compare_sem.get(position).toString());
        holder.cv_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(addTimeTableActivity);
                dialog.setContentView(R.layout.divison_dialog_layout);
                dialog.setCanceledOnTouchOutside(false);
                div_recycl = dialog.findViewById(R.id.div_recycl);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                String finnal_div[] = ttDivsArrayList.get(position).toString().split(",");
                linearLayoutManager = new LinearLayoutManager(addTimeTableActivity, RecyclerView.VERTICAL, false);
                div_recycl.setLayoutManager(linearLayoutManager);

                Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(addTimeTableActivity, finnal_div, compare_sem.get(position).toString(), "showDivShowTimetable");
                div_recycl.setAdapter(adapter_f_div_list);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return compare_sem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv_sem;
        TextView tv_sem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cv_sem = itemView.findViewById(R.id.cv_sem);
            tv_sem = itemView.findViewById(R.id.tv_sem);
        }
    }
}
