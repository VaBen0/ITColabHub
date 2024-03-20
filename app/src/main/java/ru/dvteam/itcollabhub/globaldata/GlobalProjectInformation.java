package ru.dvteam.itcollabhub.globaldata;

public class GlobalProjectInformation {
    private static GlobalProjectInformation instance;
    private String projectTitle;
    private String projectLog;
    private String webLink;
    private String description;

    public String getProjectTitle() {

        System.out.println(projectTitle);
        return projectTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setProjectTitle(String projectTitle) {

        System.out.println(projectTitle);
        this.projectTitle = projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public void setProjectLog(String projectLog) {
        this.projectLog = projectLog;
    }

    private GlobalProjectInformation(){}
    public static GlobalProjectInformation getInstance(){
        if(instance == null){
            instance = new GlobalProjectInformation();
        }
        return instance;
    }

}
