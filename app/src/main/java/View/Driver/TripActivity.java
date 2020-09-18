package View.Driver;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TripActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private Button submit;
    private Spinner spinner;
    private ProgressBar progressBar;
    private String BusRegistration, result;
    private ArrayList<String> BusReg = new ArrayList<String>();
    private EditText destination, dateInput, purpose, mileageBefore, mileageAfter, passName,
            passContact, passEmail, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar);
        destination = findViewById(R.id.txtDestination);
        dateInput = findViewById(R.id.txtDate);
        purpose = findViewById(R.id.txtpurpose);
        mileageBefore = findViewById(R.id.txtmileage);
        mileageAfter = findViewById(R.id.txtMileageAfter);
        passName = findViewById(R.id.txtpassangerName);
        passContact = findViewById(R.id.txtpassContact);
        passEmail = findViewById(R.id.txtpassEmail);
        department = findViewById(R.id.txtDepa);

        submit = findViewById(R.id.btnSubmitTrip);

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
                if (validateInputs().equals(true)) {
                    showToast("Saved");
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

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(TripActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String Bus = parent.getItemAtPosition(position).toString();
        BusRegistration = Bus;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private Boolean validateInputs() {
        validateDestination();
        validateDateInput();
        validatePurpose();
        validateMileageBefore();
        validateMileageAfter();
        validatePassName();
        validatePassContact();
        validatePassEmail();
        validateDepartment();


        return validateDestination() && validateDateInput() && validatePurpose()
                && validateMileageBefore() && validateMileageAfter()
                && validatePassName() && validatePassContact()
                && validatePassEmail() && validateDepartment();
    }

    private Boolean validateDestination() {
        String text = destination.getText().toString().trim();
        if (text.isEmpty()) {
            destination.setError("input required");
            return false;
        } else {
            destination.setError(null);
            return true;
        }
    }

    private Boolean validateDateInput() {
        String text = dateInput.getText().toString().trim();
        if (text.isEmpty()) {
            dateInput.setError("input required");
            return false;
        } else {
            dateInput.setError(null);
            return true;
        }
    }

    private Boolean validatePurpose() {
        String text = purpose.getText().toString().trim();
        if (text.isEmpty()) {
            purpose.setError("input required");
            return false;
        } else {
            purpose.setError(null);
            return true;
        }
    }

    private Boolean validateMileageBefore() {
        String text = mileageBefore.getText().toString().trim();
        if (text.isEmpty()) {
            mileageBefore.setError("input required");
            return false;
        } else {
            mileageBefore.setError(null);
            return true;
        }
    }

    private Boolean validateMileageAfter() {
        String text = mileageAfter.getText().toString().trim();
        if (text.isEmpty()) {
            mileageAfter.setError("input required");
            return false;
        } else {
            mileageAfter.setError(null);
            return true;
        }
    }

    private Boolean validatePassName() {
        String text = passName.getText().toString().trim();
        if (text.isEmpty()) {
            passName.setError("input required");
            return false;
        } else {
            passName.setError(null);
            return true;
        }
    }

    private Boolean validatePassContact() {
        String text = passContact.getText().toString().trim();
        if (text.isEmpty()) {
            passContact.setError("input required");
            return false;
        } else {
            passContact.setError(null);
            return true;
        }
    }

    private Boolean validatePassEmail() {
        String text = passEmail.getText().toString().trim();
        if (text.isEmpty()) {
            passEmail.setError("input required");
            return false;
        } else {
            passEmail.setError(null);
            return true;
        }
    }

    private Boolean validateDepartment() {
        String text = department.getText().toString().trim();
        if (text.isEmpty()) {
            department.setError("input required");
            return false;
        } else {
            department.setError(null);
            return true;
        }
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
                                    spinner.setAdapter(new ArrayAdapter<String>(TripActivity.this,
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

    private void sendTrip(final String revenue_id, final String conductorName, final String busReg,
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
                        showToast("Saved");
                        submit.setVisibility(View.VISIBLE);

                        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
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