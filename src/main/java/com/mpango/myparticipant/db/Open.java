package com.mpango.myparticipant.db;

import com.mpango.Util.Constant;
import com.mpango.dao.lms.DBConnectionPool;
import com.mpango.dao.lms.DataProvider;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;

/**
 * Open Connection before start transaction flow.
 *
 * @author HUNGPT
 *
 */
public class Open implements TransactionParticipant {

    static Logger logger = Logger.getLogger(Open.class.getName());

    @Override
    public void abort(long id, Serializable serialize) {
    }

    @Override
    public void commit(long id, Serializable serialize) {
    }

    @Override
    public int prepare(long id, Serializable serialize) {
        Context ctx = (Context) serialize;
        Connection con = DataProvider.getConnection();        
        //Connection con = null;
        //DBConnectionPool dsp = new DBConnectionPool();        
        //DataSource ds = dsp.setUp();     

        if (con == null) {
            return ABORTED | NO_JOIN;
        }

        ctx.put(Constant.CONN, con, false);
        return PREPARED | NO_JOIN;
    }

}
