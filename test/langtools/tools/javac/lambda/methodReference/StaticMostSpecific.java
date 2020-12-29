/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8231461
 * @summary static/instance overload leads to 'unexpected static method found in unbound lookup' when resolving method reference
 * @library /lib/combo /tools/lib /tools/javac/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.util
 * @run testng StaticMostSpecific
 */

import java.util.function.*;
import com.sun.tools.javac.util.Assert;

import org.testng.annotations.Test;
import tools.javac.combo.CompilationTestCase;

import static org.testng.Assert.assertEquals;

@Test
public class StaticMostSpecific extends CompilationTestCase {
    public StaticMostSpecific() {
        setDefaultFilename("Test.java");
    }

    public void testRestrictedIdentifiers() {

    }

    /*
    public String foo(Object o) { return "foo"; }
    public static String foo(String o) { return "bar"; }

    public static void main(String... args) {
        Function<String, String> f = StaticMostSpecific::foo;
        Assert.check(f.apply("").equals("bar"));
    }
    */
}
