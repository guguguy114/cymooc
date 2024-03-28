package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CollectService;
import com.cykj.service.impl.CollectServiceImpl;

/**
 * Description:
 * 收藏操作
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 20:53
 */
@Servlet("/collection")
public class CollectionServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
        CollectService service = new CollectServiceImpl();
        ResponseDto dto = service.changeCurrentCourseCollectState(uid, courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
