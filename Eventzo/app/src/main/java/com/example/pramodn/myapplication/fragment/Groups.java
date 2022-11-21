package com.example.pramodn.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.example.pramodn.myapplication.R;
import com.example.pramodn.myapplication.event_adapter.event_adapter;
import com.example.pramodn.myapplication.event_adapter.event_model;
import com.example.pramodn.myapplication.event_adapter.group_adapter;
import com.example.pramodn.myapplication.event_adapter.group_model;
import com.example.pramodn.myapplication.event_adapter.grp_adapter;
import com.example.pramodn.myapplication.event_adapter.grp_model;
import com.example.pramodn.myapplication.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Intent.getIntent;

public class Groups extends Fragment {
    private RecyclerView group_recycler;
    private com.example.pramodn.myapplication.event_adapter.grp_adapter grp_adapter;
    private List<com.example.pramodn.myapplication.event_adapter.grp_model> grp_model;
    RequestQueue requestQueue;
    com.example.pramodn.myapplication.utility utility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        group_recycler=view.findViewById(R.id.group_recycler);
        requestQueue= Volley.newRequestQueue(getActivity());
        utility=new utility();
        grp_model = new ArrayList<>();
        grp_adapter = new grp_adapter(getActivity(), grp_model,Groups.this);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        group_recycler.setLayoutManager(linearLayoutManager1);
        group_recycler.setLayoutManager(mLayoutManager1);
        group_recycler.setItemAnimator(new DefaultItemAnimator());
        group_recycler.setAdapter(grp_adapter);
        Load_data_from_DB();

        return view;
    }

    public void Load_data_from_DB() {


        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+utility.Load_groups,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        com.example.pramodn.myapplication.event_adapter.grp_model a;

                        try {
                            grp_model.clear();

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String grpname_str = c.getString("grpname");

                                a = new grp_model(grpname_str);
                                grp_model.add(a);

                            }
                            grp_adapter.notifyDataSetChanged();


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
