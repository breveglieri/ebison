/*
 * @(#)LayoutComparator.java	1.2 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package javax.swing;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Window;


/**
 * Comparator which attempts to sort Components based on their size and
 * position. Code adapted from original javax.swing.DefaultFocusManager
 * implementation.
 *
 * @version 1.2, 12/03/01
 * @author David Mendenhall
 */
final class LayoutComparator implements Comparator, java.io.Serializable {

    private static final int ROW_TOLERANCE = 10;

    private boolean horizontal = true;
    private boolean leftToRight = true;

    void setComponentOrientation(ComponentOrientation orientation) {
	horizontal = orientation.isHorizontal();
	leftToRight = orientation.isLeftToRight();
    }

    public int compare(Object o1, Object o2) {
	Component a = (Component)o1;
	Component b = (Component)o2;

	if (a == b) {
	    return 0;
	}

	// Row/Column algorithm only applies to siblings. If 'a' and 'b'
	// aren't siblings, then we need to find their most inferior
	// ancestors which share a parent. Compute the ancestory lists for
	// each Component and then search from the Window down until the
	// hierarchy branches.
	if (a.getParent() != b.getParent()) {
	    LinkedList aAncestory, bAncestory;

	    for(aAncestory = new LinkedList(); a != null; a = a.getParent()) {
		aAncestory.add(a);
		if (a instanceof Window) {
		    break;
		}
	    }
	    if (a == null) {
		// 'a' is not part of a Window hierarchy. Can't cope.
		throw new ClassCastException(a.toString());
	    }

	    for(bAncestory = new LinkedList(); b != null; b = b.getParent()) {
		bAncestory.add(b);
		if (b instanceof Window) {
		    break;
		}
	    }
	    if (b == null) {
		// 'b' is not part of a Window hierarchy. Can't cope.
		throw new ClassCastException(b.toString());
	    }

	    for (ListIterator
		     aIter = aAncestory.listIterator(aAncestory.size()),
		     bIter = bAncestory.listIterator(bAncestory.size()); ;) {
		if (aIter.hasPrevious()) {
		    a = (Component)aIter.previous();
		} else {
		    // a is an ancestor of b
		    return -1;
		}

		if (bIter.hasPrevious()) {
		    b = (Component)bIter.previous();
		} else {
		    // b is an ancestor of a
		    return 1;
		}

		if (a != b) {
		    break;
		}
	    }
	}

        int ax = a.getX(), ay = a.getY(), bx = b.getX(), by = b.getY();

	if (horizontal) {
	    if (leftToRight) {

		// LT - Western Europe (optional for Japanese, Chinese, Korean)

		if (Math.abs(ay - by) < ROW_TOLERANCE) {
		    return (ax < bx) ? -1 : 1;
		} else {
		    return (ay < by) ? -1 : 1;
		}
	    } else { // !leftToRight

		// RT - Middle East (Arabic, Hebrew)

		if (Math.abs(ay - by) < ROW_TOLERANCE) {
		    return (ax > bx) ? -1 : 1;
		} else {
		    return (ay < by) ? -1 : 1;
		}
	    }
	} else { // !horizontal
	    if (leftToRight) {

		// TL - Mongolian

		if (Math.abs(ax - bx) < ROW_TOLERANCE) {
		    return (ay < by) ? -1 : 1;
		} else {
		    return (ax < bx) ? -1 : 1;
		}
	    } else { // !leftToRight

		// TR - Japanese, Chinese, Korean

		if (Math.abs(ax - bx) < ROW_TOLERANCE) {
		    return (ay < by) ? -1 : 1;
		} else {
		    return (ax > bx) ? -1 : 1;
		}
	    }
	}
    }
}
