package com.koala.nio.blockingNIOTestDay05;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Create by koala on 2020-04-05
 */
public class TestBlockingNIO02 {

    //客户端
    @Test
    public void client() throws IOException {
        //1.获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);

        //2.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //3.读取本地文件，并发送到服务器
        while(inChannel.read(buf) != -1){
            buf.flip();
            sChannel.write(buf);
            buf.clear();
        }

        //如果不shutdownOutput会被阻塞
        sChannel.shutdownOutput();

        //4.接收服务器的反馈
        int len = 0;
        while ((len = sChannel.read(buf)) != -1){
            buf.flip();
            System.out.println(new String(buf.array(), 0, len));
            buf.clear();
        }

        //5.关闭通道
        inChannel.close();
        sChannel.close();
    }

    //服务器
    @Test
    public void server() throws IOException{
        //1.获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        FileChannel outChannel = FileChannel.open(Paths.get("2.png"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //2.绑定连接
        ssChannel.bind(new InetSocketAddress(9898));

        //3.获取客户端连接的通道
        SocketChannel sChannel = ssChannel.accept();

        //4.分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //5.接收客户端的数据，并保存到本地
        while (sChannel.read(buf) != -1){
            buf.flip();
            outChannel.write(buf);
            buf.clear();
        }

        //6.发送反馈给客户端
        buf.put("服务器接收数据成功".getBytes());
        buf.flip();
        sChannel.write(buf);

        //6.关闭通道
        sChannel.close();
        outChannel.close();
        ssChannel.close();
    }

}
