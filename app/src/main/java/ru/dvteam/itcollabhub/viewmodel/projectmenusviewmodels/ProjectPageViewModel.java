package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.model.projectmenusmodels.ProjectPageModel;

public class ProjectPageViewModel extends ViewModel {
    private ProjectPageModel projectPageModel = new ProjectPageModel();
    public String getProjectTitle() {
        return projectPageModel.getProjectTitle();
    }

    public String getProjectLog() {
        return projectPageModel.getProjectLog();
    }

    public String getProjectWebLink() {
        return projectPageModel.getProjectWebLink();
    }

    public String getProjectDescription() {
        return projectPageModel.getProjectDescription();
    }
}
