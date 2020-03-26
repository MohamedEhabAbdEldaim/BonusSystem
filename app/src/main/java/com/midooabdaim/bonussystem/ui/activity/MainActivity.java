package com.midooabdaim.bonussystem.ui.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midooabdaim.bonussystem.R;
import com.midooabdaim.bonussystem.ui.fragment.departmentFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.midooabdaim.bonussystem.helper.HelperMethod.replaceFragment;


public class MainActivity extends BaseActivity {

    @BindView(R.id.home_img_back)
    ImageView homeImgBack;
    @BindView(R.id.home_app_bar_text_view_change)
    TextView homeAppBarTextViewChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new departmentFragment());
    }

    public void setHomeAppBarTextViewChange(String Title) {
        homeAppBarTextViewChange.setText(Title);
    }


    public void setHomeImgBackVisible() {
        homeImgBack.setVisibility(View.VISIBLE);
    }


    public void setHomeImgBackUnvisible() {
        homeImgBack.setVisibility(View.GONE);
    }


    @OnClick(R.id.home_img_back)
    public void onViewClicked() {
        onBackPressed();
    }


}
