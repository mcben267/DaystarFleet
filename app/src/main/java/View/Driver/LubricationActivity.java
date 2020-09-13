package View.Driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.cliffdevops.alpha.dufleet.R;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;

public class LubricationActivity extends AppCompatActivity {
    private CardView fuelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lubrication);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView back = findViewById(R.id.btnBack);
        fuelView = findViewById(R.id.fuel_View);

        fuelView.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(this.getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
        startActivity(new Intent(getApplicationContext(), DriverActivity.class));
        overridePendingTransition(0, 0);
        finish();
    }

    protected String randomCode() {
        int length = 6;
        return RandomStringUtils.random(length, true, true);
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

    private void showView() {
        fuelView.setVisibility(View.VISIBLE);
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
}