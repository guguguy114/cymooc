package com.cykj.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/28 0:49
 */
public class HttpRequest {
    private final String request;
    private String module;
    private String type;
    private String protocol;
    private final HashMap<String, String> parameterMap;
    private final HashMap<String, String> headerMap;
    private String requestBody;

    public HttpRequest(String request){
        try {
            // 将前端url编码字符转换为正常字符(UTF-9)
            this.request = URLDecoder.decode(request, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        parameterMap = new HashMap<>();
        headerMap = new HashMap<>();
        resolveData();
    }

    /**
     * Description:
     * 用于处理传入客户端请求的方法
     * @author Guguguy
     * @since 2023/11/22 13:30
     */
    private void resolveData() {
        String[] bowserInfoPart = request.split("\r\n");
        String requestLine = bowserInfoPart[0];
        String[] apart = requestLine.split(" ");
        type = apart[0];
        module = apart[1];
        protocol = apart[2].replace("\r", "");

        for (int i = 1; i <= bowserInfoPart.length - 1; i++) {
            String[] split = bowserInfoPart[i].split(": ");
            if (split.length >= 2) {
                parameterMap.put(split[0], split[1]);
            }
        }

        String[] split = request.split("\r\n\r\n");
        if(split.length == 2) {
            requestBody = split[1]; // 得到请求体
            String[] split1 = split[1].split("&");
            for (int j = 0; j <= split1.length - 1; j++) {
                String[] dataApart = split1[j].split("=", 2);
                if(dataApart.length >= 2) {
                    headerMap.put(dataApart[0], dataApart[1]);
                }
            }
        }

        if (module.contains("?")){
            String[] moduleApart = module.split("\\?");
            module = moduleApart[0];
            String[] split1 = moduleApart[1].split("&");
            for (int j = 0; j <= split1.length - 1; j++) {
                String[] dataApart = split1[j].split("=", 2);
                headerMap.put(dataApart[0], dataApart[1]);
            }
        }



    }

    @Override
    public String toString() {
        return "{protocol=" + protocol + ", type=" + type + ", module=" + module + ", parameters=" + parameterMap.toString() + ", header=" + headerMap.toString() + "}";
    }




    public String getModule() {
        return module;
    }

    public String getType() {
        return type;
    }

    public String getProtocol() {
        return protocol;
    }

    public HashMap<String, String> getParameterMap() {
        return parameterMap;
    }

    public String getValue(String key){
        return headerMap.get(key);
    }
    public String getHeadValue(String key){
        return parameterMap.get(key);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}
