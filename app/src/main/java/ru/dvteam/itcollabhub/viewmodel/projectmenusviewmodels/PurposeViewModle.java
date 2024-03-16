package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.callbackclasses.CallBackPurpInfo;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.PurposeModel;

public class PurposeViewModle extends ViewModel {
    private final PurposeModel purposeModel = new PurposeModel();
    private String purpName = "";
    private String purpDescr = "";
    private String mediaPath = "";
    private MutableLiveData<Boolean> checkNameEmpty;
    private MutableLiveData<Boolean> checkDescrEmpty;
    private MutableLiveData<ArrayList<PurposeInformation>> purpsArray;
    public LiveData<Boolean> getNameIsEmpty(){
        if(checkNameEmpty == null){
            checkNameEmpty = new MutableLiveData<>();
        }
        return checkNameEmpty;
    }
    public LiveData<Boolean> getDescrIsEmpty(){
        if(checkDescrEmpty == null){
            checkDescrEmpty = new MutableLiveData<>();
        }
        return checkDescrEmpty;
    }
    public LiveData<ArrayList<PurposeInformation>> getPurpsArray(){
        if(purpsArray == null){
            purpsArray = new MutableLiveData<>();
        }
        return purpsArray;
    }
    public String getProjectTitle(){
        return purposeModel.getProjectTitle();
    }
    public String getProjectLog(){
        return purposeModel.getProjectPhoto();
    }

    public void setPurpName(String name){
        purpName = name;
        checkNameEmpty.setValue(purposeModel.checkEmpty(name));
    }
    public void setPurpDescr(String descr){
        purpDescr = descr;
        checkDescrEmpty.setValue(purposeModel.checkEmpty(descr));
    }
    public void setMediaPath(String mediaPath){
        this.mediaPath = mediaPath;
    }

    public void onCreateClick(){
        if(mediaPath.isEmpty()) purposeModel.createPurpWithoutImage(purpName, purpDescr);
        else purposeModel.createPurp(purpName, purpDescr, mediaPath);
    }
    public void onCompleteClick(String id){
        purposeModel.setPurposeComplete(id);
    }
    public void setPurposes(){
        purposeModel.setPurposes(new CallBackPurpInfo() {
            @Override
            public void invoke(ArrayList<PurposeInformation> purpInfo) {
                purpsArray.setValue(purpInfo);
            }
        });
    }

    public int getPurpsCnt(){
        return purposeModel.getPurpsCnt();
    }
}
