package ru.dvteam.itcollabhub.classmodels;

public class ParticipantWithRole {
    private String name;
    private String urlPhoto;
    private String roleColor;
    private String roleName;
    public boolean checked;

    public ParticipantWithRole(String name, String roleColor, String urlPhoto, boolean checked, String roleName) {
        this.name = name;
        this.roleColor = roleColor;
        this.urlPhoto = urlPhoto;
        this.checked = checked;
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public String getRoleColor() {
        return roleColor;
    }

    public boolean isChecked() {
        return checked;
    }

    public String getRoleName() {
        return roleName;
    }
}
