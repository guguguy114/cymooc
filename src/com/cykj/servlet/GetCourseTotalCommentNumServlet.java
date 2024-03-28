package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CommentService;
import com.cykj.service.impl.CommentServiceImpl;

/**
 * Description:
 * 获取课程评论总数
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/9 23:11
 */
@Servlet("/getCourseTotalCommentNum")
public class GetCourseTotalCommentNumServlet extends BasicServlet{
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
