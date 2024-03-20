package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditAdvertismentModel;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditProblemModel;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditProjectModel;

public class EditProblemViewModel extends ViewModel {
    private final EditProblemModel editAdvertismentModel = new EditProblemModel();

    public void onChangeClick(CallBackBoolean callback){
        editAdvertismentModel.onSaveChanges(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });
    }

    public void onDelete(CallBackBoolean callback){
        editAdvertismentModel.onDelete(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });
    }

    public String getProjectTitle() {
        return editAdvertismentModel.getProjectTitle();
    }

    public String getProjectLog() {
        return editAdvertismentModel.getProjectLog();
    }

    public String getAdPhoto() {
        return editAdvertismentModel.getAdPhoto();
    }

    public String getName() {
        return editAdvertismentModel.getName();
    }

    public String getDescription() {
        return editAdvertismentModel.getDescription();
    }

    public void setMediaPath(String mediaPath) {
        editAdvertismentModel.setMediaPath(mediaPath);
    }

    public void setName(String name) {
        editAdvertismentModel.setName(name);
    }

    public void setDescription(String description) {
        editAdvertismentModel.setDescription(description);
    }
}
