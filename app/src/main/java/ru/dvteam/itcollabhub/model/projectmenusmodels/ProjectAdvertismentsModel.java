package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackWith2Data;
import ru.dvteam.itcollabhub.classmodels.ClassForChangeInfo;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.classmodels.FileInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ProjectAdvertismentsModel {
    private final PostDatas postDatas = new PostDatas();
    private final String projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLog = GlobalProjectInformation.getInstance().getProjectLog();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private ArrayList<DataOfWatcher> arr = new ArrayList<>();

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog() {
        return projectLog;
    }

    public void createAdsWithoutImage(String advName, String advDescr, CallBackBoolean callback){
        postDatas.postDataCreateAdvertWithoutImage("CreateAdWithoutImage", advName,
            advDescr, projectId, userMail, new CallBackInt() {
                @Override
                public void invoke(String res) {
                    callback.invoke(true);
                }
        });
    }

    public void createAds(String advName, String advDescr, String mediaPath, CallBackBoolean callback){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataCreateAdvert("CreateAd", advName, requestBody,
                advDescr, projectId, userMail, new CallBackInt() {
                    @Override
                    public void invoke(String res) {
                        callback.invoke(true);
                    }
                });
    }

    public Boolean checkIsEmpty(String str){
        return !str.isEmpty();
    }

    public void deleteAds(String id){
        System.out.println("projectId: " + projectId + " AdId: " + id + " userMail: " + userMail);
        postDatas.postDataDeleteAd("DeleteAd", projectId, userMail, id, new CallBackInt() {
            @Override
            public void invoke(String res) {

            }
        });
    }

    public void getAds(CallBackWith2Data callback){
        postDatas.postDataGetProjectAdsIds("GetProjectAdsIds", projectId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                postDatas.postDataGetProjectAdsIds("GetProjectAdsIds2", projectId, new CallBackInt() {
                    @Override
                    public void invoke(String res2) {
                        ArrayList<String> ids = new ArrayList<>();
                        if(!res.isEmpty()) {
                            String[] res11 = res.split(",");
                            ids.addAll(Arrays.asList(res11));
                        }
                        if(!res2.isEmpty()) {
                            String[] res21 = res2.split(",");
                            ids.addAll(Arrays.asList(res21));
                        }


                        ArrayList<DataOfWatcher> data = new ArrayList<>();
                        if (!res.isEmpty() || !res2.isEmpty()) {
                            postDatas.postDataGetProjectAds("GetProjectAds", String.join(",", ids), new CallBackInt() {
                                @Override
                                public void invoke(String res) {
                                    String[] inf = res.split("\uD83D\uDD70");
                                    int cnt = 0;
                                    for(int i = 0; i < inf.length; i += 3) {
                                        data.add(new DataOfWatcher(inf[i], inf[i+1], inf[i+2], false, ids.get(cnt)));
                                        cnt++;
                                    }
                                    arr = data;
                                    callback.invoke(data);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void changeAd(int position, CallBackBoolean callback){
        DataOfWatcher data = arr.get(position);
        ClassForChangeInfo classS = ClassForChangeInfo.getInstance();
        classS.setId(data.getId());
        classS.setName(data.getObjTitle());
        classS.setPhoto(data.getObjImg());
        classS.setText(data.getObjDesc());
        callback.invoke(true);
    }
}
