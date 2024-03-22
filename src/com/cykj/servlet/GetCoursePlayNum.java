package com.cykj.servlet;

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
 * @since 2024/3/22 21:59
 */
@Servlet("/getPlayNum")
public class GetCoursePlayNum extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("courseId"));
        WatchHistoryService service = new WatchHistoryServiceImpl();
        ResponseDto dto = service.getCoursePlayNum(courseId);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
