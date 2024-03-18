package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.ChapterService;
import com.cykj.service.impl.ChapterServiceImpl;

import javax.swing.*;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 22:27
 */
@Servlet("/getCourseChapter")
public class GetCourseChapterServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        ChapterService service = new ChapterServiceImpl();
        int chapterId = Integer.parseInt(request.getValue("chapterId"));
        ResponseDto dto = service.getCourseChapter(chapterId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
