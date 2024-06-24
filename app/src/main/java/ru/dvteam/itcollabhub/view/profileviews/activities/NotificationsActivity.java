package ru.dvteam.itcollabhub.view.profileviews.activities;

import static ru.dvteam.itcollabhub.view.UsersChosenTheme.setThemeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.databinding.ActivityNotificationsBinding;
import ru.dvteam.itcollabhub.view.adapter.NotificationsAdapter;

public class NotificationsActivity extends AppCompatActivity {

    ActivityNotificationsBinding binding;
    NotificationsViewModel viewModel;
    NotificationsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity(this);
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        viewModel.getAllNotifications();

        binding.notificationsPlace.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getNotifInfo().observe(this, notificationsInfos -> {
            if(!notificationsInfos.isEmpty()) {
                binding.notificationsNotFound.setVisibility(View.GONE);
                adapter = new NotificationsAdapter(notificationsInfos, this);
                binding.notificationsPlace.setAdapter(adapter);
            }
        });

        binding.refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        startActivity(getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                        finish();
                        binding.refreshLayout.setRefreshing(false);
                    }
                }
        );

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(this, typedValue.resourceId);

        getWindow().setStatusBarColor(color);

    }
}