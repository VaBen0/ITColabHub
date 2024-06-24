package ru.dvteam.itcollabhub.view.profileviews.activities;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackNotificationsInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
import ru.dvteam.itcollabhub.classmodels.NotificationsInfo;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class NotificationsModel {
    PostDatas postDatas = new PostDatas();
    public void getAllNotifications(CallBackNotificationsInfo callBack){
        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        String mail = mailGlobalInfo.getUserMail();
        postDatas.postDataGetAllNotifications("GetNotification", mail, new CallBackTasksInfo() {
            @Override
            public void invoke(String name, String text, String urlImg, String url) {
                ArrayList<NotificationsInfo> info = new ArrayList<>();
                if (!name.equals("Нет")) {
                    info.add(new NotificationsInfo(name, text, urlImg, url));
                    postDatas.postDataSetNotificationRead("SetNotificationIsRead", mail);
                }
                callBack.invoke(info);
            }
        });
    }
}
