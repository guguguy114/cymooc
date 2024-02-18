package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.UserService;
import com.cykj.service.impl.UserServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/31 20:13
 */
public class DoRegisterServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String acc = request.getValue("acc");
        String pwd = request.getValue("pwd");
        UserService userService = new UserServiceImpl();
        ResponseDto responseDto = userService.doRegister(acc, pwd);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto responseDto = new ResponseDto(0, "unavailable method", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }
}
