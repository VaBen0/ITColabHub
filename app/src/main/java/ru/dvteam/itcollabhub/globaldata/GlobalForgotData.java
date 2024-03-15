package ru.dvteam.itcollabhub.globaldata;

public class GlobalForgotData {
    private static GlobalForgotData INSTANCE;
    private String EmailAddress;

    private GlobalForgotData() {
    }

    public static GlobalForgotData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalForgotData();
        }
        return INSTANCE;
    }

    public void setRegisterData(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getEmailAddress(){
        return EmailAddress;
    }
}
