package ru.dvteam.itcollabhub.classmodels;

public class ProjectInformation {
    private final String projectTitle;
    private final String projectLogo;
    private final String projectDescription;
    private final double isEnd;
    private final String projectPurposes;
    private final String projectProblems;
    private final String projectPeoples;
    private final String projectTime;
    private final String projectTime1;
    private final String projectTgLink;
    private final String projectVkLink;
    private final String projectWebLink;
    private final String projectPurposesIds;
    private final String projectProblemsIds;
    private final String projectIsLeader;
    private final String projectTasks;
    private final String projectIsOpen;
    private final String projectIsVisible;

    public ProjectInformation(String projectTitle, String projectLogo, String projectDescription,
                              double isEnd, String projectPurposes, String projectProblems, String projectPeoples,
                              String projectTime, String projectTime1, String projectTgLink, String projectVkLink, String projectWebLink,
                              String projectPurposesIds, String projectProblemsIds, String projectIsLeader, String projectTasks, String projectIsOpen,
                              String projectIsVisible) {
        this.projectTitle = projectTitle;
        this.projectLogo = projectLogo;
        this.projectDescription = projectDescription;
        this.isEnd = isEnd;
        this.projectPurposes = projectPurposes;
        this.projectProblems = projectProblems;
        this.projectPeoples = projectPeoples;
        this.projectTime = projectTime;
        this.projectTime1 = projectTime1;
        this.projectTgLink = projectTgLink;
        this.projectVkLink = projectVkLink;
        this.projectWebLink = projectWebLink;
        this.projectPurposesIds = projectPurposesIds;
        this.projectProblemsIds = projectProblemsIds;
        this.projectIsLeader = projectIsLeader;
        this.projectTasks = projectTasks;
        this.projectIsOpen = projectIsOpen;
        this.projectIsVisible = projectIsVisible;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public double getIsEnd() {
        return isEnd;
    }

    public String getProjectPurposes() {
        return projectPurposes;
    }

    public String getProjectProblems() {
        return projectProblems;
    }

    public String getProjectPeoples() {
        return projectPeoples;
    }

    public String getProjectTime() {
        return projectTime;
    }

    public String getProjectTime1() {
        return projectTime1;
    }

    public String getProjectTgLink() {
        return projectTgLink;
    }

    public String getProjectVkLink() {
        return projectVkLink;
    }

    public String getProjectWebLink() {
        return projectWebLink;
    }

    public String getProjectPurposesIds() {
        return projectPurposesIds;
    }

    public String getProjectProblemsIds() {
        return projectProblemsIds;
    }

    public String getProjectIsLeader() {
        return projectIsLeader;
    }

    public String getProjectTasks() {
        return projectTasks;
    }

    public String getProjectIsOpen() {
        return projectIsOpen;
    }

    public String getProjectIsVisible() {
        return projectIsVisible;
    }
}
