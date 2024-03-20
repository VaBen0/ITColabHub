package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditFileModel;

public class EditFileViewModel extends ViewModel {
    private EditFileModel editFileModel = new EditFileModel();

    public String getProjectTitle() {
        return editFileModel.getProjectTitle();
    }

    public String getProjectLog() {
        return editFileModel.getProjectLog();
    }

    public String getFilePhoto() {
        return editFileModel.getFilePhoto();
    }

    public String getFileName() {
        return editFileModel.getFileName();
    }

    public String getFileLink() {
        return editFileModel.getFileLink();
    }

    public void setMediaPath(String mediaPath) {
        editFileModel.setMediaPath(mediaPath);
    }

    public void setFileName(String fileName) {
        editFileModel.setFileName(fileName);
    }

    public void setFileLink(String fileLink) {
        editFileModel.setFileLink(fileLink);
    }

    public void onSaveClick(CallBackBoolean callback){
        editFileModel.saveFileChanges(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });
    }
}
