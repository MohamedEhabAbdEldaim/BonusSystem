package com.midooabdaim.bonussystem.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.midooabdaim.bonussystem.R;

import java.util.ArrayList;
import java.util.List;

import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.TOTAL_BONUS;
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.savaData;
import static com.midooabdaim.bonussystem.helper.HelperMethod.cleanError;

public class dialogForChangeBonus extends DialogFragment {
    private TextInputLayout dialogChangeBonusValue;
    private Button change;
    private List<TextInputLayout> textInputLayouts = new ArrayList<>();
    private Boolean add = true;
    private apply apply;
    // in ----> calling
    //dialogForChangeBonus dialog = new dialogForChangeBonus();
    //dialog.show(getActivity().getSupportFragmentManager(), "dialog For Change Bonus");

//
//    public dialogForChangeBonus(boolean add) {
//        this.add = add;
//    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        // return super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.dialog_change_value_bonus, container, false);
//        dialogChangeBonusValue = (TextInputLayout) view.findViewById(R.id.dialog_change_bonus_value);
//        textInputLayouts.add(dialogChangeBonusValue);
//
//        change = (Button) view.findViewById(R.id.dialog_button_change);
//
//        if (add) {
//            change.setText(getActivity().getText(R.string.add));
//        } else {
//            change.setText(getActivity().getText(R.string.change));
//        }
//
//        change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addBonus();
//                getDialog().dismiss();
//            }
//        });
//        return view;
//    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.dialog_change_value_bonus, null);
//        builder.setView(view);
////        builder.setCancelable(false);
//        dialogChangeBonusValue = (TextInputLayout) view.findViewById(R.id.dialog_change_bonus_value);
//        textInputLayouts.add(dialogChangeBonusValue);
//
//        change = (Button) view.findViewById(R.id.dialog_button_change);
//
//        change.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addBonus();
//                dismiss();
//            }
//        });
//        return builder.create();
//    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            apply=(apply) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBonus() {
        cleanError(textInputLayouts);
        String bonus = dialogChangeBonusValue.getEditText().getText().toString();

        if (bonus.equals("")) {
            dialogChangeBonusValue.setError(getActivity().getString(R.string.must_insert));
            return;
        }
        Float bonus_value = Float.valueOf(bonus);

        if (!(bonus_value > 0)) {
            dialogChangeBonusValue.setError(getActivity().getString(R.string.bonus_value_great_than));
            return;
        }

        apply.app(bonus);

        savaData(getActivity(), TOTAL_BONUS, bonus_value);

    }


    public interface apply {
        void app(String name);
    }


}
