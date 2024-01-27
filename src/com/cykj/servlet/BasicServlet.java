package com.cykj.servlet;

import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;

/**
 * Description:
 * 这个类的继承类将会使用反射调用因此不会显示用法
 * @author Guguguy
 * @version 1.0
 * @since 2023/12/20 20:41
 */
public abstract class BasicServlet {
    public abstract void doPost(HttpRequest request, HttpResponse response);
    public abstract void doGet(HttpRequest request, HttpResponse response);
    public void service(HttpRequest request, HttpResponse response){
        if (request.getType().equals("POST")){
            doPost(request, response);
        }else if (request.getType().equals("GET")){
            doGet(request, response);
        }
    }
}
