package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDeadlineInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
import ru.dvteam.itcollabhub.classmodels.TasksClass;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.globaldata.ProjectId;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class TasksMainModel {
    private final PostDatas postDatas = new PostDatas();
    private final String projectId = ProjectId.getInstance().getProjectId();
    private final String projectTitle = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLog = GlobalProjectInformation.getInstance().getProjectLog();
    private final String userMail = MailGlobalInfo.getInstance().getUserMail();
    private ArrayList<TasksClass> tasksMain = new ArrayList<>();
    private ArrayList<TasksClass> deadlinesMain = new ArrayList<>();
    private int tasksNum = 0, completedTasksNum = 0, deadlinesNum = 0, completedDeadlinesNum = 0;
    private final Boolean isl = GlobalProjectInformation.getInstance().getLead();
    private final Boolean isEnd = GlobalProjectInformation.getInstance().getEnd();
    private long minMillis = 999999999;


    public void getTasks(CallBack callBack){
        postDatas.postDataGetProjectTasks("GetTasksFromProject", projectId, userMail, new CallBackTasksInfo()  {
            @Override
            public void invoke(String s1, String s2, String s3, String s4) {

                if (!s1.equals("Ошибка")) {
                    String[] idsArr = s1.split("\uD83D\uDD70");
                    String[] namesArr = s2.split("\uD83D\uDD70");
                    String[] textsArr = s3.split("\uD83D\uDD70");
                    String[] completeArr = s4.split("\uD83D\uDD70");
                    ArrayList<TasksClass> tasks = new ArrayList<>();
                    tasksNum = 0;
                    completedTasksNum = 0;
                    for (int i = 0; i < namesArr.length; i++) {
                        if(isEnd){
                            completeArr[i] = "1";
                        }
                        tasks.add(new TasksClass(namesArr[i], textsArr[i], idsArr[i], completeArr[i],0));
                        tasksNum += 1;
                        if(completeArr[i].equals("1")) completedTasksNum += 1;
                    }
                    tasksMain = tasks;
                    callBack.invoke();
                }
            }

        });
    }

    public void getDeadlines(CallBack callBack){
        postDatas.postDataGetProjectDeadlines("GetDeadlinesFromProject", projectId, userMail, new CallBackDeadlineInfo()  {
            @Override
            public void invoke(String ids, String names, String taxts, String completes, String dates) {
                System.out.println(dates);

                if (!ids.equals("Ошибка")) {
                    deadlinesNum = 0;
                    completedDeadlinesNum = 0;
                    String[] idsArr = ids.split("\uD83D\uDD70");
                    String[] namesArr = names.split("\uD83D\uDD70");
                    String[] textsArr = taxts.split("\uD83D\uDD70");
                    String[] completeArr = completes.split("\uD83D\uDD70");
                    String[] date = dates.split("\uD83D\uDD70");
                    ArrayList<TasksClass> deadlines = new ArrayList<>();
                    for (int i = 0; i < namesArr.length; i++) {
                        if(isEnd){
                            completeArr[i] = "1";
                            date[i] = "0";
                        }
                        deadlines.add(new TasksClass(namesArr[i], textsArr[i], idsArr[i], completeArr[i], 0, date[i]));
                        System.out.println(idsArr[i]);
                        deadlinesNum += 1;
                        if(!date[i].equals("0") && Long.parseLong(date[i]) > 0) {
                            minMillis = Math.min(minMillis, Long.parseLong(date[i]));
                        }
                        if(completeArr[i].equals("1")) completedDeadlinesNum += 1;
                    }
                    if(minMillis == 999999999){
                        minMillis = 0;
                    }
                    deadlinesMain = deadlines;
                    callBack.invoke();
                }
            }

        });
    }

    public ArrayList<TasksClass> getTasksArray(){
        return tasksMain;
    }

    public ArrayList<TasksClass> getDeadlinesArray(){
        return deadlinesMain;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getProjectLog(){
        return projectLog;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getTasksNum() {
        return tasksNum + "/" + completedTasksNum;
    }

    public String getDeadlinesNum() {
        if(deadlinesNum == 0){
            return "0";
        }
        return completedDeadlinesNum + "/" + deadlinesNum;
    }

    public Boolean getIsl() {
        return isl;
    }

    public TasksClass getOneTask(String position){
        return tasksMain.get(Integer.parseInt(position));
    }
    public TasksClass getOneDeadline(String position){
        return deadlinesMain.get(Integer.parseInt(position));
    }

    public long getMinMillis() {
        return minMillis;
    }
}
