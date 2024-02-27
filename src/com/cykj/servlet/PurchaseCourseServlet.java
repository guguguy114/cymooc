package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.PurchaseHistoryService;
import com.cykj.service.impl.PurchaseHistoryServiceImpl;

/**
 * Description:
 * 购买课程的servlet
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/27 12:31
 */
public class PurchaseCourseServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
        PurchaseHistoryService service = new PurchaseHistoryServiceImpl();
        ResponseDto dto = service.purchaseCourse(uid, courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto dto = new ResponseDto(0, "unavailable type", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }
}
