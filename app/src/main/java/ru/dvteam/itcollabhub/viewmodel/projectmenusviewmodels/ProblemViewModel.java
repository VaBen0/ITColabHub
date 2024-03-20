package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProblemInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackPurpInfo;
import ru.dvteam.itcollabhub.classmodels.ProblemInformation;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ProblemModel;

public class ProblemViewModel extends ViewModel {
    private final ProblemModel problemModel = new ProblemModel();
    private String problemName = "";
    private String problemDescr = "";
    private String mediaPath = "";
    private MutableLiveData<Boolean> checkNameEmpty;
    private MutableLiveData<Boolean> checkDescrEmpty;
    private MutableLiveData<ArrayList<ProblemInformation>> problemsArray;
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
    public LiveData<ArrayList<ProblemInformation>> getProblemsArray(){
        if(problemsArray == null){
            problemsArray = new MutableLiveData<>();
        }
        return problemsArray;
    }
    public String getProjectTitle(){
        return problemModel.getProjectTitle();
    }
    public String getProjectLog(){
        return problemModel.getProjectPhoto();
    }

    public void setProblemName(String name){
        problemName = name;
        checkNameEmpty.setValue(problemModel.checkEmpty(name));
    }
    public void setProblemDescr(String descr){
        problemDescr = descr;
        checkDescrEmpty.setValue(problemModel.checkEmpty(descr));
    }
    public void setMediaPath(String mediaPath){
        this.mediaPath = mediaPath;
    }

    public void onCreateClick(){
        if(mediaPath.isEmpty()) problemModel.createProblemWithoutImage(problemName, problemDescr);
        else problemModel.createProblem(problemName, problemDescr, mediaPath);
    }
    public void onCompleteClick(String id){
        problemModel.setProblemComplete(id);
    }
    public void setProblems(){
        problemModel.setProblems(new CallBackProblemInfo() {
            @Override
            public void invoke(ArrayList<ProblemInformation> problemInfo) {
                problemsArray.setValue(problemInfo);
            }
        });
    }
    public int getProblemsCnt(){
        return problemModel.getProblemsCnt();
    }
    public void onChange(int position, CallBackBoolean callback){
        problemModel.onChange(position, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });

    }
}
