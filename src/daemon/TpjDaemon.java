package daemon;
//
// TpjDaemon.java	Tpj Service Node Daemon
// ----------------------------------------------------------------------------
// History:
// --------
// 02/10/20 M. Gill	Initial creation.
// ----------------------------------------------------------------------------
//
import java.lang.*;
import java.util.*;
import com.qkernel.*;
import common.TpjConstants;
//
//----------------------------------------------------------------------------
// An Application Node within a network.
//
// tpj.conf defines configuration information in a property file format. 
// This configuration information includes the flags witch determine the 
// services provided in the TpjDaemon, as well as the setup of these services. 
// TpjConfig provides default values if tpj.conf is missing (or 
// incorrectly configured)  TpjDaemon will then retrieve
// Application Node parameters, access control and state information
// from the TpjConfig ROM defaults.
//
//----------------------------------------------------------------------------
public class TpjDaemon extends Daemon implements TpjConstants
{
    //-------------------------------------------------------------------
    // Important Static Object references
    //------------------------------------------------------------------- 
    public  static TpjConfig          config;
    public  static TpjContainer       tpjContainer;
    public  static QObjectBroker      restObjectBroker;
    public  static QObjectBroker      iiopObjectBroker;

    //--------------------------------------------------------------------------------
    // METHOD   start()
    //
    // PURPOSE: start() is invoked after the Daemon initializes itself.
    //		This is where Qkernel Application Nodes init. and start their 
    //		services - it is the Qkernel equivilant of main(). In fact,
    //		main() indirectly calls this method by invoking startDaemon().
    //
    // INPUT:   String argvs[] - this is the same command line argvs sent to main()
    // RETURN:  N/A
    //--------------------------------------------------------------------------------
    public void start(String argvs[])
    {
	//---------------------------------------
	// Set default Configuration parameters
	//---------------------------------------
	config = new TpjConfig(this , argvs);
	register(CONFIG, config);

	if(config.hasEntityContainer())
	{
	//-----------------------------------------------------
	// Create an Entity Object Container 
	//-----------------------------------------------------
	String jdbcDriver   	=config.getJdbcDriver();
        String connStr      	=config.getConnStr();
        String dbUsername   	=config.getConnUsername();
        String dbPassword   	=config.getConnPassword();
        int minConn         	=config.getMinConn();
        int maxConn         	=config.getMaxConn();
        String dbPoolLog    	=config.getDbPoolLog();
        double dbResetTime  	=config.getDbResetTime();
	log("Preparing EO container using: "+jdbcDriver+" on "+connStr+" ...");	

	tpjContainer = new TpjContainer();
	tpjContainer.create( jdbcDriver, 
			     connStr,
			     dbUsername,
			     dbPassword, 
			     minConn, 
			     maxConn, 
			     dbPoolLog, 
			     dbResetTime,
			     this);
	//-------------------------------
	// Load Entity Objects
	//-------------------------------
	tpjContainer.load();
	register(CONTAINER, tpjContainer);
	log("EO container loaded using: "+jdbcDriver+" on "+connStr);	
	}
	//--------------------------------------------------------------------
	// Here we check the configured input channel types ( object brokers)
	// for the service node, and start them. 
	// SOAP-XML requests are handled by servlet adaptors that create 
	// QMessages sent to QBUS.
	// 
	// Currently we have Brokers for:
        //      REST/JSON over HTTP/HTTPS
	//	QBUS Business Objects ( Native Qkernel Enhanced RMI)
        //      
	//--------------------------------------------------------------------
	if(config.hasRestObjectBroker())
	{
	    int rbPort  =config.getBusinessPort();
	    int rbAgents=config.getBusinessAgentNum();
	    //----------------------------------------------
	    // Set PUBLIC InetAddress for this Service Node
	    //----------------------------------------------
	    setInetAddress(config.getIpAddress());
	    
	    //-------------------------------------------------------
	    // Create the REST Object Broker 
	    //-------------------------------------------------------
	    restObjectBroker = new QObjectBroker("REST Object Broker", this);
            if(config.useSSL())
	    restObjectBroker.setHTTPS();
	    else
	    restObjectBroker.setHTTP();
	    restObjectBroker.setObjectRoutes((Config)config);
	    restObjectBroker.start(rbPort, rbAgents);
	}
	if(config.hasObjectBroker())
	{
	    int obPort  =config.getQbusPort();
	    int obAgents=config.getQbusAgentNum();
	    //----------------------------------------------
	    // Set local InetAddress for this Service Node
	    //----------------------------------------------
	    setInetAddress(config.getLoIpAddress());

	   //-------------------------------------------------------
	   // Create the Object Broker ( handles all QBUS requests)
	   //-------------------------------------------------------
	    iiopObjectBroker = new QObjectBroker("Qbus Object Broker", this);
            iiopObjectBroker.setIIOP();
	    iiopObjectBroker.start(obPort, obAgents);
        }

	//--------------------------------------------------------
	// That's it!...We're running a service node :-)
	//--------------------------------------------------------
	log("TPJ Daemon initialized");
    }

    public TpjDaemon(String key)
    {
	super(key);
    }
}
