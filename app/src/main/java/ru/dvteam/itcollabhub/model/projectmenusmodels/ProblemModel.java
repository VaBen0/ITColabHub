package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProblemInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackPurpInfo;
import ru.dvteam.itcollabhub.classmodels.ClassForChangeInfo;
import ru.dvteam.itcollabhub.classmodels.DataOfWatcher;
import ru.dvteam.itcollabhub.classmodels.ProblemInformation;
import ru.dvteam.itcollabhub.classmodels.PurposeInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ProblemModel {
    private final String projectId = ProjectId.getInstance().getProjectId();
    private int problemsCnt = 0;

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
    private ArrayList<ProblemInformation> arr = new ArrayList<>();

    public void setProblems(CallBackProblemInfo callback){
        postDatas.postDataGetProjectProblems("GetProjectProblemsIDs", projectId, new CallBackInt() {
            @Override
            public void invoke(String res) {
                postDatas.postDataGetPurpose("GetProblems", res, new CallBackInt() {
                    @Override
                    public void invoke(String res1) {
                        ProblemInformation.countTicked = 0;
                        String[] inf = res1.split("\uD83d\uDD70");
                        String[] ids = res.split(",");
                        ArrayList<ProblemInformation> problems = new ArrayList<>();
                        problemsCnt = ids.length;
                        int cnt = 0;
                        for(int i = 0; i < inf.length; i += 4){
                            if(inf[i + 2].equals("1")) ProblemInformation.countTicked += 1;
                            problems.add(new ProblemInformation(inf[i], inf[i + 3], inf[i + 1], inf[i + 2].equals("1"), ids[cnt]));
                            cnt++;
                        }
                        arr = problems;
                        callback.invoke(problems);
                    }
                });
            }
        });
    }

    public void setProblemComplete(String purpId){
        postDatas.postDatasetProblemIsEnd("SetProblemComplete", purpId, projectId, mail, new CallBackInt() {
            @Override
            public void invoke(String res) {
                PurposeInformation.countTicked += 1;
            }
        });
    }

    public Boolean checkEmpty(String str){
        return !str.isEmpty();
    }

    public void createProblemWithoutImage(String purpName, String purpDescr){
        postDatas.postDataCreateProblemWithoutImage("CreateProblemWithoutImage", purpName,
                purpDescr, projectId, mail, new CallBackInt() {
                    @Override
                    public void invoke(String res) {

                    }
                });
    }
    public void createProblem(String purpName, String purpDescr, String mediaPath){
        File file = new File(mediaPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        postDatas.postDataCreateProblem("CreateProblem", purpName, requestBody,
                purpDescr, mail, projectId, new CallBackInt() {
                    @Override
                    public void invoke(String res) {

                    }
                });
    }
    public int getProblemsCnt(){
        return problemsCnt;
    }
    public void onChange(int position, CallBackBoolean callback){
        ProblemInformation data = arr.get(position);
        ClassForChangeInfo classS = ClassForChangeInfo.getInstance();
        classS.setId(data.getProblemId());
        classS.setName(data.getProblemName());
        classS.setPhoto(data.getProblemImage());
        classS.setText(data.getProblemDesc());
        callback.invoke(true);
    }
}
