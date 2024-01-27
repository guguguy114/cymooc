package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.impl.UserServiceImpl;

/**
 * Description:
 * 充值的服务层
 * @author Guguguy
 * @version 1.0
 * @since 2023/12/20 20:25
 * @see BasicServlet
 */
public class ChargeServlet extends BasicServlet{

    @Override
    public void doPost(HttpRequest request, HttpResponse response){
        int userID = Integer.parseInt(request.getValue("id"));
        int moneyToCharge = Integer.parseInt(request.getValue("money"));

        UserServiceImpl userService = new UserServiceImpl();

        ResponseDto responseDTO = userService.charge(userID, moneyToCharge);

        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response){
        ResponseDto responseDTO = new ResponseDto(0, "unavailable type", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }
}
