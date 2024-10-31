package com.luwu.xgo_robot.WebService;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import java.util.concurrent.TimeUnit;


/**
 * Description：网络请求接口类
 */
public interface ApiInterface {
    /**
     * 下载视频
     *
     * @param fileUrl
     * @return
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
