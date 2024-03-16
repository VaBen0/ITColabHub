package ru.dvteam.itcollabhub.globaldata;

public class GlobalProjectInformation {
    private static GlobalProjectInformation instance;
    private String problemsIds;
    private String purposesIds;

    public String getProblemsIds() {
        return problemsIds;
    }

    public void setProblemsIds(String problemsIds) {
        this.problemsIds = problemsIds;
    }

    public String getPurposesIds() {
        return purposesIds;
    }

    public void setPurposesIds(String purposesIds) {
        this.purposesIds = purposesIds;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public void setProjectLog(String projectLog) {
        this.projectLog = projectLog;
    }

    private String projectTitle;
    private String projectLog;
    private GlobalProjectInformation(){}
    public static GlobalProjectInformation getInstance(){
        if(instance == null){
            instance = new GlobalProjectInformation();
        }
        return instance;
    }

}
