package com.example.pramodn.myapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.event_adapter.event_adapter;
import com.example.pramodn.myapplication.event_adapter.event_model;
import com.example.pramodn.myapplication.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Hosted extends Fragment {
    private RecyclerView event_recycler;
    private com.example.pramodn.myapplication.event_adapter.event_adapter event_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.event_model> event_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    public Hosted() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hosted, container, false);
        event_recycler=view.findViewById(R.id.event_recycler);

        requestQueue= Volley.newRequestQueue(getActivity());

        utility=new utility();
        event_model = new ArrayList<>();
        event_adapter = new event_adapter(getActivity(), event_model);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);

        event_recycler.setLayoutManager(linearLayoutManager1);
        event_recycler.setLayoutManager(mLayoutManager1);
        event_recycler.setItemAnimator(new DefaultItemAnimator());
        event_recycler.setAdapter(event_adapter);
        Load_data_from_DB();

        return view;
    }
    private void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.HostedEvents,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        event_model a;

                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String event_id = c.getString("event_id");
                                String event_name = c.getString("event_name");
                                String description = c.getString("description");
                                String image = c.getString("image");
                                int likes=c.getInt("likes");
                                int numberofcomments=c.getInt("numberofcomments");


                                a = new event_model(event_id, event_name,description,image,likes,false,numberofcomments);
                                event_model.add(a);

                            }
                            event_adapter.notifyDataSetChanged();


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
