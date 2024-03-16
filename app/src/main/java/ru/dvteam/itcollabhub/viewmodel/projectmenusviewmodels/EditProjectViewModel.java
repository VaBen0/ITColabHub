package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditProjectModel;

public class EditProjectViewModel extends ViewModel {
    private MutableLiveData<ProjectInformation> prInfo;
    private EditProjectModel editProjectModel = new EditProjectModel();

    public LiveData<ProjectInformation> getPrInfo(){
        if(prInfo == null){
            prInfo = new MutableLiveData<>();
        }
        return prInfo;
    }

    public void getAllProjectInfo(){
        editProjectModel.getAllProjectInfo(projectInformation -> {
            prInfo.setValue(projectInformation);
        });
    }
}
