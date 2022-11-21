package com.example.pramodn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.group_adapter;
import com.example.pramodn.myapplication.event_adapter.group_model;
import com.example.pramodn.myapplication.fragment.Groups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendNotifications extends AppCompatActivity {

    private RecyclerView group_recycler;
    private com.example.pramodn.myapplication.event_adapter.group_adapter group_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.group_model> group_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    EditText creategroup;
    Button create;

    String cid,cfname,cimage,clname,cgender,cdob,cmoreinfo,creategroupes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notifications);
        setTitle("Add to group");

        cid=getIntent().getStringExtra("cid");
        cfname=getIntent().getStringExtra("cfname");
        cimage=getIntent().getStringExtra("cimage");
        clname=getIntent().getStringExtra("clname");
        cgender=getIntent().getStringExtra("cgender");
        cdob=getIntent().getStringExtra("cdob");
        cmoreinfo=getIntent().getStringExtra("cmoreinfo");
        creategroupes=getIntent().getStringExtra("creategroup");

        create=findViewById(R.id.create);
        creategroup=findViewById(R.id.creategroup);

        group_recycler=findViewById(R.id.group_recycler);
        requestQueue= Volley.newRequestQueue(SendNotifications.this);
        utility=new utility();
        group_model = new ArrayList<>();
        group_adapter = new group_adapter(SendNotifications.this, group_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(SendNotifications.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(SendNotifications.this, LinearLayoutManager.VERTICAL, true);
        group_recycler.setLayoutManager(linearLayoutManager1);
        group_recycler.setLayoutManager(mLayoutManager1);
        group_recycler.setItemAnimator(new DefaultItemAnimator());
        group_recycler.setAdapter(group_adapter);
        Load_data_from_DB();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (creategroup!=null) {
                    String url = utility.ip+utility.Create_group;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("Group created"))

                                    {
                                        Toast.makeText(SendNotifications.this, response, Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(SendNotifications.this,Home.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                    Toast.makeText(SendNotifications.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(SendNotifications.this, error.toString(), Toast.LENGTH_SHORT).show();

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
                            params.put("grpname", creategroup.getText().toString());
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
            }
        });
    }

    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Load_groups,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(SendNotifications.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.group_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String grpname_str = c.getString("grpname");

                                a = new group_model(grpname_str,cid,cfname,cimage,clname,cgender,cdob,cmoreinfo);
                                group_model.add(a);

                            }
                            group_adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Toast.makeText(getActivity(),constants.NETWORK_ERROR_MESSAGE, Toast.LENGTH_SHORT).show();

                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",utility.id);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(postRequest);

    }
}
