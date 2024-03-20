package ru.dvteam.itcollabhub.classmodels;

public class UserLinks {
    private final String tgLink;
    private final String vkLink;
    private final String webLink;

    public String getTgLink() {
        return tgLink;
    }

    public String getVkLink() {
        return vkLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public UserLinks(String tgLink, String vkLink, String webLink) {
        this.tgLink = tgLink;
        this.vkLink = vkLink;
        this.webLink = webLink;
    }
}
