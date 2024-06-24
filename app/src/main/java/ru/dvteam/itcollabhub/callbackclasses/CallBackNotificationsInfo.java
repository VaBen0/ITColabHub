package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.NotificationsInfo;

public interface CallBackNotificationsInfo {
    void invoke(ArrayList<NotificationsInfo> info);
}
