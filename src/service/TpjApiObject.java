package service;
/**
 * TpjApiObject -- REST business object methods for Jukebox API
 *
 * History:
 * --------
 * 02-10-20 mgill  Initial Creation. 
 */
import java.lang.*;
import java.util.*;
import java.io.*;
import com.qkernel.*;
import com.qkernel.json.*;
import com.qkernel.http.HTTPServer.*;
import common.TpjConstants;
import daemon.TpjConfig;
import daemon.TpjContainer;
import model.*;
/**
 * {@code TpjApiObject} 
 * <p>
 * Protected Methods:
 * ------------------
 * statusOK()
 * statusFAIL()
 * getString()
 * getInt()
 * getJSONArray()
 *
 * Public Methods:
 * ------------------
 * ping()
 *
 */
public class TpjApiObject extends BusinessObject implements TpjConstants
{
    public  TpjConfig    config    = (TpjConfig)daemon.lookup(CONFIG);
    public  TpjContainer container = (TpjContainer)daemon.lookup(CONTAINER);

   /**
    * ping 
    *
    * @param  REST request parameters
    * @return JSON reply 
    */
    public JSONObject ping(Request req)
    {
	JSONObject reply = new JSONObject();
	reply.put("api_data",  pingStamp());
        statusOK(reply, "ping");
	return(reply);
    }
    

   /**
    * getString -- returns String from 
    *              JSONObject parameter string
    *              (returns "" if exception caught)
    *
    * @param  JSONObject json  
    * @param  string     key 
    * @return string     value
    */
    protected String getString(JSONObject json, String key)
    {
	String js = "";
        try
	{
	    js =json.getString(key);
	}
	catch(Exception e)
	{
	    js = "";
	}
	return(js);
    }

   /**
    * getInt -- returns int from 
    *           JSONObject parameter string
    *           (returns 0 if exception caught)
    *
    * @param  JSONObject json  
    * @param  string     key 
    * @return int        value
    */
    protected int getInt(JSONObject json, String key)
    {
	int js = 0;
        try
	{
	    js =json.getInt(key);
	}
	catch(Exception e)
	{
	    js = 0;
	}
	return(js);
    }

    /**
    * getJSONArray -- returns JSONArray from 
    *                 JSONObject parameter string
    *                 (returns null if exception caught)
    *
    * @param  JSONObject json  
    * @param  string     key 
    * @return JSONArray 
    */
    protected JSONArray getJSONArray(JSONObject json, String key)
    {
	JSONArray js = null;
        try
	{
	    js =json.getJSONArray(key);
	}
	catch(Exception e)
	{
	    js = null;
	}
	return(js);
    }

   /**
    * statusOK -- sets Ok JSON status with message 
    *             and log 
    *
    * @param  JSONObject reply  
    * @param  string message
    */
    protected void statusOK(JSONObject json, String message)
    {
        log(message);
	json.put("json_status" , "OK");
	json.put("json_message", message);    
    }

   /**
    * statusFAIL -- sets FAIL JSON status with message 
    *               and log 
    *
    * @param  JSONObject reply  
    * @param  string message
    */
    protected void statusFAIL(JSONObject json, String message)
    {
        log(message);
	json.put("json_status" , "FAIL");
	json.put("json_message", message);    
    }
    
   /**
    * pingStamp 
    *
    * @return string -- current time in milliseconds
    */
    private String pingStamp()
    {
	Date   now   = new Date();
	return(Long.valueOf(now.getTime()).toString());
    }

    public TpjApiObject(Daemon d)
    { 
	super(d);
    }
}

