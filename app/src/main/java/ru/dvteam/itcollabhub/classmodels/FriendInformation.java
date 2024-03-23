package ru.dvteam.itcollabhub.classmodels;

public class FriendInformation {
    private final String friendName;
    private final String friendPhoto;
    private final String friendId;
    private final String project;
    private final int score;
    private final boolean find;

    public FriendInformation(String friendName, String friendPhoto, String friendId, String project, int score, boolean find) {
        this.friendName = friendName;
        this.friendPhoto = friendPhoto;
        this.friendId = friendId;
        this.project = project;
        this.score = score;
        this.find = find;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendPhoto() {
        return friendPhoto;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getProject() {
        return project;
    }

    public int getScore() {
        return score;
    }

    public boolean isFind() {
        return find;
    }
}
