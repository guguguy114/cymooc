package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.UserService;
import com.cykj.service.impl.UserServiceImpl;

/**
 * Description: TODO
 * 获取用户列表服务层
 * @author Guguguy
 * @version 1.0
 * @since 2023/12/24 15:58
 */
public class GetUsersServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int pageCurr = Integer.parseInt(request.getValue("pageCurr"));
        int pageSize = Integer.parseInt(request.getValue("pageSize"));
        UserService userService = new UserServiceImpl();
        ResponseDto dto = userService.getUsers(pageCurr, pageSize);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
