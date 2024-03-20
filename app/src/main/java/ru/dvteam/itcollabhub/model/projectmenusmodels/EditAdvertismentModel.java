package ru.dvteam.itcollabhub.model.projectmenusmodels;

import android.widget.Toast;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.ClassForChangeInfo;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.EditAdvertisment;

public class EditAdvertismentModel {
    private PostDatas postDatas = new PostDatas();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLog = GlobalProjectInformation.getInstance().getProjectLog();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private final String adId = ClassForChangeInfo.getInstance().getId();
    private final String adPhoto = ClassForChangeInfo.getInstance().getPhoto();
    private String mediaPath = "";
    private String name = ClassForChangeInfo.getInstance().getName(),
            description = ClassForChangeInfo.getInstance().getText();


    public void onSaveChanges(CallBackBoolean callback){
        System.out.println(name + " " + description);
        if(mediaPath.isEmpty()){
            postDatas.postDataChangeAdvertWithoutImage("ChangeAdWithoutImage", name,
                    description, projectId, userMail, adId, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            callback.invoke(true);
                        }
                    });
        }
        else {
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            postDatas.postDataChangeAdvert("ChangeAd", name, requestBody,
                    description, projectId, userMail, adId, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            callback.invoke(true);
                        }
                    });
        }
    }

    public void onDelete(CallBackBoolean callback){
        postDatas.postDataDeleteAd("DeleteAd", projectId, userMail, adId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                callback.invoke(true);
            }
        });
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public String getAdPhoto() {
        return adPhoto;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
