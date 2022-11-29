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
import com.example.fecultypannel.models.f_show_notice_dataClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

public class f_show_notice_adapter extends RecyclerView.Adapter<f_show_notice_adapter.Viewholder> {
    FragmentActivity activity;
    ArrayList<f_show_notice_dataClass> arrayList;

    public f_show_notice_adapter(FragmentActivity activity, ArrayList<f_show_notice_dataClass> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.f_show_notice_adapter, parent, false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        f_show_notice_dataClass f_show_notice_dataClass = arrayList.get(position);

        String notice_id = f_show_notice_dataClass.getId();
        String notice_title = f_show_notice_dataClass.getNotice_title();
        String date = f_show_notice_dataClass.getDate();
        String notic_url = f_show_notice_dataClass.getNotice_pdf_url();

        holder.notice_title.setText("Notice Title : " + notice_title);
        holder.upload_date.setText("Date : " + date);

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pdf_url = notic_url;
                DownloadManager dm = (DownloadManager) activity.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdf_url));

                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!

                dm.enqueue(request);
            }
        });

        holder.notice_delete.setOnClickListener(new View.OnClickListener() {
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
                        String url = "http://adkkda34.atwebpages.com/f_notice_delete.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                Log.e("notice_del", response);
                                arrayList.remove(position);
                                notifyDataSetChanged();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Log.e("notice_del1", "" + error);
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap hashMap = new HashMap();
                                hashMap.put("notice_id", notice_id);
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

        TextView notice_title, upload_date, download;
        ImageView notice_delete;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            notice_title = itemView.findViewById(R.id.notice_title);
            upload_date = itemView.findViewById(R.id.upload_date);
            download = itemView.findViewById(R.id.download);
            notice_delete = itemView.findViewById(R.id.notice_delete);
        }
    }
}
