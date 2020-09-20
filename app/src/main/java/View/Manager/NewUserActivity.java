package View.Manager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cliffdevops.alpha.dufleet.R;

import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class NewUserActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {

    String User;
    private Button submit;
    private EditText firstname, lastname, tel, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        Spinner category = findViewById(R.id.spinner);
        firstname = findViewById(R.id.txt_firstname);
        lastname = findViewById(R.id.txt_lastname);
        email = findViewById(R.id.txt_email);
        tel = findViewById(R.id.txt_mobile);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        category.setAdapter(new ArrayAdapter<>(NewUserActivity.this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.users)));

        category.setOnItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = randomCode();
                String stamp = getDate();
                String name = firstname.getText().toString().trim();
                String surname = lastname.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String mobile = tel.getText().toString().trim();

                if (validateInputs().equals(true)) {

                }
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

    protected String randomCode() {
        int length = 4;
        return RandomStringUtils.random(length, false, true);
    }

    private String getDate() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }

    private Boolean validateInputs() {
        validateName();
        validateSurname();
        validateEmail();
        validateMobile();

        return validateName() && validateSurname()
                && validateEmail() && validateMobile();
    }

    private Boolean validateName() {
        String text = firstname.getText().toString().trim();
        if (!text.isEmpty()) {
            firstname.setError(null);
            return true;
        } else {
            firstname.setError("Required");
            return false;
        }
    }

    private Boolean validateSurname() {
        String text = lastname.getText().toString().trim();
        if (!text.isEmpty()) {
            lastname.setError(null);
            return true;
        } else {
            lastname.setError("Required");
            return false;
        }
    }

    private Boolean validateEmail() {
        String text = email.getText().toString().trim();
        if (!text.isEmpty()) {
            email.setError(null);
            return true;
        } else {
            email.setError("Required");
            return false;
        }
    }

    private Boolean validateMobile() {
        String text = tel.getText().toString().trim();
        if (!text.isEmpty()) {
            tel.setError(null);
            return true;
        } else {
            tel.setError("Required");
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        User = item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}