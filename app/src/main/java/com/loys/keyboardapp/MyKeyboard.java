package com.loys.keyboardapp;

/**
 * Created by loy on 12/1/17.
 */

import android.inputmethodservice.InputMethodService;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class MyKeyboard extends InputMethodService {

    private MyView myView;

    @Override
    public View onCreateInputView() {
        myView = new MyView(this);
        myView.setInputMethodService(this);
        myView.setMinimumHeight(129);
        return myView;
    }

    public void type(String text){
        InputConnection ic = getCurrentInputConnection();
        ic.commitText(text,1);
    }

}