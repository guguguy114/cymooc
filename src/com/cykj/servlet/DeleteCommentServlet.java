package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CommentService;
import com.cykj.service.impl.CommentServiceImpl;

import javax.swing.*;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/11 22:22
 */
public class DeleteCommentServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("courseId"));
        int uid = Integer.parseInt(request.getValue("uid"));
        int commentId = Integer.parseInt(request.getValue("commentId"));
        CommentService service = new CommentServiceImpl();
        ResponseDto dto = service.deleteComment(courseId, uid, commentId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
