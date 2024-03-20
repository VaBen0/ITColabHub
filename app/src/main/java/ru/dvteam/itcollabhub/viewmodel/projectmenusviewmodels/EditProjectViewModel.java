package ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.callbackclasses.CallBackProjectInformation;
import ru.dvteam.itcollabhub.classmodels.ProjectInformation;
import ru.dvteam.itcollabhub.model.projectmenusmodels.EditProjectModel;

public class EditProjectViewModel extends ViewModel {
    private String prNameStr, prDescrStr, tgLinkStr, vkLinkStr, webLinkStr, isOpen, isVisible, mediaPath = "";
    private MutableLiveData<ProjectInformation> prInfo;
    private EditProjectModel editProjectModel = new EditProjectModel();

    public LiveData<ProjectInformation> getPrInfo() {
        if (prInfo == null) {
            prInfo = new MutableLiveData<>();
        }
        return prInfo;
    }

    public void getAllProjectInfo() {
        editProjectModel.getAllProjectInfo(projectInformation -> {
            prInfo.setValue(projectInformation);
            isOpen = projectInformation.getProjectIsOpen();
            isVisible = projectInformation.getProjectIsVisible();
            prNameStr = projectInformation.getProjectTitle();
            prDescrStr = projectInformation.getProjectDescription();
            tgLinkStr = projectInformation.getProjectTgLink();
            vkLinkStr = projectInformation.getProjectVkLink();
            webLinkStr = projectInformation.getProjectWebLink();
        });
    }

    public void setProjectName(String name) {
        prNameStr = name;
    }

    public void setProjectDescr(String descr) {
        prDescrStr = descr;
    }

    public void setProjectTgLink(String tg) {
        tgLinkStr = tg;
    }

    public void setProjectVkLink(String vk) {
        vkLinkStr = vk;
    }

    public void setProjectWebLink(String web) {
        webLinkStr = web;
    }

    public void setProjectImage(String mediaPath1) {
        mediaPath = mediaPath1;
    }
    public void setIsOpen(Boolean open){
        if(open) isOpen = "1";
        else isOpen = "0";
    }
    public void setIsVisible(Boolean visible){
        if(visible) isVisible = "1";
        else isVisible = "0";
    }


    public void onSaveDescr(CallBackBoolean callback){
        System.out.println(mediaPath);
        if(editProjectModel.checkIsEmpty(mediaPath)){
            editProjectModel.saveProjectChangesWithDescription(prNameStr, prDescrStr, mediaPath, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    mediaPath = "";
                    callback.invoke(true);
                }
            });
        }else{
            editProjectModel.saveProjectChangesWithDescriptionWithoutImage(prNameStr, prDescrStr, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    callback.invoke(true);
                }
            });
        }
    }
    public void onSaveLinks(CallBackBoolean callback){
        if(editProjectModel.checkIsEmpty(mediaPath)){
            editProjectModel.saveProjectChangesWithLinks(prNameStr, tgLinkStr, vkLinkStr,
                    webLinkStr, mediaPath, new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            mediaPath = "";
                            callback.invoke(true);
                        }
                    });
        }else{
            editProjectModel.saveProjectChangesWithLinksWithoutImage(prNameStr, tgLinkStr, vkLinkStr,
                    webLinkStr, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    callback.invoke(true);
                }
            });
        }
    }
    public void onSaveOther(CallBackBoolean callback){
        System.out.println("ViewModel" + prNameStr);
        if(editProjectModel.checkIsEmpty(mediaPath)){
            editProjectModel.saveProjectChangesWithOther(prNameStr, isOpen, isVisible, mediaPath, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    mediaPath = "";
                    callback.invoke(true);
                }
            });
        }else{
            editProjectModel.saveProjectChangesWithOtherWithoutImage(prNameStr, isOpen, isVisible, new CallBackBoolean() {
                @Override
                public void invoke(Boolean res) {
                    callback.invoke(true);
                }
            });
        }
    }
}
