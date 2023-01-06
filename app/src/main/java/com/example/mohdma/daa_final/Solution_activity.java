package com.example.mohdma.daa_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class Solution_activity extends AppCompatActivity {
    ArrayList<String> answers;
    @Override
    public void onBackPressed() {
        Intent mainactivity = new Intent(getApplicationContext(),MainActivity.class);
        mainactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(mainactivity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        System.out.println(answers);
        answers = intent.getStringArrayListExtra("answers");
        System.out.println(answers.size());
        System.out.println(answers);
        TableLayout tbl = findViewById(R.id.solution_table);
        TableRow[] row = new TableRow[answers.size()/4+1];
        TableRow.LayoutParams  params1=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

        int index_count=0;
        for(int ctr = 0;ctr<answers.size()/4+1;ctr++){
            row[ctr] = new TableRow(this);
            for(int n=index_count;n<index_count+4;n++){
                try{
                    TextView txt1=new TextView(this);
                    //setting the text
                    txt1.setText(answers.get(n));
                    txt1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    txt1.setBackgroundResource(R.drawable.solution_text_view_border);
                    txt1.setLayoutParams(params1);
                    //the textviews have to be added to the row created
                    row[ctr].addView(txt1);
                    row[ctr].setLayoutParams(params2);
                }catch (Exception e){
                    break;
                }
            }
            index_count+=4;
            tbl.addView(row[ctr]);
        }
    }
}
