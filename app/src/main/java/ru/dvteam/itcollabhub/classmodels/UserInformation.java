package ru.dvteam.itcollabhub.classmodels;

public class UserInformation {
    private final String userName;
    private final String userLog;
    private final String userId;

    public UserInformation(String userName, String userLog, String userId) {
        this.userName = userName;
        this.userLog = userLog;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLog() {
        return userLog;
    }

    public String getUserId() {
        return userId;
    }
}
