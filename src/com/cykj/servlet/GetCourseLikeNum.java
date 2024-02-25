package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.LikeService;
import com.cykj.service.impl.LikeServiceImpl;

/**
 * Description:
 * 负责获得特定课程点赞数得Servlet
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/25 22:57
 */
public class GetCourseLikeNum extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("courseId"));
        LikeService service = new LikeServiceImpl();
        ResponseDto dto = service.getCourseLikeNum(courseId);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
