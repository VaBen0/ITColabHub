package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.RolesModel;

public class EditRoleViewModel extends ViewModel {
    MutableLiveData<ArrayList<RolesInformation>> rolesInfo;
    MutableLiveData<Integer> colorInfo;
    RolesModel model = new RolesModel();

    public LiveData<ArrayList<RolesInformation>> getRoles(){
        if(rolesInfo == null){
            rolesInfo = new MutableLiveData<>();
        }
        return rolesInfo;
    }

    public LiveData<Integer> getColor(){
        if(colorInfo == null){
            colorInfo = new MutableLiveData<>();
        }
        return colorInfo;
    }

    public void getAllRoles(){

    }

    public void setColor(Integer color){
        colorInfo.setValue(color);
    }

    public String getProjectName(){
        return model.getProjectName();
    }
    public String getProjectLog(){
        return model.getProjectLogo();
    }
}
