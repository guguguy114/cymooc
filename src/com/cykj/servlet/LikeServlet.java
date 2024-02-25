package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.LikeService;
import com.cykj.service.impl.LikeServiceImpl;

/**
 * Description:
 * 负责改变当前用户的当前课程的点赞状态的Servlet
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 23:43
 */
public class LikeServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("id"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
        LikeService service = new LikeServiceImpl();
        ResponseDto dto = service.changeCurrentCourseLikeState(uid, courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
