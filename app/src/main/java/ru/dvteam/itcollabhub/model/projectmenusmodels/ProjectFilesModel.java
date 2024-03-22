package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.io.File;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.classmodels.ClassForChangeInfo;
import ru.dvteam.itcollabhub.classmodels.FileInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ProjectFilesModel {
    private final PostDatas postDatas = new PostDatas();
    private final GlobalProjectInformation globalProjectInformation = GlobalProjectInformation.getInstance();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private final String projectTitle = globalProjectInformation.getProjectTitle();
    private final String projectLog = globalProjectInformation.getProjectLog();
    private String mediaPath = "", fileNameEdit, fileLinkEdit;
    private ArrayList<FileInformation> mainFileArray = new ArrayList<>();

    public void createFile(CallBackBoolean callback){
        if(!mediaPath.isEmpty()) {
            File file = new File(mediaPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            postDatas.postDataCreateFile("CreateFile", fileNameEdit, requestBody,
                    fileLinkEdit, projectId, userMail, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            mediaPath = "";
                            callback.invoke(true);
                        }
                    });
        }else{
            postDatas.postDataCreateFileWithoutImage("CreateFileWithoutImage", fileNameEdit,
                    fileLinkEdit, projectId, userMail, new CallBackInt() {
                        @Override
                        public void invoke(String res) {
                            callback.invoke(true);
                        }
                    });
        }
    }

    public void updateFilesInfo(CallBackBoolean callback){
        postDatas.postDataGetProjectFilesIds("GetProjectFilesIds", projectId, new CallBackInt() {

            @Override
            public void invoke(String res) {
                System.out.println(projectId + " need");
                postDatas.postDataGetProjectFiles("GetProjectFiles", res, new CallBackInt() {
                    @Override
                    public void invoke(String res1) {
                        System.out.println(res + " res");
                        FileInformation.cntFixed = 0;
                        String[] inf = res1.split("\uD83D\uDD70");
                        String[] idm = res.split(",");
                        ArrayList<FileInformation> files = new ArrayList<>();
                        for(int i = 0; i < inf.length; i += 4) {
                            if(inf[i + 2].equals("1")) {
                                FileInformation.cntFixed += 1;
                                files.add(0, new FileInformation(inf[i + 1], inf[i], inf[i + 2], inf[i + 3], idm[i / 4]));
                            }else files.add(new FileInformation(inf[i + 1], inf[i], inf[i + 2], inf[i + 3], idm[i / 4]));
                        }
                        mainFileArray = files;
                        callback.invoke(false);
                    }
                });
            }
        });

    }

    public void fixFile(String fileId){
        postDatas.postDataFixFile("FixFile", projectId, userMail, fileId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                FileInformation.cntFixed += 1;
            }
        });
    }

    public void detachFile(String fileId){
        postDatas.postDataDetachFile("DetachedFile", projectId, userMail, fileId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                FileInformation.cntFixed -= 1;
            }
        });
    }

    public void deleteFile(String fileId){
        postDatas.postDataDeleteFile("DeleteFile", projectId, userMail, fileId, new CallBackInt() {
            @Override
            public void invoke(String res) {

            }
        });
    }

    public ArrayList<FileInformation> getMainFileArray() {
        return mainFileArray;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public void setFileNameEdit(String fileNameEdit) {
        this.fileNameEdit = fileNameEdit;
    }

    public void setFileLinkEdit(String fileLinkEdit) {
        this.fileLinkEdit = fileLinkEdit;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public String getProjectTitle() {
        System.out.println(projectTitle);
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public Boolean isEmpty(String str){
        return !str.isEmpty();
    }

    public void onChangeClick(int position, CallBackBoolean callback){
        FileInformation file = mainFileArray.get(position);
        ClassForChangeInfo classS = ClassForChangeInfo.getInstance();
        classS.setId(file.getFileId());
        classS.setName(file.getName());
        classS.setPhoto(file.getImg());
        classS.setText(file.getLink());
        callback.invoke(true);
    }
}
