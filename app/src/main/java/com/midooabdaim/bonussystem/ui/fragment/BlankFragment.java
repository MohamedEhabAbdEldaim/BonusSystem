package com.midooabdaim.bonussystem.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.midooabdaim.bonussystem.R;
import com.midooabdaim.bonussystem.ui.activity.MainActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment {
    Unbinder unbinder;

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, view);
        MainActivity home = (MainActivity) getActivity();
        home.setHomeAppBarTextViewChange("edit profile");
        return view;
    }

    @Override
    public void onStart() {
        intialFragment();
        super.onStart();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
        super.BackPressed();
    }
}
