package com.cykj.servlet;

import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.WatchHistoryService;
import com.cykj.service.impl.WatchHistoryServiceImpl;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/12 23:00
 */
public class GetUserWatchHistoryServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        WatchHistoryService service = new WatchHistoryServiceImpl();
        int uid = Integer.parseInt(request.getValue("uid"));
        int limit = Integer.parseInt(request.getValue("lim"));
        int page = Integer.parseInt(request.getValue("page"));
        ResponseDto dto = service.getUserWatchHistories(uid, limit, page);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
