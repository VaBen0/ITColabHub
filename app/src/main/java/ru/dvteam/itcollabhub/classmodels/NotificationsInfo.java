package ru.dvteam.itcollabhub.classmodels;

public class NotificationsInfo {
    private final String notificationName;
    private final String notificationText;
    private String notificationLink = "";
    private final String notificationUrlPhoto;

    public NotificationsInfo(String notificationName, String notificationText, String notificationLink, String notificationUrlPhoto) {
        this.notificationName = notificationName;
        this.notificationText = notificationText;
        this.notificationLink = notificationLink;
        this.notificationUrlPhoto = notificationUrlPhoto;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public String getNotificationLink() {
        return notificationLink;
    }

    public String getNotificationUrlPhoto() {
        return notificationUrlPhoto;
    }

}
