package com.mpango.myparticipant.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import com.mpango.Util.Constant;
import org.jpos.transaction.AbortParticipant;
import org.jpos.transaction.Context;

/**
 * Closed connection after a while the transaction had finished.
 *
 * @author HUNGPT
 *
 */
public class Close implements AbortParticipant {

    @Override
    public void abort(long id, Serializable serializable) {
    }

    @Override
    public void commit(long id, Serializable serializable) {
        closeConnection(id, serializable);
    }

    @Override
    public int prepare(long arg0, Serializable arg1) {
        return PREPARED;
    }

    @Override
    public int prepareForAbort(long id, Serializable serializable) {
        closeConnection(id, serializable);
        return ABORTED | READONLY | NO_JOIN;
    }

    public void closeConnection(long id, Serializable serializable) {
        Context ctx = (Context) serializable;
        Connection con = (Connection) ctx.get(Constant.CONN);

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
