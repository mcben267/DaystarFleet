package View.Manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ReportsActivity extends AppCompatActivity {

    private TextView revenue, fuel, fleet, staff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        revenue = findViewById(R.id.txt_report_revenue);
        fuel = findViewById(R.id.txt_report_oils);
        fleet = findViewById(R.id.txt_report_fleet);
        staff = findViewById(R.id.txt_report_staff);

        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getRevenue();
        getFuel();
        getStaff();
        getFleet();

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ReportsActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btnRefresh) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRevenue() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_get_revenue.php";
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        revenue.setText("Ksh " + object.getString("amount"));
                    } else if (success.equals("0")) {
                        showToast("error");
                    } else {
                        showToast("Request Failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    showToast("Error: " + ex.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Fatal Error: Request Failed");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getFuel() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_get_fuel.php";
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        fuel.setText("Ksh " + object.getString("amount"));
                    } else if (success.equals("0")) {
                        showToast("error");
                    } else {
                        showToast("Request Failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    showToast("Error: " + ex.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Fatal Error: Request Failed");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getStaff() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_get_staff.php";
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        staff.setText(object.getString("amount"));
                    } else if (success.equals("0")) {
                        showToast("error");
                    } else {
                        showToast("Request Failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    showToast("Error: " + ex.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Fatal Error: Request Failed");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getFleet() {
        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_get_fleet.php";
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        fleet.setText(object.getString("amount"));
                    } else if (success.equals("0")) {
                        showToast("error");
                    } else {
                        showToast("Request Failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    showToast("Error: " + ex.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Fatal Error: Request Failed");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}