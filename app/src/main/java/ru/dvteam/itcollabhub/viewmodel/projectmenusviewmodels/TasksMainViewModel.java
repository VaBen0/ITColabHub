package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.classmodels.TasksClass;
import ru.dvteam.itcollabhub.model.projectmenusmodels.TasksMainModel;

public class TasksMainViewModel extends ViewModel {
    private final TasksMainModel tasksMainModel = new TasksMainModel();
    private MutableLiveData<ArrayList<TasksClass>> tasksData;
    private MutableLiveData<ArrayList<TasksClass>> deadlinesData;
    private MutableLiveData<Long> tasksNum;
    private MutableLiveData<String> deadlinesNum;
    public String getProjectTitle(){
        return tasksMainModel.getProjectTitle();
    }
    public String getProjectLog(){
        return tasksMainModel.getProjectLog();
    }
    public LiveData<ArrayList<TasksClass>> getTasksData(){
        if(tasksData == null){
            tasksData = new MutableLiveData<>();
        }
        return tasksData;
    }
    public LiveData<ArrayList<TasksClass>> getDeadlinesData(){
        if(deadlinesData == null){
            deadlinesData = new MutableLiveData<>();
        }
        return deadlinesData;
    }
    public LiveData<Long> getTasksNum(){
        if(tasksNum == null){
            tasksNum = new MutableLiveData<>();
        }
        return tasksNum;
    }
    public LiveData<String> getDeadlinesNum(){
        if(deadlinesNum == null){
            deadlinesNum = new MutableLiveData<>();
        }
        return deadlinesNum;
    }
    public void setTasks(){
        tasksMainModel.getTasks(() -> {
            tasksData.setValue(tasksMainModel.getTasksArray());

        });
    }
    public void setDeadlines(){
        tasksMainModel.getDeadlines(() -> {
            deadlinesData.setValue(tasksMainModel.getDeadlinesArray());
            deadlinesNum.setValue(tasksMainModel.getDeadlinesNum());
            tasksNum.setValue(tasksMainModel.getMinMillis());
        });
    }
    public String getId(){
        return tasksMainModel.getProjectId();
    }

    public TasksClass getOneTask(String position){
        return tasksMainModel.getOneTask(position);
    }
    public TasksClass getOneDeadline(String position){
        return tasksMainModel.getOneDeadline(position);
    }

    public Boolean getIsl() {
        return tasksMainModel.getIsl();
    }

}
