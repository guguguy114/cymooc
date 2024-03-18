package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CommentService;
import com.cykj.service.impl.CommentServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/1 19:48
 */
@Servlet("/getCourseComments")
public class GetCourseCommentsServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("courseId"));
        int limitNum = Integer.parseInt(request.getValue("lim"));
        int page = Integer.parseInt(request.getValue("page"));
        CommentService service = new CommentServiceImpl();
        ResponseDto dto = service.getCourseComment(courseId, limitNum, page);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
