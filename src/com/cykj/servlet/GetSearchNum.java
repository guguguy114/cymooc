package com.cykj.servlet;

import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CourseService;
import com.cykj.service.impl.CourseServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/18 22:29
 */
@Servlet("/getSearchNum")
public class GetSearchNum extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String searchWord = request.getValue("searchWord");
        String[] types = request.getValue("type").split(",");
        String[] tags = request.getValue("tag").split(",");
        CourseService service = new CourseServiceImpl();
        ResponseDto dto = service.getSearchNum(searchWord, types, tags);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
