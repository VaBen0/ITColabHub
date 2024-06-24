package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.RolesModel;

public class RolesViewModel extends ViewModel {
    MutableLiveData<ArrayList<RolesInformation>> rolesInfo;
    MutableLiveData<ArrayList<ParticipantWithRole>> participantsWithRole;
    MutableLiveData<ArrayList<ParticipantWithRole>> participantsWithoutRole;
    MutableLiveData<Integer> colorInfo;
    MutableLiveData<ArrayList<ParticipantWithRole>> addedParticipants;
    RolesModel model = new RolesModel();

    public LiveData<ArrayList<RolesInformation>> getRoles(){
        if(rolesInfo == null){
            rolesInfo = new MutableLiveData<>();
        }
        return rolesInfo;
    }
    public LiveData<ArrayList<ParticipantWithRole>> getAddedPeoples(){
        if(addedParticipants == null){
            addedParticipants = new MutableLiveData<>();
        }
        return addedParticipants;
    }
    public LiveData<ArrayList<ParticipantWithRole>> getPeoplesWithRole(){
        if(participantsWithRole == null){
            participantsWithRole = new MutableLiveData<>();
        }
        return participantsWithRole;
    }
    public LiveData<ArrayList<ParticipantWithRole>> getPeoplesWithoutRole(){
        if(participantsWithoutRole == null){
            participantsWithoutRole = new MutableLiveData<>();
        }
        return participantsWithoutRole;
    }

    public LiveData<Integer> getColor(){
        if(colorInfo == null){
            colorInfo = new MutableLiveData<>();
        }
        return colorInfo;
    }

    public void getAllRoles(){
        rolesInfo.setValue(model.getAllRoles());
    }
    public void getPeoplesRolesWith(){
        participantsWithRole.setValue(model.getPeoplesWithRole());
    }
    public void getPeoplesRolesWithout(){
        participantsWithoutRole.setValue(model.getPeoplesWithoutRole());
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
    public void addPeople(ParticipantWithRole participant){
        model.addPeople(participant);
        addedParticipants.setValue(model.getAddedPeoples());
    }
    public void deletePeople(ParticipantWithRole participant){
        model.deletePeople(participant);
        addedParticipants.setValue(model.getAddedPeoples());
    }
    public void setAddedParticipants(){
        addedParticipants.setValue(model.getAddedPeoples());
    }
}
