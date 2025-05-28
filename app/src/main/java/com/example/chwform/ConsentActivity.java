package com.example.chwform;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ConsentActivity extends AppCompatActivity {

    Spinner spinnerConsent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consent);

        spinnerConsent = findViewById(R.id.spinnerConsent);
        Button buttonNext = findViewById(R.id.buttonNext);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.consent_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsent.setAdapter(adapter);

        buttonNext.setOnClickListener(v -> {
            String selectedConsent = spinnerConsent.getSelectedItem().toString();

            if (selectedConsent.equals("Yes, I want to be part of this important initiative!")) {
                Intent intent = new Intent(ConsentActivity.this, activity_chw_household.class);
                startActivity(intent);
            } else if (selectedConsent.equals("Select option")) {
                Toast.makeText(this, "Please select a valid option", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "You must accept to continue", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
