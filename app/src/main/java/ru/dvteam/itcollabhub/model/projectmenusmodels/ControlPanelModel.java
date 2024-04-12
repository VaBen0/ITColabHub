package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;
import java.util.Arrays;

import ru.dvteam.itcollabhub.callbackclasses.CallBackDeadlineInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
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
    private ArrayList<DataOfWatcher> tasks = new ArrayList<>();
    private String projectLog = "";
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
                        globalProjectInformation.setWebLink(webs);
                        globalProjectInformation.setDescription(description);
                        globalProjectInformation.setLead(isl.equals("1"));
                        projectLog = photo;

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
                        ids = new ArrayList<>();
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
                                    setTasks(data, callback);
                                }
                            });
                        }else {
                            setTasks(data, callback);
                        }

                    }
                });
            }
        });
    }

    public void setTasks(ArrayList<DataOfWatcher> data, CallBackWith2Data callback){
        postDatas.postDataGetProjectTasks("GetTasksFromProject", projectId, userMail, new CallBackTasksInfo()  {
            @Override
            public void invoke(String s1, String s2, String s3, String s4) {
                if(!s1.equals("Ошибка")) {
                    String[] idsArr = s1.split("\uD83D\uDD70");
                    String[] namesArr = s2.split("\uD83D\uDD70");
                    String[] textsArr = s3.split("\uD83D\uDD70");
                    String[] completeArr = s4.split("\uD83D\uDD70");
                    ArrayList<DataOfWatcher> tasksDataMini = new ArrayList<>();
                    for (int i = 0; i < namesArr.length; i++) {
                        tasksDataMini.add(0, new DataOfWatcher(namesArr[i], textsArr[i], projectLog, true));
                    }
                    data.addAll(tasksDataMini);
                    callback.invoke(data);
                }
            }
        });
    }
    public void setDeadlines(ArrayList<DataOfWatcher> data, CallBackWith2Data callback){
        postDatas.postDataGetProjectDeadlines("GetTasksFromProject", projectId, userMail, new CallBackDeadlineInfo()  {
            @Override
            public void invoke(String s1, String s2, String s3, String s4, String s5) {
                if(!s1.equals("Ошибка")) {
                    String[] idsArr = s1.split("\uD83D\uDD70");
                    String[] namesArr = s2.split("\uD83D\uDD70");
                    String[] textsArr = s3.split("\uD83D\uDD70");
                    String[] completeArr = s4.split("\uD83D\uDD70");
                    ArrayList<DataOfWatcher> tasksDataMini = new ArrayList<>();
                    for (int i = 0; i < namesArr.length; i++) {
                        data.add(new DataOfWatcher(namesArr[i], textsArr[i], projectLog, true));
                    }
                    callback.invoke(data);
                }
            }
        });
    }

}
