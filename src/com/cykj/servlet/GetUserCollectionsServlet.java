package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CollectService;
import com.cykj.service.impl.CollectServiceImpl;

/**
 * Description:
 * 获取用户的收藏列表的servlet
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/27 21:50
 */
public class GetUserCollectionsServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        int num = Integer.parseInt(request.getValue("num"));
        int currentPage = Integer.parseInt(request.getValue("currentPage"));
        CollectService service = new CollectServiceImpl();
        ResponseDto dto = service.getUserCollections(uid, num, currentPage);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
