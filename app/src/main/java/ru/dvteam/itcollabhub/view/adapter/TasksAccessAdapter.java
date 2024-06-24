package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.databinding.RoleWindowForTaskBinding;
import ru.dvteam.itcollabhub.databinding.RolesAccessTasksBinding;

public class TasksAccessAdapter extends RecyclerView.Adapter<TasksAccessAdapter.ViewHolder> {

    List<RolesInformation> rolesList;
    Context context;
    CallBack callBack;
    int color;

    public TasksAccessAdapter(List<RolesInformation> rolesList, Context context, int color, CallBack callBack) {
        this.rolesList = rolesList;
        this.context = context;
        this.callBack = callBack;
        this.color = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.roles_access_tasks, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(rolesList.get(position));
    }

    @Override
    public int getItemCount() {
        return rolesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        RolesAccessTasksBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RolesAccessTasksBinding.bind(itemView);
            final int[] arr = {0, 0, 0};
            binding.purposesButtonWatch.setOnClickListener(v -> {
                if (arr[1] == 0){
                    arr[1] = 1;
                    arr[0] = 1;
                    binding.purposesButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                }else{
                    arr[1] = 0;
                    arr[0] = 0;
                    arr[2] = 0;
                    binding.purposesButtonWatch.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.purposesButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                binding.purposes.setProgress(arr[1] + arr[2]);
                String s = (arr[1] + arr[2]) + "/2";
                binding.tickedCountPurps.setText(s);
            });
            binding.purposesButtonEdit.setOnClickListener(v -> {
                if (arr[2] == 0){
                    arr[1] = 1;
                    arr[0] = 1;
                    arr[2] = 1;
                    binding.purposesButtonWatch.setImageResource(R.drawable.checked_radio_icon);
                    binding.purposesButtonEdit.setImageResource(R.drawable.checked_radio_icon);
                }else{
                    arr[2] = 0;
                    binding.purposesButtonEdit.setImageResource(R.drawable.not_checked_radio_icon);
                }
                binding.purposes.setProgress(arr[1] + arr[2]);
                String s = (arr[1] + arr[2]) + "/2";
                binding.tickedCountPurps.setText(s);
            });
        }

        public void update(RolesInformation rolesInformation){
            ArrayList<Integer> colorsArray = new ArrayList<>();

            if(rolesInformation.getRoleColor().equals("#FFFFFF")){
                colorsArray.add(color);
                colorsArray.add(Color.parseColor("#FFFFFF"));
            }else{
                colorsArray.add(color);
                colorsArray.add(Color.parseColor(rolesInformation.getRoleColor()));
                colorsArray.add(Color.parseColor(rolesInformation.getRoleColor()));
            }

            int[] colors = new int[colorsArray.size()];
            for (int i = 0; i < colorsArray.size(); i++) {
                colors[i] = colorsArray.get(i);
            }


            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);

            binding.purposes.setMax(2);
            binding.mainImg.setBackground(gd);
            binding.mainText.setText(rolesInformation.getRoleName());

        }
    }
}
