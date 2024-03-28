package com.cykj.servlet;

import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.PurchaseHistoryService;
import com.cykj.service.impl.PurchaseHistoryServiceImpl;

/**
 * Description:
 * 获取用户的购买记录
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/15 16:55
 */
@Servlet("/getUserCoursePurchaseHistory")
public class GetUserCoursePurchaseHistoryServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        PurchaseHistoryService service = new PurchaseHistoryServiceImpl();
        int uid = Integer.parseInt(request.getValue("uid"));
        int limitNum = Integer.parseInt(request.getValue("lim"));
        int page = Integer.parseInt(request.getValue("page"));
        ResponseDto dto = service.getPurchaseHistories(uid, limitNum, page);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
