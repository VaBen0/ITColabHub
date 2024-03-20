package ru.dvteam.itcollabhub.model.projectmenusmodels;

import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;

public class ProjectPageModel {
    private final GlobalProjectInformation globalProjectInformation = GlobalProjectInformation.getInstance();
    private final String projectTitle = globalProjectInformation.getProjectTitle();
    private final String projectLog = globalProjectInformation.getProjectLog();
    private final String projectWebLink = globalProjectInformation.getWebLink();
    private final String projectDescription = globalProjectInformation.getDescription();

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public String getProjectWebLink() {
        return projectWebLink;
    }

    public String getProjectDescription() {
        return projectDescription;
    }
}
