package com.example.fecultypannel.adapter;

import android.app.Dialog;
import android.util.Log;
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
import com.example.fecultypannel.R;
import com.example.fecultypannel.ResultShowSubjectActivity;

import java.util.ArrayList;

import static com.example.fecultypannel.ResultFecultyActivity.ResultDivsArrayList;

public class ResultShowSubjectSemWiseAdapter extends RecyclerView.Adapter<ResultShowSubjectSemWiseAdapter.ViewHolder> {

    ResultShowSubjectActivity resultShowSubjectActivity;
    ArrayList result_show_subject_arraylist;
    RecyclerView div_recycl;
    LinearLayoutManager linearLayoutManager;

    public ResultShowSubjectSemWiseAdapter(ResultShowSubjectActivity resultShowSubjectActivity, ArrayList result_show_subject_arraylist) {
        this.result_show_subject_arraylist = result_show_subject_arraylist;
        this.resultShowSubjectActivity = resultShowSubjectActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(resultShowSubjectActivity).inflate(R.layout.adapter_f_sub_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_sub_name.setText(result_show_subject_arraylist.get(position).toString());

        holder.card_sub_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(resultShowSubjectActivity);
                dialog.setContentView(R.layout.divison_dialog_layout);
                dialog.setCanceledOnTouchOutside(false);
                div_recycl = dialog.findViewById(R.id.div_recycl);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                String finnal_div[] = ResultDivsArrayList.get(position).toString().split(",");

                for (int i = 0; i < finnal_div.length; i++) {
                    Log.e("finnal_divvvv", "" + finnal_div[i]);
                }

                linearLayoutManager = new LinearLayoutManager(resultShowSubjectActivity, RecyclerView.VERTICAL, false);
                div_recycl.setLayoutManager(linearLayoutManager);

                Adapter_f_div_list adapter_f_div_list = new Adapter_f_div_list(resultShowSubjectActivity, finnal_div, holder.tv_sub_name.getText().toString(), "showDivShowResult");
                div_recycl.setAdapter(adapter_f_div_list);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return result_show_subject_arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sub_name;
        CardView card_sub_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card_sub_list = itemView.findViewById(R.id.card_sub_list);

            tv_sub_name = itemView.findViewById(R.id.tv_sub_name);
        }
    }
}