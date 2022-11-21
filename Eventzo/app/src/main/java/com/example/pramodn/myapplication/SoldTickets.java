package com.example.pramodn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pramodn.myapplication.event_adapter.purchase_adapter;
import com.example.pramodn.myapplication.event_adapter.purchase_model;
import com.example.pramodn.myapplication.event_adapter.sold_adapter;
import com.example.pramodn.myapplication.event_adapter.sold_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoldTickets extends AppCompatActivity {

    private RecyclerView sold_recycler;
    private com.example.pramodn.myapplication.event_adapter.sold_adapter sold_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.sold_model> sold_model;
    RequestQueue requestQueue;
    utility utility;

    String event_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sold_tickets);
        setTitle("Sold Tickets");

        event_id=getIntent().getStringExtra("event_id");

        sold_recycler=findViewById(R.id.sold_recycler);
        requestQueue= Volley.newRequestQueue(SoldTickets.this);
        utility=new utility();
        sold_model = new ArrayList<>();
        sold_adapter = new sold_adapter(SoldTickets.this, sold_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(SoldTickets.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(SoldTickets.this, LinearLayoutManager.VERTICAL, true);
        sold_recycler.setLayoutManager(linearLayoutManager1);
        sold_recycler.setLayoutManager(mLayoutManager1);
        sold_recycler.setItemAnimator(new DefaultItemAnimator());
        sold_recycler.setAdapter(sold_adapter);
        Load_data_from_DB();

    }



    private void Load_data_from_DB() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Soldtickets,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(SoldTickets.this, response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.sold_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String event_id = c.getString("event_id");
                                String event_name = c.getString("event_name");
                                String bid = c.getString("bid");
                                String transactionid = c.getString("transactionid");
                                String tickettype = c.getString("tickettype");
                                int numberoftickets=c.getInt("numberoftickets");
                                int totalamount=c.getInt("totalamount");
//                                String ev_id=c.getString("ev_id");
                                String fname_str=c.getString("fname");
                                String lname_str=c.getString("lname");
                                String mobileno=c.getString("phone");
                                String email=c.getString("email");

                                String user_name=fname_str+" "+lname_str;

                                a = new sold_model(event_id, event_name,bid,transactionid,tickettype,numberoftickets,totalamount,user_name,mobileno,email);
                                sold_model.add(a);

                            }
                            sold_adapter.notifyDataSetChanged();
                            Log.d("size_act",String.valueOf(sold_model.size()));


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
                params.put("eid",event_id);
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
