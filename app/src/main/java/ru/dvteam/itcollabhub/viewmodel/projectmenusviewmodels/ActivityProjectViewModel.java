package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectsArray;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ActivityProjectModel;

public class ActivityProjectViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ProjectClass>> activityProjects;
    private MutableLiveData<ArrayList<ProjectClass>> endProjects;
    private ActivityProjectModel activityProjectModel = new ActivityProjectModel();

    public LiveData<ArrayList<ProjectClass>> getActivityProjects(){
        if(activityProjects == null){
            activityProjects = new MutableLiveData<>();
        }
        return activityProjects;
    }

    public LiveData<ArrayList<ProjectClass>> getEndProjects(){
        if(endProjects == null){
            endProjects = new MutableLiveData<>();
        }
        return endProjects;
    }

    public void setActivityProjects(){
        activityProjectModel.getActivityProjects(new CallBackProjectsArray() {
            @Override
            public void invoke(ArrayList<ProjectClass> arrayList) {
                activityProjects.setValue(arrayList);
            }
        });
    }

    public void setEndProjects(){
        activityProjectModel.getArchiveProjects(new CallBackProjectsArray() {
            @Override
            public void invoke(ArrayList<ProjectClass> arrayList) {
                endProjects.setValue(arrayList);
            }
        });
    }

    public void setProjectId(String id){
        activityProjectModel.setProjectId(id);
    }
}
