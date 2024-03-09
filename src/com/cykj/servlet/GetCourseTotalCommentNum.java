package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
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
 * @since 2024/3/9 23:11
 */
public class GetCourseTotalCommentNum extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("courseId"));
        CommentService service = new CommentServiceImpl();
        ResponseDto dto = service.getCourseTotalCommentNum(courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
