/*
 * @(#)SlotTableStack.java	1.7 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.corba.se.internal.Interceptors;

import org.omg.CORBA.CompletionStatus;
import org.omg.PortableInterceptor.Current;
import org.omg.PortableInterceptor.InvalidSlot;

import com.sun.corba.se.internal.corba.AnyImpl;

/**
 * SlotTableStack is the container of SlotTable instances for each thread
 */
public class SlotTableStack
{
    // SlotTablePool is the container for reusable SlotTables'
    private class SlotTablePool {

        // Contains a list of reusable SlotTable
        private SlotTable[] pool;

        // High water mark for the pool
        // If the pool size reaches this limit then putSlotTable will
        // not put SlotTable to the pool.
        private final int  HIGH_WATER_MARK = 5;

        // currentIndex points to the last SlotTable in the list
        private int currentIndex;

        SlotTablePool( ) {
            pool = new SlotTable[HIGH_WATER_MARK];
            currentIndex = 0;
        }

        /**
         * Puts SlotTable to the re-usable pool.
         */
        void putSlotTable( SlotTable table ) {
            // If there are enough SlotTables in the pool, then don't add
            // this table to the pool.
            if( currentIndex >= HIGH_WATER_MARK ) {
                // Let the garbage collector collect it.
                return;
            }
            pool[currentIndex] = table;
            currentIndex++;
        }

        /**
         * Gets SlotTable from the re-usable pool.
         */
        SlotTable getSlotTable( ) {
            // If there are no entries in the pool then return null
            if( currentIndex == 0 ) {
                return null;
            }
            // Works like a stack, Gets the last one added first
            currentIndex--;
            return pool[currentIndex];
        }
    }
   
    // Contains all the active SlotTables for each thread.
    // The ArrayList is made to behave like a stack.
    private java.util.ArrayList tableContainer;

    // Keeps track of number of PICurrents in the stack.
    private int currentIndex;
 
    // For Every Thread there will be a pool of re-usable SlotTables'
    // stored in SlotTablePool
    private SlotTablePool tablePool;

    // The PIORB associated with this slot table stack
    private PIORB piOrb;

    /**
     * Constructs the stack and and SlotTablePool
     */
    SlotTableStack( PIORB piOrb, SlotTable table ) {
       this.piOrb = piOrb;
       currentIndex = 0;
       tableContainer = new java.util.ArrayList( );
       tablePool = new SlotTablePool( );
       // SlotTableStack will be created with one SlotTable on the stack.
       // This table is used as the reference to query for number of 
       // allocated slots to create other slottables.
       tableContainer.add( currentIndex, table );
       currentIndex++;
    }
   

    /**
     * pushSlotTable  pushes a fresh Slot Table on to the stack by doing the
     * following,
     * 1: Checks to see if there is any SlotTable in SlotTablePool
     *    If present then use that instance to push into the SlotTableStack
     *
     * 2: If there is no SlotTable in the pool, then creates a new one and 
     *    pushes that into the SlotTableStack
     */
    void pushSlotTable( ) {
        SlotTable table = tablePool.getSlotTable( );
        if( table == null ) {
            // get an existing PICurrent to get the slotSize
            SlotTable tableTemp = peekSlotTable();
            table = new SlotTable( piOrb, tableTemp.getSize( ));
        }
        tableContainer.add( currentIndex, table );
        currentIndex++;
    }

    /**
     * popSlotTable does the following
     * 1: pops the top SlotTable in the SlotTableStack
     *
     * 2: resets the slots in the SlotTable which resets the slotvalues to
     *    null if there are any previous sets. 
     *
     * 3: puts the reset SlotTable into the SlotTablePool to reuse 
     */
    void  popSlotTable( ) {
        if( currentIndex <= 1 ) {
            // Do not pop the SlotTable, If there is only one.
            // This should not happen, But an extra check for safety.
            throw new org.omg.CORBA.INTERNAL( 
                      "Cannot pop the only PICurrent in the stack",
		      MinorCodes.CANT_POP_ONLY_PICURRENT,
		      CompletionStatus.COMPLETED_NO );
        }
        currentIndex--;
        SlotTable table = (SlotTable)tableContainer.get( currentIndex );
        table.resetSlots( );
        tablePool.putSlotTable( table );
    }

    /**
     * peekSlotTable gets the top SlotTable from the SlotTableStack without
     * popping.
     */
    SlotTable peekSlotTable( ) {
       return (SlotTable) tableContainer.get( currentIndex - 1);
    }

}
