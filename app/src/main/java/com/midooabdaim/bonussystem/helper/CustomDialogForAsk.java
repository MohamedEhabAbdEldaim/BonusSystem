package com.midooabdaim.bonussystem.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.midooabdaim.bonussystem.R;


public class CustomDialogForAsk extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button yes, no;
    private Button.OnClickListener listener;
    private TextView textView;
    private String titel;

    public CustomDialogForAsk(Activity activity, Button.OnClickListener onClickListenerYes, String titel) {
        super(activity);
        this.activity = activity;
        this.listener = onClickListenerYes;
        this.titel = titel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailogclear);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        textView = (TextView) findViewById(R.id.txt_dia);
        textView.setText(titel);
        no.setOnClickListener(this);
        yes.setOnClickListener(listener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no:
                cancel();
                break;
            default:
                break;
        }
        dismiss();
    }
}
