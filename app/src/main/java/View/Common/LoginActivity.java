package View.Common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cliffdevops.alpha.dufleet.R;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import View.Conductor.ConductorActivity;
import View.Driver.DriverActivity;
import View.Manager.DashboardActivity;
import View.Reception.ReceptionActivity;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private AlertDialog dialog;
    private SharedPreferences pref;
    private EditText userID, userPassword;
    private ProgressBar progressBar, rest_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("login", MODE_PRIVATE);
        login = findViewById(R.id.btnlogin);
        userID = findViewById(R.id.txtUserID);
        userPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
        TextView forgotPassword = findViewById(R.id.btnForgotpassword);

        //userID.setText("#5139");
        //userPassword.setText("Test1234");

        autoLogin();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userID.getText().toString().trim();
                String password = userPassword.getText().toString().trim();

                if (validateInputs().equals(true)) {

                    if (isOnline()) {
                        Login(id, password);
                    } else {
                        showToast("Internet connection required");
                    }

                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInput();
            }
        });
    }

    public void autoLogin() {

        String user = pref.getString("user", "");

        if (pref.getBoolean("logged", false)) {

            switch (user) {
                case "Manager":
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case "Driver":
                    startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case "Conductor":
                    startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case "Reception":
                    startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
                case "Admin":
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(0, 0);
                    finish();
                    break;
            }
        }
    }

    public void showToast(final String Text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

    private void navigateToDashboard(String value) {
        switch (value) {
            case "Admin":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                overridePendingTransition(0, 0);
                finish();

                break;
            case "Driver":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DriverActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Conductor":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ConductorActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Reception":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), ReceptionActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
            case "Manager":
                pref.edit().putBoolean("logged", true).apply();
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                overridePendingTransition(0, 0);
                finish();
                break;
        }
    }

    private Boolean validateInputs() {
        validateStaffID();
        validatePassword();
        return validateStaffID().equals(true) && validatePassword().equals(true);
    }

    private Boolean validateStaffID() {
        String val = userID.getEditableText().toString().trim();

        if (val.isEmpty()) {
            userID.setError("Field cannot be empty");
            return false;
        } else {
            userID.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = userPassword.getEditableText().toString().trim();
        if (val.isEmpty()) {
            userPassword.setError("Field cannot be empty");
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }

    protected String randomCode() {
        int length = 8;
        return RandomStringUtils.random(length, true, true);
    }

    @SuppressLint("InflateParams")
    public void resetInput() {
        final String emailPattern;
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.resetpassword_dialog, null);

        final EditText mEmail = mView.findViewById(R.id.resetEmail);
        rest_progressBar = mView.findViewById(R.id.progressBar_rest);
        final Button mReset = mView.findViewById(R.id.btnReset);

        mBuilder.setTitle("Forgot Password");
        mBuilder.setView(mView);

        dialog = mBuilder.create();
        dialog.show();

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEmail.getText().toString().isEmpty()) {
                    if (mEmail.getText().toString().trim().matches(emailPattern)) {

                        String email = mEmail.getText().toString().trim();
                        String token = randomCode().toUpperCase();

                        Log.d("Test", token);

                        if (isOnline()) {
                            rest_progressBar.setVisibility(View.VISIBLE);
                            RestPassword(email, token);
                        } else {
                            showToast("Error:\tCheck Internet Connection");
                        }

                    } else {
                        showToast("Invalid email address");
                    }

                } else {
                    showToast("Please fill any empty fields...");
                }
            }
        });
    }

    private void Login(final String staff_id, final String password) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_login.php";
        StringRequest stringRequest;

        login.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        String user_id = object.getString("user_id").trim();
                        String user = object.getString("user").trim();
                        String name = object.getString("name").trim();
                        String surname = object.getString("surname").trim();
                        String email = object.getString("email").trim();
                        String mobile = object.getString("mobile").trim();

                        pref.edit().putString("user_id", user_id).apply();
                        pref.edit().putString("user", user).apply();
                        pref.edit().putString("surname", surname).apply();
                        pref.edit().putString("name", name).apply();
                        pref.edit().putString("mobile", mobile).apply();
                        pref.edit().putString("email", email).apply();

                        login.setVisibility(View.VISIBLE);
                        navigateToDashboard(user);

                    } else {
                        showToast("Invalid Credentials ");
                        login.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    login.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    showToast(ex.toString());
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        login.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        showToast("Error: Login Failed");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("staff_id", staff_id);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void RestPassword(final String email, final String token) {

        String URL = "https://myloanapp.000webhostapp.com/DUFleet/dufleet_resetpassword.php";
        StringRequest stringRequest;

        stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Test", response);

                try {
                    JSONObject object = new JSONObject(response);
                    String success = object.getString("success");

                    if (success.equals("1")) {
                        showToast("Email sent successfully...");
                    } else {
                        showToast("Password reset failed ");
                    }
                    dialog.dismiss();

                } catch (JSONException ex) {
                    ex.printStackTrace();
                    dialog.dismiss();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error: Request Failed");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("token", token);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}