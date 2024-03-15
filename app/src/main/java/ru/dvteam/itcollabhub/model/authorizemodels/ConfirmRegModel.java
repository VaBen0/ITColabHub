package ru.dvteam.itcollabhub.model.authorizemodels;

import android.content.SharedPreferences;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.globaldata.GlobalRegisterData;

public class ConfirmRegModel {
    private final String code;
    private final PostDatas postDatas = new PostDatas();

    public ConfirmRegModel(String code) {
        this.code = code;
    }

    public static Boolean checkUserCode(String code){
        return code.length() == 6;
    }

    public void confirmReg(SharedPreferences sPref, CallBackBoolean callback, CallBackBoolean callback2){
        GlobalRegisterData globalRegisterData = GlobalRegisterData.getInstance();
        postDatas.postDataConfirm("CheckerCode", globalRegisterData.getEmailAddress(), code, new CallBackInt() {
            @Override
            public void invoke(String res) {
                if(res.equals("Проверка почты прошла успешно")) {
                    postDatas.postDataRegUser("RegNewUser", globalRegisterData.getEmailAddress(),
                            globalRegisterData.getPassword(), "", new CallBackInt() {
                        @Override
                        public void invoke(String res1) {
                            if (res1.equals("Успешная регистрация")) {
                                SharedPreferences.Editor ed = sPref.edit();
                                ed.putString("UserReg", "true");
                                ed.putString("UserMail", globalRegisterData.getEmailAddress());
                                ed.apply();

                                callback2.invoke(true);
                            }else{
                                callback2.invoke(false);
                            }
                        }
                    });
                }
                else{
                    callback.invoke(false);
                }
            }
        });
    }
}
