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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.grp_adapter;
import com.example.pramodn.myapplication.event_adapter.grp_model;
import com.example.pramodn.myapplication.event_adapter.notify_adapter;
import com.example.pramodn.myapplication.event_adapter.notify_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notify extends AppCompatActivity {

    private RecyclerView notify_recycler;
    private com.example.pramodn.myapplication.event_adapter.notify_adapter notify_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.notify_model> notify_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    String event_id,event_name,event_image,synopsis;

    TextView sendpublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        setTitle("Send Notification");

        event_id=getIntent().getStringExtra("event_id");
        event_name=getIntent().getStringExtra("event_name");
        event_image=getIntent().getStringExtra("event_image");
        synopsis=getIntent().getStringExtra("synopsis");
        sendpublic=findViewById(R.id.sendpublic);

        notify_recycler=findViewById(R.id.notify_recycler);
        requestQueue= Volley.newRequestQueue(Notify.this);
        utility=new utility();
        notify_model = new ArrayList<>();
        notify_adapter = new notify_adapter(Notify.this, notify_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(Notify.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(Notify.this, LinearLayoutManager.VERTICAL, true);
        notify_recycler.setLayoutManager(linearLayoutManager1);
        notify_recycler.setLayoutManager(mLayoutManager1);
        notify_recycler.setItemAnimator(new DefaultItemAnimator());
        notify_recycler.setAdapter(notify_adapter);
        Load_data_from_DB();

        sendpublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = utility.ip+utility.Send_notice_public;
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Added to the notice"))

                                {
                                    Notify.this.startActivity(new Intent(Notify.this, Home.class));
                                }

                                Toast.makeText(Notify.this, response, Toast.LENGTH_SHORT).show();
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(Notify.this, error.toString(), Toast.LENGTH_SHORT).show();

                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid", utility.id);
                        params.put("event_id", event_id);
                        params.put("grpname", "");
                        params.put("event_name", event_name);
                       // params.put("event_image", event_image);
                        params.put("synopsis", synopsis);
                        params.put("type", "public");

                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        });

    }

    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Load_groups,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(Notify.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.notify_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String grpname_str = c.getString("grpname");

                                a = new notify_model(grpname_str,event_id,event_name,event_image,synopsis);
                                notify_model.add(a);

                            }
                            notify_adapter.notifyDataSetChanged();


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
