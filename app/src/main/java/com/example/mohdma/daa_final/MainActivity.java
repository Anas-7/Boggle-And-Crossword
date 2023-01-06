package com.example.mohdma.daa_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public void BoggleClick(View view){
        Intent boggle = new Intent(getApplicationContext(),Boggle_activity.class);
        boggle.putExtra("count",0);
        //boggle.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(boggle);
    }
    public void CrosswordClick(View view){
        Intent crossword = new Intent(getApplicationContext(),Crossword_activity.class);
        startActivity(crossword);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onBackPressed() {
        finish();
    }
}
