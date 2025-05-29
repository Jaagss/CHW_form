package com.example.chwform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class activity_chw_symptoms extends AppCompatActivity {
    Spinner spinnerSymptoms, spinnerAntibiotics, spinnerMedication;
    EditText otherSymptoms;
    LinearLayout question2, question3, question4;
    Button nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chw_symptoms);

        spinnerSymptoms = findViewById(R.id.spinner_symptoms);
        spinnerAntibiotics = findViewById(R.id.spinner_anitbiotic_usage);
        spinnerMedication = findViewById(R.id.spinner_mode_of_medication);
        otherSymptoms = findViewById(R.id.other_symptoms);
        question2 = findViewById(R.id.question2);
        question3 = findViewById(R.id.question3);
        question4 = findViewById(R.id.question4);
        nextButton = findViewById(R.id.buttonNext);

        ArrayAdapter<CharSequence> symptoms_adapter = ArrayAdapter.createFromResource(this,
                R.array.select_symptoms, android.R.layout.simple_spinner_item);
        symptoms_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSymptoms.setAdapter(symptoms_adapter);

        ArrayAdapter<CharSequence> antibiotics_usage_adapter = ArrayAdapter.createFromResource(this,
                R.array.antibiotics_usage, android.R.layout.simple_spinner_item);
        antibiotics_usage_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAntibiotics.setAdapter(antibiotics_usage_adapter);

        ArrayAdapter<CharSequence> medication_mode_adapter = ArrayAdapter.createFromResource(this,
                R.array.mode_medication, android.R.layout.simple_spinner_item);
        medication_mode_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedication.setAdapter(medication_mode_adapter);

        spinnerSymptoms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("Others")){
                    question2.setVisibility(View.VISIBLE);
                    question3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        spinnerAntibiotics.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if(selected.equals("Yes")){
                    question4.setVisibility(View.VISIBLE);
                } else{
                    question4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nextButton.setOnClickListener(v -> {
            FormData formData = FormData.getInstance();
            formData.antibiotics = spinnerAntibiotics.getSelectedItem().toString().trim();
            formData.symptoms = otherSymptoms.getText().toString().trim();

            String selected = spinnerAntibiotics.getSelectedItem().toString();
            String medicationMode = spinnerMedication.getSelectedItem().toString();


            if (selected.equals("Yes")) {
                formData.modeOfMedication = spinnerMedication.getSelectedItem().toString().trim();
                Intent intent = new Intent(activity_chw_symptoms.this, activity_chw_prescription.class);
                intent.putExtra("medication_mode", medicationMode);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You must select 'Yes' to proceed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
