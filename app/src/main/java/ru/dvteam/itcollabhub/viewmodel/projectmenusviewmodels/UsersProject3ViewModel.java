package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.UsersProjectModel;

public class UsersProject3ViewModel extends ViewModel {
    private MutableLiveData<ProjectInformation> projectInformation;
    private final UsersProjectModel usersProjectModel = new UsersProjectModel();

    public void getProjectInformation(){
        usersProjectModel.getAllProjectInfo(new CallBackProjectInformation() {
            @Override
            public void ProjectInformation(ProjectInformation projectInformation1) {
                projectInformation.setValue(projectInformation1);
            }
        });
    }

    public LiveData<ProjectInformation> getProjectInformationData(){
        if(projectInformation == null){
            projectInformation = new MutableLiveData<>();
        }
        return projectInformation;
    }
}
