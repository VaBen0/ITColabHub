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
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.databinding.RoleWindowForTaskBinding;

public class RolesForTasksAdapter extends RecyclerView.Adapter<RolesForTasksAdapter.ViewHolder> {

    private List<RolesInformation> rolesList;
    private Context context;
    private CallBack callBack;
    private int color;

    public RolesForTasksAdapter(List<RolesInformation> rolesList, Context context, int color, CallBack callBack) {
        this.rolesList = rolesList;
        this.context = context;
        this.callBack = callBack;
        this.color = color;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.role_window_for_task, parent, false);
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

        RoleWindowForTaskBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RoleWindowForTaskBinding.bind(itemView);
            final boolean[] checked = {false};

            binding.tickButton.setOnClickListener(v -> {
                if(checked[0]){
                    binding.tickButton.setImageResource(R.drawable.not_checked_radio_icon);
                    binding.view15.setBackgroundColor(Color.rgb(78, 78, 78));
                    checked[0] = false;
                }else {
                    binding.tickButton.setImageResource(R.drawable.checked_radio_icon);
                    binding.view15.setBackgroundColor(Color.rgb(41, 182, 25));
                    checked[0] = true;
                }
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
            binding.log.setBackground(gd);
            binding.textView3.setText(rolesInformation.getRoleName());
            binding.view15.setBackgroundColor(Color.rgb(78, 78, 78));
        }
    }
}
