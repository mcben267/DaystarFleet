package View.Reception;

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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AssignTaskActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    private Button submit;
    private Spinner priority, staff;
    private String status, staffID;
    private EditText title, message;
    private ProgressBar progressBar;
    private ArrayList<String> staffName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_task);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        staff = findViewById(R.id.spinnerStaff);
        priority = findViewById(R.id.spinnerPriority);
        title = findViewById(R.id.txt_title);
        message = findViewById(R.id.txt_message);
        progressBar = findViewById(R.id.progressBar);
        submit = findViewById(R.id.btnSubmit_task);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        priority.setAdapter(new ArrayAdapter<String>(AssignTaskActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.status)));

        priority.setOnItemSelectedListener(this);
        staff.setOnItemSelectedListener(this);
        getStaffName();

        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Select")) {
                    status = null;
                } else {
                    status = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                staffID = item.substring(0, 4);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = title.getText().toString().trim();
                String taskMessage = message.getText().toString().trim();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String systemTime = timestamp.toString().substring(0, 19);

                if (validateInputs().equals(true)) {
                    addTask(staffID, status, taskTitle, taskMessage, systemTime);
                }
            }
        });

    }

    private void goBack() {
        startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AssignTaskActivity.this,
                        Text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validateInputs() {
        validateStaffId();
        validateStatus();
        validateTitle();
        validateMessage();

        return validateStatus().equals(true)
                && validateStaffId().equals(true)
                && validateTitle().equals(true)
                && validateMessage().equals(true);
    }

    private Boolean validateStaffId() {
        if (staffID != null) {
            return true;
        } else {
            showToast("Select staff");
            return false;
        }
    }

    private Boolean validateStatus() {
        if (status != null) {
            return true;
        } else {
            showToast("Priority status required");
            return false;
        }
    }

    private Boolean validateTitle() {
        String val = title.getEditableText().toString().trim();

        if (val.isEmpty()) {
            title.setError("required");
            return false;
        } else {
            title.setError(null);
            return true;
        }
    }

    private Boolean validateMessage() {
        String val = message.getEditableText().toString().trim();

        if (val.isEmpty()) {
            message.setError("required");
            return false;
        } else {
            message.setError(null);
            return true;
        }
    }

    public void getStaffName() {
        String url = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_staffname.php";
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

                                    String id = object.getString("staff_id");
                                    String name = object.getString("surname");
                                    String staff_name = id + " " + name;

                                    staffName.add(staff_name);
                                    staff.setAdapter(new ArrayAdapter<String>(AssignTaskActivity.this,
                                            android.R.layout.simple_spinner_dropdown_item, staffName));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addTask(final String id, final String status, final String title, final String message,
                        final String timestamp) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_addtask.php";
        StringRequest stringRequest;

        submit.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        showToast("Task added");
                        startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
                        finish();
                    } else {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showToast("upload failed");
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    submit.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    showToast("Error: " + ex.toString());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        submit.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        showToast("Fatal error " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id", id);
                params.put("status", status);
                params.put("title", title);
                params.put("message", message);
                params.put("timestamp", timestamp);

                Log.d("Test", "Params >> " + params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}