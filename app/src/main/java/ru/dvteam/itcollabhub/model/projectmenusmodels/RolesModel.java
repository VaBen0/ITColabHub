package ru.dvteam.itcollabhub.model.projectmenusmodels;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.classmodels.ParticipantWithRole;
import ru.dvteam.itcollabhub.classmodels.RolesInformation;
import ru.dvteam.itcollabhub.globaldata.GlobalProjectInformation;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class RolesModel {
    PostDatas postDatas = new PostDatas();
    private final String projectName = GlobalProjectInformation.getInstance().getProjectTitle();
    private final String projectLogo = GlobalProjectInformation.getInstance().getProjectLog();
    private ArrayList<ParticipantWithRole> addedPeoples = new ArrayList<>();
    ArrayList<ParticipantWithRole> rolesList1 = new ArrayList<>();
    ArrayList<ParticipantWithRole> rolesList2 = new ArrayList<>();

    public ArrayList<RolesInformation> getAllRoles(){
        ArrayList<RolesInformation> rolesList = new ArrayList<>();

        rolesList.add(new RolesInformation("СуперМенеджеры", "Человек Без Имени",
                "https://pushinka.top/uploads/posts/2023-04/1680841641_pushinka-top-p-panda-avatarka-na-telefon-avatarka-vkontak-5.jpg",
                "https://sneg.top/uploads/posts/2023-06/1687875817_sneg-top-p-samie-krinzhovie-avatarki-pinterest-19.jpg",
                "https://koshka.top/uploads/posts/2021-12/1640328596_1-koshka-top-p-kota-na-avatarku-1.jpg",
                4, "#368FF5", "https://trikky.ru/wp-content/blogs.dir/1/files/2023/10/30/28e4ac42f547e6ac0f50f7cfa916ca93.jpg"));

        rolesList.add(new RolesInformation("Участники", "Paren",
                "https://shapka-youtube.ru/wp-content/uploads/2020/08/man-silhouette.jpg",
                "https://games.mail.ru/hotbox/content_files/gallery/2020/12/11/d49a024e7ade40858a10df3b8976625d.png",
                "",
                2, "#FFFFFF", ""));

        rolesList.add(new RolesInformation("Тимлид", "Кирилл",
                "https://zamanilka.ru/wp-content/uploads/2022/07/kotik-ava-060722-1.jpg",
                "",
                "",
                1, "#00FFE1", ""));

        return rolesList;
    }

    public ArrayList<ParticipantWithRole> getPeoplesWithoutRole(){
        rolesList2 = new ArrayList<>();

        rolesList2.add(new ParticipantWithRole("Paren", "#FFFFFF","https://shapka-youtube.ru/wp-content/uploads/2020/08/man-silhouette.jpg",
                false, "Участник"));

        rolesList2.add(new ParticipantWithRole("Bob", "#FFFFFF",
                "https://pushinka.top/uploads/posts/2023-04/1681810257_pushinka-top-p-tupie-avatarki-dlya-vatsapa-instagram-7.jpg",
                false, "Участник"));

        return rolesList2;
    }

    public ArrayList<ParticipantWithRole> getPeoplesWithRole(){
        addedPeoples = new ArrayList<>();
        rolesList1 = new ArrayList<>();

        rolesList1.add(new ParticipantWithRole("Человек Без Имени", "#368FF5",
                "https://pushinka.top/uploads/posts/2023-04/1680841641_pushinka-top-p-panda-avatarka-na-telefon-avatarka-vkontak-5.jpg",
                false, "СуперМенеджеры"));

        rolesList1.add(new ParticipantWithRole("Mr. Proper", "#368FF5",
                "https://sneg.top/uploads/posts/2023-06/1687875817_sneg-top-p-samie-krinzhovie-avatarki-pinterest-19.jpg",
                false, "СуперМенеджеры"));
        rolesList1.add(new ParticipantWithRole("Coo-Men", "#368FF5",
                "https://koshka.top/uploads/posts/2021-12/1640328596_1-koshka-top-p-kota-na-avatarku-1.jpg",
                false, "СуперМенеджеры"));
        rolesList1.add(new ParticipantWithRole("Anonymus", "#368FF5",
                "https://trikky.ru/wp-content/blogs.dir/1/files/2023/10/30/28e4ac42f547e6ac0f50f7cfa916ca93.jpg",
                false, "СуперМенеджеры"));
        return rolesList1;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectLogo() {
        return projectLogo;
    }

    public void addPeople(ParticipantWithRole participant){
        ParticipantWithRole participant1 = participant;
        participant1.checked = true;
        if(!rolesList1.contains(participant)){
            rolesList2.set(rolesList2.indexOf(participant), participant1);
        }else{
            rolesList1.set(rolesList1.indexOf(participant), participant1);
        }

        addedPeoples.add(participant);
        System.out.println(addedPeoples);
    }
    public void deletePeople(ParticipantWithRole participant){
        ParticipantWithRole participant1 = participant;
        participant1.checked = false;
        if(!rolesList1.contains(participant)){
            rolesList2.set(rolesList2.indexOf(participant), participant1);
        }else{
            rolesList1.set(rolesList1.indexOf(participant), participant1);
        }
        addedPeoples.remove(participant);
    }

    public ArrayList<ParticipantWithRole> getAddedPeoples(){
        return addedPeoples;
    }
}
