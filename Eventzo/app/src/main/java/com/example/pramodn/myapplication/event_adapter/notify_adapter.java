package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.pramodn.myapplication.Notify;
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.ViewGroupInfo;
import com.example.pramodn.myapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class notify_adapter extends RecyclerView.Adapter<notify_adapter.MyViewHolder> {


    private Context mContext;
    private List<notify_model> albumList1;
    RequestQueue queue;


    String event_id,event_name,event_image,synopsis,grp_name;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupname;

        public MyViewHolder(View view) {
            super(view);
            groupname=view.findViewById(R.id.groupname);
            queue= Volley.newRequestQueue(mContext);
        }
    }


    public notify_adapter(Context mContext, List<notify_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grp_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final notify_model album = albumList1.get(position);
        Log.d("size_from_adapter",String.valueOf(albumList1.size()));


        holder.groupname.setText(album.getGrpname());

        event_id=album.getEvent_id();
        event_name=album.getEvent_name();
        event_image=album.getEvent_image();
        synopsis=album.getSynopsis();
        grp_name=album.getGrpname();

        holder.groupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = utility.ip+utility.Send_notice_public;
                grp_name=album.getGrpname();
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Added to the notice"))

                                {
                                    mContext.startActivity(new Intent(mContext, Home.class));
                                }

                                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                Log.d("Response", response);
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
                        params.put("uid", utility.id);
                        params.put("event_id", event_id);
                        params.put("grpname", grp_name);
                        params.put("event_name", event_name);
                        params.put("event_image", event_image);
                        params.put("synopsis", synopsis);
                        params.put("type", "private");

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}