package com.cykj.servlet;

import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/26 23:03
 */
public class GetPurchaseStateServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        int uid = Integer.parseInt(request.getValue("uid"));
        int courseId = Integer.parseInt(request.getValue("courseId"));
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
