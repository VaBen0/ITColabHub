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
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.databinding.PurposePanelBinding;

public class PurposeParticipantAdapter extends RecyclerView.Adapter<PurposeParticipantAdapter.PurposeParticipantViewHolder> {
    private List<PurposeInformation> purpsArray = new ArrayList<>();
    private Context context;

    public PurposeParticipantAdapter(ArrayList<PurposeInformation> purps, Context context1){
        purpsArray = purps;
        context = context1;
    }


    @NonNull
    @Override
    public PurposeParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.purpose_panel, parent, false);
        return new PurposeParticipantViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PurposeParticipantViewHolder holder, int position) {
        holder.update(purpsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return purpsArray.size();
    }

    public class PurposeParticipantViewHolder extends RecyclerView.ViewHolder {
        PurposePanelBinding binding;
        public PurposeParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PurposePanelBinding.bind(itemView);
        }
        public void update(PurposeInformation purposeInformation){
            binding.name.setText(purposeInformation.getPurpName());
            binding.description1.setText(purposeInformation.getPurpDesc());
            Glide
                    .with(context)
                    .load(purposeInformation.getPurpImage())
                    .into(binding.imagePurp);
            if(purposeInformation.getTicked()) binding.view8.setBackgroundResource(R.drawable.green_transperent);
        }
    }
}
