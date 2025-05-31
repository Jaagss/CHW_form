package com.example.chwform;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class activity_chw_household extends AppCompatActivity {

    EditText editHouseholdId, editVisitDate, editVillage, editHouseholdSize;
    Spinner spinnerState, spinnerDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chw_household);

        editHouseholdId = findViewById(R.id.editHouseholdId);
        editVisitDate = findViewById(R.id.editVisitDate);
        editVillage = findViewById(R.id.editVillage);
        editHouseholdSize = findViewById(R.id.editHouseholdSize);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        ImageView calendarIcon = findViewById(R.id.calendarIcon);
        Button buttonNext = findViewById(R.id.buttonNext);

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_options, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerState.setAdapter(stateAdapter);

        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_options, android.R.layout.simple_spinner_item);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrict.setAdapter(districtAdapter);

        calendarIcon.setOnClickListener(v -> showDatePicker());
        editVisitDate.setOnClickListener(v -> showDatePicker());

        buttonNext.setOnClickListener(v -> {
            if (validateInputs()) {
                FormData formData = FormData.getInstance();
                formData.householdId = editHouseholdId.getText().toString().trim();
                formData.visitDate = editVisitDate.getText().toString().trim();
                formData.village = editVillage.getText().toString().trim();
                formData.state = spinnerState.getSelectedItem().toString();
                formData.district = spinnerDistrict.getSelectedItem().toString();
                formData.householdSize = editHouseholdSize.getText().toString().trim();
                Intent intent = new Intent(activity_chw_household.this, activity_chw_treatment.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(activity_chw_household.this, (DatePicker view, int year1, int month1, int dayOfMonth) -> {
            String formatted = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editVisitDate.setText(formatted);
        }, year, month, day).show();
    }

    private boolean validateInputs() {
        String householdId = editHouseholdId.getText().toString().trim();
        String visitDate = editVisitDate.getText().toString().trim();
        String village = editVillage.getText().toString().trim();
        String state = spinnerState.getSelectedItem().toString();
        String district = spinnerDistrict.getSelectedItem().toString();
        String householdSize = editHouseholdSize.getText().toString().trim();

        if (householdId.isEmpty()) {
            editHouseholdId.setError("Household ID is required");
            return false;
        }

        if (visitDate.isEmpty()) {
            editVisitDate.setError("Date of visit is required");
            return false;
        }

        if (village.isEmpty()) {
            editVillage.setError("Village/Town is required");
            return false;
        }

        if (state.equals("--------SELECT--------")) {
            Toast.makeText(this, "Please select a state", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (district.equals("--------SELECT--------")) {
            Toast.makeText(this, "Please select a district", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (householdSize.isEmpty()) {
            editHouseholdSize.setError("Household size is required");
            return false;
        }

        return true;
    }
}
