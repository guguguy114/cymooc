package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.impl.UserServiceImpl;

/**
 * Description: TODO
 * servlet只负责消息的收发
 * @author Guguguy
 * @version 1.0
 * @since 2023/12/20 20:10
 */
public class DoLoginServlet extends BasicServlet{
    @Override
    public void doGet(HttpRequest request, HttpResponse response){
        ResponseDto responseDTO = new ResponseDto(0, "unavailable type", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        // 从http请求中获取数据
        String acc = request.getValue("acc");
        String pwd = request.getValue("pwd");
        UserServiceImpl userService = new UserServiceImpl();
        ResponseDto responseDTO = userService.doLogin(acc, pwd);
        // 写入数据
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }
}
