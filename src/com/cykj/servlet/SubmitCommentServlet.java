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
 * 发布评论
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/11 21:39
 */
@Servlet("/submitComment")
public class SubmitCommentServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        CommentService service = new CommentServiceImpl();
        int courseId = Integer.parseInt(request.getValue("courseId"));
        int uid = Integer.parseInt(request.getValue("uid"));
        String comment = request.getValue("comment");
        ResponseDto dto = service.submitComment(courseId, uid, comment);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
