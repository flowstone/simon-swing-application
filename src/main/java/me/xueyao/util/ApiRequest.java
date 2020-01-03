package me.xueyao.util;

import me.xueyao.domain.HttpClientResult;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Simon.Xue
 * @date 2019-12-31 10:09
 **/
public class ApiRequest {

    private static String domainUrl = "http://127.0.0.1:8080";
    private static String loginUrl = domainUrl + "/login/agent";


    public static HttpClientResult login(String username, String password){
        Map<String, String> loginParams = new HashMap<String, String>(16);
        loginParams.put("mobile", username);
        loginParams.put("password", DigestUtils.md5Hex(password.getBytes()));
        try {
            return HttpUtil.doPost(loginUrl, loginParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpClientResult(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }


}
