package ru.dvteam.itcollabhub;

public class ProfileInformation {
    private String userName, userImageUrl, userStatus, userRfr, userMail;
    private int userScore, userActivityProjects, userArchiveProjects;
    public ProfileInformation(String name, String urlImage, int topScore, String topStatus,
                              String rfr, int activityProjects, int archiveProjects, String mail){
        userName = name;
        userImageUrl = urlImage;
        userStatus = topStatus;
        userRfr = rfr;
        userActivityProjects = activityProjects;
        userScore = topScore;
        userArchiveProjects = archiveProjects;
        userMail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getUserRfr() {
        return userRfr;
    }

    public int getUserScore() {
        return userScore;
    }

    public int getUserActivityProjects() {
        return userActivityProjects;
    }

    public int getUserArchiveProjects() {
        return userArchiveProjects;
    }

    public String getUserMail() {
        return userMail;
    }
}
