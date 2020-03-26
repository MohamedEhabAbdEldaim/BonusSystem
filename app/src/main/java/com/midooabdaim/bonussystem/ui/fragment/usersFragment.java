package com.midooabdaim.bonussystem.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.midooabdaim.bonussystem.R;
import com.midooabdaim.bonussystem.adpter.usersAdapter;
import com.midooabdaim.bonussystem.data.local.room.DaoAccessUsers;
import com.midooabdaim.bonussystem.data.local.room.department;
import com.midooabdaim.bonussystem.data.local.room.users;
import com.midooabdaim.bonussystem.helper.CustomDialogForAsk;
import com.midooabdaim.bonussystem.helper.DialogForAdd;
import com.midooabdaim.bonussystem.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.midooabdaim.bonussystem.data.local.room.roomManger.getInstance;
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.TOTAL_BONUS;
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.loadDataFloat;
import static com.midooabdaim.bonussystem.helper.HelperMethod.cleanError;
import static com.midooabdaim.bonussystem.helper.HelperMethod.validationTextInputLayoutListEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class usersFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_user_rv)
    RecyclerView fragmentUserRv;
    public department Department;
    private Float totalBonus;
    private LinearLayoutManager linearLayoutManager;
    private DaoAccessUsers daoAccessUsers;
    private List<users> itemListUser = new ArrayList<>();
    private usersAdapter usersAdapter;
    private Dialog dialog;
    private TextInputLayout dialogValue;
    private TextInputLayout dialogName;
    private TextView textView;
    private Button buttonAdd;
    private List<TextInputLayout> textInputLayoutsList = new ArrayList<>();
    private users users;
    private CustomDialogForAsk ask;

    public usersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHomeBar();
        setHasOptionsMenu(true);
        daoAccessUsers = getInstance(getActivity()).daoAccessUsers();
        intial();
        setData();
        return view;
    }

    private void setHomeBar() {
        MainActivity home = (MainActivity) getActivity();
        totalBonus = loadDataFloat(getActivity(), TOTAL_BONUS);
        home.setHomeAppBarTextViewChange(Department.getDepartmentName() + " = " + (Department.getDepartmentDegree() * totalBonus));
        home.setHomeImgBackVisible();
    }

    private void intial() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentUserRv.setLayoutManager(linearLayoutManager);
        usersAdapter = new usersAdapter(getActivity(), itemListUser, computePresentge(), usersFragment.this);
        fragmentUserRv.setAdapter(usersAdapter);
    }

    public void setData() {
        try {
            itemListUser = new ArrayList<>();
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    itemListUser = daoAccessUsers.getAll(Department.getDepartmentName());
                    float presentag = computePresentge();
                    usersAdapter = new usersAdapter(getActivity(), itemListUser, presentag, usersFragment.this);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragmentUserRv.setAdapter(usersAdapter);
                        }
                    });

                }
            });
        } catch (Exception e) {
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.second, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.second_clear_all_users:
                Button.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAllUser();
                    }
                };
                ask = new CustomDialogForAsk(getActivity(), listener, getString(R.string.ask_clear_all_user));
                ask.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllUser() {
        try {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    daoAccessUsers.deleteAll(Department.getDepartmentName());
                    setData();
                }
            });
            ask.dismiss();
        } catch (Exception e) {
        }
    }

    private float computePresentge() {
        float presentge;
        double allValue = 0;
        if (!(itemListUser.size() == 0)) {
            for (int i = 0; i < itemListUser.size(); i++) {
                allValue += itemListUser.get(i).getUserValue();
            }
            presentge = (float) ((Department.getDepartmentDegree() * totalBonus) / allValue);

        } else {
            presentge = 1;
        }
        return presentge;
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

    @OnClick(R.id.fragment_user_fbt_add_user)
    public void onViewClicked() {
        adduser();
    }

    private void adduser() {
        DialogForAdd add = new DialogForAdd();
        dialog = add.setupDialog(getActivity());
        dialogName = (TextInputLayout) dialog.findViewById(R.id.dialog_add_txt_input_name);
        textInputLayoutsList.add(dialogName);
        dialogValue = (TextInputLayout) dialog.findViewById(R.id.dialog_add_txt_input_value);
        textView = (TextView) dialog.findViewById(R.id.dialog_add_txt_ask_to_add);
        CheckBox checkBoxSame = (CheckBox) dialog.findViewById(R.id.dialog_add_check_box_same);
        checkBoxSame.setVisibility(View.GONE);
        buttonAdd = (Button) dialog.findViewById(R.id.dialog_add_btn_add);
        textView.setText(getString(R.string.add_user));
        dialogName.getEditText().setHint(getString(R.string.add_name_user));
        if (Department.getDependsOn()) {
            dialogValue.setVisibility(View.GONE);
        } else {
            dialogValue.getEditText().setHint(getString(R.string.user_value));
            textInputLayoutsList.add(dialogValue);
        }
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInRoom();
            }
        });
        dialog.show();

    }

    private void addInRoom() {
        try {
            cleanError(textInputLayoutsList);
            String userName = dialogName.getEditText().getText().toString().trim();
            if (!validationTextInputLayoutListEmpty(textInputLayoutsList, getString(R.string.empty))) {
                return;
            }
            if (Department.getDependsOn()) {

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        users = new users(userName, 1, Department.getDepartmentName());
                        daoAccessUsers.add(users);
                    }
                });
            } else {
                String userValue = dialogValue.getEditText().getText().toString().trim();
                Double userValueCast = Double.valueOf(userValue);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        users = new users(userName, userValueCast, Department.getDepartmentName());
                        daoAccessUsers.add(users);
                    }
                });
            }
            Toast.makeText(getActivity(), getString(R.string.added), Toast.LENGTH_LONG).show();
            setData();
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
