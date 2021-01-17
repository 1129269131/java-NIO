package com.koala.nio.直接缓冲区与非直接缓冲区Day03;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * Create by koala on 2020-04-04
 *
 * 直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 *
 */
public class BufferTest01 {

    @Test
    public void test1(){
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        System.out.println(buf.isDirect());
    }

}
