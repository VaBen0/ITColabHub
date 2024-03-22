package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackPurpInfo;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class PurposeModel {
    private final String projectId = ProjectId.getInstance().getProjectId();
    private int purpsCnt = 0;

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectPhoto() {
        return projectPhoto;
    }

    private final String projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectPhoto = GlobalProjectInformation.getInstance().getProjectLog();
    private final String mail = MailGlobalInfo.getInstance().getUserMail();
    private final PostDatas postDatas = new PostDatas();

    public void setPurposes(CallBackPurpInfo callback){
        postDatas.postDataGetProjectPurposes("GetProjectPurposesIDs", projectId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                postDatas.postDataGetPurpose("GetPurposes", res, new CallBackInt() {
                    @Override
                    public void invoke(String res1) {
                        PurposeInformation.countTicked = 0;
                        String[] inf = res1.split("\uD83d\uDD70");
                        String[] ids = res.split(",");
                        ArrayList<PurposeInformation> purposes = new ArrayList<>();
                        purpsCnt = ids.length;
                        int cnt = 0;

                        for(int i = 0; i < inf.length; i += 4){
                            if(inf[i + 2].equals("1")) PurposeInformation.countTicked += 1;
                            if(GlobalProjectInformation.getInstance().getEnd()){
                                inf[i+2] = "1";
                            }
                            purposes.add(new PurposeInformation(inf[i], inf[i + 3], inf[i + 1], inf[i + 2].equals("1"), ids[cnt]));
                            cnt++;
                        }
                        callback.invoke(purposes);
                    }
                });
            }
        });
    }

    public void setPurposeComplete(String purpId){
        postDatas.postDatasetPurposeIsEnd("SetPurposeComplete", purpId, projectId, mail, new CallBackInt() {
            @Override
            public void invoke(String res) {
                PurposeInformation.countTicked += 1;
            }
        });
    }

    public Boolean checkEmpty(String str){
        return !str.isEmpty();
    }

    public void createPurpWithoutImage(String purpName, String purpDescr){
        postDatas.postDataCreatePurposeWithoutImage("CreatePurposeWithoutImage", purpName,
            purpDescr, projectId, mail, new CallBackInt() {
                @Override
                public void invoke(String res) {

                }
            });
    }
    public void createPurp(String purpName, String purpDescr, String mediaPath){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataCreatePurpose("CreatePurpose", purpName, requestBody,
            purpDescr, mail, projectId, new CallBackInt() {
                @Override
                public void invoke(String res) {

                }
            });
    }

    public int getPurpsCnt(){
        return purpsCnt;
    }


}
