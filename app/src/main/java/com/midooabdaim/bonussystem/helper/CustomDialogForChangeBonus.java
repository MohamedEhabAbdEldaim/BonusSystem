package com.midooabdaim.bonussystem.helper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.midooabdaim.bonussystem.R;
import com.midooabdaim.bonussystem.ui.fragment.departmentFragment;

import java.util.ArrayList;
import java.util.List;

import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.TOTAL_BONUS;
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.savaData;
import static com.midooabdaim.bonussystem.helper.HelperMethod.cleanError;


public class CustomDialogForChangeBonus extends Dialog implements View.OnClickListener {


    private TextInputLayout dialogChangeBonusValue;
    private Activity activity;
    private Button change, cancel;
    private List<TextInputLayout> textInputLayouts = new ArrayList<>();
    private Boolean add = true;
    private View view;
    private com.midooabdaim.bonussystem.ui.fragment.departmentFragment departmentFragment;

    public CustomDialogForChangeBonus(Activity activity, boolean add, departmentFragment departmentFragment) {
        super(activity);
        this.activity = activity;
        this.add = add;
        this.departmentFragment = departmentFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailogchangvalue);
        setCancelable(false);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialogChangeBonusValue = (TextInputLayout) findViewById(R.id.dialog_change_bonus_value);
        textInputLayouts.add(dialogChangeBonusValue);

        change = (Button) findViewById(R.id.dialog_button_change);

        cancel = (Button) findViewById(R.id.dialog_button_cancel);
        if (add) {
            setCanceledOnTouchOutside(false);
            change.setText(activity.getText(R.string.add));
            cancel.setVisibility(View.GONE);
        } else {
            setCanceledOnTouchOutside(true);
            change.setText(activity.getText(R.string.change));
            cancel.setVisibility(View.VISIBLE);
            cancel.setOnClickListener(this);
        }

        change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_button_change:
                addBonus();
                break;
            case R.id.dialog_button_cancel:
                cancel();
                break;
            default:
                break;
        }
        dismiss();
    }


    private void addBonus() {
        cleanError(textInputLayouts);
        String bonus = dialogChangeBonusValue.getEditText().getText().toString();

        if (bonus.equals("")) {
            dialogChangeBonusValue.setError(activity.getString(R.string.must_insert));
            return;
        }
        Float bonus_value = Float.valueOf(bonus);

        if (!(bonus_value > 0)) {
            dialogChangeBonusValue.setError(activity.getString(R.string.bonus_value_great_than));
            return;
        }
        savaData(activity, TOTAL_BONUS, bonus_value);

        departmentFragment.totalBonus = bonus_value;
        departmentFragment.setTitele();
    }


}
