/*
 * @(#)Direct-X-Buffer.java	1.39 02/05/06
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;

import sun.misc.Unsafe;
import sun.nio.ch.DirectBuffer;
import sun.nio.ch.FileChannelImpl;


class DirectShortBufferRS



    extends DirectShortBufferS

    implements DirectBuffer
{



























































































    DirectShortBufferRS(DirectByteBufferR bb) { 	// package-private













	super(bb);

    }



    DirectShortBufferRS(DirectBuffer db,	        // package-private
			       int mark, int pos, int lim, int cap,
			       int off)
    {






	super(db, mark, pos, lim, cap, off);

    }

    public ShortBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 1);
	return new DirectShortBufferRS(this, -1, 0, rem, rem, off);
    }

    public ShortBuffer duplicate() {
	return new DirectShortBufferRS(this,
					      this.markValue(),
					      this.position(),
					      this.limit(),
					      this.capacity(),
					      0);
    }

    public ShortBuffer asReadOnlyBuffer() {








	return duplicate();

    }


















































    public ShortBuffer put(short x) {




	throw new ReadOnlyBufferException();

    }

    public ShortBuffer put(int i, short x) {




	throw new ReadOnlyBufferException();

    }

    public ShortBuffer put(ShortBuffer src) {




































	throw new ReadOnlyBufferException();

    }

    public ShortBuffer put(short[] src, int offset, int length) {






















	throw new ReadOnlyBufferException();

    }
    
    public ShortBuffer compact() {











	throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
	return true;
    }

    public boolean isReadOnly() {
	return true;
    }











































    public ByteOrder order() {

	return ((ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
		? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);





    }











































}
