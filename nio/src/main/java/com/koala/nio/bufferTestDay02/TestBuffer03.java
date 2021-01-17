package com.koala.nio.bufferTestDay02;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Create by koala on 2020-04-03
 *
 * mark: 标记，表示记录当前 position 的位置。可以通过 reset() 恢复 mark 的位置
 *
 */
public class TestBuffer03 {

    @Test
    public void test1() {
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));

        System.out.println(buf.position());

        //mark(): 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        //reset(): 恢复到 mark 的位置
        buf.reset();

        System.out.println(buf.position());

        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){

            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }
    }

}
