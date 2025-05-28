package com.example.chwform;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class activity_chw_prescription extends AppCompatActivity {

    private static final int REQUEST_IMAGE_UPLOAD = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri photoURI;
    LinearLayout layoutPrescriptionUpload, layoutAntibioticType;
    EditText editName, editAge, editAntibioticDate, editDosage, editDuration, editOccupation, editSource, editPrescriptionFile, editAntibioticType;
    Spinner spinnerGender, spinnerAntibiotic, spinnerUnit, spinnerCourseComplete;
    ImageView calendarIcon, uploadIcon, iconPrescriptionUpload;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chw_prescription);

        // Initialize text fields
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        editAntibioticDate = findViewById(R.id.editAntibioticDate);
        editDosage = findViewById(R.id.editDosage);
        editDuration = findViewById(R.id.editDuration);
        editOccupation = findViewById(R.id.editOccupation); // text box now
        editSource = findViewById(R.id.editSource);         // where antibiotic was obtained

        // Initialize spinners
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerAntibiotic = findViewById(R.id.spinnerAntibiotic);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        spinnerCourseComplete = findViewById(R.id.spinnerCourseComplete);

        // Initialize icons and button
        calendarIcon = findViewById(R.id.calendarIcon);
        uploadIcon = findViewById(R.id.iconUpload);
        buttonNext = findViewById(R.id.buttonNext);

        layoutPrescriptionUpload = findViewById(R.id.layoutPrescriptionUpload);
        layoutAntibioticType = findViewById(R.id.layoutAntibioticType);
        editPrescriptionFile = findViewById(R.id.editPrescriptionFile);
        editAntibioticType = findViewById(R.id.editAntibioticType);
        iconPrescriptionUpload = findViewById(R.id.iconPrescriptionUpload);


        // Populate spinners from strings.xml
        setupSpinner(spinnerGender, R.array.gender_options);
        setupSpinner(spinnerAntibiotic, R.array.antibiotics_list);
        setupSpinner(spinnerUnit, R.array.dosage_units);
        setupSpinner(spinnerCourseComplete, R.array.antibiotics_usage);

        // Date picker setup
        calendarIcon.setOnClickListener(v -> showDatePicker());
        editAntibioticDate.setOnClickListener(v -> showDatePicker());

        // Upload icon click
        uploadIcon.setOnClickListener(v -> openImagePicker());
        iconPrescriptionUpload.setOnClickListener(v -> openImagePicker());
        editPrescriptionFile.setOnClickListener(v -> openImagePicker());


        String medicationMode = getIntent().getStringExtra("medication_mode");
        if (medicationMode != null && medicationMode.equalsIgnoreCase("Doctor Consultation")) {
            layoutPrescriptionUpload.setVisibility(View.VISIBLE);
            layoutAntibioticType.setVisibility(View.VISIBLE);
        } else {
            layoutPrescriptionUpload.setVisibility(View.GONE);
            layoutAntibioticType.setVisibility(View.GONE);
        }


        // Next button
        buttonNext.setOnClickListener(v -> {
            if (isFormValid()) {
                // Go to next activity
                Intent intent = new Intent(this, activity_chw_last_page.class); // Replace with your activity
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (DatePicker view, int year1, int month1, int dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            editAntibioticDate.setText(date);
        }, year, month, day).show();
    }

    private void openImagePicker() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooser = Intent.createChooser(galleryIntent, "Select or Capture Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        startActivityForResult(chooser, REQUEST_IMAGE_UPLOAD);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK) {
            Uri selectedImage = null;

            // Check if the intent has data from gallery
            if (data != null && data.getData() != null) {
                selectedImage = data.getData(); // Image picked from gallery
            } else if (data != null && data.getExtras() != null) {
                Bundle extras = data.getExtras();
                if (extras.get("data") != null) {
                    // You can retrieve a Bitmap from the camera
                    // Bitmap imageBitmap = (Bitmap) extras.get("data");
                    Toast.makeText(this, "Photo captured!", Toast.LENGTH_SHORT).show();
                    // Save bitmap or convert to URI
                }
            }

            if (selectedImage != null) {
                editPrescriptionFile.setText("Image selected");
            }
        }
    }

    private boolean isFormValid() {
        return !editName.getText().toString().trim().isEmpty()
                && !editAge.getText().toString().trim().isEmpty()
                && !editOccupation.getText().toString().trim().isEmpty()
                && !editSource.getText().toString().trim().isEmpty()
                && !editAntibioticDate.getText().toString().trim().isEmpty()
                && !editDosage.getText().toString().trim().isEmpty()
                && !editDuration.getText().toString().trim().isEmpty();
    }
}
