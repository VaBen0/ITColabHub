package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;
import java.util.List;

import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;

public interface CallBackWith2Data {
    void invoke(ArrayList<DataOfWatcher> data);
    void invoke2(ArrayList<DataOfWatcher> data);
}
