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
 * @since 2024/3/17 14:49
 */
@Servlet("/search")
public class SearchServlet extends BasicServlet {
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String searchWord = request.getValue("searchWord");
        int page = Integer.parseInt(request.getValue("page"));
        int limit = Integer.parseInt(request.getValue("lim"));
        String sortMode = request.getValue("sortMode");
        switch (sortMode) {
            case "collect-num":
                sortMode = "collection";
                break;
            case "comment-num":
                sortMode = "comment";
                break;
            case "play-num":
            default:
                sortMode = "watch_history";
                break;
        }
        CourseService service = new CourseServiceImpl();
        ResponseDto dto = service.search(searchWord, page, limit, sortMode);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
