package com.example.mohdma.daa_final;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Boggle_activity extends AppCompatActivity {
    //Declare the grids.
    char board[][][] = {
            {
                    {'G', 'I', 'Z'},
                    {'U', 'E', 'K'},
                    {'Q', 'S', 'E'}
            },
            {
                    {'I', 'L', 'A'},
                    {'P', 'A', 'G'},
                    {'E', 'T', 'O'}
            },
            {
                    {'L', 'P', 'S'},
                    {'E', 'U', 'T'},
                    {'O', 'R', 'N'}
            },
            {
                    {'H', 'H', 'R'},
                    {'U', 'N', 'E'},
                    {'N', 'T', 'A'}
            },
            {
                    {'A', 'K', 'E'},
                    {'M', 'I', 'N'},
                    {'K', 'S', 'T'}
            },
            {
                    {'T', 'T', 'C'},
                    {'I', 'P', 'S'},
                    {'N', 'I', 'A'}
            },
            {
                    {'A', 'P', 'O'},
                    {'T', 'E', 'R'},
                    {'E', 'N', 'I'}
            },
            {
                    {'A', 'R', 'T'},
                    {'L', 'A', 'N'},
                    {'L', 'O', 'S'}
            },
            {
                    {'F', 'O', 'S'},
                    {'L', 'A', 'R'},
                    {'D', 'E', 'T'}
            },
            {
                    {'F', 'O', 'B'},
                    {'O', 'A', 'E'},
                    {'K', 'S', 'C'}
            }
    };
    //Declare it here so that the methods of text field can access it
    ArrayList<String> list_of_possible_words;
    //Words which have already been found by player/
    HashSet<String> found_answers;
    //To check if first alphabet is already chosen or not.
    int first_alphabet_flag = 0;
    //The word created after finger is released.
    String word_created_by_drag = "";
    //The alphabets
    TextView[] array_alphabets;
    //The dimensions of the view of alphabets
    Rect[] size_alphabets;
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent mainactivity = new Intent(getApplicationContext(),MainActivity.class);
                mainactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainactivity);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //If button for solution is pressed.
    public void quitter(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you a quitter who wants to see the solution?");
        builder.setPositiveButton("Yes I am", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent solutionactivity = new Intent(getApplicationContext(),Solution_activity.class);
                solutionactivity.putStringArrayListExtra("answers",list_of_possible_words);
                //To clear previous instances.
                solutionactivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //To clear the call stack.
                //solutionactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(solutionactivity);
            }
        });
        builder.setNegativeButton("No way!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void backToMain(View view) {
        finish();
    }

    //When all fields have been completed.
    public void GameOver(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("You have won the game!");
        builder.setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent mainactivity = new Intent(getApplicationContext(),MainActivity.class);
                mainactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(mainactivity);
                finish();
            }
        });
        builder.setNegativeButton("View all words", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                Intent solution = new Intent(getApplicationContext(),Solution_activity.class);
                solution.putStringArrayListExtra("answers",list_of_possible_words);
                dialog.cancel();
                //To clear previous instances.
                solution.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(solution);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Count to keep track whether all the fields are filled correctly.
    static int correct_answer_count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_boggle_activity);
        //Setting count of answers as 0.
        Intent intent = getIntent();
        correct_answer_count = intent.getIntExtra("count",0);
        found_answers = new HashSet<String>(3);
        list_of_possible_words=new ArrayList<>();

        //Choosing the board.
        Random random = new Random();
        int grid = random.nextInt(10);
        System.out.println("Board number = "+grid+" and first char = "+board[grid][0][0]);

        final char[][] temp = board[grid];
        //Print the grid
        int char_count=1;
        for(int i = 0;i < 3; i++){
            for(int j=0;j<3;j++){
                String charblock = "char_" + char_count;
                TextView tview = findViewById(getResources().getIdentifier(charblock,"id",getPackageName()));
                String word = ""+temp[i][j];
                tview.setText(word);
                char_count+=1;
            }
        }
        //In case the serialised file isnt present. This loads the words from dictionary and serialises it.
        FileInputStream serialised_file=null;
        try{
            serialised_file=getApplicationContext().openFileInput("myfile.bin");
            System.out.println("Successful = "+serialised_file);
        }catch(Exception e){
            System.out.println("Making the serialised object;");
            try
            {
                InputStream file = getResources().getAssets().open("words.txt");  //creates a new file instance
                System.out.println("File loaded.");
                BufferedReader br=new BufferedReader(new InputStreamReader(file));  //creates a buffering character input stream
                ArrayList<String> list=new ArrayList<String>();
                String line;
                while((line=br.readLine())!=null)
                {
                    line=line.trim();
                    line=line.toUpperCase();
                    if(line.length()>=3 && line.length()<=9)
                    {
                        list.add(line);
                    }
                }
                System.out.println("File read successfully");
                br.close();
                file.close();
                try{
                    FileOutputStream fos= openFileOutput("myfile.bin",Context.MODE_PRIVATE);
                    ObjectOutputStream oos= new ObjectOutputStream(fos);
                    oos.writeObject(list);
                    oos.close();
                    fos.close();
                    System.out.println("Write successful.");
                }
                catch(IOException ioe){
                    System.out.println("Acessing oos failed.");
                    ioe.printStackTrace();
                }
                serialised_file=openFileInput("myfile.bin");
                System.out.println(serialised_file);
            }
            catch(IOException eo)
            {
                System.out.println("Accessing words_alpha failed.");
            }

        }
        //Initialising the boggle object and passing the grid to its search method.
        final Boggle boggle_object = new Boggle(serialised_file);
        //Pass the board and get list of words in return.
        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                list_of_possible_words = boggle_object.search(temp);
            }
        });
        try{
            t1.start();
            t1.join();
        }catch (Exception e){}
        long end = System.currentTimeMillis();
        System.out.println("Time taken is = "+(end-start));
        if(list_of_possible_words==null)
            System.out.println("Error");
        else
            System.out.println(list_of_possible_words);

        for(int i=0;i<3;i++){
            TextView textView = findViewById(getResources().getIdentifier("answer_found_"+(i+1),"id",getPackageName()));
            textView.setVisibility(View.INVISIBLE);
        }

        array_alphabets = new TextView[9];
        size_alphabets = new Rect[9];
        for(int i=0;i<9;i++){
            array_alphabets[i] = findViewById(getResources().getIdentifier("char_"+(i+1),"id",getPackageName()));
            array_alphabets[i].setFocusable(true);
        }
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);
        //For touch and drag.
        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                set_size();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                for(int i=0;i<9;i++){
                    int finger_removal_flag=0;
                    if(size_alphabets[i].contains(x,y)){
                        if(array_alphabets[i].isFocusable() == true) {
                            word_created_by_drag += array_alphabets[i].getText();
                            array_alphabets[i].setFocusable(false);
                            //It means user has released finger.
                            array_alphabets[i].setBackgroundResource(R.drawable.alphabet_selected);
                            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                                finger_removal_flag=1;
                                reset_color();
                                if (list_of_possible_words.contains(word_created_by_drag.toUpperCase())) {
                                    if(!found_answers.isEmpty() && found_answers.contains(word_created_by_drag.toUpperCase())){
                                        Toast.makeText(getApplicationContext(), "Word already found by you ¯\\_(ツ)_/¯", Toast.LENGTH_LONG).show();
                                        word_created_by_drag="";
                                        break;
                                    }
                                    else {
                                        found_answers.add(word_created_by_drag.toUpperCase());
                                        Toast.makeText(getApplicationContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
                                        correct_answer_count += 1;
                                        TextView textView = findViewById(getResources().getIdentifier("answer_found_"+(correct_answer_count),"id",getPackageName()));
                                        textView.setText(word_created_by_drag);
                                        textView.setVisibility(View.VISIBLE);
                                        word_created_by_drag="";
                                        if(correct_answer_count==3){
                                            GameOver();
                                        }
                                        break;
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please try another word :)", Toast.LENGTH_LONG).show();
                                }
                                word_created_by_drag="";
                            }
                        }
                    }
                    //It means user has released finger.
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP && finger_removal_flag==0){
                        reset_color();
                        if (list_of_possible_words.contains(word_created_by_drag.toUpperCase())) {
                            if(!found_answers.isEmpty() && found_answers.contains(word_created_by_drag.toUpperCase())){
                                Toast.makeText(getApplicationContext(), "Word already found by you ¯\\_(ツ)_/¯", Toast.LENGTH_LONG).show();
                                word_created_by_drag="";
                                break;
                            }
                            else {
                                found_answers.add(word_created_by_drag.toUpperCase());
                                Toast.makeText(getApplicationContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
                                correct_answer_count += 1;
                                TextView textView = findViewById(getResources().getIdentifier("answer_found_"+(correct_answer_count),"id",getPackageName()));
                                textView.setText(word_created_by_drag);
                                textView.setVisibility(View.VISIBLE);
                                word_created_by_drag="";
                                if(correct_answer_count==3){
                                    GameOver();
                                }
                                break;
                            }
                        } else {
                            if(word_created_by_drag!="")
                                Toast.makeText(getApplicationContext(), "Please try another word :)", Toast.LENGTH_LONG).show();
                        }
                        word_created_by_drag="";
                    }
                    if(correct_answer_count==3){
                        GameOver();
                        break;
                    }
                }
                return true;
            }
        });
    }

    public void reset_color(){
        for(int j=0;j<9;j++){
            array_alphabets[j].setFocusable(true);
            array_alphabets[j].setBackgroundResource(R.drawable.text_view_border);
        }
    }

    public void set_size() {
        for (int i = 0; i < 9; i++) {
            size_alphabets[i] = new Rect();
            array_alphabets[i].getGlobalVisibleRect(size_alphabets[i]);
        }
    }
}
