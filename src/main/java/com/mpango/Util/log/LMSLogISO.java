/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.log;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

/**
 *
 * @author jmulutu
 */
public class LMSLogISO {

    public static String ISOtoString(ISOMsg isoMsg) throws ISOException {
        byte[] s = isoMsg.pack();
        return new String(s);
    }

    public static String parseISOMessage(String ISOMsg) throws Exception {
        try {
            GenericPackager packager = new GenericPackager("cfg/packager/iso87ascii.xml");
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(ISOMsg.getBytes());
            return LMSLogISO.printISOMessage(isoMsg);
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    public static String printISOMessage(ISOMsg isoMsg) {
        String s = "";
        for (int i = 1; i <= isoMsg.getMaxField(); i++) {
            if (isoMsg.hasField(i)) {
                //System.out.printf("Field: (%s) Value: %s%n", i, isoMsg.getString(i));
                s += "| Field: (" + i + ") Value: (" + isoMsg.getString(i) + ")";
            }
        }
        return s;
    }

}
