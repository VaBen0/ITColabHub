package ru.dvteam.itcollabhub.callbackclasses;

public interface CallBackTaskInfo {
    void invoke(String queue, String textBlock, String youtubeBlockName, String youtubeBlockLink, String linkBlockName,
                String linkBlockLink, String imageBlockName, String imageBlockLink);
}
