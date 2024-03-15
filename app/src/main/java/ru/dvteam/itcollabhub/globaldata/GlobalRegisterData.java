package ru.dvteam.itcollabhub.globaldata;

public class GlobalRegisterData {
    private static GlobalRegisterData INSTANCE;
    private String EmailAddress;
    private String Password;

    private GlobalRegisterData() {
    }

    public static GlobalRegisterData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GlobalRegisterData();
        }
        return INSTANCE;
    }

    public void setRegisterData(String emailAddress, String password) {
        EmailAddress = emailAddress;
        Password = password;
    }

    public String getEmailAddress(){
        return EmailAddress;
    }

    public String getPassword(){
        return Password;
    }
}
