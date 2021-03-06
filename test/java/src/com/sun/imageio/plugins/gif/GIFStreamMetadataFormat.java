/*
 * @(#)GIFStreamMetadataFormat.java	1.4 01/12/03
 *
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.imageio.plugins.gif;

import java.util.Arrays;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataFormatImpl;

class GIFStreamMetadataFormat extends IIOMetadataFormatImpl {

    private static IIOMetadataFormat instance = null;

    private GIFStreamMetadataFormat() {
        super(GIFStreamMetadata.nativeMetadataFormatName,
              CHILD_POLICY_SOME);

        // root -> Version
        addElement("Version", GIFStreamMetadata.nativeMetadataFormatName,
                   CHILD_POLICY_EMPTY);
        addAttribute("Version", "value", 
                     DATATYPE_STRING, true, null,
                     Arrays.asList(GIFStreamMetadata.versionStrings));

        // root -> LogicalScreenDescriptor
        addElement("LogicalScreenDescriptor",
                   GIFStreamMetadata.nativeMetadataFormatName,
                   CHILD_POLICY_EMPTY);
        addAttribute("LogicalScreenDescriptor", "logicalScreenWidth",
                     DATATYPE_INTEGER, true, null,
                     "1", "65535", true, true);
        addAttribute("LogicalScreenDescriptor", "logicalScreenHeight",
                     DATATYPE_INTEGER, true, null,
                     "1", "65535", true, true);
        addAttribute("LogicalScreenDescriptor", "colorResolution",
                     DATATYPE_INTEGER, true, null,
                     "1", "8", true, true);
        addAttribute("LogicalScreenDescriptor", "pixelAspectRatio",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);

        // root -> GlobalColorTable
        addElement("GlobalColorTable",
                   GIFStreamMetadata.nativeMetadataFormatName,
                   2, 256);
        addAttribute("GlobalColorTable", "sizeOfGlobalColorTable", 
                     DATATYPE_INTEGER, true, null,
                     Arrays.asList(GIFStreamMetadata.colorTableSizes));
        addAttribute("GlobalColorTable", "backgroundColorIndex",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);
        addBooleanAttribute("GlobalColorTable", "sortFlag",
                            false, false);

        // root -> GlobalColorTable -> ColorTableEntry
        addElement("ColorTableEntry", "GlobalColorTable",
                   CHILD_POLICY_EMPTY);
        addAttribute("ColorTableEntry", "index",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);
        addAttribute("ColorTableEntry", "red",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);
        addAttribute("ColorTableEntry", "green",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);
        addAttribute("ColorTableEntry", "blue",
                     DATATYPE_INTEGER, true, null,
                     "0", "255", true, true);
    }

    public boolean canNodeAppear(String elementName,
                                 ImageTypeSpecifier imageType) {
        return true;
    }

    public static synchronized IIOMetadataFormat getInstance() {
        if (instance == null) {
            instance = new GIFStreamMetadataFormat();
        }
        return instance;
    }
}
