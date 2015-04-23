/*
 * @(#)FocusEvent.java	1.22 00/02/02
 *
 * Copyright 1996-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package java.awt.event;

import java.awt.Component;
import java.awt.Event;

/**
 * A low-level event which indicates that a component has gained
 * or lost the keyboard focus.
 * This low-level event is generated by a component (such as a text field).
 * The event is passed to every <code>FocusListener</code>
 * or <code>FocusAdapter</code> object which registered to receive such
 * events using the component's <code>addFocusListener</code> method.
 * (<code>FocusAdapter</code> objects implement the 
 * <code>FocusListener</code> interface.) Each such listener object 
 * gets this <code>FocusEvent</code> when the event occurs.
 * <P>
 * There are two levels of focus change events: permanent and temporary.
 * Permanent focus change events occur when focus is directly moved
 * from one component to another, such as through calls to requestFocus()
 * or as the user uses the Tab key to traverse components.
 * Temporary focus change events occur when focus is temporarily
 * gained or lost for a component as the indirect result of another
 * operation, such as window deactivation or a scrollbar drag.  In this
 * case, the original focus state will automatically be restored once
 * that operation is finished, or, for the case of window deactivation,
 * when the window is reactivated.  Both permanent and temporary focus
 * events are delivered using the FOCUS_GAINED and FOCUS_LOST event ids;
 * the levels may be distinguished in the event using the isTemporary()
 * method.
 *  
 * @see FocusAdapter
 * @see FocusListener
 * @see <a href="http://java.sun.com/docs/books/tutorial/post1.0/ui/focuslistener.html">Tutorial: Writing a Focus Listener</a>
 * @see <a href="http://www.awl.com/cp/javaseries/jcl1_2.html">Reference: The Java Class Libraries (update file)</a>
 *
 * @author Carl Quinn
 * @author Amy Fowler
 * @version 1.22 02/02/00
 * @since 1.1
 */
public class FocusEvent extends ComponentEvent {

    /**
     * The first number in the range of ids used for focus events.
     */    
    public static final int FOCUS_FIRST		= 1004;

    /**
     * The last number in the range of ids used for focus events.
     */
    public static final int FOCUS_LAST		= 1005;

    /**
     * This event indicates that the component gained the keyboard focus.  
     */
    public static final int FOCUS_GAINED = FOCUS_FIRST; //Event.GOT_FOCUS

    /**
     * This event indicates that the component lost the keyboard focus.  
     */
    public static final int FOCUS_LOST = 1 + FOCUS_FIRST; //Event.LOST_FOCUS

    /**
     * A focus event can have two different levels,
     * permanent and temporary. It will be set to true if some
     * operation takes away the focus temporarily and
     * intends on getting it back once the event is completed.
     * Otherwise it will be set to false.
     *
     * @serial
     * @see isTemporary()
     */
    boolean temporary = false;

    /*
     * JDK 1.1 serialVersionUID 
     */
     private static final long serialVersionUID = 523753786457416396L;

    /**
     * Constructs a FocusEvent object and identifies whether or not the
     * change is temporary.
     *
     * @param source    the Component that originated the event
     * @param id        an integer indicating the type of event
     * @param temporary a boolean, true if the focus change is temporary
     */
    public FocusEvent(Component source, int id, boolean temporary) {
        super(source, id);
        this.temporary = temporary;
    }

    /**
     * Constructs a FocusEvent object and identifies it as a permanent 
     * change in focus.
     *
     * @param source    the Component that originated the event
     * @param id        an integer indicating the type of event
     */
    public FocusEvent(Component source, int id) {
        this(source, id, false);
    }

    /**
     * Identifies the focus change event as temporary or permanent.
     *
     * @return a boolean value, true if the focus change is temporary
     */
    public boolean isTemporary() {
        return temporary;
    }

    /**
     * Returns a parameter string identifying this event.
     * This method is useful for event-logging and for debugging.
     *
     * @return a string identifying the event and its attributes
     */
    public String paramString() {
        String typeStr;
        switch(id) {
          case FOCUS_GAINED:
              typeStr = "FOCUS_GAINED";
              break;
          case FOCUS_LOST:
              typeStr = "FOCUS_LOST";
              break;
          default:
              typeStr = "unknown type";
        }
        return typeStr + (temporary? ",temporary" : ",permanent");
    }

}