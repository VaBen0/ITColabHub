package ru.dvteam.itcollabhub.model.profilemodels;

import android.content.SharedPreferences;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt2;
import ru.dvteam.itcollabhub.callbackclasses.CallBackNotificationsInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProfileInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
import ru.dvteam.itcollabhub.classmodels.FriendInformation;
import ru.dvteam.itcollabhub.classmodels.NotificationsInfo;
import ru.dvteam.itcollabhub.globaldata.GlobalDataScoreProfile;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.classmodels.ProfileInformation;

public class ProfileModel {
    private final PostDatas postDatas = new PostDatas();
    private Boolean banned = false;
    private String userMail;
    private ArrayList<FriendInformation> friendInformations;
    private String userName = "";
    public void getUserProfileInformation(SharedPreferences sPref, CallBackProfileInformation callback){
        String mail = sPref.getString("UserMail", "");

        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        mailGlobalInfo.setUserMail(mail);
        userMail = mail;

        postDatas.postDataGetUserData(mail, new CallBackInt2() {
            @Override
            public void invoke(String name, String urlImage, int topScore,
                               String topStatus, String rfr, int activityProjects,
                               int archiveProjects) {
                banned = topStatus.equals("Заблокирован");
                System.out.println(topStatus);
                callback.invoke(new ProfileInformation(name, urlImage, topScore, topStatus,
                        rfr, activityProjects, archiveProjects, mail));
            }
        });
    }

    public void setDataForChooseThemeActivity(int themeNum, ProfileInformation profileInformation){
        GlobalDataScoreProfile globalDataScoreProfile = GlobalDataScoreProfile.getInstance();
        globalDataScoreProfile.setScore(profileInformation.getUserScore());
        globalDataScoreProfile.setName(profileInformation.getUserName());
        globalDataScoreProfile.setThemeNum(themeNum);
        globalDataScoreProfile.setUrlPhoto(profileInformation.getUserImageUrl());
    }

    public void setUserFriends(CallBack callBack){
            postDatas.postDataGetFriends("GetUserFriends", userMail, new CallBackInt() {
                @Override
                public void invoke(String info) {
                    String[] inf = info.split(";");

                    if(!inf[0].equals("Нет1друзей564")) {
                        String[] names = inf[0].split(",");
                        String[] photo = inf[1].split(",");
                        String[] id = inf[2].split(",");
                        String[] score = inf[3].split(",");
                        String[] project = inf[4].split(",");
                        ArrayList<FriendInformation> friends = new ArrayList<>();
                        for (int i = 0; i < names.length; i++) {
                            friends.add(new FriendInformation(names[i], photo[i], id[i], project[i], Integer.parseInt(score[i]), false));
                        }
                        friendInformations = friends;
                        callBack.invoke();
                    }
                }
            });
    }

    public void findUsers(CallBackBoolean callBack){
        if(userName.isEmpty()){
            callBack.invoke(false);
        }else {
            postDatas.postDataGetFindFriend("GetUsers", userName, userMail, new CallBackInt() {
                @Override
                public void invoke(String info) {
                    String[] inf = info.split(";");

                    if (!inf[0].equals("Нет1друзей564")) {
                        String[] names = inf[0].split(",");
                        String[] photo = inf[1].split(",");
                        String[] id = inf[2].split(",");
                        String[] score = inf[3].split(",");
                        String[] project = inf[4].split(",");
                        ArrayList<FriendInformation> res = new ArrayList<>();
                        for (int i = 0; i < names.length; i++) {
                            res.add(new FriendInformation(names[i], photo[i], id[i], project[i], Integer.parseInt(score[i]), true));
                        }
                        friendInformations = res;
                        callBack.invoke(true);
                    }
                    else{
                        callBack.invoke(false);
                    }
                }
            });
        }
    }

    public ArrayList<FriendInformation> getFriendsArray(){
        return friendInformations;
    }

    public Boolean isbanned(){
        return banned;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void adFriend(String id){
        postDatas.postDataAddFriend("SendRequestToAddFriend", userMail, id, new CallBackInt() {
            @Override
            public void invoke(String res) {

            }
        });
    }

    public void getAllNotifications(CallBackInt callBack){
        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        String mail = mailGlobalInfo.getUserMail();
        postDatas.postDataGetAllNotifications("GetNotification", mail, new CallBackTasksInfo() {
            @Override
            public void invoke(String name, String text, String urlImg, String url) {
                if(name.equals("Нет")){
                    callBack.invoke("0");
                }else{
                    callBack.invoke("1");
                }
            }
        });
    }
}
