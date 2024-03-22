package ru.dvteam.itcollabhub.model.projectmenusmodels;

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

public class EditFileModel {
    private final PostDatas postDatas = new PostDatas();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLog = GlobalProjectInformation.getInstance().getProjectLog();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private final String filePhoto = ClassForChangeInfo.getInstance().getPhoto();
    private final String fileId = ClassForChangeInfo.getInstance().getId();

    private String mediaPath = "", fileName = ClassForChangeInfo.getInstance().getName(),
            fileLink = ClassForChangeInfo.getInstance().getText();

    public void saveFileChanges(CallBackBoolean callback){
        System.out.println(fileName + " " + fileLink);
        if(mediaPath.isEmpty()){
            postDatas.postDataChangeFileWithoutImage("ChangeFileWithoutImage", fileName,
                    fileLink, projectId, userMail, fileId, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            callback.invoke(true);
                        }
                    });
        } else{
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            postDatas.postDataChangeFile("ChangeFile", fileName, requestBody,
                    fileLink, projectId, userMail, fileId, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            callback.invoke(true);
                        }
                    });
        }
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public String getFilePhoto() {
        return filePhoto;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
