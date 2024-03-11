package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.net.StaticResourceHandler;
import com.cykj.service.UserService;
import com.cykj.service.impl.UserServiceImpl;

import java.util.Map;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/10 16:23
 */
public class UploadFaceImageServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String imageData = request.getValue("data");
        System.out.println("data = " + imageData);
        int uid = Integer.parseInt(request.getValue("uid"));
//        Map<String, String> dataMap = StaticResourceHandler.getUploadDataDetail(imageData);
        UserService service = new UserServiceImpl();
        System.out.println("---------" + imageData.split(";base64,")[1]);
        ResponseDto dto = service.uploadFaceImage(uid, imageData.split(";base64,")[1]);

        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
