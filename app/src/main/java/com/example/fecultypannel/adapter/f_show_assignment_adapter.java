package com.example.fecultypannel.adapter;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fecultypannel.R;
import com.example.fecultypannel.models.f_show_assignment_dataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

public class f_show_assignment_adapter extends RecyclerView.Adapter<f_show_assignment_adapter.Viewholder> {

    FragmentActivity activity;
    ArrayList<f_show_assignment_dataClass> arrayList;

    public f_show_assignment_adapter(FragmentActivity activity, ArrayList<f_show_assignment_dataClass> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_f_show_assignment, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        f_show_assignment_dataClass fShowAssignmentDataClass = arrayList.get(position);

        String assi_id = fShowAssignmentDataClass.getId();
        String pdfURL = fShowAssignmentDataClass.getPdfURL();
        String pdf_title = fShowAssignmentDataClass.getPdf_title();
        String s_div = fShowAssignmentDataClass.getS_div();
        String upload_date = fShowAssignmentDataClass.getUpload_date();
        String submission_date = fShowAssignmentDataClass.getSubmission_date();
        String sub_name = fShowAssignmentDataClass.getSub_name();
        String f_name = fShowAssignmentDataClass.getF_name();
        String f_email = fShowAssignmentDataClass.getF_email();

        holder.sub_name.setText(sub_name);
        holder.assi_title.setText("Assignment title : " + pdf_title);
        holder.upload_date.setText("Upload Date : " + upload_date);
        holder.submission_date.setText("Submission Date : " + submission_date);
        holder.div.setText("Div : " + s_div);
        holder.feulty_name.setText(f_name);

        if (sub_name.startsWith("1")) {
            holder.sem.setText("Sem : 1");
        }
        if (sub_name.startsWith("2")) {
            holder.sem.setText("Sem : 2");
        }
        if (sub_name.startsWith("3")) {
            holder.sem.setText("Sem : 3");
        }
        if (sub_name.startsWith("4")) {
            holder.sem.setText("Sem : 4");
        }
        if (sub_name.startsWith("5")) {
            holder.sem.setText("Sem : 5");
        }
        if (sub_name.startsWith("6")) {
            holder.sem.setText("Sem : 6");
        }

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdf_url = pdfURL;
                DownloadManager dm = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdf_url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!

                dm.enqueue(request);
            }
        });
        holder.assi_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setIcon(R.drawable.ic_alert);
                builder.setTitle("Deleting...");
                builder.setMessage("Are You Sure Want To Delete..?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        ProgressDialog progressDialog = ProgressDialog.show(activity, "", "Please Wait...", false);
                        RequestQueue requestQueue = Volley.newRequestQueue(activity);
                        String url = "http://adkkda34.atwebpages.com/assignment_delete.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Log.e("assi_del", response);
                                arrayList.remove(position);
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.e("assi_del1", "" + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap hashMap = new HashMap();
                                hashMap.put("assi_id", assi_id);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView sub_name, assi_title, upload_date, submission_date, div, sem, feulty_name, download;
        ImageView assi_delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            sub_name = itemView.findViewById(R.id.sub_name);
            assi_title = itemView.findViewById(R.id.assi_title);
            upload_date = itemView.findViewById(R.id.upload_date);
            submission_date = itemView.findViewById(R.id.submission_date);
            div = itemView.findViewById(R.id.div);
            sem = itemView.findViewById(R.id.sem);
            feulty_name = itemView.findViewById(R.id.feulty_name);
            download = itemView.findViewById(R.id.download);
            assi_delete = itemView.findViewById(R.id.assi_delete);
        }
    }
}