package com.cykj.servlet;

import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.CourseService;
import com.cykj.service.impl.CourseServiceImpl;

/**
 * Description:
 * 获取搜索结果的tag
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/21 17:23
 */
@Servlet("/getSearchResultTags")
public class GetSearchResultTags extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String searchWord = request.getValue("searchWord");
        CourseService service = new CourseServiceImpl();
        ResponseDto dto = service.getSearchResultTags(searchWord);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
