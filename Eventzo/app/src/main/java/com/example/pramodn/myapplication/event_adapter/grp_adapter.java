package com.example.pramodn.myapplication.event_adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.Home;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.TicketPurchase;
import com.example.pramodn.myapplication.ViewAllComments;
import com.example.pramodn.myapplication.ViewGroupInfo;
import com.example.pramodn.myapplication.description;
import com.example.pramodn.myapplication.fragment.Groups;
import com.example.pramodn.myapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

public class grp_adapter extends RecyclerView.Adapter<grp_adapter.MyViewHolder> {


    private Context mContext;
    private List<grp_model> albumList1;
    RequestQueue queue;
    Groups groups;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupname;

        public MyViewHolder(View view) {
            super(view);
            queue= Volley.newRequestQueue(mContext);
            groupname=view.findViewById(R.id.groupname);
        }
    }


    public grp_adapter(Context mContext, List<grp_model> albumList1,Groups groups) {
        this.mContext = mContext;
        this.albumList1 = albumList1;
        this.groups=groups;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grp_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final grp_model album = albumList1.get(position);
        Log.d("size_from_adapter",String.valueOf(albumList1.size()));


        holder.groupname.setText(album.getGrpname());
        holder.groupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mContext, ViewGroupInfo.class);
                    intent.putExtra("groupname", album.getGrpname());
                    mContext.startActivity(intent);
            }
        });

        holder.groupname.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("Delete group"+" "+holder.groupname.getText().toString());
                alertDialog.setMessage("Do you really want to delete this group");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int which) {
                                String url = utility.ip+utility.Deletegroup;
                                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                if (response.contains("Group deleted successfully"))
                                                {


                                                    dialog.dismiss();
                                                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                                    groups.Load_data_from_DB();

                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();

                                                Log.d("Error.Response", error.toString());
                                            }
                                        }
                                ) {
                                    @Override
                                    protected Map<String, String> getParams() {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("groupname",holder.groupname.getText().toString());
                                        params.put("uid", utility.id);

                                        return params;
                                    }
                                };
                                queue.add(postRequest);
                            }
                        });
                alertDialog.show();

                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}
