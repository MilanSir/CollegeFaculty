package com.example.fecultypannel;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Adapter_f_div_list extends RecyclerView.Adapter<Adapter_f_div_list.Viewholder> {
    Context context;
    String[] finnal_div;
    String hhhh_sub_name;
    String showDivison;

    public Adapter_f_div_list(Context context, String[] finnal_div, String hhhh_sub_name, String showDivison) {
        this.context = context;
        this.finnal_div = finnal_div;
        this.hhhh_sub_name = hhhh_sub_name;
        this.showDivison = showDivison;
    }

    @NonNull
    @Override
    public Adapter_f_div_list.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.divison_design, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_f_div_list.Viewholder holder, int position) {
        holder.btn_div.setText(finnal_div[position]);
        /*HashMap hashMap = (HashMap) arrayList.get(position);
        holder.btn_div.setText(hashMap.get("1").toString());*/

        if (showDivison.equals("showDivAddAttendance")) {
            String testing = holder.btn_div.getText().toString();
            holder.btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    chooseDate(testing);
                }
            });
        } else if (showDivison.equals("showDivShowAttendence")) {
            String testing = holder.btn_div.getText().toString();

            holder.btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showStudDivSemWise(testing);
                }
            });
        } else if (showDivison.equals("showDivShowTimetable")) {
            String testing = holder.btn_div.getText().toString();

            holder.btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TimeTableDataActivity.class);
                    intent.putExtra("sem1", hhhh_sub_name);
                    intent.putExtra("div", testing);
                    context.startActivity(intent);
                }
            });
        } else if (showDivison.equals("showDivAddAssignment")) {
            String testing = holder.btn_div.getText().toString();
            holder.btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddAssignmentDetails.class);
                    intent.putExtra("sub_name", hhhh_sub_name);
                    intent.putExtra("assi_div",testing);
                    context.startActivity(intent);
                }
            });
        }
        else if (showDivison.equals("showDivResult")){
            String testing = holder.btn_div.getText().toString();
            holder.btn_div.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ShowStudentSemWiseActivity.class);
                    intent.putExtra("tvresult", hhhh_sub_name);
                    intent.putExtra("div", testing);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void chooseDate(String div) {
        @SuppressLint({"NewApi", "LocalSuppress"}) DatePickerDialog datePickerDialog = new DatePickerDialog(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String selectedDate = i2 + "/" + (i1 + 1) + "/" + i;

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
                                    intent.putExtra("sub_name", hhhh_sub_name);
                                    intent.putExtra("month", String.valueOf(currentMonth));
                                    context.startActivity(intent);
                                }
                                // TODO: 02-Apr-21 current date mate
                                else if (currentDay == selectDay) {
                                    Toast.makeText(context, "you select current date", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, AttendenceListStudAPActivity.class);
                                    intent.putExtra("div", div);
                                    intent.putExtra("currDate", currentDate);
                                    intent.putExtra("sub_name", hhhh_sub_name);
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
                                intent.putExtra("sub_name", hhhh_sub_name);
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
    }

    public void showStudDivSemWise(String div) {
        Intent intent = new Intent(context, ShowAttendenceShowStudSDActivity.class);
        intent.putExtra("div", div);
        intent.putExtra("sub_name", hhhh_sub_name);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return finnal_div.length;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView btn_div;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            btn_div = itemView.findViewById(R.id.btn_div);
        }
    }
}
