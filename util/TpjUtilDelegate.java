package util;

import java.lang.*;
import com.qkernel.*;
import common.TpjConstants;
import daemon.TpjConfig;

public class TpjUtilDelegate extends BusinessDelegate implements TpjConstants
{

    public String getOrbAddress(Daemon d)
    {
    	TpjConfig config = (TpjConfig)d.lookup(CONFIG);
	return( config.getIpAddress() );
    }

    public int getOrbPort(Daemon d)
    {
    	TpjConfig config = (TpjConfig)d.lookup(CONFIG);
	return( config.getBusinessPort() );
    }

    public boolean isRemote()
    {
	return(true);
    }

    public TpjUtilDelegate(String h, int p)
    {
	super(h,p);
    }
}
