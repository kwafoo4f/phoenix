package com.kuafoo4f.phoenix.rpc;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.kuafoo4j.phoenix.commom.core.api.*;
import com.kuafoo4j.phoenix.commom.core.constants.ApiConstant;
import com.kuafoo4j.phoenix.commom.core.pojo.ServiceInfo;
import com.kuafoo4j.phoenix.utils.JacksonUtils;
import com.kuafoo4j.phoenix.utils.MapUtil;

import java.util.Map;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-18 11:25
 */
public class PhoenixHttpClient implements PhoenixServiceApi {
    /**
     * 远程服务端地址
     */
    private String REMOTE_HOST = "";

    public PhoenixHttpClient() {
    }

    public PhoenixHttpClient(String remoteHost) {
        this.REMOTE_HOST = remoteHost;
    }

    @Override
    public ReturnResp registerService(RegisterReq registerReq) {
        HttpResponse execute = HttpRequest.post(REMOTE_HOST + ApiConstant.INSTANCE_URL)
                .body(JacksonUtils.objectToJson(registerReq))
                .execute();
        return JacksonUtils.jsonToPojo(execute.bodyBytes(),ReturnResp.class);
    }

    @Override
    public ReturnResp deRegisterService() {
        // TODO
        HttpResponse execute = HttpRequest.delete(REMOTE_HOST + ApiConstant.INSTANCE_URL)
                .execute();
        return JacksonUtils.jsonToPojo(execute.bodyBytes(),ReturnResp.class);
    }

    @Override
    public ReturnResp beat(BeatReq beatReq) {
        HttpResponse execute = HttpRequest.post(REMOTE_HOST + ApiConstant.INSTANCE_BEAT_URL)
                .body(JacksonUtils.objectToJson(beatReq))
                .execute();
        return JacksonUtils.jsonToPojo(execute.bodyBytes(),ReturnResp.class);
    }

    @Override
    public ReturnResp<ServiceInfo> getAllInstances(ServiceBaseReq req) {
        Map<String, String> params = MapUtil.pojo2Map(req);
        String url = getUrlWithParam(REMOTE_HOST + ApiConstant.INSTANCE_URL, params);
        HttpResponse execute = HttpRequest.get(url).execute();
        return JacksonUtils.jsonToPojo(execute.bodyBytes(),ReturnResp.class);
    }

    /**
     * 请求url后面拼接参数
     * @param url
     * @param params
     * @return
     */
    private static String getUrlWithParam(String url, Map<String, String> params) {
        StringBuilder urlWithParam = new StringBuilder(url);
        urlWithParam.append("?");
        params.forEach((k, v)->{urlWithParam.append(k).append("=").append(v).append("&");});
        return urlWithParam.toString();
    }
}
