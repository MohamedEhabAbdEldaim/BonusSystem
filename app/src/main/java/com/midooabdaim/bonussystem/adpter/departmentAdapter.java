package com.midooabdaim.bonussystem.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.midooabdaim.bonussystem.R;
import com.midooabdaim.bonussystem.data.local.room.DaoAccessDepartment;
import com.midooabdaim.bonussystem.data.local.room.department;
import com.midooabdaim.bonussystem.ui.activity.BaseActivity;
import com.midooabdaim.bonussystem.ui.activity.MainActivity;
import com.midooabdaim.bonussystem.ui.fragment.departmentFragment;
import com.midooabdaim.bonussystem.ui.fragment.usersFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.midooabdaim.bonussystem.data.local.room.roomManger.getInstance;
import static com.midooabdaim.bonussystem.helper.HelperMethod.replaceFragment;


public class departmentAdapter extends RecyclerView.Adapter<departmentAdapter.ViewHolder> {


    private Context context;
    private Activity activity;
    private List<department> itemList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private String lang;
    private Float allbonus;
    private DaoAccessDepartment dao;
    private departmentFragment departmentFragment;

    public departmentAdapter(Activity activity, List<department> itemList, Float allbonus, departmentFragment departmentFragment) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.itemList = itemList;
        this.allbonus = allbonus;
        this.departmentFragment = departmentFragment;
        dao = getInstance(activity).daoAccessDepartment();
        lang = activity.getString(R.string.eg);
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_depart,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setSwip(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        try {
            holder.position = position;
            holder.itemDepartTvName.setText(itemList.get(position).getDepartmentName());
            holder.itemDepartTvDegree.setText(String.valueOf(itemList.get(position).getDepartmentDegree()));
            holder.itemDepartTvValue.setText(String.valueOf((float) (itemList.get(position).getDepartmentDegree() * allbonus)));
        } catch (Exception e) {

        }
    }

    private void setSwip(ViewHolder holder, int position) {
        try {
            holder.itemDepartSwipeRevealLayout.computeScroll();
            if (lang.equals("ar")) {
                holder.itemDepartSwipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
            } else {
                holder.itemDepartSwipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
            }
            viewBinderHelper.bind(holder.itemDepartSwipeRevealLayout, itemList.get(position).getDepartmentName());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBinderHelper.openLayout(itemList.get(position).getDepartmentName());
                    holder.itemDepartSwipeRevealLayout.computeScroll();
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_depart_tv_name)
        TextView itemDepartTvName;
        @BindView(R.id.item_depart_tv_degree)
        TextView itemDepartTvDegree;
        @BindView(R.id.item_depart_tv_value)
        TextView itemDepartTvValue;
        @BindView(R.id.item_depart_swipe_reveal_layout)
        SwipeRevealLayout itemDepartSwipeRevealLayout;

        private int position;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.item_depart_tv_info, R.id.item_depart_rl_delete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_depart_tv_info:
                    MainActivity mainActivity = (MainActivity) activity;
                    usersFragment usersFragment = new usersFragment();
                    usersFragment.Department = itemList.get(position);
                    replaceFragment(mainActivity.getSupportFragmentManager(), R.id.home_frame_fragment, usersFragment);
                    break;
                case R.id.item_depart_rl_delete:
                    delete();
                    break;
            }
        }

        private void delete() {
            try {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        dao.delete(itemList.get(position));
                        departmentFragment.setData();
                    }
                });
            } catch (Exception e) {

            }

        }

    }


}
