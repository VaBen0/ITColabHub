package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.classmodels.FileInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ProjectFilesModel;

public class ProjectFilesViewModel extends ViewModel {
    private ProjectFilesModel projectFilesModel = new ProjectFilesModel();
    MutableLiveData<ArrayList<FileInformation>> filesInfo;
    MutableLiveData<Boolean> fileName;
    MutableLiveData<Boolean> fileLink;

    public void getFilesInfo(){
        projectFilesModel.updateFilesInfo(res -> {
            filesInfo.setValue(projectFilesModel.getMainFileArray());
        });
    }
    public LiveData<ArrayList<FileInformation>> getAllFiles(){
        if(filesInfo == null){
            filesInfo = new MutableLiveData<>();
        }
        return filesInfo;
    }
    public LiveData<Boolean> getNotEmptyName(){
        if(fileName == null){
            fileName = new MutableLiveData<>();
        }
        return fileName;
    }
    public LiveData<Boolean> getNotEmptyLink(){
        if(fileLink == null){
            fileLink = new MutableLiveData<>();
        }
        return fileLink;
    }

    public void setFileName(String fileNameStr){
        projectFilesModel.setFileNameEdit(fileNameStr);
        fileName.setValue(projectFilesModel.isEmpty(fileNameStr));
    }
    public void setFileLink(String fileLinkStr){
        projectFilesModel.setFileLinkEdit(fileLinkStr);
        fileLink.setValue(projectFilesModel.isEmpty(fileLinkStr));
    }
    public void setMediaPath(String fileImage){
        projectFilesModel.setMediaPath(fileImage);
    }
    public String getPrName(){
        return projectFilesModel.getProjectTitle();
    }
    public String getPrLog(){
        return projectFilesModel.getProjectLog();
    }

    public void onCreateClick(){

        projectFilesModel.createFile(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                getFilesInfo();
            }
        });
    }
    public void onDeleteClick(String id){
        projectFilesModel.deleteFile(id);
    }
    public void onFixClick(String id){
        projectFilesModel.fixFile(id);
    }
    public void onDetachClick(String id){
        projectFilesModel.detachFile(id);
    }
    public void onChangeClick(int position, CallBackBoolean callback){
        projectFilesModel.onChangeClick(position, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });
    }
}
