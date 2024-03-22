package ru.dvteam.itcollabhub.globaldata;

public class GlobalProjectInformation {
    private static GlobalProjectInformation instance;
    private String projectTitle;
    private String projectLog;
    private String webLink;
    private String description;
    private Boolean isLead;
    private Boolean isEnd = false;

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
        this.projectTitle = projectTitle;
    }

    public void setLead(Boolean lead) {
        isLead = lead;
    }

    public Boolean getLead() {
        return isLead;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public void setEnd(Boolean end) {
        isEnd = end;
    }

    public Boolean getEnd() {
        return isEnd;
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
