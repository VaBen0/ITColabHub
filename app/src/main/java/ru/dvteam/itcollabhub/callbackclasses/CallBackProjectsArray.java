package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ProjectClass;

public interface CallBackProjectsArray {
    void invoke(ArrayList<ProjectClass> arrayList);
}
