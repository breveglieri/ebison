/*
 * @(#)MemoryCacheImageInputStream.java	1.21 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.imageio.stream;

import java.io.InputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import java.util.ArrayList;

/**
 * An implementation of <code>ImageInputStream</code> that gets its
 * input from a regular <code>InputStream</code>.  A memory buffer is
 * used to cache at least the data between the discard position and
 * the current read position.
 *
 * <p> In general, it is preferable to use a
 * <code>FileCacheImageInputStream</code> when reading from a regular
 * <code>InputStream</code>.  This class is provided for cases where 
 * it is not possible to create a writable temporary file.
 *
 * @version 0.5
 */
public class MemoryCacheImageInputStream extends ImageInputStreamImpl {

    private InputStream stream;

    private MemoryCache cache = new MemoryCache();

    /**
     * Constructs a <code>MemoryCacheImageInputStream</code> that will read
     * from a given <code>InputStream</code>.
     *
     * @param stream an <code>InputStream</code> to read from.
     *
     * @exception IllegalArgumentException if <code>stream</code> is
     * <code>null</code>.
     */
    public MemoryCacheImageInputStream(InputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("stream == null!");
        }        
        this.stream = stream;
    }

    public int read() throws IOException {
        checkClosed();
        bitOffset = 0;
        long thisByte = streamPos;
        long pos = cache.loadFromStream(stream, streamPos+1);
        if (pos >= streamPos+1) {
            return cache.read(streamPos++);
        } else {
            return -1;
        }
    }

    public int read(byte[] b, int off, int len) throws IOException {
        checkClosed();
        // Will throw NullPointerException
        if (off < 0 || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException
                ("off < 0 || len < 0 || off + len > b.length!");
        }

        bitOffset = 0;

        if (len == 0) {
            return 0;
        }

        long pos = cache.loadFromStream(stream, streamPos+len);

        len = (int)(pos - streamPos);  // In case stream ended early

        if (len > 0) {
            cache.read(b, off, len, streamPos);
            streamPos += len;
            return len;
        } else {
            return -1;
        }
    }

    public void flushBefore(long pos) throws IOException {
        super.flushBefore(pos);
        cache.disposeBefore(pos);
    }

    /**
     * Returns <code>true</code> since this
     * <code>ImageInputStream</code> caches data in order to allow
     * seeking backwards.
     *
     * @return <code>true</code>.
     *
     * @see #isCachedMemory
     * @see #isCachedFile
     */
    public boolean isCached() {
        return true;
    }

    /**
     * Returns <code>false</code> since this
     * <code>ImageInputStream</code> does not maintain a file cache.
     *
     * @return <code>false</code>.
     *
     * @see #isCached
     * @see #isCachedMemory
     */
    public boolean isCachedFile() {
        return false;
    }

    /**
     * Returns <code>true</code> since this
     * <code>ImageInputStream</code> maintains a main memory cache.
     *
     * @return <code>true</code>.
     *
     * @see #isCached
     * @see #isCachedFile
     */
    public boolean isCachedMemory() {
        return true;
    }

    /**
     * Closes this <code>MemoryCacheImageInputStream</code>, freeing
     * the cache.  The source <code>InputStream</code> is not closed.
     */
    public void close() throws IOException {
        super.close();
        cache.reset();
        stream = null;
    }
    
}
