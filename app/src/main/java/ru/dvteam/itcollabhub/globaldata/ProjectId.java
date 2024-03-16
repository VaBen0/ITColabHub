package ru.dvteam.itcollabhub.globaldata;

public class ProjectId {
    private static ProjectId INSTANCE;
    private String projectId;
    public ProjectId(){

    }

    public static ProjectId getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ProjectId();
        }
        return INSTANCE;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId(){
        return projectId;
    }
}
