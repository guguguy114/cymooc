package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
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
@Servlet("/charge")
public class ChargeServlet extends BasicServlet{

    @Override
    public void doPost(HttpRequest request, HttpResponse response){
        String acc = request.getValue("acc");
        int moneyToCharge = Integer.parseInt(request.getValue("num"));

        UserServiceImpl userService = new UserServiceImpl();

        ResponseDto responseDTO = userService.charge(acc, moneyToCharge);

        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response){
        ResponseDto responseDTO = new ResponseDto(0, "unavailable type", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDTO));
    }
}
