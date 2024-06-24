package ru.dvteam.itcollabhub.viewmodel.profileviewmodels;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Objects;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.classmodels.FriendInformation;
import ru.dvteam.itcollabhub.classmodels.ProfileInformation;
import ru.dvteam.itcollabhub.model.profilemodels.ProfileModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileInformation> profileInformation;
    private final ProfileModel profileModel = new ProfileModel();
    private MutableLiveData<Boolean> banned;
    private MutableLiveData<ArrayList<FriendInformation>> friends;
    private MutableLiveData<Boolean> notifications;

    public LiveData<ProfileInformation> getUserAllInfo(){
        if(profileInformation == null){
            profileInformation = new MutableLiveData<>();
        }
        return profileInformation;
    }

    public LiveData<Boolean> getNotifications(){
        if(notifications == null){
            notifications = new MutableLiveData<>();
        }
        return notifications;
    }

    public void getProfileInformation(SharedPreferences sPref){
        profileModel.getUserProfileInformation(sPref, new CallBackProfileInformation() {
            @Override
            public void invoke(ProfileInformation profileInform) {
                profileInformation.setValue(profileInform);
            }
        });
    }
    public void getNotificationsIsNot(){
        profileModel.getAllNotifications(new CallBackInt() {
            @Override
            public void invoke(String res) {
                notifications.setValue(res.equals("1"));
            }
        });
    }
    public LiveData<Boolean> getBanned(){
        if(banned == null){
            banned = new MutableLiveData<>();
        }
        return banned;
    }
    public LiveData<ArrayList<FriendInformation>> getFriendList(){
        if(friends == null){
            friends = new MutableLiveData<>();
        }
        return friends;
    }

    public void setDataForChooseTheme(int themeNum){
        profileModel.setDataForChooseThemeActivity(themeNum, Objects.requireNonNull(profileInformation.getValue()));
    }

    public void setFriends(){
        profileModel.setUserFriends(new CallBack() {
            @Override
            public void invoke() {
                getFriends();
            }
        });
    }

    public void adFriend(String id){
        profileModel.adFriend(id);
    }

    public void findFriend(CallBack callBack){
        profileModel.findUsers(new CallBackBoolean() {
            @Override
            public void invoke(Boolean res) {
                if(res){
                    getFriends();
                    callBack.invoke();
                }
            }
        });
    }

    public void getFriends(){
        friends.setValue(profileModel.getFriendsArray());
    }

    public void isbanned(){
        banned.setValue(profileModel.isbanned());
    }
    public void setUserName(String userName) {
        profileModel.setUserName(userName);
    }
}
