package com.lu.chang.bitmap.demo;

import com.googlecode.javaewah.EWAHCompressedBitmap;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * created by LuChang
 * 2019/3/9 11:40
 */
public class BitMapMain {

    public static void main(String[] args) throws IOException {
        EWAHCompressedBitmap ewahBitmap1 = EWAHCompressedBitmap.bitmapOf(0, 2, 55, 64, 1 << 30);
        EWAHCompressedBitmap ewahBitmap2 = EWAHCompressedBitmap.bitmapOf(1, 3, 64,
                1 << 30);
        ewahBitmap1.set(1000000);
        System.out.println("ewahBitmap1 memory usage: " + ewahBitmap1.sizeInBytes() + " bytes");
        System.out.println("bitmap 1: " + ewahBitmap1);
        System.out.println("bitmap 2: " + ewahBitmap2);
        // or
        EWAHCompressedBitmap orBitMap = ewahBitmap1.or(ewahBitmap2);
        System.out.println("bitmap 1 OR bitmap 2: " + orBitMap);
        System.out.println("memory usage: " + orBitMap.sizeInBytes() + " bytes");
        // and
        EWAHCompressedBitmap andBitMap = ewahBitmap1.and(ewahBitmap2);
        System.out.println("bitmap 1 AND bitmap 2: " + andBitMap);
        System.out.println("memory usage: " + andBitMap.sizeInBytes() + " bytes");
        // xor
        EWAHCompressedBitmap xorBitMap = ewahBitmap1.xor(ewahBitmap2);
        System.out.println("bitmap 1 XOR bitmap 2:" + xorBitMap);
        System.out.println("memory usage: " + xorBitMap.sizeInBytes() + " bytes");
        // fast aggregation over many bitmaps
        EWAHCompressedBitmap ewahBitmap3 = EWAHCompressedBitmap.bitmapOf(5, 55,
                1 << 30);
        EWAHCompressedBitmap ewahBitmap4 = EWAHCompressedBitmap.bitmapOf(4, 66,
                1 << 30);
        System.out.println("bitmap 3: " + ewahBitmap3);
        System.out.println("bitmap 4: " + ewahBitmap4);
        andBitMap = EWAHCompressedBitmap.and(ewahBitmap1, ewahBitmap2, ewahBitmap3,
                ewahBitmap4);
        System.out.println("b1 AND b2 AND b3 AND b4: " + andBitMap);
        // serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // Note: you could use a file output steam instead of ByteArrayOutputStream
        ewahBitmap1.serialize(new DataOutputStream(bos));
        EWAHCompressedBitmap ewahBitmap1new = new EWAHCompressedBitmap();
        byte[] bout = bos.toByteArray();
        ewahBitmap1new.deserialize(new DataInputStream(new ByteArrayInputStream(bout)));
        System.out.println("bitmap 1 (recovered) : " + ewahBitmap1new);
        if (!ewahBitmap1.equals(ewahBitmap1new)) throw new RuntimeException("Will not happen");
        //
        // we can use a ByteBuffer as backend for a bitmap
        // which allows memory-mapped bitmaps
        //
        ByteBuffer bb = ByteBuffer.wrap(bout);
        EWAHCompressedBitmap rmap = new EWAHCompressedBitmap(bb);
        System.out.println("bitmap 1 (mapped) : " + rmap);

        if (!rmap.equals(ewahBitmap1)) throw new RuntimeException("Will not happen");
        //
        // support for threshold function (new as of version 0.8.0):
        // mark as true a bit that occurs at least T times in the source
        // bitmaps
        //
        EWAHCompressedBitmap threshold2 = EWAHCompressedBitmap.threshold(2,
                ewahBitmap1, ewahBitmap2, ewahBitmap3, ewahBitmap4);
        System.out.println("threshold 2 : " + threshold2);
    }

}
