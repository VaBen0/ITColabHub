package ru.dvteam.itcollabhub.classmodels;

public class RolesInformation {
    private final String roleName;
    private final String firstParticipant;
    private final String urlPhotoFirstParticipant;
    private final String urlPhotoSecondParticipant;
    private final String urlPhotoThirdParticipant;
    private final String urlPhotoFourthParticipant;
    private final int numOfParticipants;
    private final String roleColor;

    public RolesInformation(String roleName, String firstParticipant, String urlPhotoFirstParticipant, String urlPhotoSecondParticipant,
                            String urlPhotoThirdParticipant, int numOfParticipants, String roleColor, String urlPhotoFourthParticipant) {
        this.roleName = roleName;
        this.firstParticipant = firstParticipant;
        this.urlPhotoFirstParticipant = urlPhotoFirstParticipant;
        this.urlPhotoSecondParticipant = urlPhotoSecondParticipant;
        this.urlPhotoThirdParticipant = urlPhotoThirdParticipant;
        this.numOfParticipants = numOfParticipants;
        this.roleColor = roleColor;
        this.urlPhotoFourthParticipant = urlPhotoFourthParticipant;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getFirstParticipant() {
        return firstParticipant;
    }

    public String getUrlPhotoFirstParticipant() {
        return urlPhotoFirstParticipant;
    }

    public String getUrlPhotoSecondParticipant() {
        return urlPhotoSecondParticipant;
    }

    public String getUrlPhotoThirdParticipant() {
        return urlPhotoThirdParticipant;
    }

    public String getUrlPhotoFourthParticipant() {
        return urlPhotoFourthParticipant;
    }

    public int getNumOfParticipants() {
        return numOfParticipants;
    }

    public String getRoleColor() {
        return roleColor;
    }
}
