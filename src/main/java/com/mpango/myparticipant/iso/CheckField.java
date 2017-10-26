package com.mpango.myparticipant.iso;

import java.io.Serializable;

import com.mpango.Util.Constant;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;

/**
 * Check all field the LMS system needs.
 *
 * @author HungPT
 *
 */
public class CheckField implements TransactionParticipant, Configurable {

    Configuration cfg = null;

    @Override
    public void abort(long id, Serializable context) {
    }

    @Override
    public void commit(long id, Serializable context) {
    }

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOMsg m = (ISOMsg) ctx.get(Constant.REQUEST);
        String fields = cfg.get("fields");
        String[] campos = fields.split(",");
        int c = 0;
        boolean validate = true;
        while ((c < campos.length) && (validate)) {
            validate = (m.hasField(Integer.parseInt(campos[c])));
            c++;
        }
        if (validate) {
            return PREPARED | READONLY | NO_JOIN;
        } else {
            ctx.put(Constant.RC, "17");
            return ABORTED | NO_JOIN;
        }
    }

    @Override
    public void setConfiguration(Configuration cfg)
            throws ConfigurationException {
        this.cfg = cfg;
    }
}
