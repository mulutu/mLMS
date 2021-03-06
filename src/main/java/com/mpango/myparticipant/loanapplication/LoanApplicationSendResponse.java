/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.myparticipant.loanapplication;

import com.mpango.Util.Constant;
import com.mpango.dao.lms.SMSDAO;
import com.mpango.Util.MessageHelper;
import com.mpango.Util.log.LMSLogISO;
import com.mpango.Util.log.LMSLogger;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOFilter;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOSource;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;
import static org.jpos.transaction.TransactionConstants.ABORTED;
import static org.jpos.transaction.TransactionConstants.NO_JOIN;
import static org.jpos.transaction.TransactionConstants.PREPARED;
import static org.jpos.transaction.TransactionConstants.READONLY;

/**
 *
 * @author jmulutu
 */
public class LoanApplicationSendResponse implements AbortParticipant {

    static Logger logger = Logger.getLogger(LoanApplicationSendResponse.class);

    @Override
    public void abort(long id, Serializable context) {
    }

    @Override
    public int prepareForAbort(long id, Serializable context) {
        sendResponse(id, (Context) context);
        return ABORTED | READONLY | NO_JOIN;
    }

    @Override
    public void commit(long id, Serializable context) {
        sendResponse(id, (Context) context);
    }

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOSource source = (ISOSource) ctx.get(Constant.SOURCE);
        if (source == null || !source.isConnected()) {
            return ABORTED | READONLY | NO_JOIN;
        }

        return PREPARED | READONLY;
    }

    /**
     * Send response to client.
     *
     * @param id Identify of transaction
     * @param ctx Context of transaction
     */
    private void sendResponse(long id, Context ctx) {

        ISOSource source = (ISOSource) ctx.get(Constant.SOURCE);
        Connection con = (Connection) ctx.get(Constant.CONN);

        if (source == null || !source.isConnected()) {
            return; // too late to send
        }

        try {
            ISOMsg msg = (ISOMsg) ctx.get(Constant.REQUEST);
            String rc = (String) ctx.get(Constant.RC);
            if (source != null && source.isConnected() && msg != null) {

                GenericPackager packager = new GenericPackager("cfg/packager/iso87ascii.xml");
                //XMLPackager packager = new XMLPackager();
                ISOMsg msgResponse = new ISOMsg();
                msgResponse.setPackager(packager);
                //msgResponse.setHeader("ISO016000055".getBytes());
                //msgResponse.setMTI("0210");
                msgResponse.set(0, "0210");
                msgResponse.set(2, (String) msg.getValue(2));
                msgResponse.set(3, (String) msg.getValue(3));
                msgResponse.set(42, (String) msg.getValue(42));

                String CustomerMSISDN = (String) msg.getValue(2);
                String SMSMessage = null;

                if (rc == null || "00".equals(rc)) {

                    String trxRefNumber = (String) ctx.get(Constant.LOAN_REF_NUMBER);
                    msgResponse.set(39, "00");

                    String field37Value = (String) msg.getValue(37);
                    msgResponse.set(37, field37Value);

                    msgResponse.set(44, trxRefNumber);

                    SMSMessage = "Dear customer, your loan application has been received and is being processed.";

                } else if (rc != null) {
                    int error = Integer.parseInt(rc);
                    String strError = "";
                    switch (error) {
                        case 13:
                            msgResponse.set(39, "13");
                            msgResponse.set(44, Constant.CUSTOMER_NOT_FOUND);
                            SMSMessage = "Dear customer, Your loan application has been decline because you are not registered.";
                            break;
                        case 14:
                            msgResponse.set(39, "14");
                            msgResponse.set(44, Constant.DUPLICATE_LOAN);
                            break;
                        case 15:
                            msgResponse.set(39, "15");
                            msgResponse.set(44, Constant.DISBURSEMENT_SQL_ERROR);
                            break;
                        case 16:
                            msgResponse.set(39, "16");
                            msgResponse.set(44, Constant.EXISTING_LOAN);
                            SMSMessage = "Dear customer, Your loan application has been decline because you have an existing loan.";
                            break;
                        case 17:
                            msgResponse.set(39, "17");
                            msgResponse.set(44, Constant.INVALID_FIELD);
                            break;
                        case 51: //
                            msgResponse.set(39, "51");
                            msgResponse.set(44, Constant.NO_LOAN_LIMIT);
                            SMSMessage = "Dear customer, Your loan application has been decline because you do not have a loan limit.";
                            break;
                        case 50:
                            msgResponse.set(39, "50");
                            msgResponse.set(44, "EXISTING LOAN APPLICATION");
                            SMSMessage = "Dear customer, Your loan application has been decline because you have an existing loan application.";
                            break;

                        case 3:
                            msgResponse.set(39, "03");
                            strError = MessageHelper.makeTLV("FF39", Constant.MID_OR_TID_NOT_FOUND);
                            msgResponse.set(61, ISOUtil.hex2byte(strError));
                            break;
                        case 58:
                            msgResponse.set(39, "58");
                            strError = MessageHelper.makeTLV("FF39", Constant.POSCC_NOT_FOUND);
                            msgResponse.set(61, ISOUtil.hex2byte(strError));
                            break;
                        case 24:
                            msgResponse.set(39, "24");
                            strError = MessageHelper.makeTLV("FF39", Constant.FORWARD_FAIL);
                            msgResponse.set(61, ISOUtil.hex2byte(strError));
                            break;
                        case 93:
                            msgResponse.set(39, "93");
                            strError = MessageHelper.makeTLV("FF39", Constant.NO_ACTIVATED_CARD);
                            msgResponse.set(61, ISOUtil.hex2byte(strError));
                            break;
                        case 98:
                            msgResponse.set(39, "16");
                            strError = MessageHelper.makeTLV("FF39", Constant.TRANSACTION_REVERSAL);
                            msgResponse.set(61, ISOUtil.hex2byte(strError));
                            break;
                        default:
                            msgResponse.set(39, "12");
                            msgResponse.set(44, Constant.OTHER_ERROR);
                            SMSMessage = "Dear customer, your loan application has failed. Please try again later.";
                            break;
                    }

                    
                }

                int createSMSresult = SMSDAO.createSMS(1, CustomerMSISDN, SMSMessage, con);

                source.send(msgResponse);

                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "ResponseISO [ " + LMSLogISO.ISOtoString(msgResponse) + " ]");
                logger.info(ctx.get(Constant.SESSION_ID) + " : " + "ResponseISO [ " + LMSLogISO.parseISOMessage(LMSLogISO.ISOtoString(msgResponse)) + " ]");

            }
        } catch (ISOFilter.VetoException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ISOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LoanApplicationSendResponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
