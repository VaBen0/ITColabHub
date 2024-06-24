package ru.dvteam.itcollabhub.model.projectmenusmodels;

import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class EditRoleModel {
    PostDatas postDatas = new PostDatas();
    private final String projectName = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLogo = GlobalProjectInformation.getInstance().getProjectLog();

    public void getAllRoles(){

    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectLogo() {
        return projectLogo;
    }
}
