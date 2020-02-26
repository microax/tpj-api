package util;

import java.lang.*;
import java.io.*;
import java.util.*;
import com.qkernel.*;

@SuppressWarnings("unchecked")

//------------------------------------------------------
// A simple utility to shutdown the  Server
//------------------------------------------------------
public class shutdown
{
    public static void main(String argvs[])
    {
	QMessage r = null;
        Config   c = new Config(argvs);
	String  ip = c.getLoIpAddress();
	int   port = c.getQbusPort();
	
	TpjUtilDelegate p = new TpjUtilDelegate(ip,port);
	QMessage        m = new QMessage();

	m.put("node","ZeroOne");
	m.put("password","xPl0Der");
	try
	{
	    r = p.invoke("util.RemoteUtil","shutdown", m, null);
	}
	catch(Exception e)
	{
	    System.out.println("Server not running");
	    System.exit(0);
	}
        System.out.println("Shutdown status = "+r.getParameter("status"));
    }
}
