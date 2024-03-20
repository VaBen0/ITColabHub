package ru.dvteam.itcollabhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDelOrChangeAd;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.ProblemInformation;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.databinding.ProblemPanelBinding;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ProblemViewHolder> {
    List<ProblemInformation> data;
    Context context;
    CallBackDelOrChangeAd callback;

    public ProblemAdapter(ArrayList<ProblemInformation> data1, Context context1, CallBackDelOrChangeAd callback1){
        data = data1;
        context = context1;
        callback = callback1;
    }

    @NonNull
    @Override
    public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_panel, parent, false);
        return new ProblemViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemViewHolder holder, int position) {
        holder.update(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ProblemViewHolder extends RecyclerView.ViewHolder{
        ProblemPanelBinding binding;
        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProblemPanelBinding.bind(itemView);
            binding.getRoot().setOnClickListener(v -> {
                if(ProblemInformation.getCountTicked() < data.size() - 1 && !data.get(getAdapterPosition()).getTicked()){
                    binding.view8.setBackgroundResource(R.drawable.progress_panel_background2);
                    binding.descriptionPurpose.setVisibility(View.GONE);
                    binding.yesOrNo.setVisibility(View.VISIBLE);
                    binding.yes.setOnClickListener(v1 -> {
                        callback.delete(data.get(getAdapterPosition()).getProblemId());
                        binding.view8.setBackgroundResource(R.drawable.green_transperent);
                        binding.yesOrNo.setVisibility(View.GONE);
                        binding.descriptionPurpose.setVisibility(View.VISIBLE);
                    });
                    binding.no.setOnClickListener(v12 -> {
                        binding.view8.setBackgroundResource(R.drawable.progress_panel_background);
                        binding.descriptionPurpose.setVisibility(View.VISIBLE);
                        binding.yesOrNo.setVisibility(View.GONE);
                    });
                }

            });
            binding.editProblem.setOnClickListener(v -> {
                callback.change(getAdapterPosition());
            });
        }

        public void update(ProblemInformation problemInformation){
            binding.problemName.setText(problemInformation.getProblemName());
            binding.problemDescription.setText(problemInformation.getProblemDesc());
            Glide
                    .with(context)
                    .load(problemInformation.getProblemImage())
                    .into(binding.problemImage);
            if(problemInformation.getTicked()) binding.view8.setBackgroundResource(R.drawable.green_transperent);
        }

    }
}
