package com.luwu.xgo_robot.WebService;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description：下载文件工具类
 */

public class DownloadUtil {
    private static final String TAG = "DownloadUtil";
    private String PATH_DownloadFile;

    //下载相关
    protected ApiInterface mApi;
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mFilePath; //下载到本地的文件路径+名称

    public DownloadUtil(String external_dir) {
        PATH_DownloadFile = external_dir + "/DownloadFile";
        if (mApi == null) {
            mApi = ApiHelper.getInstance().buildRetrofit("https://gitee.com/hitbency/xgo2-dog-wiki/")
                    .createService(ApiInterface.class);
        }
    }

    public void downloadFile(String url, final DownloadListener downloadListener) {
        //通过Url得到保存到本地的文件名
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_DownloadFile)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i);
                mFilePath = PATH_DownloadFile +
                        name;
            }
        }

        if (TextUtils.isEmpty(mFilePath)) {
            Log.e(TAG, "download: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mFilePath);
//        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
        if (mApi == null) {
            Log.e(TAG, "download: 下载接口为空了");
            return;
        }
        mCall = mApi.downloadFile(url);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //下载文件放在子线程
                mThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存到本地
                        writeFile2Disk(response, mFile, downloadListener);
                    }
                };
                mThread.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                downloadListener.onFailure("网络错误！");
            }
        });
//        } else {
//            downloadListener.onFinish(mFilePath);
//        }
    }

    public void downloadFileRead(String url, final DownloadListener downloadListener) {
        //通过Url得到保存到本地的文件名
        String name = url;
        String file_context;
        if (FileUtils.createOrExistsDir(PATH_DownloadFile)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i);
                mFilePath = PATH_DownloadFile +
                        name;
            }
        }

        if (TextUtils.isEmpty(mFilePath)) {
            Log.e(TAG, "download: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mFilePath);
//        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
        if (mApi == null) {
            Log.e(TAG, "download: 下载接口为空了");
            return;
        }
        mCall = mApi.downloadFile(url);
        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //下载文件放在子线程
                mThread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //保存到本地
                        writeFile2DiskRead(response, mFile, downloadListener);
                    }
                };
                mThread.start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                downloadListener.onFailure("网络错误！");
            }
        });
//        } else {
//            downloadListener.onFinish(mFilePath);
//        }
    }

    private void writeFile2Disk(Response<ResponseBody> response, File file, DownloadListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;

        if (response.body() == null) {
            downloadListener.onFailure("资源错误！");
            return;
        }
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                Log.e(TAG, "当前进度: " + currentLength);
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
//                if ((int) (100 * currentLength / totalLength) == 100) {
////                    downloadListener.onFinish(mFilePath);
//                }
            }
            Log.e(TAG, "finished");
            downloadListener.onFinish(mFilePath);

        } catch (FileNotFoundException e) {
            downloadListener.onFailure("未找到文件！");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onFailure("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeFile2DiskRead(Response<ResponseBody> response, File file, DownloadListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;
        byte[] msg = new byte[0];
        byte[] msg_new;
        int msg_old_length = 0;

        if (response.body() == null) {
            downloadListener.onFailure("资源错误！");
        }
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();

        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                msg_old_length = msg.length;
                msg_new = Arrays.copyOf(msg, msg_old_length + len);
                System.arraycopy(buff, 0, msg_new, msg_old_length, len);
                msg = msg_new;
                currentLength += len;
//                Log.e(TAG, "当前进度: " + currentLength);
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
//                if ((int) (100 * currentLength / totalLength) == 100) {
//                    downloadListener.onFinish(mFilePath);
//                }
            }
//            Log.e(TAG, "finished");

            String msg_string = new String(msg);
            msg_string = msg_string.replaceAll("[^0-9A-Za-z.:]","");
            if (msg_string != null && msg_string.length() > 0){
                downloadListener.onFinish(msg_string);
            }

        } catch (FileNotFoundException e) {
            downloadListener.onFailure("未找到文件！");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onFailure("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}