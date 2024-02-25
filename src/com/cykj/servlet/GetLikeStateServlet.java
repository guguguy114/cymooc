package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.LikeService;
import com.cykj.service.impl.LikeServiceImpl;

/**
 * Description:
 * 获取当前课程的点赞状态
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/25 21:53
 */
public class GetLikeStateServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
        LikeService service = new LikeServiceImpl();
        ResponseDto dto = service.getCurrentCourseLikeState(uid, courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto))   ;
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
