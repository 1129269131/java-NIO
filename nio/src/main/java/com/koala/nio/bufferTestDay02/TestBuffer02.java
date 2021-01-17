package com.koala.nio.bufferTestDay02;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Create by koala on 2020-04-03
 */
public class TestBuffer02 {

    @Test
    public void test1() {
        String str = "abcde";
        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //2.利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("------------put()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3.切换读取数据模式
        buf.flip();

        System.out.println("------------flip()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4.利用 get() 读取缓冲区中的数据
        byte[] dst = new byte[buf.limit()];
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("------------get()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5.rewind(): 可重复读
        buf.rewind();

        System.out.println("------------rewind()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6.clear(): 清空缓冲区，但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();

        System.out.println("------------clear()-------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("------------测试缓冲区中是否依然存在数据-------------");
        System.out.println((char)buf.get());
    }

}
