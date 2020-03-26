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
import com.midooabdaim.bonussystem.data.local.room.DaoAccessUsers;
import com.midooabdaim.bonussystem.data.local.room.users;
import com.midooabdaim.bonussystem.ui.activity.BaseActivity;

import com.midooabdaim.bonussystem.ui.fragment.usersFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.midooabdaim.bonussystem.data.local.room.roomManger.getInstance;

public class usersAdapter extends RecyclerView.Adapter<usersAdapter.ViewHolder> {


    private Context context;
    private Activity activity;
    private List<users> itemListusers = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private String lang;
    private Float presentag;
    private usersFragment usersFragment;
    private DaoAccessUsers daoAccessUsers;

    public usersAdapter(Activity activity, List<users> itemListusers, Float presentag, com.midooabdaim.bonussystem.ui.fragment.usersFragment usersFragment) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.itemListusers = itemListusers;
        this.presentag = presentag;
        this.usersFragment = usersFragment;
        lang = activity.getString(R.string.eg);
        daoAccessUsers = getInstance(activity).daoAccessUsers();
        viewBinderHelper.setOpenOnlyOne(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setSwipe(holder, position);
    }


    private void setData(ViewHolder holder, int position) {
        try {
            holder.position = position;
            holder.itemUserName.setText(itemListusers.get(position).getUserName());
            holder.itemUserValue.setText(String.valueOf(itemListusers.get(position).getUserValue()));
            holder.itemUserBonus.setText(String.valueOf((float) (itemListusers.get(position).getUserValue() * presentag)));
        } catch (Exception e) {

        }
    }

    private void setSwipe(ViewHolder holder, int position) {
        try {
            holder.itemUserSwipeRevealLayout.computeScroll();
            if (lang.equals("ar")) {
                holder.itemUserSwipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
            } else {
                holder.itemUserSwipeRevealLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
            }
            viewBinderHelper.bind(holder.itemUserSwipeRevealLayout, String.valueOf(itemListusers.get(position).getId()));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBinderHelper.openLayout(String.valueOf(itemListusers.get(position).getId()));
                    holder.itemUserSwipeRevealLayout.computeScroll();
                }
            });
        } catch (Exception e) {

        }
    }


    @Override
    public int getItemCount() {
        return itemListusers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_user_name)
        TextView itemUserName;
        @BindView(R.id.item_user_value)
        TextView itemUserValue;
        @BindView(R.id.item_user_bonus)
        TextView itemUserBonus;
        @BindView(R.id.item_user_swipe_reveal_layout)
        SwipeRevealLayout itemUserSwipeRevealLayout;
        private int position;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.item_user_tv_update, R.id.item_user_rl_delete})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.item_user_tv_update:
                    break;
                case R.id.item_user_rl_delete:
                    delete();
                    break;
            }
        }


        private void delete() {
            try {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        daoAccessUsers.delete(itemListusers.get(position));
                        usersFragment.setData();
                    }
                });
            } catch (Exception e) {
            }

        }

        private void update() {
            try {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            } catch (Exception e) {

            }

        }


    }


}
