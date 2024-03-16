package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectsArray;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ActivityProjectModel {
    private final PostDatas postDatas = new PostDatas();
    public void getActivityProjects(CallBackProjectsArray callback){
        MailGlobalInfo mailGlobalInfo = MailGlobalInfo.getInstance();
        String mail = mailGlobalInfo.getUserMail();

        ArrayList<ProjectClass> arrayList = new ArrayList<>();

        postDatas.postDataGetUserProjects("GetUserProject", mail, new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split(";");
                if(!inf[0].equals("Нет1проектов564")) {
                    String[] names = inf[0].split(",");
                    String[] photo = inf[1].split(",");
                    String[] id = inf[2].split(",");
                    String[] userName = inf[4].split(",");
                    String[] userImg = inf[5].split(",");
                    String[] userScore = inf[6].split(",");
                    String[] percents = inf[7].split(",");
                    for(int i = 0; i < id.length; i++){
                        arrayList.add(0, new ProjectClass(names[i], photo[i], userName[i], Integer.parseInt(userScore[i]),
                                userImg[i], Integer.parseInt(percents[i]), id[i]));
                    }
                }
                callback.invoke(arrayList);
            }
        });

    }

    public void setProjectId(String projectId){
        ProjectId projectId1 = ProjectId.getInstance();
        projectId1.setProjectId(projectId);
    }
}
