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
 * {@code TpjApi} 
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
public class TpjApi extends TpjApiObject implements TpjConstants
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

	String   username = req.getParam("username");
	String   passcode = req.getParam("passcode");
	int      jukeboxId= Integer.parseInt(req.getParam("jukeboxId"));
        vo_media m;
	
	vo_user  u = user.findByUserPasscode(username, passcode);
	if(u!= null)
	{	
            if(u.userId==jukeboxId)
		m = media.findNextInQueue(jukeboxId);
	    else
		m = media.findCurrentlyPlaying(jukeboxId);

	    if(m.mediaSource.equals("UPLOAD"))
		m.mediaFile = config.getPlayerUrl(m.mediaFile);
	    reply.put("mediaId",      m.mediaId);
	    reply.put("userId",       m.userId);
	    reply.put("mediaSource",  m.mediaSource);
	    reply.put("mediaArtist",  m.mediaArtist);
	    reply.put("mediaTitle",   m.mediaTitle);
	    reply.put("mediaYear",    m.mediaYear);
	    reply.put("mediaDuration",m.mediaDuration);
	    reply.put("mediaCreated", m.mediaCreated);
	    reply.put("mediaModified",m.mediaModified);
	    reply.put("mediaStatus",  m.mediaStatus);	    
	    statusOK(reply, "Play Next Successful");
	}
	else
	{
            statusFAIL(reply, "User Not Found");
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
    
    public TpjApi(Daemon d)
    { 
	super(d);
    }
}

