package ru.dvteam.itcollabhub;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.ParticipantsWithRoles;
import ru.dvteam.itcollabhub.databinding.ActivityRolesBinding;
import ru.dvteam.itcollabhub.view.adapter.RolesAdapter;

public class RolesActivity extends AppCompatActivity {

    ActivityRolesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRolesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        List<ParticipantsWithRoles> records = new ArrayList<ParticipantsWithRoles>(); //список значений
        ParticipantsWithRoles record;
        RolesAdapter adapter;
        int parentId;

        record = new ParticipantsWithRoles();
        record.setValueId(1);
        record.setValueText("Родительское значение 1");
        record.setItemParent(true); //родительское значение
        records.add(record);
        parentId = records.size() -1;
        for (int ind = 1; ind <= 3; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Текст " + ind);
            record.setParentId(parentId);
            records.add(record);
        }

        record = new ParticipantsWithRoles();
        record.setValueId(1);
        record.setValueText("Второе родительское значение");
        record.setItemParent(true); //родительское значение
        records.add(record);
        parentId = records.size() -1;
        for (int ind = 4; ind <= 7; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Дочерний текст " + ind);
            record.setParentId(parentId);
            records.add(record);
        }

        record = new ParticipantsWithRoles();
        record.setValueId(1);
        record.setValueText("Еще родительское значение");
        record.setItemParent(true); //родительское значение
        records.add(record);
        parentId = records.size() -1;
        for (int ind = 8; ind <= 12; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Значение " + ind);
            record.setParentId(parentId);
            records.add(record);
        }

        for (int ind = 13; ind <= 18; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Текст без родителя" + ind);
            records.add(record);
        }

        for (int ind = 19; ind <= 21; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Элемент тоже без родителя" + ind);
            records.add(record);
        }

        record = new ParticipantsWithRoles();
        record.setValueId(1);
        record.setValueText("Опять родительское значение");
        record.setItemParent(true); //родительское значение
        records.add(record);
        parentId = records.size() -1;
        for (int ind = 22; ind <= 30; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Дочернее: " + ind);
            record.setParentId(parentId);
            records.add(record);
        }

        for (int ind = 31; ind <= 45; ind ++) {
            record = new ParticipantsWithRoles();
            record.setValueId(ind);
            record.setValueText("Последние без родителя " + ind);
            records.add(record);
        }

        adapter = new RolesAdapter(records, this, new CallBack() {
            @Override
            public void invoke() {

            }
        });
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rolesList.setAdapter(adapter);
        binding.rolesList.setLayoutManager(layoutManager);
        binding.rolesList.setItemAnimator(itemAnimator);
    }
}