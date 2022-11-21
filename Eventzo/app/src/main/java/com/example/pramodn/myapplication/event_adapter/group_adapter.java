package com.example.pramodn.myapplication.event_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
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
import com.example.pramodn.myapplication.SendNotifications;
import com.example.pramodn.myapplication.fragment.Groups;
import com.example.pramodn.myapplication.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.content.ContextCompat.startActivity;

public class group_adapter extends RecyclerView.Adapter<group_adapter.MyViewHolder> {


    private Context mContext;
    private List<group_model> albumList1;
    RequestQueue requestQueue;

    String cid,cfname,clname,cimage,cgender,cmoreinfo,cdob,grp_name;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView groupname;


        public MyViewHolder(View view) {
            super(view);
            groupname=view.findViewById(R.id.groupname);
            requestQueue= Volley.newRequestQueue(mContext);
        }
    }


    public group_adapter(Context mContext, List<group_model> albumList1) {
        this.mContext = mContext;
        this.albumList1 = albumList1;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final group_model album = albumList1.get(position);
        Log.d("size_from_adapter",String.valueOf(albumList1.size()));


        holder.groupname.setText(album.getGrpname());
        cid=album.getCid();
        cfname=album.getCfname();
        clname=album.getClname();
        cimage=album.getCimage();
        cgender=album.getCgender();
        cmoreinfo=album.getMoreinfor();
        cdob=album.getDateofbirth();

        holder.groupname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = utility.ip+utility.Add_to_group;
                grp_name=album.getGrpname();

                Log.d("grp",grp_name);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Added to the group"))

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
                        params.put("fname", cfname);
                        params.put("uid", utility.id);
                        params.put("lname", clname);
                        params.put("grpname", grp_name);
                        params.put("connectedid", cid);
                        params.put("image", cimage);
                        params.put("gender", cgender);
                        params.put("dob", cdob);
                        params.put("moreinfo",cmoreinfo = cmoreinfo.replace("'","''"));

                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        });
    }
    @Override
    public int getItemCount() {
        return albumList1.size();
    }

}
