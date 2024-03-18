package com.cykj.servlet;

import com.alibaba.fastjson2.JSON;
import com.cykj.annotation.Servlet;
import com.cykj.net.HttpRequest;
import com.cykj.net.HttpResponse;
import com.cykj.net.ResponseDto;
import com.cykj.util.ServerConsoleUtils;
import com.cykj.util.VerifyCodeUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Description: 获取验证码服务
 * @author Guguguy
 * @version 1.0
 * @since 2024/2/4 20:56
 */
@Servlet("/getVerifyCode")
public class GetVerifyCodeServlet extends BasicServlet{
    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        ResponseDto responseDto = new ResponseDto(0, "unavailable method", null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }


    @Override
    public void doGet(HttpRequest request, HttpResponse response) {
        File dir = new File("./webapps/static/images/verifycodes");
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                boolean r = file.delete();
                if (r) {
                    ServerConsoleUtils.printOut("removed file : " + fileName);
                } else {
                    ServerConsoleUtils.printOut("remove failed");
                }
            }
        }
        int width=200,height=80;
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        String vc = verifyCode;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(verifyCode.getBytes());
            verifyCode = new BigInteger(1, md.digest()).toString(16);
            ServerConsoleUtils.printOut("vc : " + verifyCode);
            File file = new File(dir, verifyCode + ".jpg");
            VerifyCodeUtils.outputImage(width, height, file, vc);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        ResponseDto responseDto = new ResponseDto(1, verifyCode, null);
        response.write("text/html; charset:utf-8", JSON.toJSONBytes(responseDto));
    }
}
