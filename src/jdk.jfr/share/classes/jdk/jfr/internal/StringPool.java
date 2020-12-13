/*
 * Copyright (c) 2016, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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
package jdk.jfr.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import jdk.internal.misc.Unsafe;

public final class StringPool {

    private static final Unsafe unsafe = Unsafe.getUnsafe();

    static final int MIN_LIMIT = 16;
    static final int MAX_LIMIT = 128; /* 0 MAX means disabled */
    static final long DO_NOT_POOL = -1;
    static final long RETRY = -2;
    private static final long generationAddress;
    private static final SimpleStringIdPool sp = new SimpleStringIdPool();
    static {
        generationAddress = JVM.getJVM().getStringPoolGenerationAddress();
        sp.reset();
    }
    public static long addString(String s) {
        long result;
        do {
            result = sp.addString(s);
        } while (result == RETRY);
        return result;
    }
    private static long getCurrentGeneration() {
        return unsafe.getLong(generationAddress);
    }
    private static class SimpleStringIdPool {
        /* string id index */
        private final AtomicLong sidIdx = new AtomicLong(1);
        /* generation of cached strings */
        private long poolGen;
        /* the cache */
        private final ConcurrentHashMap<String, Long> cache;
        /* max size */
        private final int MAX_SIZE = 32*1024;
        /* max size bytes*/
        private final long MAX_SIZE_UTF16 = 16*1024*1024;
        /* max size bytes*/
        private long currentSizeUTF16;

        /* looking at a biased data set 4 is a good value */
        private final String[] preCache = new String[]{"", "" , "" ,""};
        /* index of oldest */
        private int preCacheOld = 0;
        /* loop mask */
        private static final int preCacheMask = 0x03;

        SimpleStringIdPool() {
            cache = new ConcurrentHashMap<>(MAX_SIZE, 0.75f);
        }
        void reset() {
            reset(getCurrentGeneration());
        }
        private void reset(long generation) {
            this.cache.clear();
            this.poolGen = generation;
            this.currentSizeUTF16 = 0;
        }
        private long addString(String s) {
            long currentGen = getCurrentGeneration();
            if (poolGen == currentGen) {
                /* pool is for current chunk */
                Long lsid = this.cache.get(s);
                if (lsid != null) {
                    return lsid.longValue();
                }
            } else {
                /* pool is for an old chunk */
                reset(currentGen);
            }
            if (!preCache(s)) {
                /* we should not pool this string */
                return DO_NOT_POOL;
            }
            if (cache.size() > MAX_SIZE || currentSizeUTF16 > MAX_SIZE_UTF16) {
                /* pool was full */
                reset(currentGen);
            }
            return storeString(s);
        }

        private long storeString(String s) {
            long sid = this.sidIdx.getAndIncrement();
            /* we can race but it is ok */
            this.cache.put(s, sid);
            long currentGen;
            synchronized(SimpleStringIdPool.class) {
                currentGen = JVM.addStringConstant(poolGen, sid, s);
                currentSizeUTF16 += s.length();
            }
            /* did we write in chunk that this pool represent */
            return currentGen == poolGen ? sid : RETRY;
        }
        private boolean preCache(String s) {
            if (preCache[0].equals(s)) {
                return true;
            }
            if (preCache[1].equals(s)) {
                return true;
            }
            if (preCache[2].equals(s)) {
                return true;
            }
            if (preCache[3].equals(s)) {
                return true;
            }
            preCacheOld = (preCacheOld - 1) & preCacheMask;
            preCache[preCacheOld] = s;
            return false;
        }
    }
}
