package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/23 15:25
 */
public class GetCourseChaptersServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int courseId = Integer.parseInt(request.getValue("id"));

    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        ResponseDto responseDto = new ResponseDto(0, "unavailable type!", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }
}
