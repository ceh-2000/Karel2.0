package com.example.clareheinbaugh.volley;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    ImageButton mHomeButton;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);


        mText = findViewById(R.id.inst);
        mText.setMovementMethod(new ScrollingMovementMethod());


        mHomeButton = findViewById(R.id.home);
        mHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(EndScreen.this, Home.class));
            }
        });


    }
}
