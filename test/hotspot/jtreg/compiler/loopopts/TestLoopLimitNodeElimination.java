/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
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

/*
 * @test
 * @bug 8260370
 * @summary C2: LoopLimit node is not eliminated
 *
 * @run main/othervm
 *      -Xcomp
 *      -XX:CompileOnly=compiler/loopopts/TestLoopLimitNodeElimination
 *      compiler.loopopts.TestLoopLimitNodeElimination
 */

package compiler.loopopts;

public class TestLoopLimitNodeElimination {
    int a = 400;
    static int counter = 0;
    long b[] = new long[a];

    void c(String[] d) {
        int e = 0, f, g, h, i[] = new int[a];
        long j;
        f = 2;
        b[f] = e;
        b = b;
        for (j = 301; j > 2; j -= 2) {
            g = 1;
            do {
                for (h = (int) j; h < 1; h++) {
                }
            } while (++g < 4);
        }
        counter++;
        if (counter == 100000) {
            throw new RuntimeException("expected");
        }
    }

    public static void main(String[] k) {
        try {
            TestLoopLimitNodeElimination l = new TestLoopLimitNodeElimination();
            for (;;) {
                l.c(k);
            }
        } catch (RuntimeException ex) {
            // Expected
        }
    }
}

