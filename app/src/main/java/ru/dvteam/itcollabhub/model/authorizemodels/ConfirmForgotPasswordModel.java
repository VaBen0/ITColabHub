package ru.dvteam.itcollabhub.model.authorizemodels;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.globaldata.GlobalForgotData;

public class ConfirmForgotPasswordModel {
    private final String code;
    private final PostDatas postDatas = new PostDatas();

    public ConfirmForgotPasswordModel(String code) {
        this.code = code;
    }

    public static Boolean checkUserCode(String code){
        return code.length() == 6;
    }

    public void confirmForgotPass(CallBackBoolean callback){
        GlobalForgotData globalForgotData = GlobalForgotData.getInstance();
        System.out.println(globalForgotData.getEmailAddress());

        postDatas.postDataConfirm("UserLogInMai2l", globalForgotData.getEmailAddress(), code, new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Проверка почты прошла успешно"));
            }
        });
    }
}
