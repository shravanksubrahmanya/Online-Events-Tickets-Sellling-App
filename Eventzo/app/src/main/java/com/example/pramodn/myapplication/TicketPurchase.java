package com.example.pramodn.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class TicketPurchase extends AppCompatActivity {

    TextView ticketprice,ticketsleft,termsconditions;
    EditText numberoftickets,totalamount,transactionid,tnumber;
    Button submit,calculate;
    Spinner tickettype;
    CheckBox checkterms;
    String selected_type,event_id,event_name,fname,lname,phno,email,hosterid;
    RequestQueue requestQueue;
    String[] type;
    double[] price;
    int[] no_of_t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_purchase);
        setTitle("Ticket Purchase");

        event_id = getIntent().getStringExtra("event_id");
        event_name = getIntent().getStringExtra("event_name");

        tickettype=findViewById(R.id.tickettype);

        tickettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ticketprice.setText(String.valueOf(price[position]));
                ticketsleft.setText(String.valueOf(no_of_t[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requestQueue=Volley.newRequestQueue(this);

        ticketprice=findViewById(R.id.ticketprice);
        ticketsleft=findViewById(R.id.ticketsleft);
        tnumber=findViewById(R.id.tnumber);
        numberoftickets=findViewById(R.id.numberoftickets);
        totalamount=findViewById(R.id.totalamount);
        transactionid=findViewById(R.id.transactionid);
        submit=findViewById(R.id.submit);
        termsconditions=findViewById(R.id.termsconditions);
        checkterms=findViewById(R.id.checkterms);
        calculate=findViewById(R.id.calculate);
        numberoftickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ( s.toString().length()<=0) {
                    numberoftickets.setText("0");

                }
                else{

                    double price, totamt;
                    int noticket;
                    price = Double.parseDouble(ticketprice.getText().toString());
                    noticket = Integer.parseInt(s.toString());
                    totamt = price * noticket;
                    totalamount.setText("" + totamt);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Load_ticket();
        Load_user();
        Load_hoster_number();

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        termsconditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(TicketPurchase.this).create();
                alertDialog.setTitle("Terms & Conditions");
                alertDialog.setMessage("1.If the transaction is not successful then you must inform the Creator of the event using given contact details or you can inform EVENTZO " + "2. In case of any criminal activities comitted by you is considerable to take strict actions against you"+"");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
        submit.setVisibility(View.INVISIBLE);
        checkterms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                {
                    submit.setVisibility(View.INVISIBLE);
                }
                else {

                    submit.setVisibility(View.VISIBLE);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (transactionid!=null && numberoftickets !=null) {
                    final int tl,nt,tt;
                    tl=Integer.parseInt(ticketsleft.getText().toString());
                    nt=Integer.parseInt(numberoftickets.getText().toString());
                    tt=tl-nt;

                    String url = utility.ip+utility.Purchase;
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.contains("Purchase is successful"))

                                    {
                                        startActivity(new Intent(TicketPurchase.this,Purchases.class));
                                    }


                                    Toast.makeText(TicketPurchase.this, response, Toast.LENGTH_SHORT).show();
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    Toast.makeText(TicketPurchase.this, error.toString(), Toast.LENGTH_SHORT).show();

                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("uid", utility.id);
                            params.put("eid", event_id);
                            params.put("transactionid", transactionid.getText().toString());
                            params.put("event_name", event_name);
                            params.put("ticket_type", tickettype.getSelectedItem().toString());
                            params.put("nooftickets", numberoftickets.getText().toString());
                            params.put("phno", phno);
                            params.put("email", email);
                            params.put("totalamount", totalamount.getText().toString());

                            params.put("tickets_left", String.valueOf(tt));
                            return params;
                        }
                    };
                    requestQueue.add(postRequest);
                }

            }
        });

    }


    private void Load_ticket() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+ utility.load_ticket_types,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        try {

                            JSONArray data = new JSONArray(response);
                            type=new String[data.length()];
                            price=new double[data.length()];
                            no_of_t=new int[data.length()];

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                type[i] = c.getString("type");
                                price[i]= c.getDouble("price");
                                no_of_t[i]= c.getInt("notickets");

                            }
                            ArrayAdapter<String> gameKindArray= new ArrayAdapter<String>(TicketPurchase.this,android.R.layout.simple_spinner_item, type);
                            gameKindArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tickettype.setAdapter(gameKindArray);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id",event_id);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }

    private void Load_hoster_number() {
        Log.d("event_id_id",event_id);
        StringRequest postRequest = new StringRequest(Request.Method.POST,utility.ip+ utility.load_hoster_number,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("hoster_number",response);


                        Toast.makeText(TicketPurchase.this, response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String hosternumber = c.getString("number");

                                tnumber.setText(hosternumber);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(TicketPurchase.this, error.toString(), Toast.LENGTH_SHORT).show();

                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("event_id",event_id);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


    private void Load_user() {
        StringRequest postRequest = new StringRequest(Request.Method.POST,utility.ip+ utility.load_user_ticket,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(TicketPurchase.this, response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String fname_str = c.getString("fname");
                                String lname_str=c.getString("lname");
                                String ph_no = c.getString("phno");
                                String e_mail = c.getString("email");


                                 fname= fname_str;
                                 lname=lname_str;
                                phno=ph_no;
                                email=e_mail;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(TicketPurchase.this, error.toString(), Toast.LENGTH_SHORT).show();

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