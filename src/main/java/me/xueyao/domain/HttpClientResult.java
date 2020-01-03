package me.xueyao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Simon.Xue
 * @date 2019-12-31 09:43
 **/
@Data
@AllArgsConstructor
public class HttpClientResult implements Serializable {
    private int code;
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }
}
