/*
 * Copyright (c) 2021, Huawei Technologies Co., Ltd. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/**
 * @test
 * @bug 8260585
 * @summary AArch64: Wrong code generated for shifting right and accumulating four unsigned short integers.
 * @run main/othervm compiler.c2.TestShiftRightAndAccumulate
 *
 */

package compiler.c2;

import java.util.Random;
import java.util.Arrays;

public class TestShiftRightAndAccumulate {
    private static final int SMALL_LEN = 16;
    private static final int LARGE_LEN = 1000;
    private static final int NUM_ITERS = 200000;
    private static final int MAX_TESTS = 10;

    private static byte[]  bytesA,  bytesB,  bytesC;
    private static short[] shortsA, shortsB, shortsC;
    private static char[]  charsA,  charsB,  charsC;
    private static int[]   intsA,   intsB,   intsC;
    private static long[]  longsA,  longsB,  longsC;

    private static byte  gBytes[][];
    private static short gShorts[][];
    private static char  gChars[][];
    private static int   gInts[][];
    private static long  gLongs[][];

    private static Random r = new Random(32781);

    public static void main(String args[]) {
      test_small();
      test_large();
      System.out.println("Test PASSED");
    }

    static void test_small() {
        test_init(SMALL_LEN);
        for (int i = 0; i < NUM_ITERS; i++) {
            test_bytes();
            test_shorts();
            test_chars();
            test_ints();
            test_longs();
        }
    }

    static void test_large() {
        test_init(LARGE_LEN);
        for (int i = 0; i < NUM_ITERS; i++) {
            test_bytes();
            test_shorts();
            test_chars();
            test_ints();
            test_longs();
        }
    }

