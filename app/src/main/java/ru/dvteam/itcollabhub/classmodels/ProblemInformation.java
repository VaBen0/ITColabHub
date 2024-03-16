package ru.dvteam.itcollabhub.classmodels;

public class ProblemInformation {
    public static int countTicked = 0;
    private final String problemName;
    private final String problemImage;
    private final String problemDesc;
    private final Boolean ticked;
    private final String problemId;

    public ProblemInformation(String purpName, String purpImage, String purpDesc, Boolean ticked, String purpId) {
        this.problemName = purpName;
        this.problemImage = purpImage;
        this.problemDesc = purpDesc;
        this.ticked = ticked;
        this.problemId = purpId;
    }

    public String getProblemName() {
        return problemName;
    }
    public String getProblemId() {
        return problemId;
    }
    public String getProblemImage() {
        return problemImage;
    }

    public String getProblemDesc() {
        return problemDesc;
    }

    public Boolean getTicked() {
        return ticked;
    }
    public static int getCountTicked(){
        return countTicked;
    }
}
