package ru.dvteam.itcollabhub.di.component;

import javax.inject.Singleton;

import dagger.Component;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.MainActivity;
import ru.dvteam.itcollabhub.view.authorizeviews.ConfirmReg;
import ru.dvteam.itcollabhub.view.authorizeviews.LogIn;
import ru.dvteam.itcollabhub.view.profileviews.activities.ChooseThemeForApp;
import ru.dvteam.itcollabhub.view.profileviews.activities.EditProfile;
import ru.dvteam.itcollabhub.view.profileviews.activities.FriendProfile;
import ru.dvteam.itcollabhub.view.profileviews.activities.GetFriend;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.create.CreateProject2;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.ControlPanelDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.CreateProject2Demo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.DemoProjectPage;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.EditProjectDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.ProjectProblemsDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.ProjectPurposesDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.demo.UsersProjectDemo;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.ActivityProject;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.EndProject;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.ProjectRequests;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.UsersProject2;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.ActivityTaskMenuForLeader;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.ActivityWork;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.AnswerForTask;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.CompltedTasksByParticipants;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.CreateDedline2;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.PartisipantTasks;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.tasks.TaskMenuActivity;

@Singleton
@Component(modules = {SharedPreferencesModule.class})
public interface AppComponent {
    void inject(Profile profile);
    void inject(LogIn logIn);
    void inject(ChooseThemeForApp chooseThemeForApp);
    void inject(EditProfile editProfile);
    void inject(MainActivity mainActivity);
    void inject(ConfirmReg confirmReg);
    void inject(GetFriend getFriend);
    void inject(CreateProject2 createProject2);
    void inject(ProjectRequests projectRequests);
    void inject(PartisipantTasks partisipantTasks);
    void inject(TaskMenuActivity taskMenuActivity);
    void inject(CreateDedline2 createDedline2);
    void inject(CompltedTasksByParticipants compltedTasksByParticipants);
    void inject(AnswerForTask answerForTask);
    void inject(ActivityWork activityWork);
    void inject(ActivityTaskMenuForLeader createDedline2);
    void inject(EndProject endProject);
    void inject(UsersProject2 usersProject2);
    void inject(CreateProject2Demo usersProject2);
    void inject(DemoProjectPage demoProjectPage);
    void inject(ControlPanelDemo controlPanelDemo);
    void inject(EditProjectDemo editProjectDemo);
    void inject(ProjectProblemsDemo projectProblemsDemo);
    void inject(ProjectPurposesDemo projectPurposesDemo);
    void inject(UsersProjectDemo usersProjectDemo);
    void inject(ActivityProject activityProject);
    void inject(FriendProfile friendProfile);
}
