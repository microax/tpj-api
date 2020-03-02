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
 * addQueue()
 * currentlyPlaying()
 * uploadCatalog()
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
		m = playlist.findNextInQueue(jukeboxId);
	    else
		m = playlist.getCurrentlyPlaying(jukeboxId);

	    if(m.mediaSource.equals("UPLOAD"))
		m.mediaFile = config.getPlayerUrl(jukeboxId);
	    reply.put("mediaId",      m.mediaId);
	    reply.put("userId",       m.userId);
	    reply.put("mediaSource",  m.mediaSource);
	    reply.put("mediaFile",    m.mediaFile);
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

   /**
    * addQueue
    *
    * TPJ API add song to  jukebox queue.
    *
    * parameters:
    * -----------
    * username
    * passcode
    * jukeboxId
    * mediaId
    *
    * return:
    * ---------
    * JSON of newly inserted playlist object
    *
    * @param  REST request parameters
    * @return JSON reply 
    */
    public JSONObject addQueue(Request req)
    {
	JSONObject reply = new JSONObject();
	//--------------------------------------------------------------
	// Gather parameters and initialize counts and whatnot...
	//--------------------------------------------------------------
	String  username = req.getParam("username");
	String  passcode = req.getParam("passcode");
	int     jukeboxId= Integer.parseInt(req.getParam("jukeboxId"));
	int     mediaId  = Integer.parseInt(req.getParam("mediaId"));
	String  now      = TpjSql.now();
	int     selectCount= 0;
	int     numSongs   = 0;
	int     selectMax  = config.getSongSelectMax();
        boolean selectLimit=false;
	//--------------------------------------------------------
	// Must be a valid user who's already logged-in...
	//--------------------------------------------------------
	vo_user  u = user.findByUserPasscode(username, passcode);
	if(u!= null)
	{	
            ArrayList<vo_playlist> songs = playlist.findUserPlaylist(jukeboxId);
	    numSongs = songs.size();
	    //----------------------------------------------
	    // Check to see if user has already selected
	    // enough songs...
	    //----------------------------------------------
	    for(int i=0; i< numSongs; i++)
	    {
	        if(songs.get(i).playlistUserId == u.userId)
		{
		    if(selectCount++ == selectMax)
		    {
			selectLimit= true;
			break;
		    }
	        }
	    }
            if(selectLimit)
	    {
		//------------------------------------------
		// politely inform user to knock it off :-)
		//------------------------------------------
                reply.put("userId",          jukeboxId);
	        reply.put("mediaId",         mediaId);
                reply.put("playlistUserId",  u.userId);
                reply.put("playlistOrder",   selectMax);
                reply.put("playlistCreated", now);
		reply.put("playlistModified",now);
	        reply.put("playlistStatus",  "PLAY_LIMIT");
                statusFAIL(reply, "Play Limit");
	    }
	    else
	    {
		//---------------------------------------------
		// add selected song to jukebox playlist queue 
		//---------------------------------------------
                vo_playlist newSong = new vo_playlist();
                newSong.userId          = jukeboxId;
	        newSong.mediaId         = mediaId;
                newSong.playlistUserId  = u.userId;
                newSong.playlistOrder   = numSongs +1;
                newSong.playlistCreated = now;
	        newSong.playlistModified= now;
	        newSong.playlistStatus  = "QUEUE";
		//----------------------------------------------------
		// This is where newSong.makeJSON()
		// would be really nice to have...
		// OK, we insert newSong and make a nice reply :-)
		//----------------------------------------------------
		playlist.insert(newSong);
                reply.put("userId",          newSong.userId);
	        reply.put("mediaId",         newSong.mediaId);
                reply.put("playlistUserId",  newSong.playlistUserId);
                reply.put("playlistOrder",   newSong.playlistOrder);
                reply.put("playlistCreated", newSong.playlistCreated);
		reply.put("playlistModified",newSong.playlistModified);
	        reply.put("playlistStatus",  newSong.playlistStatus);
                statusOK(reply, "Inserted song on queue");
	    }
	}
	else
	{
            statusFAIL(reply, "User Not Found");
	}	
	return(reply);
    }

    
    public TpjApi(Daemon d)
    { 
	super(d);
    }
}

