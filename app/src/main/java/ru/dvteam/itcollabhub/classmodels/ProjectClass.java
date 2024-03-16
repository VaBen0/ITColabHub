package ru.dvteam.itcollabhub.classmodels;

public class ProjectClass {
    private final String projectTitle;
    private final String projectLog;
    private final String projectCreatorName;
    private final int projectCreatorScore;
    private final String projectCreatorLog;
    private final int projectProgress;
    private final String projectId;

    public ProjectClass(String projectTitle, String projectLog, String projectCreatorName,
                        int projectCreatorScore, String projectCreatorLog, int projectProgress,
                        String projectId) {
        this.projectTitle = projectTitle;
        this.projectLog = projectLog;
        this.projectCreatorName = projectCreatorName;
        this.projectCreatorScore = projectCreatorScore;
        this.projectCreatorLog = projectCreatorLog;
        this.projectProgress = projectProgress;
        this.projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public String getProjectCreatorName() {
        return projectCreatorName;
    }

    public int getProjectCreatorScore() {
        return projectCreatorScore;
    }

    public String getProjectCreatorLog() {
        return projectCreatorLog;
    }

    public int getProjectProgress() {
        return projectProgress;
    }

    public String getProjectId() {
        return projectId;
    }
}