    static void test_bytes() {
        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >> 1));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[0]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >> 8));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[1]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >> 13));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[2]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >> 19));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[3]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >>> 2));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[4]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >>> 8));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[5]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >>> 13));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[6]));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (bytesB[i] >>> 19));
            bytesC[i] = (byte) (bytesC[i] + bytesA[i] + bytesB[i]);
        }
        assertTrue(Arrays.equals(bytesC, gBytes[7]));
    }

    static void test_shorts() {
        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 7));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[0]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 16));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[1]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 23));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[2]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 35));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[3]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >>> 7));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[4]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >>> 16));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[5]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >>> 23));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[6]));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >>> 35));
            shortsC[i] = (short) (shortsC[i] + shortsA[i] + shortsB[i]);
        }
        assertTrue(Arrays.equals(shortsC, gShorts[7]));
    }

    static void test_chars() {
        for (int i = 0; i < charsC.length; i++) {
            charsC[i] = (char) (charsA[i] + (charsB[i] >>> 4));
            charsC[i] = (char) (charsC[i] + charsA[i] + charsB[i]);
        }
        assertTrue(Arrays.equals(charsC, gChars[0]));

        for (int i = 0; i < charsC.length; i++) {
            charsC[i] = (char) (charsA[i] + (charsB[i] >>> 16));
            charsC[i] = (char) (charsC[i] + charsA[i] + charsB[i]);
        }
        assertTrue(Arrays.equals(charsC, gChars[1]));

        for (int i = 0; i < charsC.length; i++) {
            charsC[i] = (char) (charsA[i] + (charsB[i] >>> 19));
            charsC[i] = (char) (charsC[i] + charsA[i] + charsB[i]);
        }
        assertTrue(Arrays.equals(charsC, gChars[2]));

        for (int i = 0; i < charsC.length; i++) {
            charsC[i] = (char) (charsA[i] + (charsB[i] >>> 35));
            charsC[i] = (char) (charsC[i] + charsA[i] + charsB[i]);
        }
        assertTrue(Arrays.equals(charsC, gChars[3]));
    }

    static void test_ints() {
        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >> 19));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[0]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >> 32));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[1]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >> 49));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[2]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >> 67));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[3]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >>> 19));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[4]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >>> 32));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[5]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >>> 49));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[6]));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsA[i] + (intsB[i] >>> 67));
            intsC[i] = (intsC[i] + intsA[i] + intsB[i]);
        }
        assertTrue(Arrays.equals(intsC, gInts[7]));
    }

    static void test_longs() {
        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >> 37));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[0]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >> 64));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[1]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >> 93));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[2]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >> 137));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[3]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >>> 37));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[4]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >>> 64));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[5]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >>> 93));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[6]));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsA[i] + (longsB[i] >>> 137));
            longsC[i] = (longsC[i] + longsA[i] + longsB[i]);
        }
        assertTrue(Arrays.equals(longsC, gLongs[7]));
    }

    static void test_init(int count) {
        bytesA  = new byte[count];
        shortsA = new short[count];
        charsA  = new char[count];
        intsA   = new int[count];
        longsA  = new long[count];

        bytesB  = new byte[count];
        shortsB = new short[count];
        charsB  = new char[count];
        intsB   = new int[count];
        longsB  = new long[count];

        bytesC  = new byte[count];
        shortsC = new short[count];
        charsC  = new char[count];
        intsC   = new int[count];
        longsC  = new long[count];

        gBytes  = new byte[MAX_TESTS][count];
        gShorts = new short[MAX_TESTS][count];
        gChars  = new char[MAX_TESTS][count];
        gInts   = new int[MAX_TESTS][count];
        gLongs  = new long[MAX_TESTS][count];
        
        for (int i = 0; i < count; i++) {
            bytesA[i]  = (byte) r.nextInt();
            shortsA[i] = (short) r.nextInt();
            charsA[i]  = (char) r.nextInt();
            intsA[i]   = r.nextInt();
            longsA[i]  = r.nextLong();

            bytesB[i]  = (byte) r.nextInt();
            shortsB[i] = (short) r.nextInt();
            charsB[i]  = (char) r.nextInt();
            intsB[i]   = r.nextInt();
            longsB[i]  = r.nextLong();
        }

        for (int i = 0; i < count; i++) {
            gBytes[0][i]  = (byte) (bytesA[i] + (bytesB[i] >> 1));
            gBytes[0][i]  = (byte) (gBytes[0][i] + bytesA[i] + bytesB[i]);
            gBytes[1][i]  = (byte) (bytesA[i] + (bytesB[i] >> 8));
            gBytes[1][i]  = (byte) (gBytes[1][i] + bytesA[i] + bytesB[i]);
            gBytes[2][i]  = (byte) (bytesA[i] + (bytesB[i] >> 13));
            gBytes[2][i]  = (byte) (gBytes[2][i] + bytesA[i] + bytesB[i]);
            gBytes[3][i]  = (byte) (bytesA[i] + (bytesB[i] >> 19));
            gBytes[3][i]  = (byte) (gBytes[3][i] + bytesA[i] + bytesB[i]);
            gBytes[4][i]  = (byte) (bytesA[i] + (bytesB[i] >>> 2));
            gBytes[4][i]  = (byte) (gBytes[4][i] + bytesA[i] + bytesB[i]);
            gBytes[5][i]  = (byte) (bytesA[i] + (bytesB[i] >>> 8));
            gBytes[5][i]  = (byte) (gBytes[5][i] + bytesA[i] + bytesB[i]);
            gBytes[6][i]  = (byte) (bytesA[i] + (bytesB[i] >>> 13));
            gBytes[6][i]  = (byte) (gBytes[6][i] + bytesA[i] + bytesB[i]);
            gBytes[7][i]  = (byte) (bytesA[i] + (bytesB[i] >>> 19));
            gBytes[7][i]  = (byte) (gBytes[7][i] + bytesA[i] + bytesB[i]);

            gShorts[0][i]  = (short) (shortsA[i] + (shortsB[i] >> 7));
            gShorts[0][i]  = (short) (gShorts[0][i] + shortsA[i] + shortsB[i]);
            gShorts[1][i]  = (short) (shortsA[i] + (shortsB[i] >> 16));
            gShorts[1][i]  = (short) (gShorts[1][i] + shortsA[i] + shortsB[i]);
            gShorts[2][i]  = (short) (shortsA[i] + (shortsB[i] >> 23));
            gShorts[2][i]  = (short) (gShorts[2][i] + shortsA[i] + shortsB[i]);
            gShorts[3][i]  = (short) (shortsA[i] + (shortsB[i] >> 35));
            gShorts[3][i]  = (short) (gShorts[3][i] + shortsA[i] + shortsB[i]);
            gShorts[4][i]  = (short) (shortsA[i] + (shortsB[i] >>> 7));
            gShorts[4][i]  = (short) (gShorts[4][i] + shortsA[i] + shortsB[i]);
            gShorts[5][i]  = (short) (shortsA[i] + (shortsB[i] >>> 16));
            gShorts[5][i]  = (short) (gShorts[5][i] + shortsA[i] + shortsB[i]);
            gShorts[6][i]  = (short) (shortsA[i] + (shortsB[i] >>> 23));
            gShorts[6][i]  = (short) (gShorts[6][i] + shortsA[i] + shortsB[i]);
            gShorts[7][i]  = (short) (shortsA[i] + (shortsB[i] >>> 35));
            gShorts[7][i]  = (short) (gShorts[7][i] + shortsA[i] + shortsB[i]);

            gChars[0][i]  = (char) (charsA[i] + (charsB[i] >>> 4));
            gChars[0][i]  = (char) (gChars[0][i] + charsA[i] + charsB[i]);
            gChars[1][i]  = (char) (charsA[i] + (charsB[i] >>> 16));
            gChars[1][i]  = (char) (gChars[1][i] + charsA[i] + charsB[i]);
            gChars[2][i]  = (char) (charsA[i] + (charsB[i] >>> 19));
            gChars[2][i]  = (char) (gChars[2][i] + charsA[i] + charsB[i]);
            gChars[3][i]  = (char) (charsA[i] + (charsB[i] >>> 35));
            gChars[3][i]  = (char) (gChars[3][i] + charsA[i] + charsB[i]);

            gInts[0][i]  = intsA[i] + (intsB[i] >> 19);
            gInts[0][i]  = gInts[0][i] + intsA[i] + intsB[i];
            gInts[1][i]  = intsA[i] + (intsB[i] >> 32);
            gInts[1][i]  = gInts[1][i] + intsA[i] + intsB[i];
            gInts[2][i]  = intsA[i] + (intsB[i] >> 49);
            gInts[2][i]  = gInts[2][i] + intsA[i] + intsB[i];
            gInts[3][i]  = intsA[i] + (intsB[i] >> 67);
            gInts[3][i]  = gInts[3][i] + intsA[i] + intsB[i];
            gInts[4][i]  = intsA[i] + (intsB[i] >>> 19);
            gInts[4][i]  = gInts[4][i] + intsA[i] + intsB[i];
            gInts[5][i]  = intsA[i] + (intsB[i] >>> 32);
            gInts[5][i]  = gInts[5][i] + intsA[i] + intsB[i];
            gInts[6][i]  = intsA[i] + (intsB[i] >>> 49);
            gInts[6][i]  = gInts[6][i] + intsA[i] + intsB[i];
            gInts[7][i]  = intsA[i] + (intsB[i] >>> 67);
            gInts[7][i]  = gInts[7][i] + intsA[i] + intsB[i];

            gLongs[0][i]  = longsA[i] + (longsB[i] >> 37);
            gLongs[0][i]  = gLongs[0][i] + longsA[i] + longsB[i];
            gLongs[1][i]  = longsA[i] + (longsB[i] >> 64);
            gLongs[1][i]  = gLongs[1][i] + longsA[i] + longsB[i];
            gLongs[2][i]  = longsA[i] + (longsB[i] >> 93);
            gLongs[2][i]  = gLongs[2][i] + longsA[i] + longsB[i];
            gLongs[3][i]  = longsA[i] + (longsB[i] >> 137);
            gLongs[3][i]  = gLongs[3][i] + longsA[i] + longsB[i];
            gLongs[4][i]  = longsA[i] + (longsB[i] >>> 37);
            gLongs[4][i]  = gLongs[4][i] + longsA[i] + longsB[i];
            gLongs[5][i]  = longsA[i] + (longsB[i] >>> 64);
            gLongs[5][i]  = gLongs[5][i] + longsA[i] + longsB[i];
            gLongs[6][i]  = longsA[i] + (longsB[i] >>> 93);
            gLongs[6][i]  = gLongs[6][i] + longsA[i] + longsB[i];
            gLongs[7][i]  = longsA[i] + (longsB[i] >>> 137);
            gLongs[7][i]  = gLongs[7][i] + longsA[i] + longsB[i];
        }
    }

    static void assertTrue(boolean okay) {
        if (!okay) {
            throw new RuntimeException("Test Failed");
        }
    }
}
