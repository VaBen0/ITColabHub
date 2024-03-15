package ru.dvteam.itcollabhub.model.authorizemodels;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.globaldata.GlobalRegisterData;

public class CreateAccountModel {
    private final String name;
    private final String mediaPath;

    public CreateAccountModel(String name, String mediaPath){
        this.name = name;
        this.mediaPath = mediaPath;
    }

    public static Boolean checkCorrectlyName(String enteredName){
        return enteredName.length() > 2 && enteredName.length() < 16;
    }

    public void createAccount(CallBackBoolean callback){
        GlobalRegisterData globalRegisterData = GlobalRegisterData.getInstance();
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        PostDatas post = new PostDatas();
        post.postDataCreateAccount("CreateNameLog", name, requestBody, globalRegisterData.getEmailAddress(), new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(res.equals("Сохранено"));
            }
        });
    }
}
