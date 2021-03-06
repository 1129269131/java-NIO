package com.koala.nio.channelTest04;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Create by koala on 2020-04-05
 *
 * 分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）: 将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）: 将多个缓冲区中的数据聚集到通道中
 *
 */
public class TestChannel03 {

    //分散和聚集
    @Test
    public void test1() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //1.获取通道
        FileChannel channel = raf1.getChannel();

        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3.分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-------------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4.聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);
    }

}
