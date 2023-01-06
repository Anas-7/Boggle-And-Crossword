package com.example.mohdma.daa_final;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Crossword_activity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
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

    public void backToMain(View view) {
        finish();
    }

    EditText ed;
    TextView textView;
    int puzzle = 0; // Decides grid

    String[][] arr = {
            {"-++++++++",
                    "-++++++++",
                    "-++++++++",
                    "-----++++",
                    "-+++-++++",
                    "-+++-++++",
                    "++++-++++",
                    "------+++",
                    "++++-++++"},

            {"+-+++++++",
                    "+-+++++++",
                    "+-------+",
                    "+-+++++++",
                    "+-+++++++",
                    "+------++",
                    "+-+++-+++",
                    "+++++-+++",
                    "+++++-+++"},

            {"++++++-++",
                    "++------+",
                    "++++++-++",
                    "++++++-++",
                    "+++------",
                    "++++++-+-",
                    "++++++-+-",
                    "++++++++-",
                    "++++++++-"},

            {"+-+++++++",
                    "+-++-++++",
                    "+-------+",
                    "+-++-++++",
                    "+-++-++++",
                    "+-++-++++",
                    "++++-++++",
                    "++------+",
                    "+++++++++"},

            {"+-+++++++",
                    "+-++-++++",
                    "+-------+",
                    "+-++-++-+",
                    "+-++-++-+",
                    "+-++-++-+",
                    "++++-++-+",
                    "+--------",
                    "+++++++++",},

            {"-++++++++",
                    "-++++++++",
                    "-------++",
                    "-++++++++",
                    "-----++++",
                    "-+++-++++",
                    "++++-++++",
                    "++++-++++",
                    "++++-----",},

            {"--------+",
                    "+++-+++++",
                    "+++-+++++",
                    "+++------",
                    "+++-+++-+",
                    "+++-+++-+",
                    "+++-+++-+",
                    "+++++++-+",
                    "+++++++-+",},

            {"-++++++++",
                    "-------++",
                    "-++-+++++",
                    "-------++",
                    "-++-++++-",
                    "-++-++++-",
                    "-++------",
                    "++++++++-",
                    "+++++++++"}};

    String[] words = {"LONDON,DELHI,INDORE,ANKARA", "AGRA,NORWAY,ENGLAND,GWALIOR", "ICELAND,MEXICO,PANAMA,AKOLA", "CANADA,NIGERIA,TELAVIV,NEVADA", "LASVEGAS,NIGERIA,CANADA,TELAVIV,ALASKA", "SYDNEY,TOKYO,DETROIT,EGYPT,OMAHA", "UDAIPUR,TIRUPATI,IMPHAL,AMBALA", "ANDAMAN,MANIPUR,ICELAND,ALLEPY,YANGON,PUNE"};

    String[] hints = {"Capital of England,National Capital Territory of India,Both IIT & IIM Campus resides here,Capital of Turkey",
            "Taj Mahal,Called 'Norþweg' in Old English,Great United Kingdom,Famous for its Sun Temple",
            "Popularly called 'Land of Fire and Ice'!,Sombrero is widely popular here!,Known for its Canal,Called the 'Cotton City' of India",
            "Has a Red Maple Leaf as its Flag,Africa's Largest Oil Producer!,'White City' of Israel,Home to the Las Vegas",
            "Most Famous for its Casino!,Africa's Largest Oil Producer!,Has a Red Maple Leaf as its Flag,'White City' of Israel,Residents get paid to Live in this US City!",
            "Widely known for its Opera House,Capital City of Japan,'Motor City' of US,Pyramids and Sphinx are found here,'Warren Buffett's' Home city",
            "Hailed as the 'City of Lakes',Known for its 'Shri Venkateshwara Temple',Capital City of Manipur,Known as 'Twin City'",
            "One of the Island Territories of India,Literally Means 'Jeweled Town'!,Popularly called 'Land of Fire and Ice'!,Now Known as 'Allapuzha',Called as 'Rangoon',Nicknamed 'Oxford of the East'!"};


    static class Crossword {

        static int emptyx, emptyy;

        static char[][] crossword(String[] grid, String words) {
            //Scanner sc = new Scanner(System.in);
            char[][] arr = new char[9][9];
            int i, j;

            for (i = 0; i < 9; i++) {
                String s = grid[i];
                for (j = 0; j < 9; j++)
                    arr[i][j] = s.charAt(j);
            }

            String[] str = words.split(",");
            boolean[] done = new boolean[str.length];
            solve(arr, str, done);

            return arr;
        }

        static boolean solve(char[][] arr, String[] str, boolean[] done) {
            if (isFull(arr))
                return true;

            else {
                //part 1

                int i, j, k, l, m, flag;
                i = emptyx;
                j = emptyy;
                j--;

                while (ifSafe(j) && arr[i][j] != '+') {
                    j--;
                }

                j++;
                k = j;
                int length = 0;

                while (ifSafe(k) && arr[i][k] != '+') {
                    length++;
                    k++;
                }

                k--;

                for (l = 0; l < str.length; l++) {
                    if (str[l].length() == length && !done[l]) {
                        char[] tempstr = str[l].toCharArray();
                        char[] tempchar = new char[length];

                        for (m = 0; m < length; m++)
                            tempchar[m] = arr[i][j + m];
                        flag = 0;

                        for (m = 0; m < length; m++) {
                            if (arr[i][j + m] == tempstr[m] || arr[i][j + m] == '-') {

                            } else
                                flag = 1;
                        }

                        if (flag == 0) {

                            done[l] = true;

                            for (m = 0; m < length; m++)
                                arr[i][j + m] = tempstr[m];

                            if (solve(arr, str, done))
                                return true;

                            done[l] = false;

                            for (m = 0; m < length; m++)
                                arr[i][j + m] = tempchar[m];

                        }
                    }
                }

                //part 2

                i = emptyx;
                j = emptyy;
                i--;

                while (ifSafe(i) && arr[i][j] != '+') {
                    i--;
                }

                i++;
                k = i;
                length = 0;

                while (ifSafe(k) && arr[k][j] != '+') {
                    length++;
                    k++;
                }

                k--;

                for (l = 0; l < str.length; l++) {
                    if (str[l].length() == length && !done[l]) {

                        char[] tempstr = str[l].toCharArray();
                        char[] tempchar = new char[length];

                        for (m = 0; m < length; m++)
                            tempchar[m] = arr[i + m][j];

                        flag = 0;

                        for (m = 0; m < length; m++) {
                            if (arr[i + m][j] == tempstr[m] || arr[i + m][j] == '-') {

                            } else
                                flag = 1;
                        }

                        if (flag == 0) {
                            done[l] = true;

                            for (m = 0; m < length; m++)
                                arr[i + m][j] = tempstr[m];

                            if (solve(arr, str, done))
                                return true;

                            done[l] = false;

                            for (m = 0; m < length; m++)
                                arr[i + m][j] = tempchar[m];

                        }
                    }
                }
                return false;
            }
        }

        static boolean ifSafe(int x) {
            return x >= 0 && x < 9;
        }


        static boolean isFull(char[][] arr) {
            int i, j;
            for (i = 0; i < 9; i++) {
                for (j = 0; j < 9; j++) {
                    if (arr[i][j] == '-') {
                        emptyx = i;
                        emptyy = j;
                        return false;
                    }
                }
            }
            return true;
        }
    }


    public void NewBoard(View view) {

        puzzle++;

        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setVisibility(View.VISIBLE);
        final Button solutionButton = findViewById(R.id.solutionButton);
        solutionButton.setVisibility(View.VISIBLE);

        TableLayout crosswordLayout = findViewById(R.id.crossword_table);

        textView = findViewById(R.id.words);
        textView.setSingleLine(false);

        String[] str = hints[puzzle % 8].split(",");
        StringBuilder text = new StringBuilder();
        text.append("HINTS : \n");

        int num = 1;

        for (String s : str) {
            text.append(num).append(". ").append(s).append("\n");
            num++;
        }

        textView.setText(text.toString());

        for (int i = 0; i < 9; i++) {

            TableRow row = (TableRow) crosswordLayout.getChildAt(i);
            char[] current = arr[puzzle % 8][i].toCharArray();

            for (int j = 0; j < 9; j++) {

                ed = (EditText) row.getChildAt(j);
                ed.getText().clear();
                ed.setBackgroundResource(R.drawable.back);
                ed.setEnabled(true);
                ed.setTag(null);

                if (current[j] == '+') {
                    ed.setBackgroundColor(Color.parseColor("#4D5656"));
                    ed.setEnabled(false);
                } else
                    ed.setTextColor(Color.parseColor("#000000"));

                ed.setSelectAllOnFocus(true);
                ed.setSingleLine();
                ed.setTag(10 * i + j);
                ed.setFocusable(true);
                ed.setFocusableInTouchMode(true);
                ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1), new InputFilter.AllCaps()});

            }
        }

        for (int i = 0; i < 9; i++) {

            TableRow row = (TableRow) crosswordLayout.getChildAt(i);

            for (int j = 0; j < 9; j++) {
                ed = (EditText) row.getChildAt(j);

                ed.addTextChangedListener(new CustomTextWatcher(ed));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Submit(View view) {

        TableLayout crosswordLayout = findViewById(R.id.crossword_table);
        int flag = 0;

        char[][] solution = Crossword.crossword(arr[puzzle % 8], words[puzzle % 8]);

        for (int i = 0; i < 9; i++) {

            TableRow row = (TableRow) crosswordLayout.getChildAt(i);

            for (int j = 0; j < 9; j++) {

                if (solution[i][j] != '+') {
                    ed = (EditText) row.getChildAt(j);
                    String var = ed.getText().toString();

                    if (var.isEmpty()) {
                        flag = 2;
                        break;
                    } else if (var.charAt(0) != solution[i][j]) {
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 1 || flag == 2)
                break;

        }

        if (flag == 1)
            Toast.makeText(this, "Wrong Answer! Please Try Again.", Toast.LENGTH_SHORT).show();

        else if (flag == 2)
            Toast.makeText(this, "Please Finish the Crossword.", Toast.LENGTH_SHORT).show();

        else {
            Toast.makeText(this, "Congratulations! You have successfully solved the crossword.", Toast.LENGTH_SHORT).show();
            Button solutionButton = findViewById(R.id.solutionButton);
            solutionButton.setVisibility(View.INVISIBLE);
            for (int i = 0; i < 9; i++) {
                TableRow row = (TableRow) crosswordLayout.getChildAt(i);

                for (int j = 0; j < 9; j++) {
                    ed = (EditText) row.getChildAt(j);
                    ed.setEnabled(false);
                }
            }
        }

    }

    //If button for solution is pressed.
    public void Quitter(View view) {

        final Button submitButton = findViewById(R.id.submitButton);
        final Button solutionButton = findViewById(R.id.solutionButton);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are you a quitter who wants to see the solution?");
        builder.setPositiveButton("Yes I am", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                TableLayout crosswordLayout = findViewById(R.id.crossword_table);

                char[][] solution = Crossword.crossword(arr[puzzle % 8], words[puzzle % 8]);

                for (int i = 0; i < 9; i++) {

                    TableRow row = (TableRow) crosswordLayout.getChildAt(i);

                    for (int j = 0; j < 9; j++) {

                        if (solution[i][j] != '+') {
                            ed = (EditText) row.getChildAt(j);
                            ed.setText(String.valueOf(solution[i][j]));
                            ed.setEnabled(false);
                        }
                    }
                }

                solutionButton.setVisibility(View.INVISIBLE);
                submitButton.setVisibility(View.INVISIBLE);

            }
        });
        builder.setNegativeButton("No way!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crossword_activity);
        Intent intent = getIntent();
        TableLayout crosswordLayout = findViewById(R.id.crossword_table);

        textView = findViewById(R.id.words);
        textView.setSingleLine(false);

        Button solutionButton = findViewById(R.id.solutionButton);
        solutionButton.setVisibility(View.VISIBLE);

        String[] str = hints[0].split(",");
        StringBuilder text = new StringBuilder();
        text.append("HINTS : \n");

        int num = 1;

        for (String s : str) {
            text.append(num).append(". ").append(s).append("\n");
            num++;
        }

        textView.setText(text.toString());

        for (int i = 0; i < 9; i++) {

            TableRow row = (TableRow) crosswordLayout.getChildAt(i);
            char[] current = arr[0][i].toCharArray();

            for (int j = 0; j < 9; j++) {
                ed = (EditText) row.getChildAt(j);
                if (current[j] == '+') {
                    ed.setBackgroundColor(Color.parseColor("#4D5656"));
                    ed.setEnabled(false);
                } else
                    ed.setTextColor(Color.parseColor("#000000"));

                ed.setSelectAllOnFocus(true);
                ed.setSingleLine();
                ed.setTag(10 * i + j);
                ed.setFocusable(true);
                ed.setFocusableInTouchMode(true);
                ed.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1), new InputFilter.AllCaps()});

            }
        }

        for (int i = 0; i < 9; i++) {

            TableRow row = (TableRow) crosswordLayout.getChildAt(i);

            for (int j = 0; j < 9; j++) {
                ed = (EditText) row.getChildAt(j);

                ed.addTextChangedListener(new CustomTextWatcher(ed));
            }
        }
    }
}
