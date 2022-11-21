package com.example.pramodn.myapplication;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditAccount extends AppCompatActivity implements View.OnClickListener{

    private static final int RESULT_LOAD_IMAGE =1;
    private static final String TAG = "MainActivity";
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    EditText fname,lname,mailid,password,mobileno,dateofbirth,moreinfo;
    ImageView imagetoupload;
    TextView termsconditions;
    Button update;
    ImageView picker;
    RadioGroup gender;
    RadioButton male,female;
    CheckBox checkterms;
    String selectedgender;
    RequestQueue queue;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Account");
        setContentView(R.layout.activity_edit_account);

        fname=findViewById(R.id.fname);
        moreinfo=findViewById(R.id.moreinfo);
        picker=findViewById(R.id.picker);
        gender=findViewById(R.id.gender);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        lname=findViewById(R.id.lname);
        mailid=findViewById(R.id.mailid);
        password=findViewById(R.id.password);
        mobileno=findViewById(R.id.mobileno);
        imagetoupload=(ImageView) findViewById(R.id.imagetoupload);
        termsconditions=findViewById(R.id.termsconditions);
        update=findViewById(R.id.update);
        checkterms=findViewById(R.id.checkterms);
        queue = Volley.newRequestQueue(this);


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                final String value =
                        ((RadioButton)findViewById(gender.getCheckedRadioButtonId()))
                                .getText().toString();

                Toast.makeText(getBaseContext(), value, Toast.LENGTH_SHORT).show();
                selectedgender=value;


            }
        });
        imagetoupload.setOnClickListener(this);
        update.setOnClickListener(this);
        picker.setOnClickListener(this);

        dateofbirth=(EditText) findViewById(R.id.dateofbirth);
        dateofbirth.setOnClickListener(this);

        Load_user_profile();

    }
    @Override
    public void onClick(View v) {
        if (v == imagetoupload) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
        }

        else if (v==update){
            if (fname!=null && password!=null && mailid!=null && dateofbirth!=null) {
                String url = utility.ip+utility.EditAccount;
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.contains("Updation is successful"))

                                {
                                    startActivity(new Intent(EditAccount.this,login.class));
                                }


                                Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();
                                Log.d("Response", response);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(EditAccount.this, error.toString(), Toast.LENGTH_SHORT).show();

                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fname", fname.getText().toString());
                        params.put("imagetoupload", imagetoupload.getImageMatrix().toString());
                        params.put("lname", lname.getText().toString());
                        params.put("mailid", mailid.getText().toString());
                        params.put("password", password.getText().toString());
                        params.put("mobileno", mobileno.getText().toString());
                        params.put("selectedgender", selectedgender.toString());
                        params.put("dateofbirth", dateofbirth.getText().toString());
                        params.put("moreinfo",moreinfo.getText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        }
        else if (v==picker) {

            new DatePickerDialog(EditAccount.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== RESULT_LOAD_IMAGE && resultCode== RESULT_OK && data != null){
            Uri selectedimage = data.getData();
            imagetoupload.setImageURI(selectedimage);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateofbirth.setText(sdf.format(myCalendar.getTime()));
    }

    private void Load_user_profile() {

        StringRequest postRequest = new StringRequest(Request.Method.POST, utility.ip+ utility.load_profile,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response.toString());


                        Toast.makeText(EditAccount.this, response, Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject c = data.getJSONObject(i);

                                String image_str = c.getString("image");
                                String fname_str = c.getString("fname");
                                String lname_str = c.getString("lname");
                                String emailid_str = c.getString("email");
                                String gender_str = c.getString("gender");
                                String phone_str = c.getString("phone");
                                String dob_str = c.getString("dob");
                                String moreinfo_str = c.getString("moreinfo");
                                String password_str = c.getString("password");

                                fname.setText(fname_str);
                                lname.setText(lname_str);
                                mailid.setText(emailid_str);
                                password.setText(password_str);
                                dateofbirth.setText(dob_str);
                                moreinfo.setText(moreinfo_str);
                                mobileno.setText(phone_str);
                                if (gender_str.equals("Male")){
                                    male.setChecked(true);
                                    female.setChecked(false);
                                }
                                else {
                                    female.setChecked(true);
                                    male.setChecked(false);
                                }
                                Glide.with(EditAccount.this).load(utility.IMAGE_URL +image_str).into(imagetoupload);

                            }


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
                params.put("uid",utility.id);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                6 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }

}
