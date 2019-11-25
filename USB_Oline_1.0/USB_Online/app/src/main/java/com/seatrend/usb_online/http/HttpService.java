package com.seatrend.usb_online.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.JsonSyntaxException;
import com.seatrend.usb_online.common.BaseModule;
import com.seatrend.usb_online.enity.CommonResponse;
import com.seatrend.usb_online.util.Constants;
import okhttp3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by seatrend on 2018/8/20.
 */

public class HttpService {

    private static HttpService mHttpService;
    private BaseModule mBaseModule;

    private final int SUCCESS_CODE = 0;
    private final int FAILED_CODE = 1;
    private final int PREGRESS_CODE = 2;

    private final int TIME_OUT = 60 * 1000;

    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            .build();


    public static HttpService getInstance() {
        if (mHttpService == null) {
            synchronized (HttpService.class) {
                if (mHttpService == null) {
                    mHttpService = new HttpService();
                }
            }
        }
        return mHttpService;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS_CODE:
                    CommonResponse commonResponse1 = (CommonResponse) msg.obj;
                    mBaseModule.doWorkResults(commonResponse1, true);
                    break;
                case FAILED_CODE:
                    CommonResponse commonResponse2 = (CommonResponse) msg.obj;
                    mBaseModule.doWorkResults(commonResponse2, false);
                    break;
                case PREGRESS_CODE:
//                    CommonProgress commonProgress = (CommonProgress) msg.obj;
//                    ((ProgressModule)mBaseModule).downloadProgress(commonProgress);
                    break;
            }
        }
    };

    public void getDataFromServer(Map<String, String> map, final String url, String method, BaseModule module) {
        this.mBaseModule = module;

        String baseUrl = "http://"+Constants.IP+":"+Constants.PORT;/////////////////////////////////////////
        final String finalUrl = baseUrl + url;
        Request request;
        try {
            if (method.equals(Constants.GET)) {
                StringBuffer buffer = new StringBuffer();
                buffer.append("?");
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    buffer.append(entry.getKey().trim() + "=" + entry.getValue().trim() + "&");
                }
                String s = buffer.toString();
                String parameter = s.substring(0, s.length() - 1);
                //有token验证 ->  解除登录退出和获取码表接口的token验证
                request = new Request.Builder()
                        .url(finalUrl + parameter)
                        .get()
                        .build();

                Log.i("HttpService", finalUrl + parameter);
            } else {

                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.add(entry.getKey().trim(), entry.getValue().trim());
                }
                RequestBody requestBody = builder.build();
                request = new Request.Builder()
                        .url(finalUrl)
                        .post(requestBody)
                        .addHeader(Constants.HEADER, Constants.TOKEN)
                        .build();
                Log.i("HttpService", finalUrl);
            }
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = FAILED_CODE;
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setUrl(url);
            commonResponse.setResponseString(e.getMessage());
            message.obj = commonResponse;
            mHandler.sendMessage(message);
            return;
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = FAILED_CODE;
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setUrl(url);
                commonResponse.setResponseString(e.getMessage());
                message.obj = commonResponse;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = Message.obtain();
                String resp = response.body().string();
                Log.d("httpservice ", " result = " + resp);
                if (TextUtils.isEmpty(resp)) {
                    message.what = FAILED_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString("服务器响应内容为空");
                    message.obj = commonResponse;
                    mHandler.sendMessage(message);
                    return;
                }
                try {
                    message.what = SUCCESS_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString(resp);
                    message.obj = commonResponse;

                } catch (JsonSyntaxException e) {
                    try {
                        message.what = FAILED_CODE;
                        CommonResponse commonResponse = new CommonResponse();
                        commonResponse.setUrl(url);
                        commonResponse.setResponseString("JsonSyntaxException " + resp);
                        message.obj = commonResponse;
                    } catch (JsonSyntaxException e1) {
                        message.what = FAILED_CODE;
                        CommonResponse commonResponse = new CommonResponse();
                        commonResponse.setUrl(url);
                        commonResponse.setResponseString(resp);
                        message.obj = commonResponse;
                    }
                }
                mHandler.sendMessage(message);
            }
        });
    }

    public void getDataFromServerByJson(String json, final String url, BaseModule module) {
        this.mBaseModule = module;
        String baseUrl = "http://"+Constants.IP+":"+Constants.PORT;/////////////////////////////////////////
        final String finalUrl = baseUrl + url;

        Request request;
        try {
            MediaType mjson = MediaType.parse("application/json; charset=utf-8");
            RequestBody vehicleTemp = RequestBody.create(mjson, json);

            request = new Request.Builder()
                    .url(finalUrl)
                    .post(vehicleTemp)
                    .addHeader(Constants.HEADER, Constants.TOKEN)
                    .build();
            Log.i("HttpService", finalUrl);
        } catch (Exception e) {
            Message message = Message.obtain();
            message.what = FAILED_CODE;
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setUrl(url);
            commonResponse.setResponseString(e.getMessage());
            message.obj = commonResponse;
            mHandler.sendMessage(message);
            return;
        }

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = FAILED_CODE;
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setUrl(url);
                commonResponse.setResponseString(e.getMessage());
                message.obj = commonResponse;
                mHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = Message.obtain();
                String resp = response.body().string();
                if (TextUtils.isEmpty(resp)) {
                    message.what = FAILED_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString("服务器响应内容为空");
                    message.obj = commonResponse;
                    mHandler.sendMessage(message);
                    return;
                }
                try {
                    message.what = SUCCESS_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString(resp);
                    message.obj = commonResponse;
                } catch (JsonSyntaxException e) {
                    try {
                        message.what = FAILED_CODE;
                        CommonResponse commonResponse = new CommonResponse();
                        commonResponse.setUrl(url);
                        commonResponse.setResponseString("JsonSyntaxException " + resp);
                        message.obj = commonResponse;
                    } catch (JsonSyntaxException e1) {
                        message.what = FAILED_CODE;
                        CommonResponse commonResponse = new CommonResponse();
                        commonResponse.setUrl(url);
                        commonResponse.setResponseString("JsonSyntaxException" + e1.getMessage());
                        message.obj = commonResponse;
                    }
                }
                mHandler.sendMessage(message);
            }
        });
    }


    private class HttpCallback implements Callback {
        private String url;

        public HttpCallback(String url) {
            this.url = url;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            Message message = Message.obtain();
            message.what = FAILED_CODE;
            CommonResponse commonResponse = new CommonResponse();
            commonResponse.setUrl(url);
            commonResponse.setResponseString(e.getMessage());
            message.obj = commonResponse;
            mHandler.sendMessage(message);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message message = Message.obtain();
            String resp = response.body().string();
            if (TextUtils.isEmpty(resp)) {
                message.what = FAILED_CODE;
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setUrl(url);
                commonResponse.setResponseString("服务器响应内容为空");
                message.obj = commonResponse;
                mHandler.sendMessage(message);
                return;
            }
            try {
                message.what = SUCCESS_CODE;
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setUrl(url);
                commonResponse.setResponseString(resp);
                message.obj = commonResponse;
            } catch (JsonSyntaxException e) {
                try {
                    message.what = FAILED_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString("JsonSyntaxException " + resp);
                    message.obj = commonResponse;
                } catch (JsonSyntaxException e1) {
                    message.what = FAILED_CODE;
                    CommonResponse commonResponse = new CommonResponse();
                    commonResponse.setUrl(url);
                    commonResponse.setResponseString("JsonSyntaxException" + e1.getMessage());
                    message.obj = commonResponse;
                }
            }
            mHandler.sendMessage(message);
        }
    }
}
