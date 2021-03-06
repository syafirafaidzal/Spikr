package com.example.spikr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Home extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button scanner, viewRecord, test;
    private EditText editText;
    private static final int ZXING_CAMERA_PERMISSION = 1;
    private Class<?> mClss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        scanner = (Button) findViewById(R.id.btn_scanner);
        viewRecord = (Button) findViewById(R.id.btn_viewRecord);
        test = (Button) findViewById(R.id.btn_test);
        editText = (EditText) findViewById(R.id.edit_test);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    editText.setError("This field cannot be blank");
                }
                else {
                    editText.setError(null);
                }

                if (!validate(s.toString())) {
                    editText.setError("NO NUMBERS ALLOW");
                }
                else {
                    editText.setError("REGEX FAILED");
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                if (editText.getText().toString().matches("[0-9]")) {
                    Toast.makeText(Home.this, "Contains "+editText.getText().toString(), Toast.LENGTH_LONG).show();
                }

                else if (editText.getText().toString().matches("[A-Z]")) {
                    editText.setError("Should not consist A-Z");
                }
            }
        });


        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Toast.makeText(Home.this, "Opening Scanner...", Toast.LENGTH_SHORT).show();
                launchScanner();
            }
        });

        viewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Toast.makeText(Home.this, "Loading records...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, RecordView.class);
                startActivity(intent);
            }
        });
    }

    private boolean validate (String text) {
        Pattern p = Pattern.compile("\\d*");
        Matcher m = p.matcher(text);
        return m.matches();
    }
    public void launchScanner() {
        launchActivity(Scanner.class);
    }

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

}