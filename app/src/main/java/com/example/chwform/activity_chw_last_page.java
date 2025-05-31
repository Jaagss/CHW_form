package com.example.chwform;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class activity_chw_last_page extends AppCompatActivity {
    Spinner spinner_importance, spinner_missuse, spinner_knowledge, spinner_responsible_use;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_chw_last_page);

        spinner_importance = findViewById(R.id.spinner_importance);
        spinner_missuse = findViewById(R.id.spinner_missuse);
        spinner_knowledge = findViewById(R.id.spinner_knowledge);
        spinner_responsible_use = findViewById(R.id.spinner_responsible_use);

        buttonNext = findViewById(R.id.buttonNext);

        ArrayAdapter<CharSequence> option = ArrayAdapter.createFromResource(this,
                R.array.antibiotics_usage, android.R.layout.simple_spinner_item);
        option.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_importance.setAdapter(option);

        ArrayAdapter<CharSequence> missuse = ArrayAdapter.createFromResource(this,
                R.array.antibiotics_usage, android.R.layout.simple_spinner_item);
        missuse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_missuse.setAdapter(missuse);

        ArrayAdapter<CharSequence> know = ArrayAdapter.createFromResource(this,
                R.array.antibiotics_usage, android.R.layout.simple_spinner_item);
        know.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_knowledge.setAdapter(know);

        ArrayAdapter<CharSequence> use = ArrayAdapter.createFromResource(this,
                R.array.booleans, android.R.layout.simple_spinner_item);
        use.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_responsible_use.setAdapter(use);

        buttonNext.setOnClickListener(v -> {
            FormData formData = FormData.getInstance();

            formData.doctor = spinner_importance.getSelectedItem().toString();
            formData.antibioticMisuse = spinner_missuse.getSelectedItem().toString();
            formData.antibioticResistance = spinner_knowledge.getSelectedItem().toString();
            formData.wantInfo = spinner_responsible_use.getSelectedItem().toString().equalsIgnoreCase("Yes");
            buttonNext.setEnabled(false);
            sendFormData(formData);
        });
    }

    public void sendFormData(FormData data) {

        Log.d("FormUpload", "Preparing to send form data...");
        Log.d("FormUpload", "householdId: " + data.householdId);
        Log.d("FormUpload", "visitDate: " + data.visitDate);
        Log.d("FormUpload", "village: " + data.village);
        Log.d("FormUpload", "state: " + data.state);
        Log.d("FormUpload", "district: " + data.district);
        Log.d("FormUpload", "householdSize: " + data.householdSize);
        Log.d("FormUpload", "symptoms: " + data.symptoms);
        Log.d("FormUpload", "modeOfMedication: " + data.modeOfMedication);
        Log.d("FormUpload", "antibiotics: " + data.antibiotics);
        Log.d("FormUpload", "name: " + data.name);
        Log.d("FormUpload", "age: " + data.age);
        Log.d("FormUpload", "gender: " + data.gender);
        Log.d("FormUpload", "occupation: " + data.occupation);
        Log.d("FormUpload", "obtainedFrom: " + data.obtainedFrom);
        Log.d("FormUpload", "dateOfAntibioticUsed: " + data.dateOfAntibioticUsed);
        Log.d("FormUpload", "dosage: " + data.dosage);
        Log.d("FormUpload", "unit: " + data.unit);
        Log.d("FormUpload", "duration: " + data.duration);
        Log.d("FormUpload", "fullCourseTaken: " + data.fullCourseTaken);
        Log.d("FormUpload", "doctor: " + data.doctor);
        Log.d("FormUpload", "antibioticMisuse: " + data.antibioticMisuse);
        Log.d("FormUpload", "antibioticResistance: " + data.antibioticResistance);
        Log.d("FormUpload", "wantInfo: " + data.wantInfo);
        Log.d("FormUpload", "image included: " + (data.antibioticImage != null));

        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        // Add text form fields
        multipartBuilder.addFormDataPart("householdid", data.householdId);
        multipartBuilder.addFormDataPart("date_of_visit", data.visitDate); // Format: YYYY-MM-DD
        multipartBuilder.addFormDataPart("village", data.village);
        multipartBuilder.addFormDataPart("state", data.state);
        multipartBuilder.addFormDataPart("district", data.district);
        multipartBuilder.addFormDataPart("household_size", data.householdSize);
        multipartBuilder.addFormDataPart("symptoms", data.symptoms);
        multipartBuilder.addFormDataPart("mode_of_medication", data.modeOfMedication);
        multipartBuilder.addFormDataPart("antibiotics", data.antibiotics);
        multipartBuilder.addFormDataPart("patient_id", "0"); // Leave empty if auto-generated
        multipartBuilder.addFormDataPart("name", data.name);
        multipartBuilder.addFormDataPart("age", data.age);
        multipartBuilder.addFormDataPart("gender", data.gender);
        multipartBuilder.addFormDataPart("occupation", data.occupation);
        multipartBuilder.addFormDataPart("obtained_from", data.obtainedFrom);
        multipartBuilder.addFormDataPart("date_of_antibiotic_used", data.dateOfAntibioticUsed); // Format: YYYY-MM-DD
        multipartBuilder.addFormDataPart("dosage", data.dosage);
        multipartBuilder.addFormDataPart("unit", data.unit);
        multipartBuilder.addFormDataPart("duration", data.duration);
        multipartBuilder.addFormDataPart("full_course_taken", String.valueOf(data.fullCourseTaken));
        multipartBuilder.addFormDataPart("doctor", data.doctor);
        multipartBuilder.addFormDataPart("antibiotic_misuse", data.antibioticMisuse);
        multipartBuilder.addFormDataPart("antibiotic_resistance", data.antibioticResistance);
        multipartBuilder.addFormDataPart("want_info", String.valueOf(data.wantInfo));

        // Add image if present
        if (data.antibioticImage != null && data.antibioticImage.length > 0) {
            RequestBody imageBody = RequestBody.create(data.antibioticImage, MediaType.parse(data.imageMimeType));
            multipartBuilder.addFormDataPart("antibiotic_image", "image.jpg", imageBody);
        }

        RequestBody requestBody = multipartBuilder.build();

        Request request = new Request.Builder()
                .url("http://103.25.231.28:8080/community/CW123457/upload")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("FormUpload", "Upload failed: " + e.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(activity_chw_last_page.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseBody = response.body().string();
                Log.d("FormUpload", "Response code: " + response.code());
                Log.d("FormUpload", "Response body: " + responseBody);

                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(activity_chw_last_page.this, "Upload successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity_chw_last_page.this, "Upload failed: " + responseBody, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}

