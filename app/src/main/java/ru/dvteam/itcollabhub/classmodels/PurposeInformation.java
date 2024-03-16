package ru.dvteam.itcollabhub.classmodels;

public class PurposeInformation {
    public static int countTicked = 0;
    private final String purpName;
    private final String purpImage;
    private final String purpDesc;
    private final Boolean ticked;
    private final String purpId;

    public PurposeInformation(String purpName, String purpImage, String purpDesc, Boolean ticked, String purpId) {
        this.purpName = purpName;
        this.purpImage = purpImage;
        this.purpDesc = purpDesc;
        this.ticked = ticked;
        this.purpId = purpId;
    }

    public String getPurpName() {
        return purpName;
    }
    public String getPurpId() {
        return purpId;
    }
    public String getPurpImage() {
        return purpImage;
    }

    public String getPurpDesc() {
        return purpDesc;
    }

    public Boolean getTicked() {
        return ticked;
    }
    public static int getCountTicked(){
        return countTicked;
    }
}
