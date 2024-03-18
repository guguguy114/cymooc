package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.WatchHistoryService;
import com.cykj.service.impl.WatchHistoryServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 21:45
 */
@Servlet("/addWatchHistory")
public class AddWatchHistoryServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        WatchHistoryService service = new WatchHistoryServiceImpl();
        int uid = Integer.parseInt(request.getValue("uid"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
        int chapterId = Integer.parseInt(request.getValue("chapterId"));
        ResponseDto dto = service.addWatchHistory(uid, courseId, chapterId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
