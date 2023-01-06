package com.example.mohdma.daa_final;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CustomTextWatcher implements TextWatcher
{

    private EditText ed;
    private boolean isGoingRight = true;

    CustomTextWatcher(EditText editText)
    {
        ed = editText;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (ed != null)
        {

            ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus)
                        isGoingRight = true;
                }
            });

            if (ed.getText().toString().length() == 1)
            {

                View next = ed.focusSearch(View.FOCUS_RIGHT);
                View down = ed.focusSearch(View.FOCUS_DOWN);

                if (isGoingRight)
                {
                    if (next != null && next instanceof EditText && ((Integer) next.getTag() - (Integer) ed.getTag() == 1))
                        next.requestFocus();

//                    else if(down != null && down instanceof EditText)
//                    {
//                        down.requestFocus();
//                        isGoingRight = false;
//                    }
                    else
                        isGoingRight = false;
                }

//                else
//                {
//                    if (down != null && down instanceof EditText && ((Integer) down.getTag() - (Integer) ed.getTag() == 10))
//                        down.requestFocus();
//                }
            }
        }
    }
}
