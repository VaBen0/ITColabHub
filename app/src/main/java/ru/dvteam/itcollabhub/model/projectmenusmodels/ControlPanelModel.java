package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;
import java.util.Arrays;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackWith2Data;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ControlPanelModel {
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final PostDatas postDatas = new PostDatas();
    private ArrayList<String> ids = new ArrayList<>();
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

                        callback.ProjectInformation(new ProjectInformation(name, photo, description, isend, purposes, problems, peoples,
                                time, time1, tg, vk, webs, purposesids, problemsids, isl, tasks, isOpen, isVisible));
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
                                    for(int i = 0; i < inf.length; i += 3) {
                                        data.add(new DataOfWatcher(inf[i], inf[i+1], inf[i+2], false));
                                    }
                                    callback.invoke(data);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
