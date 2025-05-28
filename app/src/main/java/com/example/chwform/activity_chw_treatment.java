package com.example.chwform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import androidx.appcompat.app.AppCompatActivity;

public class activity_chw_treatment extends AppCompatActivity {
    Spinner spinnerTreatment, spinnerHouseholdTreatment, spinnerAntibiotics;
    LinearLayout question2, question3, question4;
    EditText member_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chw_form_page2);

        spinnerTreatment = findViewById(R.id.spinnerTreatment);
        spinnerHouseholdTreatment = findViewById(R.id.spinnerHouseholdTreatment);
        spinnerAntibiotics = findViewById(R.id.spinner_anitbiotic_usage);
        member_count = findViewById(R.id.members_count);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        Button buttonNext = findViewById(R.id.buttonNext);

        //Spinner 1
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"--------SELECT--------", "Yes", "No"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTreatment.setAdapter(adapter);

        //Spinner 2
        ArrayAdapter<String> householdAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"--------SELECT--------", "Yes", "No"});
        householdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHouseholdTreatment.setAdapter(householdAdapter);

        //spinner 3
        ArrayAdapter<String> anitibiotics_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                new String[]{"--------SELECT--------", "Yes", "No"});
        anitibiotics_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAntibiotics.setAdapter(anitibiotics_adapter);

        spinnerTreatment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("No")) {
                    question2.setVisibility(View.VISIBLE);
                } else {
                    question2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerHouseholdTreatment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("No")){
                    question4.setVisibility(View.GONE);
                    question3.setVisibility(View.VISIBLE);
                } else if (selected.equals("Yes")){
                    question3.setVisibility(View.GONE);
                    question4.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonNext.setOnClickListener(v -> {
            String selected = spinnerTreatment.getSelectedItem().toString();
            String selected2 = spinnerHouseholdTreatment.getSelectedItem().toString();
            String selected3 = spinnerAntibiotics.getSelectedItem().toString();
            if (selected.equals("Yes") || selected2.equals("Yes") || selected3.equals("No")) {
                Intent intent = new Intent(activity_chw_treatment.this, activity_chw_symptoms.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You must select 'Yes' to proceed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
