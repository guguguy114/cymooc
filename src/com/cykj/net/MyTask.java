package com.cykj.net;

import com.cykj.servlet.BasicServlet;
import com.cykj.util.ServerConsoleUtils;
import com.cykj.util.ServletUtils;

/**
 * Description: TODO
 * 任务对象，用于线程池的使用
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/17 16:06
 */
public class MyTask extends Thread{
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;
    public MyTask(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    @Override
    public void run() {
        // 判断请求类型，是请求静态资源还是动态资源
        if (httpRequest.getModule().contains(".")){
            // 根据请求获取访问路径
            String url = "./webapps" + httpRequest.getModule();
            // 将文件内容写入fileBytes中
            byte[] fileBytes = StaticResourceHandler.getContentBytes(url);
            // 根据文件类型获取文件类型字符串
            String mimeType = StaticResourceHandler.getMimeType(url);
            // 将路径指向的文件进行传输
            httpResponse.write(mimeType, fileBytes);
            ServerConsoleUtils.printOut("Transform Complete!", ServerConsoleUtils.GREEN);
        } else if (httpRequest.getModule().equals("/") || httpRequest.getModule().isEmpty()){
            // 根据请求获取访问路径
            String url = "./webapps/pages/Index.html";
            // 将文件内容写入fileBytes中
            byte[] fileBytes = StaticResourceHandler.getContentBytes(url);
            // 根据文件类型获取文件类型字符串
            String mimeType = StaticResourceHandler.getMimeType(url);
            // 将路径指向的文件进行传输
            httpResponse.write(mimeType, fileBytes);
            ServerConsoleUtils.printOut("Transform Complete!", ServerConsoleUtils.GREEN);
        } else {
            ServerConsoleUtils.printOut("module:" + httpRequest.getModule(), ServerConsoleUtils.GREEN);
            // 获取servlet对象，并执行服务
            BasicServlet servlet = ServletUtils.getServlet(httpRequest.getModule());
            servlet.service(httpRequest, httpResponse);
        }
    }
}
