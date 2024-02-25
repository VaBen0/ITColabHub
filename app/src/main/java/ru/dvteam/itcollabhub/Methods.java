package ru.dvteam.itcollabhub;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Methods {

    @FormUrlEncoded
    @POST("/")
    Call<Model> login(@Field("Request")String req, @Field("UserMail")String mail, @Field("UserPassword") String pass);

    @FormUrlEncoded
    @POST("/")
    Call<Model> postCodeMail(@Field("Request")String req, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getUserInformation(@Field("Request")String req, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> confirm(@Field("Request")String req, @Field("UserMail")String mail, @Field("UserCode")String code);

    @FormUrlEncoded
    @POST("/")
    Call<Model> regEnd(@Field("Request")String req, @Field("UserMail")String mail, @Field("UserPassword")String pass, @Field("UserName")String name);

    @FormUrlEncoded
    @POST("/")
    Call<Model> changeName(@Field("Request")String req, @Field("UserName")String name);

    @FormUrlEncoded
    @POST("/")
    Call<Model> uploadLog(@Field("Request")String req, @Field("UserMail")String mail, @Field("UserName")String name, @Field("UserImage")String img);

    @Multipart
    @POST("/")
    Call<Model> uploadImage(@Part MultipartBody.Part file, @Part("UserName") RequestBody name, @Part("Request") RequestBody req, @Part("UserMail") RequestBody mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getUserFriends(@Field("Request")String req, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getFindFriends(@Field("Request")String req, @Field("UserName")String name, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> addFriends(@Field("Request")String req, @Field("UserMail")String mail, @Field("Id")String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> editName(@Field("Request")String req, @Field("UserName")String name, @Field("UserMail")String mail);

    @Multipart
    @POST("/")
    Call<Model> createProject(@Part MultipartBody.Part file, @Part("ProjectName") RequestBody name, @Part("Request") RequestBody req, @Part("ProjectPurposes") RequestBody purpose,
                                @Part("UserMail") RequestBody mail, @Part("ProjectTasks") RequestBody task, @Part("UsersId") RequestBody id, @Part("ProjectDescription") RequestBody description);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getUserProjects(@Field("Request")String req, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createProjectWithoutImage(@Field("ProjectName") String name, @Field("Request") String req, @Field("ProjectPurposes") String purpose,
                              @Field("UserMail") String mail, @Field("ProjectTasks") String task, @Field("UsersId") String id, @Field("ProjectDescription") String description);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectInformation(@Field("Request")String req, @Field("ProjectId")String id, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> sendUserLink(@Field("Request")String req, @Field("UserMail")String mail, @Field("Link")String link);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getUserLinks(@Field("Request")String req, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getFriendLinks(@Field("Request")String req, @Field("UserId")String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectRequests(@Field("Request")String req, @Field("UserMail")String mail);
    @FormUrlEncoded
    @POST("/")
    Call<Model> answerOnProjectReq(@Field("Request")String req, @Field("UserMail")String mail, @Field("ProjectId")String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getPurposes(@Field("Request")String req, @Field("ProjectId")String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProblems(@Field("Request")String req, @Field("ProjectId")String id);

    @Multipart
    @POST("/")
    Call<Model> createPurpose(@Part MultipartBody.Part file, @Part("PurposeName") RequestBody name, @Part("Request") RequestBody req,
                              @Part("ProjectId") RequestBody id, @Part("PurposeDescription") RequestBody description, @Part("UserMail") RequestBody mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createPurposeWithoutImage(@Field("PurposeName") String name, @Field("Request") String req,
                                          @Field("ProjectId") String id, @Field("PurposeDescription") String description,
                                          @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> setPurposesIsEnd(@Field("Request")String req, @Field("PurposeId")String id, @Field("ProjectId")String pid, @Field("UserMail")String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> setProblemIsEnd(@Field("Request")String req, @Field("ProblemId")String id, @Field("ProjectId")String pid, @Field("UserMail")String mail);

    @Multipart
    @POST("/")
    Call<Model> createProblem(@Part MultipartBody.Part file, @Part("ProblemName") RequestBody name, @Part("Request") RequestBody req,
                              @Part("ProjectId") RequestBody id, @Part("ProblemDescription") RequestBody description,
                              @Part("UserMail") RequestBody mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createProblemWithoutImage(@Field("ProblemName") String name,
                                          @Field("Request") String req, @Field("ProjectId") String id,
                                          @Field("ProblemDescription") String description, @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> deleteProblem(@Field("Request")String req, @Field("ProblemId") String problemId,
                                          @Field("UserMail") String mail, @Field("ProjectId") String projectId);


    @FormUrlEncoded
    @POST("/")
    Call<Model> changeProblemWithoutImage(@Field("ProblemName") String name,
                                          @Field("Request") String req, @Field("ProjectId") String id,
                                          @Field("ProblemDescription") String description, @Field("UserMail") String mail,
                                          @Field("ProblemId") String problemId);

    @Multipart
    @POST("/")
    Call<Model> changeProblem(@Part MultipartBody.Part file, @Part("ProblemName") RequestBody name, @Part("Request") RequestBody req,
                              @Part("ProjectId") RequestBody id, @Part("ProblemDescription") RequestBody description,
                              @Part("UserMail") RequestBody mail, @Part("ProblemId") RequestBody problemId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectProblemIds(@Field("Request") String req, @Field("ProjectId") String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectPurposeIds(@Field("Request") String req, @Field("ProjectId") String id);

    @Multipart
    @POST("/test_file_upload/")
    Call<Model> uploadFile(@Part MultipartBody.Part file, @Part("TIP") RequestBody tip);

    @Multipart
    @POST("/")
    Call<Model> createFile(@Part MultipartBody.Part file, @Part("FileName") RequestBody name, @Part("Request") RequestBody req,
                              @Part("FileLink") RequestBody link, @Part("ProjectId") RequestBody id,
                              @Part("UserMail") RequestBody mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createFileWithoutImage(@Field("FileName") String name,
                                          @Field("Request") String req, @Field("ProjectId") String id,
                                          @Field("FileLink") String link, @Field("UserMail") String mail);

    @Multipart
    @POST("/")
    Call<Model> changeFile(@Part MultipartBody.Part file, @Part("FileName") RequestBody name, @Part("Request") RequestBody req,
                           @Part("FileLink") RequestBody link, @Part("ProjectId") RequestBody id,
                           @Part("UserMail") RequestBody mail, @Part("FileId") RequestBody fileId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> changeFileWithoutImage(@Field("FileName") String name,
                                       @Field("Request") String req, @Field("ProjectId") String id,
                                       @Field("FileLink") String link, @Field("UserMail") String mail, @Field("FileId") String fileId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectFilesIds(@Field("Request") String req, @Field("ProjectId") String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectFiles(@Field("Request") String req, @Field("FilesId") String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> fixFile(@Field("Request") String req, @Field("FileId") String id, @Field("UserMail") String mail, @Field("ProjectId") String projectId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> detachFile(@Field("Request") String req, @Field("FileId") String id, @Field("UserMail") String mail, @Field("ProjectId") String projectId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> deleteFile(@Field("Request") String req, @Field("FileId") String id, @Field("UserMail") String mail, @Field("ProjectId") String projectId);


    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectAdvertsIds(@Field("Request") String req, @Field("ProjectId") String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectAds(@Field("Request") String req, @Field("AdsId") String id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> deleteAd(@Field("Request") String req, @Field("AdId") String id, @Field("UserMail") String mail, @Field("ProjectId") String projectId);

    @Multipart
    @POST("/")
    Call<Model> createAdvert(@Part MultipartBody.Part file, @Part("AdName") RequestBody name, @Part("Request") RequestBody req,
                           @Part("Advertisement") RequestBody advertisement, @Part("ProjectId") RequestBody id,
                           @Part("UserMail") RequestBody mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createAdvertWithoutImage(@Field("Request") String req, @Field("ProjectId") String id, @Field("AdName") String name,
                                         @Field("Advertisement") String advertisement, @Field("UserMail") String mail);

    @Multipart
    @POST("/")
    Call<Model> changeAdvert(@Part MultipartBody.Part file, @Part("AdName") RequestBody name, @Part("Request") RequestBody req,
                           @Part("Advertisement") RequestBody advertisement, @Part("ProjectId") RequestBody id,
                           @Part("UserMail") RequestBody mail, @Part("AdId") RequestBody adId);

    @FormUrlEncoded
    @POST("/")
    Call<Model> changeAdvertWithoutImage(@Field("AdName") String name,
                                       @Field("Request") String req, @Field("ProjectId") String id,
                                       @Field("Advertisement") String advertisement, @Field("UserMail") String mail, @Field("AdId") String adId);

    @Multipart
    @POST("/")
    Call<Model> editProject(@Part MultipartBody.Part file, @Part("ProjectName") RequestBody name, @Part("Request") RequestBody req,
                             @Part("Description") RequestBody description, @Part("ProjectId") RequestBody id,
                             @Part("UserMail") RequestBody mail, @Part("TgLink") RequestBody tg, @Part("VkLink") RequestBody vk,
                            @Part("WebLink") RequestBody web);

    @FormUrlEncoded
    @POST("/")
    Call<Model> editProjectWithoutImage(@Field("ProjectName") String name,
                                         @Field("Request") String req, @Field("ProjectId") String id,
                                         @Field("Description") String description, @Field("UserMail") String mail,
                                        @Field("TgLink") String tg, @Field("VkLink") String vk, @Field("WebLink") String web);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectPartisipant(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> setProjectStatus(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createTextBlock(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail, @Field("TextBlock") String text);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createLinkBlock(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail,
                                @Field("NameLinkBlock") String linkName, @Field("LinkBlock") String link);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createYoutubeBlock(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail,
                                @Field("NameYouTubeBlock") String linkName, @Field("YouTubeBlock") String link);

    @Multipart
    @POST("/")
    Call<Model> createImageBlock(@Part MultipartBody.Part file, @Part("Request") RequestBody req,
                            @Part("NameImageBlock") RequestBody name, @Part("UserMail") RequestBody mail, @Part("ProjectID") RequestBody id);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createTask(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail,
                                   @Field("TaskName") String taskName, @Field("QueueBlocks") String queue, @Field("TextBlocks") String textBlocks,
                           @Field("LinkBlocks") String linkBlocks, @Field("PeoplesIds") String peoplesIds,
                           @Field("ImageBlocks") String imageBlocks, @Field("YouTubeBlocks") String youtubeBlocks);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getPeoplesFromProject(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getProjectTasks(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail);

    @FormUrlEncoded
    @POST("/")
    Call<Model> checkCountofReadyWorks(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail,
                                       @Field("TaskID") String taskID);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getMoreInfoTask(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail,
                                       @Field("TaskID") String taskID);

    @FormUrlEncoded
    @POST("/")
    Call<Model> createWork(@Field("Request") String req, @Field("ProjectID") String id, @Field("UserMail") String mail,
                           @Field("TaskID") String taskName, @Field("TextBlocks") String textBlocks,
                           @Field("LinkBlocks") String linkBlocks, @Field("ImageBlocks") String imageBlocks);

    @FormUrlEncoded
    @POST("/")
    Call<Model> getPeoplesCompletedWork(@Field("Request")String req, @Field("ProjectID")String id, @Field("UserMail") String mail,
                                @Field("TaskID") String taskID);
}