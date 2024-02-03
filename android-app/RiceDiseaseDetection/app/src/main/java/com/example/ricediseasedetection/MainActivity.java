package com.example.ricediseasedetection;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.titleTV);
        //title.setTypeface(tf);
        TextView welcome = findViewById(R.id.welcomeTV);
        //welcome.setTypeface(tf);
        TextView slogan = findViewById(R.id.sloganTV);
        //slogan.setTypeface(tf);

        Button process = findViewById(R.id.processBtn);
        Button predict = findViewById(R.id.predictBtn);

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,process.class);
                startActivity(i);
                finish();
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, predict.class);
                //i.putExtra("image","xx");
                startActivity(i);
                finish();
            }
        });

    }//onCreate end
}

