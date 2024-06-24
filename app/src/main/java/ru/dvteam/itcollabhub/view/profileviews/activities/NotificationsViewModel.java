package ru.dvteam.itcollabhub.view.profileviews.activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackNotificationsInfo;
import ru.dvteam.itcollabhub.classmodels.NotificationsInfo;

public class NotificationsViewModel extends ViewModel {
    NotificationsModel model = new NotificationsModel();

    MutableLiveData<ArrayList<NotificationsInfo>> notifInfo;

    public LiveData<ArrayList<NotificationsInfo>> getNotifInfo(){
        if(notifInfo == null){
            notifInfo = new MutableLiveData<>();
        }
        return notifInfo;
    }

    public void getAllNotifications(){
        model.getAllNotifications(new CallBackNotificationsInfo() {
            @Override
            public void invoke(ArrayList<NotificationsInfo> info) {
                notifInfo.setValue(info);
            }
        });
    }
}
