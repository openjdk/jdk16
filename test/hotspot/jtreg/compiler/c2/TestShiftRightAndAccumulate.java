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

    private static byte[]  bytesA,  bytesB,  bytesC,  bytesD,  bytesE;
    private static short[] shortsA, shortsB, shortsC, shortsD, shortsE;
    private static char[]  charsA,  charsB,  charsC,  charsD,  charsE;
    private static int[]   intsA,   intsB,   intsC,   intsD,   intsE;
    private static long[]  longsA,  longsB,  longsC,  longsD,  longsE;

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
        }
        assertTrue(Arrays.equals(bytesC, bytesD));

        for (int i = 0; i < bytesC.length; i++) {
            bytesC[i] = (byte) (bytesA[i] + (((byte) (bytesB[i] >>> 3))));
        }
        assertTrue(Arrays.equals(bytesC, bytesE));
    }

    static void test_shorts() {
        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 5));
        }
        assertTrue(Arrays.equals(shortsC, shortsD));

        for (int i = 0; i < shortsC.length; i++) {
            shortsC[i] = (short) (shortsA[i] + (shortsB[i] >> 7));
        }
        assertTrue(Arrays.equals(shortsC, shortsE));
    }

    static void test_chars() {
        for (int i = 0; i < charsC.length; i++) {
            charsC[i] = (char) (charsA[i] + (charsB[i] >>> 4));
        }
        assertTrue(Arrays.equals(charsC, charsD));
    }

    static void test_ints() {
        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = intsA[i] + (intsB[i] >> 2);
        }
        assertTrue(Arrays.equals(intsC, intsD));

        for (int i = 0; i < intsC.length; i++) {
            intsC[i] = (intsB[i] >>> 2) + intsA[i];
        }
        assertTrue(Arrays.equals(intsC, intsE));
    }

    static void test_longs() {
        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = longsA[i] + (longsB[i] >> 5);
        }
        assertTrue(Arrays.equals(longsC, longsD));

        for (int i = 0; i < longsC.length; i++) {
            longsC[i] = (longsB[i] >>> 2) + longsA[i];
        }
        assertTrue(Arrays.equals(longsC, longsE));
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

        bytesD  = new byte[count];
        shortsD = new short[count];
        charsD  = new char[count];
        intsD   = new int[count];
        longsD  = new long[count];

        bytesE  = new byte[count];
        shortsE = new short[count];
        charsE  = new char[count];
        intsE   = new int[count];
        longsE  = new long[count];

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
            bytesD[i]  = (byte) (bytesA[i] + (bytesB[i] >> 1));
            bytesE[i]  = (byte) (bytesA[i] + (((byte) (bytesB[i] >>> 3))));
            shortsD[i] = (short) (shortsA[i] + (shortsB[i] >> 5));
            shortsE[i] = (short) (shortsA[i] + (shortsB[i] >> 7));
            charsD[i]  = (char) (charsA[i] + (charsB[i] >>> 4));
            intsD[i]   = intsA[i] + (intsB[i] >> 2);
            intsE[i]   = (intsB[i] >>> 2) + intsA[i];
            longsD[i]  = longsA[i] + (longsB[i] >> 5);
            longsE[i]  = (longsB[i] >>> 2) + longsA[i];
        }
    }

    static void assertTrue(boolean okay) {
        if (!okay) {
            throw new RuntimeException("Test Failed");
        }
    }
}
