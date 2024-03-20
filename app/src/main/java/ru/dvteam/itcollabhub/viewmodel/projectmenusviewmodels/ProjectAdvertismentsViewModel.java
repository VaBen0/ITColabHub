package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackWith2Data;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.model.projectmenusmodels.ProjectAdvertismentsModel;

public class ProjectAdvertismentsViewModel extends ViewModel {
    private String advName = "";
    private String mediaPath = "";
    private String advDescr = "";
    private ArrayList<DataOfWatcher> adds1Array = new ArrayList<>();
    private ArrayList<DataOfWatcher> adds2Array = new ArrayList<>();
    private MutableLiveData<ArrayList<DataOfWatcher>> adds;
    private final ProjectAdvertismentsModel projectAdvertismentsModel = new ProjectAdvertismentsModel();
    private MutableLiveData<Boolean> nameNotEmpty;
    private MutableLiveData<Boolean> descrEmpty;

    public LiveData<Boolean> getNameNotEmpty(){
        if(nameNotEmpty == null){
            nameNotEmpty = new MutableLiveData<>();
        }
        return nameNotEmpty;
    }
    public LiveData<ArrayList<DataOfWatcher>> getAdds1(){
        if(adds == null){
            adds = new MutableLiveData<>();
        }
        return adds;
    }
    public MutableLiveData<Boolean> getDescrNotEmpty(){
        if(descrEmpty == null){
            descrEmpty = new MutableLiveData<>();
        }
        return descrEmpty;
    }
    public void setAdvName(String name){
        advName = name;
        System.out.println(projectAdvertismentsModel.checkIsEmpty(name));
        nameNotEmpty.setValue(projectAdvertismentsModel.checkIsEmpty(name));
    }
    public void setAdvDescr(String descr){
        advDescr = descr;
        System.out.println(projectAdvertismentsModel.checkIsEmpty(descr));
        descrEmpty.setValue(projectAdvertismentsModel.checkIsEmpty(descr));
    }
    public void setAdvImg(String mediaPath2){
        mediaPath = mediaPath2;
    }

    public void onCreateClick(){
        if(mediaPath.isEmpty()) {
            projectAdvertismentsModel.createAdsWithoutImage(advName, advDescr, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {

                }
            });
        }else{
            projectAdvertismentsModel.createAds(advName, advDescr, mediaPath, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {

                }
            });
        }
    }

    public void deleteAd(String id){
        projectAdvertismentsModel.deleteAds(id);
    }

    public void setAdverts(){
        projectAdvertismentsModel.getAds(new CallBackWith2Data() {
            @Override
            public void invoke(ArrayList<DataOfWatcher> data) {
                adds.setValue(data);
            }

            @Override
            public void invoke2(ArrayList<DataOfWatcher> data) {
            }
        });
    }
    public String getProjectTitle(){
        return projectAdvertismentsModel.getProjectTitle();
    }
    public String getProjectPhoto(){
        return projectAdvertismentsModel.getProjectLog();
    }
    public void onChangeClick(int position, CallBackBoolean callback){
        projectAdvertismentsModel.changeAd(position, new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                callback.invoke(true);
            }
        });
    }
}
