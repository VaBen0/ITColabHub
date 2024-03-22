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
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.databinding.PurposePanelBinding;

public class PurposesAdapter extends RecyclerView.Adapter<PurposesAdapter.PurposesViewHolder> {
    private List<PurposeInformation> purpsArray = new ArrayList<>();
    private Context context;
    private CallBackInt callback;

    public PurposesAdapter(ArrayList<PurposeInformation> purps, Context context1, CallBackInt callback1){
        purpsArray = purps;
        context = context1;
        callback = callback1;
    }

    @NonNull
    @Override
    public PurposesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.purpose_panel, parent, false);
        return new PurposesViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull PurposesViewHolder holder, int position) {
        holder.update(purpsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return purpsArray.size();
    }

    public class PurposesViewHolder extends RecyclerView.ViewHolder {
        PurposePanelBinding binding;
        public PurposesViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = PurposePanelBinding.bind(itemView);
            binding.getRoot().setOnClickListener(v -> {
                if(PurposeInformation.getCountTicked() < purpsArray.size() - 1 && !purpsArray.get(getAdapterPosition()).getTicked()){
                    binding.view8.setBackgroundResource(R.drawable.progress_panel_background2);
                    binding.descriptionPurpose.setVisibility(View.GONE);
                    binding.yesOrNo.setVisibility(View.VISIBLE);
                    binding.yes.setOnClickListener(v1 -> {
                        callback.invoke(purpsArray.get(getAdapterPosition()).getPurpId());
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
