package ru.dvteam.itcollabhub.classmodels;

public class FileInformation {
    private final String link;
    private final String name;
    private final String fixed;
    private final String img;
    private final String fileId;
    public static int cntFixed = 0;

    public FileInformation(String link, String name, String fixed, String img, String fileId) {
        this.link = link;
        this.name = name;
        this.fixed = fixed;
        this.img = img;
        this.fileId = fileId;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getFixed() {
        return fixed;
    }
    public String getFileId(){
        return fileId;
    }
    public String getImg() {
        return img;
    }
    public static int getCntFixed(){
        return cntFixed;
    }
}
