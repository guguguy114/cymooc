package com.cykj.servlet;

import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.PurchaseHistoryService;
import com.cykj.service.impl.PurchaseHistoryServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/15 17:54
 */
public class GetPurchaseHistoryNumServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        PurchaseHistoryService service = new PurchaseHistoryServiceImpl();
        ResponseDto dto = service.getPurchaseHistoryNum(uid);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
