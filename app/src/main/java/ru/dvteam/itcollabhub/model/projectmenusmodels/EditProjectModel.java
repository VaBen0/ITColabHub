package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class EditProjectModel {
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final PostDatas postDatas = new PostDatas();
    private ArrayList<String> ids = new ArrayList<>();
    ProjectInformation projectInfo;
    public void getAllProjectInfo(CallBackProjectInformation callback){
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", projectId,
                userMail, new CallBackInt4() {
                    @Override
                    public void invoke(String name, String photo, String description, double isend, String purposes,
                                       String problems, String peoples, String time, String time1, String tg, String vk, String webs,
                                       String purposesids,
                                       String problemsids, String isl, String tasks, String isOpen, String isVisible) {

                        GlobalProjectInformation globalProjectInformation = GlobalProjectInformation.getInstance();
                        globalProjectInformation.setProjectTitle(name);
                        globalProjectInformation.setProjectLog(photo);
                        projectInfo = new ProjectInformation(name, photo, description, isend, purposes, problems, peoples,
                                time, time1, tg, vk, webs, purposesids, problemsids, isl, tasks, isOpen, isVisible);
                        callback.ProjectInformation(projectInfo);
                    }
                });
    }

    public void saveProjectChangesWithDescriptionWithoutImage(String projectName, String projectDescr, CallBackBoolean callback){
        postDatas.postDataChangeProjectWithoutImageWithDescription("SaveChangesFromProjectWithoutImageAndDescription",
            projectName, projectDescr, projectId, userMail, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        callback.invoke(true);
                    }
                });
    }

    public void saveProjectChangesWithDescription(String projectName, String projectDescr, String mediaPath, CallBackBoolean callback){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataChangeProjectWithDescription("SaveChangesFromProjectWithImageAndDescription",
                projectName, requestBody, projectId, userMail, projectDescr, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        callback.invoke(true);
                    }
                });
    }

    public void saveProjectChangesWithLinksWithoutImage(String projectName, String tgLink, String vkLink,
                                                        String webLink, CallBackBoolean callback){
        postDatas.postDataChangeProjectWithoutImageWithLink("SaveChangesFromProjectWithoutImageAndLinks",
            projectName, tgLink, vkLink, webLink, projectId, userMail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    callback.invoke(true);
                }
            });
    }

    public void saveProjectChangesWithLinks(String projectName, String tgLink, String vkLink,
                                            String webLink, String mediaPath, CallBackBoolean callback){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataChangeProjectWithLink("SaveChangesFromProjectWithImageAndLinks", projectName,
                requestBody, projectId, userMail, tgLink, vkLink, webLink, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        callback.invoke(true);
                    }
                });
    }

    public void saveProjectChangesWithOtherWithoutImage(String projectName, String open, String visible, CallBackBoolean callback){
        System.out.println(projectName);
        postDatas.postDataChangeProjectWithoutImageWithStatus("SaveChangesFromProjectWithoutImageAndIsOpenAndIsVisibile",
                projectName, open, visible, projectId, userMail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    callback.invoke(true);
                }
            });
    }

    public void saveProjectChangesWithOther(String projectName, String open, String visible, String mediaPath, CallBackBoolean callback){
        File file = new File(mediaPath);
        System.out.println(projectName);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataChangeProjectWithStatus("SaveChangesFromProjectWithImageAndIsOpenAndIsVisibile", projectName,
            requestBody, projectId, userMail, open, visible, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    callback.invoke(true);
                }
            });
    }

    public Boolean checkIsEmpty(String str){
        return !str.isEmpty();
    }
}
