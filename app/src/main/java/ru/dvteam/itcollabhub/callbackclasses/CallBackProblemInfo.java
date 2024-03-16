package ru.dvteam.itcollabhub.callbackclasses;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ProblemInformation;

public interface CallBackProblemInfo {
    void invoke(ArrayList<ProblemInformation> problemInfo);
}
