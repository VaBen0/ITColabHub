package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackUserInformation;
import ru.dvteam.itcollabhub.classmodels.UserInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ProjectParticipantsModel;

public class ProjectParticipantsViewModel extends ViewModel {
    private final ProjectParticipantsModel projectParticipantsModel = new ProjectParticipantsModel();

    private MutableLiveData<ArrayList<UserInformation>> usersData;

    public LiveData<ArrayList<UserInformation>> getUsersFromProject(){
        if(usersData == null){
            usersData = new MutableLiveData<>();
        }
        return usersData;
    }

    public void getProjectUsers(){
        projectParticipantsModel.getProjectUsers(new CallBackUserInformation() {
            @Override
            public void invoke(ArrayList<UserInformation> users) {
                usersData.setValue(users);
            }
        });
    }

    public String getProjectTitle(){
        return projectParticipantsModel.getProjectTitle();
    }
    public String getProjectLog(){
        return projectParticipantsModel.getProjectLog();
    }
    public void findParticipant(){
        usersData.setValue(projectParticipantsModel.findUser());
    }

    public void setFindName(String s){
        projectParticipantsModel.setNameOfFindParticipant(s);
    }

}
