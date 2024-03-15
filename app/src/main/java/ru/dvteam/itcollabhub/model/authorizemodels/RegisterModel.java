package ru.dvteam.itcollabhub.model.authorizemodels;

import android.util.Patterns;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.globaldata.GlobalRegisterData;

public class RegisterModel {
    private final String emailAddress;
    private final String password;
    private final PostDatas postDatas = new PostDatas();

    public RegisterModel(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    static public Boolean checkEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    static public Boolean checkPassLength(String pass){
        return pass.length() > 5;
    }

    static public Boolean checkPassesSimilarity(String pass, String passAgain){
        return pass.equals(passAgain);
    }

    public void registerUser(CallBackBoolean callback){
        GlobalRegisterData globalRegisterData = GlobalRegisterData.getInstance();
        globalRegisterData.setRegisterData(emailAddress, password);
        postDatas.postDataPostCodeMail("PostToNewUserCode", emailAddress, new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Код отправлен"));
            }
        });
    }
}
