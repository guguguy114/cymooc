package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.ChapterService;
import com.cykj.service.impl.ChapterServiceImpl;

/**
 * Description:
 * 获取课程全部章节
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 15:25
 */
@Servlet("/getCourseChapters")
public class GetCourseChaptersServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("id"));
        ChapterService service = new ChapterServiceImpl();
        ResponseDto dto = service.getCourseChapters(courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto dto = new ResponseDto(0, "unavailable type!", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }
}
