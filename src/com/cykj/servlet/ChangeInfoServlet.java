package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.UserService;
import com.cykj.service.impl.UserServiceImpl;

/**
 * Description: 进行更新个人信息的功能
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 1:07
 */
@Servlet("/changeInfo")
public class ChangeInfoServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        UserService service = new UserServiceImpl();
        String changeType = request.getValue("type");
        String info = request.getValue("info");
        String acc = request.getValue("acc");
        ResponseDto responseDto = service.changeInfo(changeType, info, acc);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto dto = new ResponseDto(0, "unavailable type", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }
}
