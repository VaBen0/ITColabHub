package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.databinding.RoleWindowBinding;

public class RolesIconsAdapter extends RecyclerView.Adapter<RolesIconsAdapter.ViewHolder> {

    private List <RolesInformation> rolesInformationList;
    private Context context;
    private CallBack callBack;
    private int color;

    public RolesIconsAdapter(ArrayList<RolesInformation> rolesInformationList, Context context, int color, CallBack callBack){
        this.rolesInformationList = rolesInformationList;
        this.context = context;
        this.callBack = callBack;
        this.color = color;
    }

    @NonNull
    @Override
    public RolesIconsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.role_window, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.update(rolesInformationList.get(position));
    }

    @Override
    public int getItemCount() {
        return rolesInformationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RoleWindowBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = RoleWindowBinding.bind(itemView);
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
            binding.view18.setBackground(gd);
            binding.view12.setBackground(gd);
            binding.roleName.setText(rolesInformation.getRoleName());
            binding.mainUserName.setText(rolesInformation.getFirstParticipant());
            Glide
                    .with(context)
                    .load(rolesInformation.getUrlPhotoFirstParticipant())
                    .into(binding.log);
            if(rolesInformation.getNumOfParticipants() == 1){
                binding.view18.setVisibility(View.VISIBLE);
                binding.view12.setVisibility(View.INVISIBLE);
                binding.log2.setVisibility(View.GONE);
                binding.log3.setVisibility(View.GONE);
                binding.log4.setVisibility(View.GONE);
                binding.userCircle2.setVisibility(View.GONE);
                binding.userCircle3.setVisibility(View.GONE);
                binding.userCircle4.setVisibility(View.GONE);
                binding.countUsers.setVisibility(View.GONE);
            }else if(rolesInformation.getNumOfParticipants() == 2){
                binding.log3.setVisibility(View.GONE);
                binding.log4.setVisibility(View.GONE);
                binding.userCircle3.setVisibility(View.GONE);
                binding.userCircle4.setVisibility(View.GONE);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoSecondParticipant())
                        .into(binding.log2);
                binding.countUsers.setText("И ещё 1 пользователь");
            }else if(rolesInformation.getNumOfParticipants() == 3){
                binding.log4.setVisibility(View.GONE);
                binding.userCircle4.setVisibility(View.GONE);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoSecondParticipant())
                        .into(binding.log2);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoThirdParticipant())
                        .into(binding.log3);
                binding.countUsers.setText("И ещё 2 пользователя");
            }else if(rolesInformation.getNumOfParticipants() == 4){
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoSecondParticipant())
                        .into(binding.log2);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoThirdParticipant())
                        .into(binding.log3);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoFourthParticipant())
                        .into(binding.log4);
                binding.countUsers.setText("И ещё 3 пользователя");
            }else{
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoSecondParticipant())
                        .into(binding.log2);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoThirdParticipant())
                        .into(binding.log3);
                Glide
                        .with(context)
                        .load(rolesInformation.getUrlPhotoFourthParticipant())
                        .into(binding.log4);
                String s;
                if(rolesInformation.getNumOfParticipants() < 10) {
                    switch ((int) rolesInformation.getNumOfParticipants() % 10) {
                        case 1:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователь";
                            break;
                        case 2:
                        case 3:
                        case 4:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователя";
                            break;
                        default:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователей";
                            break;
                    }
                } else if(rolesInformation.getNumOfParticipants() < 20){
                    s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователей";
                }else{
                    switch ((int) rolesInformation.getNumOfParticipants() % 10) {
                        case 1:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователь";
                            break;
                        case 2:
                        case 3:
                        case 4:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователя";
                            break;
                        default:
                            s = "Ещё " + rolesInformation.getNumOfParticipants() + " пользователей";
                            break;
                    }
                }
            }
        }
    }
}
