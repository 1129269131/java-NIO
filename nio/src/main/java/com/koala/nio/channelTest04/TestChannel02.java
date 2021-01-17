package com.koala.nio.channelTest04;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Create by koala on 2020-04-04
 *
 * 通道之间的数据传输
 * transferFrom()
 * transferTo()
 *
 */
public class TestChannel02 {

    //通道之间的数据传输（直接缓冲区）
    @Test
    public void test1()throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        inChannel.transferTo(0, inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();
    }

    @Test
    public void test2()throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.png"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

}
