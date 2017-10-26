package com.mpango.Util;

import java.math.BigDecimal;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

public class MessageHelper {
    
    public static String getCreditAccount(ISOMsg msg) {
        String result = "";
        try {
            String field103 = MessageHelper.stripLeadingZeros((String) msg.getValue(103));
            result = field103;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static String getDebitAccount(ISOMsg msg) {
        String result = "";
        try {
            String field102 = MessageHelper.stripLeadingZeros((String) msg.getValue(102));
            result = field102;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int getLoanTypeID(ISOMsg msg) {
        int result = 0;
        try {
            String s = MessageHelper.stripLeadingZeros((String) msg.getValue(18));
            int field18 = Integer.parseInt(s);
            result = field18;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static BigDecimal getRepaymentAmount(ISOMsg msg) {
        BigDecimal result = null;
        try {
            String s = MessageHelper.stripLeadingZeros((String) msg.getValue(4));
            BigDecimal field4 = new BigDecimal(s);
            result = field4;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String stripLeadingZeros(String str) {
        String s = str;
        s = s.replaceFirst("^0*", "");
        if (s.isEmpty()) {
            s = "0";
        }
        return s;
    }

    public static String getTransactionRef(ISOMsg msg) {
        String result = "";
        try {
            String field37 = MessageHelper.stripLeadingZeros((String) msg.getValue(37));
            result = field37;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getTransactionType(ISOMsg msg) {
        try {
            return MessageHelper.stripLeadingZeros((String) msg.getValue(3));
        } catch (Exception e) {
            return null;
        }
    }

    public static int getLoanRepaymentPeriod(ISOMsg msg) {
        int result = 0;
        try {
            String s = MessageHelper.stripLeadingZeros((String) msg.getValue(9));
            int field5 = Integer.parseInt(s);
            result = field5;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getLoanRefNumber(ISOMsg msg) {
        String result = "";
        try {
            String field42 = MessageHelper.stripLeadingZeros((String) msg.getValue(42));
            result = field42;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static String getFOSAAccountNumber(ISOMsg msg) {
        String result = "";
        try {
            String field42 = MessageHelper.stripLeadingZeros((String) msg.getValue(42));
            result = field42;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static BigDecimal getAmountApplied(ISOMsg msg) {
        BigDecimal result = null;
        try {
            String s = MessageHelper.stripLeadingZeros((String) msg.getValue(4));
            BigDecimal field4 = new BigDecimal(s);
            result = field4;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getCustomerIDNumber(ISOMsg msg) {
        String result = "";
        try {
            String field42 = MessageHelper.stripLeadingZeros((String) msg.getValue(42));
            result = field42;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getCustomerMSISDN(ISOMsg msg) {
        String result = "";
        try {
            String field2 = MessageHelper.stripLeadingZeros((String) msg.getValue(2));
            result = field2;
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public static String getCardId(ISOMsg msg) {
        String result = "";
        try {
            String field35 = (String) msg.getValue(35);
            result = field35.substring(0, 16);
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return result;

    }

    public static String getTID(ISOMsg msg) {
        String field41 = "";
        try {
            field41 = (String) msg.getValue(41);
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return field41;
    }

    public static String getMID(ISOMsg msg) {
        String field42 = "";
        try {
            field42 = (String) msg.getValue(42);
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return field42;
    }

    public static String getPoSCC(ISOMsg msg) {
        String field25 = "";
        try {
            field25 = (String) msg.getValue(25);
        } catch (ISOException ex) {
            ex.printStackTrace();
        }
        return field25;
    }

    public static String format(String s) {
        int length = s.length();
        if (length >= 10) {
            return s.substring(0, 10);
        } else {
            StringBuffer buffer = new StringBuffer(5);
            for (int i = length; i < 10; i++) {
                buffer.append('0');
            }
            buffer.append(s);
            return buffer.toString();
        }
    }

    public static String format(String s, int num) {
        int length = s.length();
        if (length >= num) {
            return s.substring(0, num);
        } else {
            StringBuffer buffer = new StringBuffer();
            for (int i = length; i < num; i++) {
                buffer.append('0');
            }
            buffer.append(s);
            return buffer.toString();
        }
    }


    public static int getAmount(ISOMsg msg) {
        int result = 0;
        String field61 = "";
        try {
            field61 = ISOUtil.hexString(msg.getComponent(61).getBytes());
            String money = field61.substring(6, 14);
            result = Integer.parseInt(money);
        } catch (ISOException e) {
            e.printStackTrace();
            result = -1;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            result = -1;
        }

        return result;
    }

    public static int getGiftPoint(ISOMsg msg) {
        int result = 0;
        String field48 = "";
        try {
            field48 = ISOUtil.hexString(msg.getComponent(48).getBytes());
            int index = field48.indexOf("FF21");
            if (index != -1) {
                String strPoint = field48.substring(index + 4, index + 10);
                result = Integer.parseInt(strPoint);
            } else {
                return -1;
            }

        } catch (ISOException e) {
            e.printStackTrace();
            result = -1;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            result = -1;
        }
        return result;
    }

    public static String makeTLV(String tag, String value) {
        String result = "";
        int length = value.length();
        result = tag + ConvertHelper.decToHex(length) + ConvertHelper.asciiToHex(value);
        return result;
    }

    public static String getTransactionName(ISOMsg msg) {
        System.out.println("----- getTransactionName ------- ");

        String field48 = "";
        try {
            System.out.println(msg.getComponent(48).getBytes() + "xxxxxx   " + msg);
            field48 = ISOUtil.hexString(msg.getComponent(48).getBytes());
            System.out.println("field48   " + field48);
            int index = field48.indexOf("FF21");
            if (index != -1) {
                System.out.println("field48XX   " + field48.substring(index + 4, index + 10));
                return field48.substring(index + 4, index + 10);
            } else {
                return null;
            }
        } catch (ISOException e) {
            System.out.println("kjdfhgkjdhk gjhdfjkgdfg hdfjg ");
            return null;
        }
    }

    public static String getInvoiceLog(ISOMsg msg) {
        String field48 = "";
        try {
            field48 = ISOUtil.hexString(msg.getComponent(48).getBytes());
            int index = field48.indexOf("FF2F");
            if (index != -1) {
                return field48.substring(index + 6, index + 18);
            } else {
                return null;
            }
        } catch (ISOException e) {
            return null;
        }
    }

    public static String getInvoice(ISOMsg msg) {
        String field48 = "";
        try {
            field48 = ISOUtil.hexString(msg.getComponent(48).getBytes());
            int index = field48.indexOf("FF34");
            if (index != -1) {
                return field48.substring(index + 24, index + 36);
            } else {
                return null;
            }
        } catch (ISOException e) {
            return null;
        }
    }

    public static int getReloadAmount(ISOMsg msg) {
        int result = 0;
        String field61 = "";
        try {
            field61 = ISOUtil.hexString(msg.getComponent(48).getBytes());
            int index = field61.indexOf("FF2B");
            if (index != -1) {
                String money = field61.substring(index + 6, index + 16);
                result = Integer.parseInt(money);
            } else {
                return -1;
            }
        } catch (ISOException e) {
            e.printStackTrace();
            result = -1;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            result = -1;
        }

        return result;
    }
}
