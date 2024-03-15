package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.InformationForChooseThemeForApp;
import ru.dvteam.itcollabhub.model.profilemodels.ChooseThemeForAppModel;

public class ChooseThemeForAppViewModel extends ViewModel {
    private MutableLiveData<InformationForChooseThemeForApp> info;
    private ChooseThemeForAppModel chooseThemeForAppModel = new ChooseThemeForAppModel();

    public LiveData<InformationForChooseThemeForApp> getInfo(){
        if(info == null){
            info = new MutableLiveData<>();
        }
        return info;
    }

    public void getInfoFromModel(){
        info.setValue(chooseThemeForAppModel.getImportantInfo());
    }
}
