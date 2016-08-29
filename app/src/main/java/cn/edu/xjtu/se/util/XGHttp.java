package cn.edu.xjtu.se.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by DUAN Yufei on 16-6-27.
 */
public class XGHttp {
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static XGHttp mInstance = null;
    //获取UI线程
    private Handler mHandler = new Handler(Looper.getMainLooper());

    //回调接口
    public interface MOkCallBack {
        void onSuccess(String str);

        void onError();
    }

    //获取实例
    public static XGHttp getInstance() {
        if (mInstance == null) {
            synchronized (XGHttp.class) {
                if (mInstance == null) {
                    mInstance = new XGHttp();
                }
            }
        }
        return mInstance;
    }

    /**
     * get方式进行网络访问
     *
     * @param url       地址
     * @param mCallBack 回调
     */
    public void get(String url, final MOkCallBack mCallBack) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }
        });
    }

    public void post(String url, MOkCallBack mCallBack) {
        post(url, null, mCallBack);
    }

    public void post(String url, String json, final MOkCallBack mCallBack) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (json != null && json.length() > 0) {
            RequestBody rb = RequestBody.create(JSON, json);
            builder.post(rb);
        }
        Request request = builder.build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallBack.onError();
                    }
                });
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                final String re = response.body().string();
                mHandler.post(new Runnable() {
                    public void run() {
                        mCallBack.onSuccess(re);
                    }
                });
            }
        });
    }
}
