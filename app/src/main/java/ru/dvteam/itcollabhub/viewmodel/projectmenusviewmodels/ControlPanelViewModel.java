package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackWith2Data;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ControlPanelModel;

public class ControlPanelViewModel extends ViewModel {
    private ArrayList<DataOfWatcher> adds1Array;
    private ArrayList<DataOfWatcher> adds2Array;
    private MutableLiveData<ProjectInformation> projectInformation;
    private MutableLiveData<ArrayList<DataOfWatcher>> adds;
    private String isl;
    private ControlPanelModel controlPanelModel = new ControlPanelModel();
    public void setProjectInformation(){
        controlPanelModel.getAllProjectInfo(new CallBackProjectInformation() {
            @Override
            public void ProjectInformation(ProjectInformation projectInformation1) {
                projectInformation.setValue(projectInformation1);
                isl = projectInformation1.getProjectIsLeader();
            }
        });
    }

    public LiveData<ArrayList<DataOfWatcher>> getAdds1(){
        if(adds == null){
            adds = new MutableLiveData<>();
        }
        return adds;
    }


    public LiveData<ProjectInformation> getProjectInformation(){
        if(projectInformation == null){
            projectInformation = new MutableLiveData<>();
        }
        return projectInformation;
    }

    public String getIsl() {
        return isl;
    }

    public void setAdverts(){
        controlPanelModel.getAds(new CallBackWith2Data() {
            @Override
            public void invoke(ArrayList<DataOfWatcher> data) {
                adds.setValue(data);
            }

            @Override
            public void invoke2(ArrayList<DataOfWatcher> data) {

            }
        });
    }
}
