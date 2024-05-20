package ru.dvteam.itcollabhub.view.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBack;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectsArray;
import ru.dvteam.itcollabhub.classmodels.ProjectClass;
import ru.dvteam.itcollabhub.classmodels.StatesClass;
import ru.dvteam.itcollabhub.databinding.ActivityForumBinding;
import ru.dvteam.itcollabhub.globaldata.MailGlobalInfo;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.adapter.ActivityProjectAdapter;
import ru.dvteam.itcollabhub.view.adapter.AnswersAdapter;
import ru.dvteam.itcollabhub.view.adapter.ForumProjectsAdapter;
import ru.dvteam.itcollabhub.view.adapter.StatesAdapter;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.projectMenu.ActivityProject;

public class Forum extends AppCompatActivity {

    ActivityForumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForumBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ArrayList<StatesClass> states = createClassElements();
        ArrayList<StatesClass> answers = createAnswersElements();
        getProjects(new CallBackProjectsArray() {
            @Override
            public void invoke(ArrayList<ProjectClass> arrayList) {
                ArrayList<ProjectClass> projects = arrayList;
                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(Forum.this, LinearLayoutManager.HORIZONTAL, false);
                binding.projectsPlace.setLayoutManager(layoutManager);
                ForumProjectsAdapter adapter = new ForumProjectsAdapter(Forum.this, new CallBack() {
                    @Override
                    public void invoke() {

                    }
                }, projects);
                binding.projectsPlace.setAdapter(adapter);
            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.states.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.answers.setLayoutManager(layoutManager1);

        StatesAdapter adapter = new StatesAdapter(this, new CallBack() {
            @Override
            public void invoke() {
                System.out.println("lol");
            }
        }, states);

        AnswersAdapter adapter1 = new AnswersAdapter(this, new CallBack() {
            @Override
            public void invoke() {

            }
        }, answers);


        binding.answers.setAdapter(adapter1);
        binding.states.setAdapter(adapter);

        binding.projectMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum.this, ActivityProject.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });

        binding.profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forum.this, Profile.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
    }

    private ArrayList<StatesClass> createClassElements(){
        ArrayList<StatesClass> vsp = new ArrayList<>();
        String[] titles = {"Хакеры", "RecyclerView", "Для новичков", "Язык программирования", "Человек и код", "Проекты", "Java"};
        String[] texts = {"Специалисты, которые исследуют и анализируют системы информационной безопасности с целью выявления уязвимостей и защиты от атак.",
                        "Компонент в Android SDK, который позволяет эффективно отображать списки данных на экране устройства.",
                        "Начать изучение программирования можно с простых концепций и понятий, таких как переменные, условия и циклы.",
                        "Специальный синтаксис, с помощью которого программисты пишут код для создания программ и приложений.",
                        "Программирование не только про создание программ, но и об умении решать задачи, логически мыслить и творчески подходить к решению проблем.",
                        "Разработка программных проектов требует не только технических навыков, но и умения работать в команде, управлять временем и ресурсами.",
                        "Один из самых популярных языков программирования, используемый для создания различных типов приложений, веб-сервисов и игр."};
        String[] imgs = {"https://avatars.mds.yandex.net/i?id=be516fec878e8b9ccde423829dc7bcae05909e19-10471469-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=36b4a2b633f4ed22732af80de1b8660ec5318a19-10868190-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=2a0000017a01f947ba98b7cb366cb9d9f4b2-4978136-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=0e06e1c298071f5910499e001904cd329283afb9-12153883-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=1d8ae8c3a6f43d0d2e6e9ce1aed2552d293dd2f9-11920397-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=3266d4f3aeed0771cff4146ad25e75008352ad6c-8219252-images-thumbs&n=13",
                        "https://avatars.mds.yandex.net/i?id=0cdcc500541814b2089ac15fd7c659cc15293d77-10651780-images-thumbs&n=13"};
        for(int i = 0; i < titles.length; i++){
            vsp.add(new StatesClass(titles[i], texts[i], imgs[i]));
        }
        return vsp;
    }
    private ArrayList<StatesClass> createAnswersElements(){
        ArrayList<StatesClass> vsp = new ArrayList<>();
        String[] titles = {"Как создать проект?", "Что такое игровой движок?", "Как создать свою игру?"};
        String[] texts = {"Чтобы создать проект, вам сначала нужно определить его цель и задачи, затем разработать план действий и составить бюджет. После этого вы можете начать работу над проектом, нанимая специалистов...",
                            "Игровой движок - это программное обеспечение, которое используется для разработки компьютерных игр. Он предоставляет разработчикам инструменты и ресурсы для создания...",
                            "Чтобы создать свою игру, вам сначала нужно выбрать подходящий игровой движок (например, Unity, Unreal Engine, Godot и др.). Затем вам нужно будет изучить его функционал и возможности..."};
        String[] imgs = {"https://avatars.mds.yandex.net/i?id=7feb7b8a47b686c371cdd65473dc4954f237358e-5331568-images-thumbs&n=13",
                            "https://avatars.mds.yandex.net/i?id=f8d635b4c1fb2ee32783ca1d345dbe0370b9a41a-10850495-images-thumbs&n=13",
                            "https://avatars.mds.yandex.net/i?id=e4d80327a47a76b7f590f28fa58f3b4f068b2e0d-12473832-images-thumbs&n=13"};
        for(int i = 0; i < titles.length; i++){
            vsp.add(new StatesClass(titles[i], texts[i], imgs[i]));
        }
        return vsp;
    }

    private void getProjects(CallBackProjectsArray callBackProjectsArray){

        ArrayList<ProjectClass> arrayList = new ArrayList<>();
        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetUserProjects("GetUserProject", "catvano4551@gmail.com", new CallBackInt() {
            @Override
            public void invoke(String info) {
                String[] inf = info.split("\uD83D\uDD70");
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

                callBackProjectsArray.invoke(arrayList);
            }
        });

    }
}