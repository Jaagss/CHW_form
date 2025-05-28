package com.example.chwform;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

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
                R.array.antibiotics_usage, android.R.layout.simple_spinner_item);
        use.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_responsible_use.setAdapter(use);
    }
}
