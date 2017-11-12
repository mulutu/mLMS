/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpango.Util.cron;

import com.mpango.bus.LoanBUS;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbutils.DbUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author jmulutu
 */
public class UpdateEMISchedule extends QuartzJobBean {
    
    static Logger logger = Logger.getLogger(UpdateEMISchedule.class.getName());

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        updateEMISchedule();
    }

    public void updateEMISchedule() {
        Connection con = DataProvider.getConnection();
        LoanBUS.updateEMISchedule(con);
        if( con != null ){
            DbUtils.closeQuietly(con);
        }
    }
}