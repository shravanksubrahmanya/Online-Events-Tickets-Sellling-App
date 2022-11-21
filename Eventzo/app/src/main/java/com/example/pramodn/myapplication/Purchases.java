package com.example.pramodn.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
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
import com.example.pramodn.myapplication.event_adapter.purchase_adapter;
import com.example.pramodn.myapplication.event_adapter.purchase_model;
import com.example.pramodn.myapplication.fragment.Home_fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchases extends AppCompatActivity {
    private RecyclerView purchase_recycler;
    private com.example.pramodn.myapplication.event_adapter.purchase_adapter purchase_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.purchase_model> purchase_model;
    RequestQueue requestQueue;
    utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);
        setTitle("Purchases");
        purchase_recycler=findViewById(R.id.purchase_recycler);
        requestQueue= Volley.newRequestQueue(Purchases.this);
        utility=new utility();
        purchase_model = new ArrayList<>();
        purchase_adapter = new purchase_adapter(Purchases.this, purchase_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(Purchases.this, 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(Purchases.this, LinearLayoutManager.VERTICAL, true);
        purchase_recycler.setLayoutManager(linearLayoutManager1);
        purchase_recycler.setLayoutManager(mLayoutManager1);
        purchase_recycler.setItemAnimator(new DefaultItemAnimator());
        purchase_recycler.setAdapter(purchase_adapter);
        Load_data_from_DB();

    }



    private void Load_data_from_DB() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Tickets,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                       Toast.makeText(Purchases.this, response, Toast.LENGTH_SHORT).show();

                        purchase_model a;

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



                                a = new purchase_model(event_id, event_name,bid,transactionid,tickettype,numberoftickets,totalamount);
                                purchase_model.add(a);

                            }
                            purchase_adapter.notifyDataSetChanged();
                            Log.d("size_act",String.valueOf(purchase_model.size()));


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
