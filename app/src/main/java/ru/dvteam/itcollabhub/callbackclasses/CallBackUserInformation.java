package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.UserInformation;

public interface CallBackUserInformation {
    void invoke(ArrayList<UserInformation> users);
}
