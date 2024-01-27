package com.cykj.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Description: TODO
 *
 * @author Guguguy
 * @version 1.0
 * @since 2024/1/28 0:50
 */
public class HttpResponse {
    private final SocketChannel socketChannel;

    public HttpResponse(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
    }

    /**
     * Description: TODO
     * 向对方进行写入流
     * @param mimeType 传入数据类型
     * @param data 传入数据
     * @author Guguguy
     * @since 2023/11/22 20:06
     */
    public void write(String mimeType, byte[] data){
        // 创建字符串，用于构建返回消息
        StringBuilder stringBuilder = new StringBuilder();
        //构建返回消息
        stringBuilder.append("HTTP/1.1 200 OK");
        stringBuilder.append("\r\n");
        stringBuilder.append("Connection:keep-alive");
        stringBuilder.append("\r\n");
        stringBuilder.append("Content-Type:").append(mimeType).append("; charset=utf-8");// 这里的字符类型填需要传输的文件类型
        stringBuilder.append("\r\n");
        stringBuilder.append("Content-Length:").append(data.length);// 这里拼接传输文件的字节长度
        stringBuilder.append("\r\n");
        stringBuilder.append("\r\n");
        String code = stringBuilder.toString();


        try {
            // 开辟缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(code.getBytes().length);
            // 向缓冲区写入内容
            byteBuffer.put(code.getBytes());
            // 读写转换
            byteBuffer.flip();
            // 向外输出数据
            socketChannel.write(byteBuffer);
            // 创建缓冲区并填充字节数组
            ByteBuffer dataBuffer = ByteBuffer.wrap(data);
            // 当缓冲区还有数据时继续输出
            while (dataBuffer.hasRemaining()){
                socketChannel.write(dataBuffer);
            }
            // 向外输出数据
            socketChannel.shutdownOutput();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
