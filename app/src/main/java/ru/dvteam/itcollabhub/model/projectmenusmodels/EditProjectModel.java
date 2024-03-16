package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;

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
                        globalProjectInformation.setProblemsIds(problemsids);
                        globalProjectInformation.setPurposesIds(purposesids);
                        projectInfo = new ProjectInformation(name, photo, description, isend, purposes, problems, peoples,
                                time, time1, tg, vk, webs, purposesids, problemsids, isl, tasks, isOpen, isVisible);
                        callback.ProjectInformation(projectInfo);
                    }
                });
    }
}
