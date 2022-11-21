package com.example.pramodn.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class createnext extends AppCompatActivity {
    String event_id, liveornot;
    EditText tickettype, price, noticket;
    TextView addone;
    Button next;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnext);
        liveornot = getIntent().getStringExtra("liveornot");
        event_id = getIntent().getStringExtra("event_id");

        queue = Volley.newRequestQueue(this);

        tickettype = findViewById(R.id.tickettype);
        price = findViewById(R.id.price);
        noticket = findViewById(R.id.noticket);
        addone = findViewById(R.id.addone);
        next = findViewById(R.id.next);

        addone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_ticket();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload_ticket();
                Intent intent = new Intent(createnext.this, HostedEvents.class);
                startActivity(intent);
            }
        });
    }


    private void upload_ticket() {
        if (tickettype != null && price != null && noticket != null) {
            String url = utility.ip + utility.tickets;
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.contains("Tickets information uploaded")) {
                                startActivity(new Intent(createnext.this, HostedEvents.class));
                            }
                            Toast.makeText(createnext.this, response, Toast.LENGTH_SHORT).show();
                            Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(createnext.this, error.toString(), Toast.LENGTH_SHORT).show();

                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("event_id", event_id);
                    params.put("uid", utility.id);
                    params.put("tickettype", tickettype.getText().toString());
                    params.put("price", price.getText().toString());
                    params.put("noticket", noticket.getText().toString());

                    return params;
                }

            };
            queue.add(postRequest);
           }
    }
    }

