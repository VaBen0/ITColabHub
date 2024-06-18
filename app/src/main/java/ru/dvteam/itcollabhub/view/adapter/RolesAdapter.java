package ru.dvteam.itcollabhub.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.ParticipantsWithRoles;
import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.databinding.FriendWindow3Binding;
import ru.dvteam.itcollabhub.databinding.FriendWindowBinding;
import ru.dvteam.itcollabhub.databinding.ItemBinding;
import ru.dvteam.itcollabhub.databinding.ProjectWindowBinding;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.ViewHolder> {

    private List<ParticipantsWithRoles> participList;
    private Context context;
    private CallBack callback;

    public RolesAdapter(List<ParticipantsWithRoles> participList, Context context, CallBack callback){
        this.participList = participList;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        if(participList.get(position).isItemParent()){
            return 1;
        }else{
            return 0;
        }
    }

    @NonNull
    @Override
    public RolesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        if(viewType == 1){
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }else{
            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        }
        return new RolesAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull RolesAdapter.ViewHolder holder, int position) {
        holder.update(participList.get(position));
    }

    @Override
    public int getItemCount() {
        return participList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private FriendWindowBinding binding;
        private ItemBinding binding1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            if(getItemViewType() == 1){
                binding1 = ItemBinding.bind(itemView);
            }else{
                binding1 = ItemBinding.bind(itemView);
            }
        }
        public void update(ParticipantsWithRoles participList1) {

            String value = participList1.getValueText();
            int id = participList1.getValueId();
            int parentId = participList1.getParentId();
            final int position = getAdapterPosition();
            final String text = "#" + id + ": " + value + " (id родительского элемента: " + parentId + ")";

            //покажем или скроем элемент, если он дочерний
            if (parentId >= 0) {
                //видимость делаем по параметру родительского элемента
                setVisibility(binding1.getRoot(), participList.get(parentId).isChildVisibility(), parentId);
            }
            else { //элемент не дочерний, показываем его
                setVisibility(binding1.getRoot(), true, parentId);
            }

            //покажем или скроем иконку деревовидного списка
            if (participList1.isItemParent()) {
                binding1.iconTree.setVisibility(View.VISIBLE);
                //показываем нужную иконку
                if (participList1.isChildVisibility()) //показываются дочерние элементы
                    binding1.iconTree.setBackgroundResource(R.drawable.link_icon);
                else //скрыты дочерние элементы
                    binding1.iconTree.setBackgroundResource(R.drawable.image_icon);
            }
            else //элемент не родительский
                binding1.iconTree.setVisibility(View.GONE);

            //устанавливаем текст элемента
            if (!TextUtils.isEmpty(value)) {
                binding1.valueName.setText(value);
            }

            //добавляем обработку нажатий по значению
            binding1.valueName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParticipantsWithRoles dataItem = participList.get(position);
                    if (dataItem.isItemParent()) { //нажали по родительскому элементу, меняем видимость дочерних элементов
                        dataItem.setChildVisibility(!dataItem.isChildVisibility());
                        notifyDataSetChanged();
                    }
                    else { //нажали по обычному элементу, обрабатываем как нужно
                        Snackbar snackbar = Snackbar.make(binding1.getRoot(), text, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            });
        }
    }

    private void setVisibility(View curV,  boolean visible, int parentId) {
        LinearLayout vPadding = curV.findViewById(R.id.block_text);

        LinearLayout.LayoutParams params;
        if (visible) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (vPadding != null) {
                if (parentId >= 0) {
                    vPadding.setPadding(80, 0, 0, 0);
                }
                else {
                    vPadding.setPadding(0, 0, 0, 0);
                }
            }
        }
        else
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        curV.setLayoutParams(params);
    }
}
