package rpc;

import java.lang.*;
import java.util.*;
import java.io.*;
import com.qkernel.*;
import com.qkernel.batch.*;
import com.qkernel.json.*;
import com.qkernel.http.HTTPServer.*;
import common.SpnConstants;
import daemon.SpnConfig;
import daemon.SpnContainer;
import model.*;
/*********************************
 * Ipstack Rest API Wrapper
 * -------------------------------
 *
 * public methods:
 *
 * - endStream()  
 * - startRecord()  
 * - stopRecord()  
 * - ping()
 *
 * @author  mgill
 *
 ********************************
 */
public class IpstackApi extends Object
{
    private String url          ="";
    private String auth         ="";
    private String username     ="";
    private String passcode     ="";
    private String password     ="";
    private String errorMessage ="";
    private Daemon daemon       =null;
    //------------------------------------------------
    // Shared Objects
    //------------------------------------------------
    private byte[]                 dataBytes   = null;


   /**
    * endStream  
    *
    * @param  String streamName
    * @return JSONObject
    */
    public JSONObject endStream(String s)
    {
        return(this.liveStreamsAction(s, "unpublish"));
    }
    

   /**
    * startRecord  
    *
    * @param  String streamName
    * @return JSONObject
    */
    public JSONObject startRecord(String s)
    {
        return(this.liveStreamsAction(s, "startrecord"));
    }
    
   /**
    * stopRecord  
    *
    * @param  String streamName
    * @return JSONObject
    */
    public JSONObject stopRecord(String s)
    {
        return(this.liveStreamsAction(s, "stoprecord"));
    }
    

   /**
    * ping  
    *
    * @param  void
    * @return JSONObject
    */
    public JSONObject ping()
    {
	//------------------------------------
	// set up username,passcode possibly
	// API authentecation yadda yadda
	// put into our parameter hash
	// and instantiate a Rest() object...
	//------------------------------------
	String url =getUrl();
	String auth=getAuth();
	String resp="";
        JSONObject json = null;
	
	RestProxy rest  = new RestProxy(url, auth, daemon);
	QMessage params = new QMessage();
	params.putString(getUsername(), getPasscode());

	try
	{
	    //------------------------------------------------------
	    // Here we call the REST function for pinbg
	    //------------------------------------------------------
	    resp  = rest.get("/api/v1/server/ping/", params);
	    json  = new JSONObject(resp);
	    setErrorMessage("");
	    return(json);
	}
	catch(Exception e)
	{
	    determineError(resp);
	    json = null;
	    return(json);
	}
    }
    
   /**
    * liveStreamsAction  
    *
    * @param  String streamName
    * @param  String action
    * @return JSONObject
    */
    private JSONObject liveStreamsAction(String s, String action)
    {
	//------------------------------------
	// set up username,passcode possibly
	// API authentecation yadda yadda
	// put into our parameter hash
	// and instantiate a Rest() object...
	//------------------------------------
	String url =getUrl();
	String auth=getAuth();
	String resp="";
        JSONObject json = null;
	boolean found= false;
	
	RestProxy rest  = new RestProxy(url, auth, daemon);
	QMessage params = new QMessage();
	params.putString(getUsername(), getPasscode());

	try
	{
	    //------------------------------------------------------
	    // First, we have to see if this stream is live...
	    //------------------------------------------------------
	    resp  = rest.get("/api/v1/applications/live/streams", params);
	    json  = new JSONObject(resp);
	    JSONArray ja = json.getJSONArray("data");
	    for(int i=0; i < ja.length(); i++)
	    {
		if(s.equals(ja.getString(i)))
		{
	            //------------------------------------------------------
	            // Here we call the REST function 
		    // ONLY if it's on the list of active streams.
	            //------------------------------------------------------
	            resp  = rest.get("/api/v1/applications/live/streams/"+s+"/action/"+action, params);
	            json  = new JSONObject(resp);
	            setErrorMessage("");
	            daemon.log(action+" on: "+s);
                    found=true;
                    break;
		}
	    }
	    if(!found)
	    {
		json = new JSONObject();
		json.put("status", "not an active stream"); 
		json.put("data"  , s); 
	    }
	    return(json);
	}
	catch(Exception e)
	{
	    determineError(resp);
	    json = null;
	    return(json);
	}
    }
    
    /**
     * 
     *
     */
    private byte[] getDataBytes(int id)
    {
        String url=getUrl();
        String auth=getAuth();
        String resp="";

        RestProxy rest  = new RestProxy(url, auth);
        QMessage params = new QMessage();
        params.putInt("id", id);

        try
        {
	    dataBytes= rest.getDataBytes("/", params);
            return(dataBytes);
        }
        catch(Exception e)
        {
            dataBytes= null;
            return(dataBytes);
        }
    }

   /**
    * detemineError  
    *
    * We got some kind of error...
    * try to determine source of error
    * then set errorMessage
    *
    * @param resp -- the response from server
    */
    private void determineError(String resp)
    {
        /*try
	    {
            ApiError error = new ApiError( new JSONObject(resp));
            setErrorMessage(error.errorMessage);		
	    }
	    catch(Exception e)
	    {*/
	        setErrorMessage("API Server Error");
	/* }*/
    }


    //-----------------------------
    // Get/Set functions
    //-----------------------------
    public void setUrl(String u)
    {
	url = u;
    }
    public String getUrl()
    {
	return(url);
    }
    public void setAuth(String a)
    {
	auth = a;
    }
    public String getAuth()
    {
	return(auth);
    }
    public String getUsername()
    {
	return(username);
    }
    public void setUsername(String u)
    {
	username = u;
    }
    public String getPassword()
    {
	return(password);
    }
    public void setPassword(String p)
    {
	password = p;
    }
    public String getPasscode()
    {
	return(passcode);
    }
    public void setPasscode(String p)
    {
	passcode = p;
    }
    public String getErrorMessage()
    {
	return(errorMessage);
    }
    public void setErrorMessage(String e)
    {
	errorMessage = e;
    }
        
    
   /**
    * Here we have our happy constructors
    */
    public IpstackApi(String u, String usr, String pass, Daemon d)
    {
	this.daemon   = d;
	this.url      = u;
        this.auth     = "";
        this.username = usr;
        this.passcode = pass;
    }
    public IpstackApi(String u, String a, String usr, String pass)
    {
	    this.url      = u;
	    this.auth     = a;
	    this.username = usr;
	    this.passcode = pass;
    }
    
    public IpstackApi()
    {

    }
}
