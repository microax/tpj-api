package service;
/**
 * ThePeoplesJukebox -- REST business object methods for TPJ API
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
import common.TpjSql;
import daemon.TpjConfig;
import daemon.TpjContainer;
import model.*;
/**
 * {@code Tpj} 
 * <p>
 * Methods:
 * --------
 * login()
 * playNext()
 * catalog()
 * uploadCatalog()
 * addQueue()
 * currentlyPlaying()
 * searchProfile()
 * searchSong()
 * faq()
 * permissions()
 *
 */
public class Tpj extends TpjApiObject implements TpjConstants
{
    //------ Objects in our model ------------------------
    //
    private  eo_user     user     = container.user;
    private  eo_media    media    = container.media;
    private  eo_playlist playlist = container.playlist;
    private  eo_upload   upload   = container.upload;
    private  eo_faq      faq      = container.faq;
       
   /**
    * login
    *
    * TPJ API user login.
    *
    * parameters:
    * -----------
    * username
    * password
    *
    * return:
    * ---------
    * vo_user object
    *
    * @param  REST request parameters
    * @return JSON reply 
    */
    public JSONObject login(Request req)
    {
	JSONObject reply = new JSONObject();

	String username = req.getParam("username");
	String password = req.getParam("password");
	
	vo_user u = user.findUserLogin(username, password);
        if(u!=null)
	{
	reply.put("userId",        u.userId);
	reply.put("accountId",     u.accountId);
	reply.put("userName",      u.userName);
	reply.put("userPassword",  u.userPassword);
	reply.put("userFirstName", u.userFirstName);
	reply.put("userLastName",  u.userLastName);
	reply.put("userIsJukebox", u.userIsJukebox);
	reply.put("userNickName",  u.userNickName);
	reply.put("userLikes",     u.userLikes);
	reply.put("userWorkplace", u.userWorkplace);
	reply.put("userWorkHours", u.userWorkHours);
	reply.put("userPhoto",     u.userPhoto);
	reply.put("userLongitude", u.userLongitude);
	reply.put("userLatitude",  u.userLatitude);
	reply.put("userCreated",   u.userCreated);
	reply.put("userModified",  u.userModified);	
	reply.put("userStatus",    u.userStatus);
        statusOK(reply,           "Login Successful");
        }
	else
	{
        statusFAIL(reply, "User Not Found");
	}
	return(reply);
    }

   /**
    * playNext
    *
    * TPJ API get next song on queue.
    *
    * parameters:
    * -----------
    * username
    * passcode
    * jukeboxId
    *
    * return:
    * ---------
    * vo_media media object
    *
    * @param  REST request parameters
    * @return JSON reply 
    */
    public JSONObject playNext(Request req)
    {
	JSONObject reply = new JSONObject();

	String username = req.getParam("username");
	String passcode = req.getParam("passcode");
	int    jukeboxId= Integer.parseInt(req.getParam("jukeboxId"));
	
	JSONArray medias = media.findByUserId(jukeboxId);
        if(medias!=null)
	{
	reply.put("catalog", medias);
        statusOK(reply, "Play Next Successful");
        }
	else
	{
        statusFAIL(reply, "Not Found");
	}
	return(reply);
    }


   /**
    * catalog
    *
    * TPJ API get jukebox catalog.
    *
    * parameters:
    * -----------
    * username
    * passcode
    * jukeboxId
    *
    * return:
    * ---------
    * JSONArray of media objects
    *
    * @param  REST request parameters
    * @return JSON reply 
    */
    public JSONObject catalog(Request req)
    {
	JSONObject reply = new JSONObject();

	String username = req.getParam("username");
	String passcode = req.getParam("passcode");
	int    jukeboxId= Integer.parseInt(req.getParam("jukeboxId"));
	
	JSONArray medias = media.findByUserId(jukeboxId);
        if(medias!=null)
	{
	reply.put("catalog", medias);
        statusOK(reply, "Catalog Successful");
        }
	else
	{
        statusFAIL(reply, "Catalog Not Found");
	}
	return(reply);
    }
    
    public Tpj(Daemon d)
    { 
	super(d);
    }
}

