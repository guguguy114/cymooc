package com.cykj.servlet;

import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.service.UserService;
import com.cykj.service.impl.UserServiceImpl;

import java.util.Map;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/3/10 16:23
 */
@Servlet("/uploadFaceImage")
public class UploadFaceImageServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        String imageData = request.getValue("data");
        int uid = Integer.parseInt(request.getValue("uid"));
        UserService service = new UserServiceImpl();
        ResponseDto dto = service.uploadFaceImage(uid, imageData.split(";base64,")[1]);
        writeDto(response, dto);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        doPost(request, response);
    }
}
