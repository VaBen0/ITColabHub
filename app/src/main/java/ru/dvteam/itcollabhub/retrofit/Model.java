package ru.dvteam.itcollabhub.retrofit;

public class Model {
    private String ret;
    private String name;
    private String tgLink;
    private String vkLink;
    private String webLink;
    private int topScore;
    private String topStatus;
    private String urlImg;
    private String rFr;
    private String description;
    private double isend;
    private String purposes;
    private String problems;
    private String peoples;
    private String time;
    private String time1;
    private String tg;
    private String vk;
    private String webs;
    private String purposesids;
    private String problemsids;
    private String isl;
    private String ids;
    private String names;
    private String photos;
    private String texts;
    private String queue_blocks;
    private String text_blocks;
    private String link_blocks_names;
    private String link_blocks_links;
    private String image_blocks_names;
    private String image_blocks_links;
    private String youtube_blocks_names;
    private String youtube_blocks_links;
    private String tasks;
    private String complete;
    private String isVisible;
    private String isOpen;
    private String photoLocalLinks;
    private String endTime;

    private int activityProjects, archiveProjects;

    public Model(String name, String urlImg, String ret, String topStatus, int topScore, String rFr, String description, String tgLink,
                 String vkLink, String webLink, double isend, String purposes, String problems, String peoples, String time, String time1,
                 String purposesids, String problemsids, String isl, int activityProjects, int archiveProjects, String names, String photos,
                 String ids, String texts, String queue_blocks, String text_blocks, String link_blocks_names, String link_blocks_links,
                 String image_blocks_names, String image_blocks_links, String youtube_blocks_names, String youtube_blocks_links, String tasks,
                 String complete, String isVisible, String isOpen, String photoLocalLinks, String endTime) {
        this.name = name;
        this.ret = ret;
        this.urlImg = urlImg;
        this.topStatus = topStatus;
        this.topScore = topScore;
        this.rFr = rFr;
        this.description = description;
        this.tgLink = tgLink;
        this.vkLink = vkLink;
        this.webLink = webLink;
        this.purposesids = purposesids;
        this.problemsids = problemsids;
        this.isl = isl;
        this.activityProjects = activityProjects;
        this.archiveProjects = archiveProjects;
        this.names = names;
        this.photos = photos;
        this.ids = ids;
        this.texts = texts;
        this.queue_blocks = queue_blocks;
        this.text_blocks = text_blocks;
        this.link_blocks_names = link_blocks_names;
        this.link_blocks_links = link_blocks_links;
        this.image_blocks_names = image_blocks_names;
        this.image_blocks_links = image_blocks_links;
        this.youtube_blocks_names = youtube_blocks_names;
        this.youtube_blocks_links = youtube_blocks_links;
        this.tasks = tasks;
        this.complete = complete;
        this.isVisible = isVisible;
        this.isOpen = isOpen;
        this.photoLocalLinks = photoLocalLinks;
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getName() {
        return this.name;
    }

    public String getReturn() {
        return this.ret;
    }

    public int getTopScore(){return this.topScore;}

    public String getTopStatus(){return this.topStatus;}

    public String getUrlImg(){return this.urlImg;}

    public String getrFr(){return this.rFr;}

    public String getDescription() {return this.description;}

    public String getTgLink(){return this.tgLink;}
    public String getVkLink(){return this.vkLink;}
    public String getWebLink(){return this.webLink;}
    public String getTg() {return this.tg;}
    public double getIsend() {return this.isend;}
    public String getPeoples() {return this.peoples;}
    public String getProblems() {return this.problems;}
    public String getPurposes() {return this.purposes;}
    public String getTime() {return this.time;}
    public String getTime1() {return this.time1;}
    public String getVk() {return this.vk;}
    public String getWebs() {return this.webs;}
    public String getIsl() {return isl;}
    public String getProblemsids() {return problemsids;}
    public String getPurposesids() {return purposesids;}

    public int getActivityProjects() {return activityProjects;}
    public int getArchiveProjects() {return archiveProjects;}

    public String getIds() {
        return ids;
    }

    public String getNames() {
        return names;
    }

    public String getPhotos() {
        return photos;
    }

    public String getTexts(){
        return texts;
    }

    public String getImage_blocks_links() {return image_blocks_links;}

    public String getImage_blocks_names() {return image_blocks_names;}

    public String getLink_blocks_links() {return link_blocks_links;}

    public String getLink_blocks_names() {return link_blocks_names;}

    public String getQueue_blocks() {return queue_blocks;}

    public String getText_blocks() {return text_blocks;}

    public String getYoutube_blocks_links() {return youtube_blocks_links;}

    public String getYoutube_blocks_names() {return youtube_blocks_names;}

    public String getTasks() {
        return tasks;
    }

    public String getComplete() {
        return complete;
    }

    public String getIsVisible() {
        return isVisible;
    }

    public String getisOpen() {
        return isOpen;
    }

    public String getPhotoLocalLinks() {
        return photoLocalLinks;
    }
}
