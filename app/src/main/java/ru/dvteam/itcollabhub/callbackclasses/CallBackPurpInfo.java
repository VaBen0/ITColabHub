package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.PurposeInformation;

public interface CallBackPurpInfo {
    void invoke(ArrayList<PurposeInformation> purpInfo);
}
