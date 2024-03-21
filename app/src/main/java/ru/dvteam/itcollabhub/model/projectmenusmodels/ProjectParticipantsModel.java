package ru.dvteam.itcollabhub.model.projectmenusmodels;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.R;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.callbackclasses.CallBackUserInformation;
import ru.dvteam.itcollabhub.classmodels.UserInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.ProjectParticipants;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.UsersProject;

public class ProjectParticipantsModel {
    private final PostDatas postDatas = new PostDatas();
    private final GlobalProjectInformation globalProjectInformation = GlobalProjectInformation.getInstance();
    private final String projectTitle = globalProjectInformation.getProjectTitle();
    private final String projectLog = globalProjectInformation.getProjectLog();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private ArrayList<UserInformation> usersFromProject = new ArrayList<>();
    private String nameOfFindParticipant = "";
    private final Boolean isl = GlobalProjectInformation.getInstance().getLead();

    public void getProjectUsers(CallBackUserInformation callback){
        postDatas.postDataGetProjectParticipant("GetPeoplesFromProjects", projectId, userMail, new CallBackInt5() {
            @Override
            public void invoke(String ids, String names, String photos) {
                String[] idsArr = ids.split("\uD83D\uDD70");
                String[] namesArr = names.split("\uD83D\uDD70");
                String[] photosArr = photos.split("\uD83D\uDD70");

                ArrayList<UserInformation> userInformations = new ArrayList<>();

                for (int i = 0; i < idsArr.length; i++) {
                    userInformations.add(new UserInformation(namesArr[i], photosArr[i], idsArr[i]));
                }
                usersFromProject = userInformations;
                callback.invoke(userInformations);
            }
        });
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public ArrayList<UserInformation> findUser(){
        ArrayList<UserInformation> array = new ArrayList<>();

        for(UserInformation i : usersFromProject){
            if(i.getUserName().toLowerCase().contains(nameOfFindParticipant.toLowerCase())){
                array.add(i);
            }
        }

        return array;
    }

    public Boolean getIsl() {
        return isl;
    }

    public void setNameOfFindParticipant(String nameOfFindParticipant) {
        this.nameOfFindParticipant = nameOfFindParticipant;
    }
}
