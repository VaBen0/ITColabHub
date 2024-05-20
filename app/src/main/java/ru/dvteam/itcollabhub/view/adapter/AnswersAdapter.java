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

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.StatesClass;
import ru.dvteam.itcollabhub.databinding.StatePanelBinding;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder>{
    static Context context;
    CallBack callBack;
    ArrayList<StatesClass> statesClass;
    private int lastPosition = -1;

    public AnswersAdapter(Context context, CallBack callBack, ArrayList<StatesClass> statesClass){
        this.context = context;
        this.callBack = callBack;
        this.statesClass = statesClass;
    }

    @NonNull
    @Override
    public AnswersAdapter.AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_panel, parent, false);
        return new AnswersAdapter.AnswersViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
        holder.update(statesClass.get(position));
        if (position < 2) {
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return statesClass.size();
    }

    public static class AnswersViewHolder extends RecyclerView.ViewHolder {
        private final StatePanelBinding binding;

        public AnswersViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StatePanelBinding.bind(itemView);
        }

        public void update(StatesClass statesClass) {
            binding.title.setText(statesClass.getTitle());
            binding.text.setText(statesClass.getText());
            Glide
                    .with(context)
                    .load(statesClass.getImg())
                    .into(binding.loadImg);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(1200);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
