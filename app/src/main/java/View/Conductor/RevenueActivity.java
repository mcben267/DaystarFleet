package View.Conductor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class RevenueActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private SharedPreferences pref;
    private Spinner spinner;
    private String BusRegistration, result;
    private ArrayList<String> BusReg = new ArrayList<String>();
    private EditText amount, mpesaRef;
    private Button submit;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_conductor);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        spinner = findViewById(R.id.spinner);
        amount = findViewById(R.id.txtRevenueAmount);
        mpesaRef = findViewById(R.id.txtRevenuMpesaRef);
        submit = findViewById(R.id.btnSendRvenue);
        progressBar = findViewById(R.id.progressBar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner.setOnItemSelectedListener(this);

        getBusRegNum();

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "R" + randomCode();
                String amt = amount.getText().toString().trim();
                String ref = mpesaRef.getText().toString().toUpperCase().trim();
                String user = getUser();
                String dateStamp = getDate();

                Log.d("Time-stamp", dateStamp + " >> " + BusRegistration);

                if (validateInputs().equals(true)) {
                    sendRevenue(id, user, BusRegistration, amt, ref, dateStamp);
                }

            }
        });

    }

    private String getUser() {
        return pref.getString("name", null) + " " +
                pref.getString("surname", null);
    }

    private String getDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    private Boolean validateInputs() {
        validateAmount();
        validateRef();

        return validateRef() && validateRef();
    }

    private Boolean validateAmount() {
        String val = amount.getEditableText().toString().trim();

        if (val.isEmpty()) {
            amount.setError("Field cannot be empty");
            return false;
        } else {
            amount.setError(null);
            return true;
        }
    }

    private Boolean validateRef() {
        String val = mpesaRef.getEditableText().toString().trim();

        if (val.isEmpty()) {
            mpesaRef.setError("Field cannot be empty");
            return false;
        } else {
            mpesaRef.setError(null);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RevenueActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected String randomCode() {
        int length = 4;
        return RandomStringUtils.random(length, false, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Bus = parent.getItemAtPosition(position).toString();
        BusRegistration = Bus;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void getBusRegNum() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_busreg.php";
        StringRequest stringRequest;

        RequestQueue queue = Volley.newRequestQueue(this);
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            if (!response.equals("error")) {
                                JSONArray array = new JSONArray(response);

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);

                                    result = object.getString("reg_no");

                                    BusReg.add(result);
                                    spinner.setAdapter(new ArrayAdapter<String>(RevenueActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, BusReg));

                                }

                            }

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            showToast(ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Error: Request Failed");
            }
        });

        queue.add(stringRequest);

    }

    private void sendRevenue(final String revenue_id, final String conductorName, final String busReg,
                             final String amount, final String mpesaRef, final String datestamp) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_addRevenue.php";
        StringRequest stringRequest;

        submit.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("message");

                    if (message.equals("success")) {
                        showToast("added successfully");
                        submit.setVisibility(View.VISIBLE);

                        startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
                        overridePendingTransition(0, 0);

                    } else {
                        showToast("Error: Failed to save new inputs ");
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    showToast("Error: Fatal error");
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        showToast("Error: Failed");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("revenue_id", revenue_id);
                params.put("conductor_name", conductorName);
                params.put("fleet_id", busReg);
                params.put("amount", amount);
                params.put("mpesa_ref", mpesaRef);
                params.put("timestamp", datestamp);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}