package com.example.pramodn.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.event_adapter;
import com.example.pramodn.myapplication.event_adapter.event_model;
import com.example.pramodn.myapplication.event_adapter.hosted_adapter;
import com.example.pramodn.myapplication.event_adapter.hosted_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostedEvents extends AppCompatActivity {
    private RecyclerView hosted_recycler;
    private hosted_adapter hosted_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.hosted_model> hosted_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosted_events);
        setTitle("Hosted Events");

        hosted_recycler=findViewById(R.id.hosted_recycler);

        requestQueue= Volley.newRequestQueue(HostedEvents.this);

        utility=new utility();
        hosted_model = new ArrayList<>();
        hosted_adapter = new hosted_adapter(HostedEvents.this, hosted_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(HostedEvents.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(HostedEvents.this, LinearLayoutManager.VERTICAL, true);

        hosted_recycler.setLayoutManager(linearLayoutManager1);
        hosted_recycler.setLayoutManager(mLayoutManager1);
        hosted_recycler.setItemAnimator(new DefaultItemAnimator());
        hosted_recycler.setAdapter(hosted_adapter);
        Load_data_from_DB();


    }

    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.hosted,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(HostedEvents.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.hosted_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String event_id = c.getString("event_id");
                                String event_name = c.getString("event_name");
                                String description = c.getString("synopsis");
                                String image = c.getString("image");
                                int likes=c.getInt("likes");
                                int numberofcomments=c.getInt("numberofcomments");


                                a = new hosted_model(event_id, event_name,description,image,likes,false,numberofcomments);
                                hosted_model.add(a);

                            }
                            hosted_adapter.notifyDataSetChanged();


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
