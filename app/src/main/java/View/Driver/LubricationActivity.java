package View.Driver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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

public class LubricationActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private CardView fuelView;
    private Spinner spinner;
    private Button submit;
    private String fleet = null;
    private ProgressBar progressBar;
    private EditText station, amount;
    private SharedPreferences pref;
    private ArrayList<String> BusReg = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lubrication);
        pref = getSharedPreferences("login", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        fuelView = findViewById(R.id.fuel_View);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar);
        station = findViewById(R.id.txtStation);
        amount = findViewById(R.id.txtFuelAmount);
        submit = findViewById(R.id.btn_fuel_submit);

        fuelView.setVisibility(View.GONE);

        spinner.setOnItemSelectedListener(this);
        getBusRegNum();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = randomCode();
                String stamp = getDate();
                String stationName = station.getText().toString();
                String amt = amount.getText().toString();

                if (validateInputs().equals(true)) {
                    addFuelRecord(id, getUser(), fleet, amt, stationName, stamp);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    protected String randomCode() {
        int length = 4;
        return "F" + RandomStringUtils.random(length, false, true);
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LubricationActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getUser() {
        return pref.getString("name", null) + " " +
                pref.getString("surname", null);
    }

    private void showView() {
        fuelView.setVisibility(View.VISIBLE);
    }

    private String getDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    private Boolean validateInputs() {
        validateAmount();
        validateStation();

        return validateStation() && validateAmount();
    }

    private Boolean validateStation() {
        String text = station.getText().toString().trim();
        if (!text.isEmpty()) {
            station.setError(null);
            return true;
        } else {
            station.setError("Required");
            return false;
        }
    }

    private Boolean validateAmount() {
        String text = amount.getText().toString().trim();
        if (!text.isEmpty()) {
            amount.setError(null);
            return true;
        } else {
            amount.setError("Required");
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_fuel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnAddFuelItem) {
            showView();
        }
        return super.onOptionsItemSelected(item);
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

                                    String result = object.getString("reg_no");
                                    BusReg.add(result);
                                    spinner.setAdapter(new ArrayAdapter<String>(LubricationActivity.this,
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

    private void addFuelRecord(final String id, final String driver, final String fleet,
                               final String amount, final String station, final String timestamp) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_addfuel.php";
        StringRequest stringRequest;

        submit.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String message = object.getString("success");

                    if (message.equals("1")) {
                        showToast("added successfully");
                        submit.setVisibility(View.VISIBLE);

                        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                        overridePendingTransition(0, 0);
                        finish();

                    } else if (message.equals("0")) {
                        showToast("failed");
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

                params.put("id", id);
                params.put("driver", driver);
                params.put("fleet_id", fleet);
                params.put("amount", amount);
                params.put("station", station);
                params.put("timestamp", timestamp);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        fleet = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}