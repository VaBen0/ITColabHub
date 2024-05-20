package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackActivityProject;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.databinding.ReminderBinding;
import ru.dvteam.itcollabhub.globaldata.Information;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderAdapterViewHolder> {

    List<DataOfWatcher> data = new ArrayList<>();
    private final Context context;
    private final CallBackActivityProject callback;
    private int lastPosition = -1;

    public ReminderAdapter(ArrayList<DataOfWatcher> data, Context context, CallBackActivityProject callback){
        this.data = data;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ReminderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder, parent, false);
        return new ReminderAdapterViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderAdapterViewHolder holder, int position) {
        holder.update(data.get(position));
        if (position < 5) {
            setAnimation(holder.itemView, position);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReminderAdapterViewHolder extends RecyclerView.ViewHolder{
        private final ReminderBinding binding;
        public ReminderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ReminderBinding.bind(itemView);

            binding.getRoot().setOnClickListener(v -> {
                if(data.get(getAdapterPosition()).isType()){
                    Information inf = Information.getInstance();
                    inf.setObjImage(data.get(getAdapterPosition()).getObjImg());
                    inf.setObjText(data.get(getAdapterPosition()).getObjDesc());
                    inf.setObjTitle(data.get(getAdapterPosition()).getObjTitle());
                    callback.setActivity("2");
                }else {
                    Information inf = Information.getInstance();
                    inf.setObjImage(data.get(getAdapterPosition()).getObjImg());
                    inf.setObjText(data.get(getAdapterPosition()).getObjDesc());
                    inf.setObjTitle(data.get(getAdapterPosition()).getObjTitle());
                    callback.setActivity("1");
                }
            });

        }

        public void update(DataOfWatcher data){
            String[] nameArr = data.getObjTitle().split(" ");
            int len = 0;
            String itog = "";
            for(int j = 0; j < nameArr.length; j++){
                len += nameArr[j].length();
                if(len + 3 < 22){
                    if(j == 0){
                        itog += nameArr[j];
                    }
                    else{
                        itog += " " + nameArr[j];
                    }
                }
                else{
                    itog += "...";
                    break;
                }
            }
            binding.textView33.setText(itog);
            if(data.isType()) binding.textView32.setText("Задание");
            else binding.textView32.setText("Объявление");
            Glide
                    .with(context)
                    .load(data.getObjImg())
                    .into(binding.loadImg);
        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(800);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
