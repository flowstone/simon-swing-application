package me.xueyao.util;

import me.xueyao.domain.HttpClientResult;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Simon.Xue
 * @date 2019-12-31 09:33
 **/
public class HttpUtil {
    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final int CONNECT_TIMEOUT = 6000;

    private static final int SOCKET_TIMEOUT = 6000;

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(CONNECT_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT).build();


    public static HttpClientResult doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    public static HttpClientResult doGet(String url, Map<String, String> params) throws Exception{
        return doGet(url, null, params);
    }

    private static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (null != params) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpGet httpGet = new HttpGet(uriBuilder.build());

        httpGet.setConfig(requestConfig);
        packageHeader(headers, httpGet);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    public static HttpClientResult doPost(String url) throws Exception {
        return doPost(url, null, null);
    }

    public static HttpClientResult doPost(String url, Map<String, String> params) throws Exception{
        return doPost(url, null, params);
    }

    public static HttpClientResult doPost(String url, Map<String, String> headers, Map<String, String> params) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        packageHeader(headers, httpPost);

        packageParam(params, httpPost);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            release(httpResponse, httpClient);
        }
    }



    private static void packageParam(Map<String, String> params, HttpEntityEnclosingRequestBase httpMethod) throws Exception{
        if (null != params) {
            List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                nvpList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            httpMethod.setEntity(new UrlEncodedFormEntity(nvpList, DEFAULT_CHARSET));
        }
    }

    /**
     * 封装请求头
     * @param params
     * @param httpRequestBase
     */
    public static void packageHeader(Map<String, String> params, HttpRequestBase httpRequestBase) {
        if (null != params) {
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> entry : entries) {
                httpRequestBase.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 处理请求返回的结果
     * @param httpResponse
     * @param httpClient
     * @param httpRequestBase
     * @return
     * @throws Exception
     */
    public static HttpClientResult getHttpClientResult(CloseableHttpResponse httpResponse,
                                                       CloseableHttpClient httpClient,
                                                       HttpRequestBase httpRequestBase) throws Exception{
        httpResponse = httpClient.execute(httpRequestBase);

        if (null != httpRequestBase && null != httpResponse.getStatusLine()) {
            String content = "";
            if (null != httpResponse.getEntity()) {
                content = EntityUtils.toString(httpResponse.getEntity());
            }
            return new HttpClientResult(httpResponse.getStatusLine().getStatusCode(), content);
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * 释放资源
     * @param httpResponse
     * @param httpClient
     * @throws Exception
     */
    public static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) throws Exception{
        if (null != httpResponse) {
            httpResponse.close();
        }

        if (null != httpClient) {
            httpClient.close();
        }
    }
}
