package ru.dvteam.itcollabhub.model.projectmenusmodels;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class UsersProject3Model {
    private final PostDatas postDatas = new PostDatas();
    public void getAllProjectInfo(CallBackProjectInformation callback){
        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        ProjectId projectId = ProjectId.getInstance();
        System.out.println(projectId.getProjectId());
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", projectId.getProjectId(),
                mailGlobalInfo.getUserMail(), new CallBackInt4() {
                    @Override
                    public void invoke(String name, String photo, String description, double isend, String purposes,
                                       String problems, String peoples, String time, String time1, String tg, String vk, String webs, String purposesids,
                                       String problemsids, String isl, String tasks, String isOpen, String isVisible) {
                        callback.ProjectInformation(new ProjectInformation(name, photo, description, isend, purposes, problems, peoples, time, time1,
                                tg, vk, webs, purposesids, problemsids, isl, tasks, isOpen, isVisible));
                    }
                });
    }
}
