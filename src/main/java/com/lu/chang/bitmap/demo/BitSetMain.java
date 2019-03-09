package com.lu.chang.bitmap.demo;

import java.util.BitSet;

/**
 * created by LuChang
 * 2019/3/9 12:56
 */
public class BitSetMain {

    public static void main(String[] args) {
        BitSet a = new BitSet();
        BitSet b = BitSet.valueOf(new long[]{1,2,3});
        a.set(1);
        a.set(1<<30);
        System.out.println(a);
        System.out.println(b);
        System.out.println(a.toByteArray().length/1024.0/1024);
    }

}
