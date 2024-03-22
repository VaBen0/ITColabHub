package ru.dvteam.itcollabhub.view.adapter;

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
import ru.dvteam.itcollabhub.classmodels.ProblemInformation;
import ru.dvteam.itcollabhub.databinding.ProblemPanelBinding;

public class ProblemsParticipAdapter extends RecyclerView.Adapter<ProblemsParticipAdapter.ProblemsParticipViewHolder> {
    List<ProblemInformation> data;
    Context context;

    public ProblemsParticipAdapter(ArrayList<ProblemInformation> data1, Context context1){
        data = data1;
        context = context1;
    }

    @NonNull
    @Override
    public ProblemsParticipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.problem_panel, parent, false);
        return new ProblemsParticipViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ProblemsParticipViewHolder holder, int position) {
        holder.update(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProblemsParticipViewHolder extends RecyclerView.ViewHolder{
        ProblemPanelBinding binding;
        public ProblemsParticipViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ProblemPanelBinding.bind(itemView);
        }

        public void update(ProblemInformation problemInformation){
            binding.problemName.setText(problemInformation.getProblemName());
            binding.problemDescription.setText(problemInformation.getProblemDesc());
            Glide
                    .with(context)
                    .load(problemInformation.getProblemImage())
                    .into(binding.problemImage);
            if(problemInformation.getTicked()) binding.view8.setBackgroundResource(R.drawable.green_transperent);
            binding.editProblem.setVisibility(View.GONE);
        }

    }
}
