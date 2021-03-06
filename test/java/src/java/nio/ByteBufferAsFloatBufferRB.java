/*
 * @(#)ByteBufferAs-X-Buffer.java	1.13 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio;


class ByteBufferAsFloatBufferRB			// package-private
    extends ByteBufferAsFloatBufferB
{








    ByteBufferAsFloatBufferRB(ByteBuffer bb) {	// package-private












	super(bb);

    }

    ByteBufferAsFloatBufferRB(ByteBuffer bb,
				     int mark, int pos, int lim, int cap,
				     int off)
    {





	super(bb, mark, pos, lim, cap, off);

    }

    public FloatBuffer slice() {
	int pos = this.position();
	int lim = this.limit();
	assert (pos <= lim);
	int rem = (pos <= lim ? lim - pos : 0);
	int off = (pos << 2) + offset;
	return new ByteBufferAsFloatBufferRB(bb, -1, 0, rem, rem, off);
    }

    public FloatBuffer duplicate() {
	return new ByteBufferAsFloatBufferRB(bb,
						    this.markValue(),
						    this.position(),
						    this.limit(),
						    this.capacity(),
						    offset);
    }

    public FloatBuffer asReadOnlyBuffer() {








	return duplicate();

    }

















    public FloatBuffer put(float x) {




	throw new ReadOnlyBufferException();

    }

    public FloatBuffer put(int i, float x) {




	throw new ReadOnlyBufferException();

    }

    public FloatBuffer compact() {
















	throw new ReadOnlyBufferException();

    }

    public boolean isDirect() {
	return bb.isDirect();
    }

    public boolean isReadOnly() {
	return true;
    }







































    public ByteOrder order() {

	return ByteOrder.BIG_ENDIAN;




    }

}
