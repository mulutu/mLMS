package com.mpango.Util.db;

import org.jpos.q2.QBeanSupportMBean;
import org.jdom.*;

public interface ILMSConfigMBean extends QBeanSupportMBean {
	
	public String getTypeOfDatabase();
	
	public String getHost();
	
	public String getPort() ;
	
	public String getDBName();
	
	public String getUserName();
	
	public String getPassword();
}
