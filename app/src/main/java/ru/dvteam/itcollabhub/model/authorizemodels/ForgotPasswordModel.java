package ru.dvteam.itcollabhub.model.authorizemodels;

import android.util.Patterns;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.globaldata.GlobalForgotData;

public class ForgotPasswordModel {
    private final String userMail;
    private final PostDatas post = new PostDatas();

    public ForgotPasswordModel(String mail){
        userMail = mail;
    }

    public static Boolean checkMail(String mail){
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    public void getMailCode(CallBackBoolean callback){
        GlobalForgotData globalForgotData = GlobalForgotData.getInstance();
        globalForgotData.setRegisterData(userMail);
        post.postDataPostCodeMail("UserLogInMail", userMail, new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Код отправлен"));
            }
        });
    }
}
