/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.cron;

import com.mpango.Util.objects.SMSMessage;
import com.mpango.dao.lms.SMSDAO;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import com.mpango.sms.AfricasTalkingGateway;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.json.*;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author jmulutu
 */
public class SendSMS extends QuartzJobBean {

    static Logger logger = Logger.getLogger(SendSMS.class.getName());

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        String hostname = "error::: ";
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            logger.info("SendSMS: Error getting the Hostname of teh machine.");
        }

        if (!hostname.equalsIgnoreCase("TIR118216")) {
            processSMS();
        }
    }

    public void processSMS() {
        List<SMSMessage> smslist = new ArrayList<>();
        Connection con = DataProvider.getConnection();
        String username = "mulutu";
        String apiKey = "abf7106f8ff585968e159d9d638570749b1831f88542aef269746e65519ef9c7";
        AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey);
        //DBConnectionPool dsp = new DBConnectionPool();
        //DataSource ds = dsp.setUp();
        //Connection con = null;

        try {
            //con = ds.getConnection();
            smslist = SMSDAO.getAllPendinSMS(con);
            for (int i = 0; i < smslist.size(); i++) {
                SMSMessage sms = smslist.get(i);
                String message = sms.getSmsMessage();
                String recepients = "+" + sms.getReceiverMSISDN();
                JSONArray results = gateway.sendMessage(recepients, message);
                for (int k = 0; k < results.length(); ++k) {
                    JSONObject result = results.getJSONObject(i);
                    System.out.print(result.getString("status") + ","); // status is either "Success" or "error message"
                    System.out.print(result.getString("number") + ",");
                    System.out.print(result.getString("messageId") + ",");
                    System.out.println(result.getString("cost"));

                    String smsSendStatus = result.getString("status");

                    if (smsSendStatus.equalsIgnoreCase("Success")) {
                        int sendSMSstatus = SMSDAO.updateSMSsent(sms, con);
                        if (sendSMSstatus == 1) {
                            // sms sent succefully
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.info("SendSMS no GW CONN: " + ex);
        }
        if (con != null) {
            DbUtils.closeQuietly(con);
        }
    }
}
