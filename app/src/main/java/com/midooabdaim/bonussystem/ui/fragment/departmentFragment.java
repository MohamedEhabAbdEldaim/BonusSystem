package com.midooabdaim.bonussystem.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import com.midooabdaim.bonussystem.adpter.departmentAdapter;
import com.midooabdaim.bonussystem.data.local.room.DaoAccessDepartment;
import com.midooabdaim.bonussystem.data.local.room.department;
import com.midooabdaim.bonussystem.helper.CustomDialogForAsk;
import com.midooabdaim.bonussystem.helper.CustomDialogForChangeBonus;
import com.midooabdaim.bonussystem.helper.DialogForAdd;
import com.midooabdaim.bonussystem.helper.dialogForChangeBonus;
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
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.clean;
import static com.midooabdaim.bonussystem.data.local.shared.SharedPrefrance.loadDataFloat;
import static com.midooabdaim.bonussystem.helper.HelperMethod.cleanError;
import static com.midooabdaim.bonussystem.helper.HelperMethod.replaceFragment;
import static com.midooabdaim.bonussystem.helper.HelperMethod.restratFragment;
import static com.midooabdaim.bonussystem.helper.HelperMethod.validationTextInputLayoutListEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class departmentFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_department_rv)
    RecyclerView fragmentDepartmentRv;
    private LinearLayoutManager linearLayoutManager;
    public Float totalBonus;
    private departmentAdapter departmentAdapter;
    private List<department> listdepart = new ArrayList<>();
    private DaoAccessDepartment dao;
    private Dialog dialog;
    private TextInputLayout dialogName;
    private TextInputLayout dialogValue;
    private TextView textView;
    private Button buttonAdd;
    private CheckBox checkBoxSame;
    private List<TextInputLayout> textInputLayoutsList = new ArrayList<>();
    private department Department;
    private MainActivity home;
    private CustomDialogForAsk ask;
    private CustomDialogForChangeBonus dialogForChangeBonus;


    public departmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_department, container, false);
        unbinder = ButterKnife.bind(this, view);
        setHomeBar();
        setHasOptionsMenu(true);
        dao = getInstance(getActivity()).daoAccessDepartment();
        intial();
        setData();
        return view;
    }

    private void setHomeBar() {
        home = (MainActivity) getActivity();
        home.setHomeImgBackUnvisible();
        totalBonus = loadDataFloat(getActivity(), TOTAL_BONUS);
        if (!(totalBonus > 0)) {
            dialogForChangeBonus = new CustomDialogForChangeBonus(getActivity(), true, departmentFragment.this);
            dialogForChangeBonus.show();
        }
        setTitele();
    }

    public void setTitele() {
        home.setHomeAppBarTextViewChange(getString(R.string.allBonus) + totalBonus);
    }

    private void intial() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        fragmentDepartmentRv.setLayoutManager(linearLayoutManager);
        departmentAdapter = new departmentAdapter(getActivity(), listdepart, totalBonus, departmentFragment.this);
        fragmentDepartmentRv.setAdapter(departmentAdapter);
    }

    public void setData() {
        try {
            listdepart = new ArrayList<>();
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    listdepart = dao.getAll();
                    departmentAdapter = new departmentAdapter(getActivity(), listdepart, totalBonus, departmentFragment.this);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragmentDepartmentRv.setAdapter(departmentAdapter);
                        }
                    });

                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_change_bonus_value:
                dialogForChangeBonus = new CustomDialogForChangeBonus(getActivity(), false, departmentFragment.this);
                dialogForChangeBonus.show();
                return true;
            case R.id.main_clear_all_department:
                Button.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAllDeprtment();
                    }
                };
                ask = new CustomDialogForAsk(getActivity(), listener, getString(R.string.ask_clear_all_deprt));
                ask.show();
                return true;
            case R.id.main_clear_all_data_from_app:
                Button.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteAllData();
                    }
                };
                ask = new CustomDialogForAsk(getActivity(), clickListener, getString(R.string.ask_clear_all_data));
                ask.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteAllData() {
        try {
            clean(getActivity());
            deleteAllDeprtment();
            ask.dismiss();
            restratFragment(getActivity().getSupportFragmentManager(), departmentFragment.this);

        } catch (Exception e) {

        }
    }

    private void deleteAllDeprtment() {
        try {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    dao.deleteALL();
                    setData();
                }
            });
            ask.dismiss();
        } catch (Exception e) {
        }
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
        getActivity().finish();
    }

    @OnClick(R.id.fragment_department_fbt_add_department)
    public void onViewClicked() {
        addDepartment();
    }

    private void addDepartment() {
        DialogForAdd add = new DialogForAdd();
        dialog = add.setupDialog(getActivity());
        dialogName = (TextInputLayout) dialog.findViewById(R.id.dialog_add_txt_input_name);
        dialogValue = (TextInputLayout) dialog.findViewById(R.id.dialog_add_txt_input_value);
        textInputLayoutsList.add(dialogName);
        textInputLayoutsList.add(dialogValue);
        textView = (TextView) dialog.findViewById(R.id.dialog_add_txt_ask_to_add);
        checkBoxSame = (CheckBox) dialog.findViewById(R.id.dialog_add_check_box_same);
        buttonAdd = (Button) dialog.findViewById(R.id.dialog_add_btn_add);
        textView.setText(getString(R.string.add_department));
        dialogName.getEditText().setHint(getString(R.string.add_name_department));
        dialogValue.getEditText().setHint(getString(R.string.prsantage));
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
            String departmentName = dialogName.getEditText().getText().toString().trim();
            String departmentValue = dialogValue.getEditText().getText().toString().trim();
            boolean checkBox = checkBoxSame.isChecked();
            if (!validationTextInputLayoutListEmpty(textInputLayoutsList, getString(R.string.empty))) {
                return;
            }
            float allPresentagBefor = 0;
            if (!(listdepart.size() == 0)) {
                for (int i = 0; i < listdepart.size(); i++) {
                    allPresentagBefor += listdepart.get(i).getDepartmentDegree();
                    if (departmentName.equals(listdepart.get(i).getDepartmentName())) {
                        dialogName.setError(getString(R.string.department_exist));
                        return;
                    }
                }
            }
            float departmentDegree = Float.parseFloat(departmentValue);
            float allPresentag = allPresentagBefor + departmentDegree;

            if (allPresentag > 1) {
                dialogValue.setError(getString(R.string.prsantage_less_or_equal) + (1 - allPresentagBefor));
                return;
            }
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Department = new department(departmentName, departmentDegree, checkBox);
                    dao.add(Department);
                }
            });

            Toast.makeText(getActivity(), getString(R.string.added), Toast.LENGTH_LONG).show();
            setData();

            dialog.dismiss();
        } catch (Exception e) {
            Log.i("midoo", "addDepartment: " + e);
        }

    }


}

