package ru.dvteam.itcollabhub.retrofit;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDeadlineInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt1;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt2;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTaskInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackTasksInfo;
import ru.dvteam.itcollabhub.callbackclasses.CallBackWorkInfo;

public class PostDatas {

    public void postDataLogIn(String req, String mail, String pass, CallBackInt1 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.login(req, mail, pass);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn(), response.body().getName());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера", "");
            }
        });
    }
    public void postDataConfirm(String req, String mail, String code, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.confirm(req, mail, code);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                System.out.println("Ok");
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }
    public void postDataRegUser(String req, String mail, String pass, String name, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.regEnd(req, mail, pass, name);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }
    public void postDataPostCodeMail(String req, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.postCodeMail(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }

    public void postDataCreateAccount(String req, String name, RequestBody requestFile, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.uploadImage(fileToUpload, requestName, requestReq, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataGetUserData(String mail, CallBackInt2 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserInformation("GetUserInformation", mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getName(), response.body().getUrlImg(),
                        response.body().getTopScore(), response.body().getTopStatus(), response.body().getrFr(),
                        response.body().getActivityProjects(), response.body().getArchiveProjects());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("","", 0, "", "", 0, 0);
            }
        });
    }

    public void postDataCreateLog(String req, String mail, String name,String img, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.uploadLog(req, mail, name, img);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }

    public void postDataGetFriends(String req, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserFriends(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    public void postDataAddFriend(String req, String mail, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.addFriends(req, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Failure");
            }
        });
    }

    public void postDataGetFindFriend(String req, String name, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getFindFriends(req, name, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }

        });
    }

    public void postDataEditProfile(String req, String name, String tg, String vk, String web, RequestBody requestFile, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestTg = RequestBody.create(MediaType.parse("text/plain"), tg);
        RequestBody requestVk = RequestBody.create(MediaType.parse("text/plain"), vk);
        RequestBody requestWeb = RequestBody.create(MediaType.parse("text/plain"), web);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProfile(fileToUpload, requestName, requestReq, requestMail, requestTg, requestVk, requestWeb);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataEditProfileWithoutImage(String req, String mail, String name, String tg, String vk, String web, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProfileWithooutImage(req, name, mail, tg, vk, web);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }

        });
    }

    public void postDataCreateProject(String req, String name, RequestBody requestFile, String mail, String purposes, String tasks,
                                      String description, String id, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestDes = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody requestPur = RequestBody.create(MediaType.parse("text/plain"), purposes);
        RequestBody requestTasks = RequestBody.create(MediaType.parse("text/plain"), tasks);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createProject(fileToUpload, requestName, requestReq, requestPur, requestMail, requestTasks, requestId,requestDes);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataGetUserProjects(String req, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserProjects(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }
    public void postDataGetUserProjects1(String req, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserProjects(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }
    public void postDataCreateProjectWithoutImage(String req, String name, String mail, String purposes, String tasks,
                                        String description, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createProjectWithoutImage(name, req, purposes, mail, tasks, id, description);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectInformation(String req, String id, String mail, CallBackInt4 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectInformation(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getName(), response.body().getUrlImg(), response.body().getDescription(),
                        response.body().getIsend(), response.body().getPurposes(), response.body().getProblems(), response.body().getPeoples(),
                        response.body().getTime(), response.body().getTime1(), response.body().getTg(), response.body().getVk(),
                        response.body().getWebs(),response.body().getPurposesids(), response.body().getProblemsids(), response.body().getIsl(),
                        response.body().getTasks(), response.body().getisOpen(), response.body().getIsVisible());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol", "lol", "lol", 1, "lol", "lol", "lol", "lol"
                        //, "lol", "lol", "lol", "lol", "lol", "lol", "lol");
            }
        });
    }

    public void postDataSendLink(String req, String mail, String link){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.sendUserLink(req, mail, link);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetLinks(String req, String mail, CallBackInt5 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserLinks(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getTgLink(), response.body().getVkLink(), response.body().getWebLink());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetFriendLinks(String req, String id, CallBackInt5 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getFriendLinks(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getTgLink(), response.body().getVkLink(), response.body().getWebLink());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectReq(String req, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectRequests(req, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataAnswerProject(String req, String mail, String id,CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.answerOnProjectReq(req, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetPurpose(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getPurposes(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProblems(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProblems(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreatePurpose(String req, String name, RequestBody requestFile, String description,
                                      String mail, String id, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestDes = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createPurpose(fileToUpload, requestName, requestReq, requestId, requestDes, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataCreatePurposeWithoutImage(String req, String name, String description, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createPurposeWithoutImage(name, req, id, description, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateProblem(String req, String name, RequestBody requestFile, String description, String id, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestDes = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createProblem(fileToUpload, requestName, requestReq, requestId, requestDes, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataCreateProblemWithoutImage(String req, String name, String description, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createProblemWithoutImage(name, req, id, description, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDatasetPurposeIsEnd(String req, String id, String pid, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.setPurposesIsEnd(req, id, pid, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDatasetProblemIsEnd(String req, String id, String pid, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.setProblemIsEnd(req, id, pid, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataDeleteProblem(String req, String problemId, String mail, String projectId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.deleteProblem(req, problemId, mail, projectId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeProblemWithoutImage(String req, String name, String description, String id, String mail, String problemId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeProblemWithoutImage(name, req, id, description, mail, problemId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeProblem(String req, String name, RequestBody requestFile, String description, String id, String mail,
                                      String problemId, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestDes = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestProblemId = RequestBody.create(MediaType.parse("text/plain"), problemId);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeProblem(fileToUpload, requestName, requestReq, requestId, requestDes, requestMail, requestProblemId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataGetProjectPurposes(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectPurposeIds(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getPurposesids());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectProblems(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectProblemIds(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getProblemsids());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataUploadFile(RequestBody requestFile, String tip, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestTip = RequestBody.create(MediaType.parse("text/plain"), tip);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.uploadFile(fileToUpload, requestTip);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Trowble",t.toString());
            }
        });
    }

    public void postDataCreateFile(String req, String name, RequestBody requestFile, String link, String id, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestLink = RequestBody.create(MediaType.parse("text/plain"), link);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createFile(fileToUpload, requestName, requestReq, requestLink, requestId, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataCreateFileWithoutImage(String req, String name, String link, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createFileWithoutImage(name, req, id, link, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeFile(String req, String name, RequestBody requestFile, String link, String id, String mail,
                                   String fileId, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestLink = RequestBody.create(MediaType.parse("text/plain"), link);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestFileId = RequestBody.create(MediaType.parse("text/plain"), fileId);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeFile(fileToUpload, requestName, requestReq, requestLink, requestId, requestMail, requestFileId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataChangeFileWithoutImage(String req, String name, String link, String id, String mail, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeFileWithoutImage(name, req, id, link, mail, fileId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataFixFile(String req, String id, String mail, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.fixFile(req, fileId, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataDetachFile(String req, String id, String mail, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.detachFile(req, fileId, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataDeleteFile(String req, String id, String mail, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.deleteFile(req, fileId, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectFilesIds(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectFilesIds(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void postDataGetProjectFiles(String req, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectFiles(req, fileId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectAdverts(String req, String id, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectPurposeIds(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getPurposesids());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateAdvert(String req, String name, RequestBody requestFile, String advertisment, String id, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestLink = RequestBody.create(MediaType.parse("text/plain"), advertisment);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createAdvert(fileToUpload, requestName, requestReq, requestLink, requestId, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataCreateAdvertWithoutImage(String req, String name, String advertisment, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createAdvertWithoutImage(req, id, name, advertisment, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Trowble",t.toString());
            }
        });
    }

    public void postDataChangeAdvert(String req, String name, RequestBody requestFile, String advertisment, String id, String mail,
                                   String fileId, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestLink = RequestBody.create(MediaType.parse("text/plain"), advertisment);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestFileId = RequestBody.create(MediaType.parse("text/plain"), fileId);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeAdvert(fileToUpload, requestName, requestReq, requestLink, requestId, requestMail, requestFileId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataChangeAdvertWithoutImage(String req, String name, String advertisment, String id, String mail, String fileId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.changeAdvertWithoutImage(name, req, id, advertisment, mail, fileId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectAds(String req, String adsId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectAds(req, adsId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectAdsIds(String req, String prId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectAdvertsIds(req, prId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataDeleteAd(String req, String id, String mail, String adId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.deleteAd(req, adId, mail, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeProjectWithDescription(String req, String name, RequestBody requestFile, String id, String mail,
                                     String description, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestDescription = RequestBody.create(MediaType.parse("text/plain"), description);
//        RequestBody requestTg = RequestBody.create(MediaType.parse("text/plain"), tg);
//        RequestBody requestVk = RequestBody.create(MediaType.parse("text/plain"), vk);
//        RequestBody requestWeb = RequestBody.create(MediaType.parse("text/plain"), web);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectWithDescription(fileToUpload, requestName, requestReq, requestDescription, requestId, requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataChangeProjectWithoutImageWithDescription(String req, String name, String description, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectWithoutImageWithDescription(name, req, id, description, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeProjectWithLink(String req, String name, RequestBody requestFile, String id, String mail,
                                                     String tg, String vk, String web, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestTg = RequestBody.create(MediaType.parse("text/plain"), tg);
        RequestBody requestVk = RequestBody.create(MediaType.parse("text/plain"), vk);
        RequestBody requestWeb = RequestBody.create(MediaType.parse("text/plain"), web);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectWithLinks(fileToUpload, requestName, requestReq, requestId, requestTg, requestVk, requestWeb,
                requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataChangeProjectWithoutImageWithLink(String req, String name, String tg, String vk, String web, String id,
                                                          String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectWithoutImageWithLinks(name, req, id, vk, tg, web, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataChangeProjectWithStatus(String req, String name, RequestBody requestFile, String id, String mail,
                                              String open, String visible, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestName = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestOpen = RequestBody.create(MediaType.parse("text/plain"), open);
        RequestBody requestVisible = RequestBody.create(MediaType.parse("text/plain"), visible);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectStatus(fileToUpload, requestName, requestReq, requestId, requestOpen, requestVisible,
                requestMail);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataChangeProjectWithoutImageWithStatus(String req, String name, String open, String visible, String id,
                                                          String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.editProjectWithoutImageWithStatus(name, req, id, open, visible, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataSetProjectStatus(String req, String id, String mail, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.setProjectStatus(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectParticipant(String req, String id, String mail, CallBackInt5 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectPartisipant(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getIds(), response.body().getNames(), response.body().getPhotos());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateStringBlock(String req, String id, String mail, String text, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createTextBlock(req, id, mail, text);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateYoutubeBlock(String req, String id, String mail, String nameLink, String link, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createYoutubeBlock(req, id, mail, nameLink, link);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateLinkBlock(String req, String id, String mail, String nameLink, String link, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createLinkBlock(req, id, mail, nameLink, link);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateImageBlock(String req, String name, RequestBody requestFile, String id, String mail, CallBackInt result){

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "lol", requestFile);
        RequestBody requestReq = RequestBody.create(MediaType.parse("text/plain"), req);
        RequestBody requestId = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody requestMail = RequestBody.create(MediaType.parse("text/plain"), mail);
        RequestBody requestImgName = RequestBody.create(MediaType.parse("text/plain"), name);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createImageBlock(fileToUpload, requestReq, requestImgName, requestMail, requestId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("All bad");
            }
        });
    }

    public void postDataGetProjectParticipants(String req, String id, String mail, CallBackInt5 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getPeoplesFromProject(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getIds(), response.body().getNames(), response.body().getPhotos());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateTask(String req, String id, String mail, String taskName, String queue, String textBlocks,
                                   String linkBlocks, String peopleIds, String imageBlocks, String youtubeBlocks,
                                   CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createTask(req, id, mail, taskName, queue, textBlocks, linkBlocks, peopleIds,
                imageBlocks, youtubeBlocks);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateDeadline(String req, String id, String mail, String taskName, String queue, String textBlocks,
                                   String linkBlocks, String peopleIds, String imageBlocks, String youtubeBlocks, String date,
                                   CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createDeadline(req, id, mail, taskName, queue, textBlocks, linkBlocks, peopleIds,
                imageBlocks, youtubeBlocks, date);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectTasks(String req, String id, String mail, CallBackTasksInfo result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectTasks(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getIds(), response.body().getNames(), response.body().getTexts(), response.body().getComplete());
            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetProjectDeadlines(String req, String id, String mail, CallBackDeadlineInfo result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectTasks(req, id, mail);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull retrofit2.Response<Model> response) {
                result.invoke(response.body().getIds(), response.body().getNames(), response.body().getTexts(),
                        response.body().getComplete(), response.body().getEndTime());
            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void postDataGetCompletedWorks(String req, String projectId, String mail, String taskId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.checkCountofReadyWorks(req, projectId, mail, taskId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetMoreInfoTask(String req, String projectId, String mail, String taskId, CallBackTaskInfo result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getMoreInfoTask(req, projectId, mail, taskId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getQueue_blocks(), response.body().getText_blocks(), response.body().getYoutube_blocks_names(),
                        response.body().getYoutube_blocks_links(), response.body().getLink_blocks_names(), response.body().getLink_blocks_links(),
                        response.body().getImage_blocks_names(), response.body().getImage_blocks_links());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataCreateWork(String req, String id, String mail, String taskId, String textBlocks,
                                   String linkBlocks, String imageBlocks, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createWork(req, id, mail, taskId, textBlocks, linkBlocks,
                imageBlocks);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetPeoplesComplitedWork(String req, String projectId, String mail, String taskId, CallBackInt5 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getPeoplesCompletedWork(req, projectId, mail, taskId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getIds(), response.body().getNames(), response.body().getPhotos());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataGetWork(String req, String projectId, String mail, String workId, CallBackWorkInfo result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getWork(req, projectId, mail, workId);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getText_blocks(), response.body().getLink_blocks_links(), response.body().getLink_blocks_names(),
                        response.body().getImage_blocks_links(), response.body().getImage_blocks_names());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void postDataMakeTaskCompleted(String req, String projectId, String mail, String taskId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.makeTaskCompleted(req, projectId, mail, taskId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                //result.invoke("lol");
            }
        });
    }

    public void postDataFindFriendForProject(String req, String name, String mail, String prId, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.findFriendForProject(req, name, mail, prId);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }

        });
    }

    public void postDataSetProjectIsEnd(String req, String mail, String prId, String ins, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.setProjectIsEnd(req, prId, mail, ins);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {

            }
        });
    }

    public void postDataGetAddedPeoples(String req, String id, CallBackInt1 result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getPeoplesAdded(req, id);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getNames(), response.body().getPhotoLocalLinks());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера", "");
            }
        });
    }

    public void postDataGetAiCheck(String txt, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getAiCheck(txt);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }

    public void postDataSendInfoWithQR(String login, CallBackInt result){
        Methods methods = RetrofitClient2.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.sendInfoWithQR("GetSession", login);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }

    public void postDataCreateProjectCode(String req, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.createProjectCode(req);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }

    public void postDataGetProjectCode(String req, CallBackInt result){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getProjectCode(req);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                assert response.body() != null;
                result.invoke(response.body().getReturn());
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                result.invoke("Ошибка сервера");
            }
        });
    }
}
