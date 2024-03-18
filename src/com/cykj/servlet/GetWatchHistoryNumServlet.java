package com.cykj.servlet;

import com.cykj.annotation.Servlet;
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
 * @since 2024/3/12 22:43
 */
@Servlet("/getWatchHistoryNum")
public class GetWatchHistoryNumServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        WatchHistoryService service = new WatchHistoryServiceImpl();
        int uid = Integer.parseInt(request.getValue("uid"));
        ResponseDto dto = service.getWatchHistoryNum(uid);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
