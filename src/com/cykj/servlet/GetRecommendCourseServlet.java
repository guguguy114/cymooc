package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CourseService;
import com.cykj.service.impl.CourseServiceImpl;

/**
 * Description:
 * 获取推荐视频
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/21 17:00
 */
@Servlet("/getRecommendCourse")
public class GetRecommendCourseServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        CourseService service = new CourseServiceImpl();
        String func = request.getValue("func");
        int num = Integer.parseInt(request.getValue("num"));
        ResponseDto responseDto = service.getRecommendCourse(func, num);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto dto = new ResponseDto(0, "unavailable type!", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(dto));
    }
}
