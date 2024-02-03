package com.example.ricediseasedetection;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class solution extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solution);

        Button back= findViewById(R.id.backBtnSolution);
        TextView solution= findViewById(R.id.txtSolution);
        solution.setMovementMethod(new ScrollingMovementMethod());

        Intent in = getIntent();
        String prediction = in.getStringExtra("prediction");

        if(prediction.equals("Brown Spot")){
            solution.setText("সমাধান:\nরোগ আক্রমণের যেকোন পর্যায়ে নিচে উল্লেখিত যেকোন একটি বালাইনাশক শেষ বিকালে ৫-৭ দিন অন্তর দুবার স্প্রে করুন।\nবালাইনাশকের নামঃ ফলিকুর ২৫ ইসি\nগ্রুপঃ টেবুকোনাজল\nপ্রয়োগ মাত্রাঃ ১ মিলি/প্রতি লিটার পানি\nকোম্পানীঃ বেয়ার ক্রপসায়েন্স লিমিটেড\nবালাইনাশকের নামঃ ডিফেন্ডার ২৫ ইসি \nগ্রুপঃ টেবুকোনাজল \nপ্রয়োগ মাত্রাঃ ০.৫ মিলি/প্রতি লিটার পানি\nকোম্পানীঃ হেকেম (বাংলাদেশ) লিমিটেড\nবালাইনাশকের নামঃ রোভরাল ৫০ ডব্লিউপি \nগ্রুপঃ ইপ্রোডিয়ন\nপ্রয়োগ মাত্রাঃ বেয়ার ক্রপসায়েন্স লিমিটেড \nকোম্পানীঃ বেয়ার ক্রপসায়েন্স লিমিটেড\nবালাইনাশকের নামঃ কনটাফ ৫ ইসি\nগ্রুপঃ হেক্সাকোনাজল\nপ্রয়োগ মাত্রাঃ ১ মিঃলিঃ/প্রতি লিটার পানি\nকোম্পানীঃ অটো ক্রপ কেয়ার লিমিটেড\n");
        }
        else if(prediction.equals("Leaf Blast")){
            solution.setText("সমাধান:\nরোগ আক্রমণের যেকোন পর্যায়ে নিচে উল্লেখিত যেকোন একটি ছত্রাকনাশক শেষ বিকালে ৫-৭ দিন অন্তর দুবার স্প্রে করুন।\nবালাইনাশকের নামঃ নাটিভো ৭৫ ডব্লিউপি\\nগ্রুপঃ টেবুকোনাজল (৫০%)+ ট্রাইফ্লোক্সিস্ট্রোবিন(২৫%\nপ্রয়োগ মাত্রাঃ ৪০ গ্রাম/বিঘা\nকোম্পানীঃ বায়ার ক্রপসায়েন্স লিমিটেড\nবালাইনাশকের নামঃ ফিলিয়া ৫২৫ এসই\nগ্রুপঃ প্রোপিকোনাজল (১২.৫%) + ট্রাইসাক্লাজোল (৪০%)\nপ্রয়োগ মাত্রাঃ ১৩৩ মিঃলিঃ/বিঘা\nকোম্পানীঃ সিনজেনটা বাংলাদেশ লিমিটেড");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(solution.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });



    }
}
