package ru.dvteam.itcollabhub.globaldata;

public class MailGlobalInfo {
    private static MailGlobalInfo INSTANCE;
    private String userMail;
    public MailGlobalInfo(){

    }

    public static MailGlobalInfo getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MailGlobalInfo();
        }
        return INSTANCE;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserMail(){
        return userMail;
    }
}
