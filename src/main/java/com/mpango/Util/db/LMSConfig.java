package com.mpango.Util.db;

import com.mpango.Util.db.ILMSConfigMBean;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;

public class LMSConfig extends QBeanSupport implements ILMSConfigMBean {

    public LMSConfig() {
        super();
    }

    public String getTypeOfDatabase() {
        return cfg.get("dbtype");
    }

    public String getHost() {
        return cfg.get("host");
    }

    public String getPort() {
        return cfg.get("port");
    }

    public String getDBName() {
        return cfg.get("dbname");
    }

    public String getUserName() {
        return cfg.get("username");
    }

    public String getPassword() {
        return cfg.get("password");
    }

    public int getConnetionPool() {
        return Integer.parseInt(cfg.get("connectionpool"));
    }

    public void startService() {
        log.info("LMSConfig start");
        this.setName("MyConfigDB");
    }

    public void setName(String name) {
        super.setName(name);
        NameRegistrar.register(name, this);
    }
}
