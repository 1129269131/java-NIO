package com.koala.nio.channelTest04;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Create by koala on 2020-04-04
 *
 * 一、通道（Channel）: 用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 二、通道的主要实现类
 *  java.nio.channels.Channel 接口：
 *      |--FileChannel
 *      |--SocketChannel
 *      |--ServerSocketChannel
 *      |--DatagramChannel
 *
 * 三、获取通道
 *  1.Java 针对支持通道的类提供了 getChannel() 方法
 *      本地 IO：
 *      FileInputStream/FileOutputStream
 *      RandomAccessFile
 *
 *      网络 IO：
 *      Socket
 *      ServerSocket
 *      DatagramSocket
 *
 *  2.在 JDK1.7 中的NIO.2 针对各个通道提供了静态方法 open()
 *  3.在 JDK1.7 中的NIO.2 的 Files 工具类的 newByteChannel()
 *
 */
public class TestChannel01 {

    //1.利用通道完成文件的复制（非直接缓冲区）
    @Test
    public void test1(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.png");
            fos = new FileOutputStream("2.png");

            //➀获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //➁分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //➂将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1){
                buf.flip(); //切换读取数据的模式
                //➃将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear(); //清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //使用直接缓冲区完成文件的复制（内存映射文件）
    @Test
    public void test2()throws IOException{
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"),StandardOpenOption.READ);
        //FileChannel outChannel = FileChannel.open(Paths.get("2.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);
        FileChannel outChannel = FileChannel.open(Paths.get("2.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);
        //内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();
    }

}
