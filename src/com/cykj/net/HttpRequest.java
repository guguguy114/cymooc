package com.cykj.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

    public HttpRequest(String request){
        try {
            this.request = URLDecoder.decode(request, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        parameterMap = new HashMap<>();
        headerMap = new HashMap<>();
        resolveData();
    }

    /**
     * Description: TODO
     * 用于处理传入客户端请求的方法
     * @author Guguguy
     * @since 2023/11/22 13:30
     */
    private void resolveData() {
        Scanner sc = new Scanner(request).useDelimiter("\\r\\n");
        ArrayList<String> bowserInfoPart = new ArrayList<>();


        while (sc.hasNext()) {
            bowserInfoPart.add(sc.next());
        }
        String[] apart;
        apart = bowserInfoPart.get(0).split(" ");
        type = apart[0];
        module = apart[1];
        protocol = apart[2].replace("\r", "");
        if (module.contains(".")){
            for (int i = 1; i <= bowserInfoPart.size() - 1; i++) {
                if (!bowserInfoPart.get(i).isEmpty()) {
                    apart = bowserInfoPart.get(i).split(":", 2);
                    parameterMap.put(apart[0], apart[1].trim());
                }
            }
        }else {
            for (int i = 1; i <= bowserInfoPart.size() - 1; i++) {
                if (i == bowserInfoPart.size() - 1 && !bowserInfoPart.get(i).isEmpty()) {
                    apart = bowserInfoPart.get(i).split("&");
                    for (int j = 0; j <= apart.length - 1; j++) {
                        String[] dataApart = apart[j].split("=", 2);
                        headerMap.put(dataApart[0], dataApart[1]);
                    }
                }else {
                    if (!bowserInfoPart.get(i).isEmpty()) {
                        apart = bowserInfoPart.get(i).split(":", 2);
                        parameterMap.put(apart[0], apart[1].trim());
                    }
                }
            }
            if (module.contains("?")){
                String[] moduleApart = module.split("\\?");
                module = moduleApart[0];
                apart = moduleApart[1].split("&");
                for (int j = 0; j <= apart.length - 1; j++) {
                    String[] dataApart = apart[j].split("=", 2);
                    headerMap.put(dataApart[0], dataApart[1]);
                }
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
}
