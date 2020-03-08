/* eo_playlist.java
 *
 *****************************************************************************
 * History:
 * ========
 * mgill    lifted from php model/UserPlaylistModel.php
 *
 *****************************************************************************
 * 
 */ 
package model;

import java.lang.*;
import java.sql.*;
import java.util.*;
import java.util.Random;

import com.qkernel.*;
import common.TpjSql;
import common.TpjConstants;
import daemon.TpjContainer;
import daemon.TpjConfig;

@SuppressWarnings({"unchecked", "fallthrough", "serial" })
/************************************************************
 * An entity object for the db table playlist
 * 
 * 
 * @author  Initially created by EntityObjectBuilder
 * @version 200223  
 ************************************************************
 */
public class eo_playlist extends eo_playlist_gen implements TpjConstants
{
   /**
    * Returns next vo_media in playlist for a jukebox
    *
    * @param  int      -- jukeboxId
    * @return vo_media -- media
    *
    */
    public vo_media findNextInQueue(int id)
    {
        ArrayList<vo_playlist> pl = this.findUserPlaylist(id);
        int                    c  = pl.size();
        String                 now= TpjSql.now();
	
	if(c > 1)
        {
            vo_playlist t = null;
	    
            this.delete(pl.get(0).playlistId);
	    //-------------------------
	    // rotate elements down
	    //-------------------------
            for(int i=1; i<c; i++)
	    {
		pl.set(i-1, pl.get(i));
		t = pl.get(i-1);
		t.playlistOrder = i;
		pl.set(i-1, t);
	    }
            t = pl.get(0);
            t.playlistModified= now;
            t.playlistOrder   = 1; 	    
            t.playlistStatus  = "PLAYING";
            pl.set(0,t);
            this.updateUserPlaylist(pl);
        }
        else if(c ==1)
        {
            ArrayList<vo_media> medias = this.getJukeboxCatalog(id);
            Random r = new Random();
            int rand = r.ints(0, medias.size()).findFirst().getAsInt();
	    vo_media randomMedia = medias.get(rand);
            vo_playlist p     = pl.get(0);
	    p.playlistOrder   = 1;
            p.mediaId         = randomMedia.mediaId;
            p.userId          = id;
            p.playlistUserId  = id;
            p.playlistModified= now;
            p.playlistStatus  = "PLAYING";
            this.update(p);   
        }
        else
        {
            ArrayList<vo_media> medias = this.getJukeboxCatalog(id);
            Random r = new Random();
            int rand = r.ints(0, medias.size()).findFirst().getAsInt();
	    vo_media randomMedia = medias.get(rand);
	    vo_playlist     p = new vo_playlist();
            p.playlistOrder   = 1;
            p.mediaId         = randomMedia.mediaId;
            p.userId          = id;
            p.playlistUserId  = id;
            p.playlistCreated = TpjSql.now();
            p.playlistModified= TpjSql.now();
            p.playlistStatus  = "PLAYING";
            this.insert(p);
        }
        
        return(this.getCurrentlyPlaying(id));
    }
    
   /**
    * Returns Playlist for a jukebox
    *
    * @param  int       -- jukeboxId
    * @return ArrayList -- playlists
    */
    public ArrayList<vo_playlist> findUserPlaylist(int id)
    {
        String q="SELECT * "+
	         "FROM playlist "+
           "WHERE (playlistStatus='QUEUE' OR playlistStatus='PLAYING') "+
           "AND userId="+id+" ORDER by playlistOrder";

        return((ArrayList<vo_playlist>)executeQueryList(q));
    }

    
   /**
    * update User Playlist 
    *
    * @param array playlists
    *
    */
    public void updateUserPlaylist(ArrayList<vo_playlist> pl)
    {
	String query ="";
	String col   ="";
	String where ="";
	
        query= " UPDATE playlist SET ";
	
	col = "userId = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  "+pl.get(i).userId+" ";
        col+="ELSE userId END, ";
        query+=col; 
        
        col = "mediaId = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  "+pl.get(i).mediaId+" ";
        col+="ELSE mediaId END, ";
        query+=col; 
	
        col = "playlistUserId = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  "+pl.get(i).playlistUserId+" ";
        col+="ELSE playlistUserId END, ";
        query+=col;
	
        col = "playlistStatus = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  '"+pl.get(i).playlistStatus+"' ";
        col+="ELSE playlistStatus END, ";
        query+=col; 
	
        col = "playlistOrder = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  "+pl.get(i).playlistOrder+" ";
        col+="ELSE playlistOrder END, ";
        query+=col; 

        col = "playlistModified = CASE ";
	for(int i=0; i < pl.size(); i++) col+="WHEN playlistId="+pl.get(i).playlistId+" THEN  '"+pl.get(i).playlistModified+"' ";
        col+="ELSE playlistModified END ";
        query+=col;
	
	where= "WHERE ";
        for(int i=0; i < pl.size(); i++) where+="playlistId="+pl.get(i).playlistId+" OR ";
        where+="playlistId=0";
        query+=where;

        this.executeUpdate(query);
    }

    
   /**
    * Returns a Media by userId (it's the jukebox catalog)
    *
    * @param  int -- jukeboxId
    * @return ArrayList<vo_media>
    */
    public ArrayList<vo_media> getJukeboxCatalog(int id)
    {
	TpjContainer c  = (TpjContainer) entityContainer;
	eo_media mediaEO= c.media;	

        String q="SELECT mediaId,"+
                   "userId,"+
                   "mediaFile,"+
                   "mediaSource,"+
                   "mediaArtist,"+
                   "mediaTitle,"+
                   "mediaYear,"+
                   "mediaDuration,"+
                   "mediaCreated,"+
                   "mediaModified,"+
                   "mediaStatus "+                    		               
	         "FROM media "+
	         "WHERE mediaStatus='ACTIVE' AND userId="+id;
	
        return((ArrayList<vo_media>)mediaEO.executeQueryList(q));	
    }


   /**
    * Returns a Media by userId (jukebox) 
    * that's currently playing -- joins playlist
    *
    * @param  int -- jukeboxId
    * @return vo_media
    *
    */
    public vo_media getCurrentlyPlaying(int id)
    {
	TpjContainer c  = (TpjContainer) entityContainer;
	eo_media mediaEO= c.media;	
	vo_media media  = null;
        String   q;	

	q="SELECT media.mediaId,"+
             "media.userId,"+
             "media.mediaFile,"+
             "media.mediaSource,"+
             "media.mediaArtist,"+
             "media.mediaTitle,"+
             "media.mediaYear,"+
             "media.mediaDuration,"+
             "media.mediaCreated,"+
             "media.mediaModified,"+
             "media.mediaStatus "+                      		               
	   "FROM media, playlist "+
	   "WHERE playlist.playlistStatus='PLAYING' "+
           "AND media.mediaId=playlist.mediaId "+
           "AND media.userId=playlist.userId "+
           "AND playlist.userId="+id;

	if((media=(vo_media)mediaEO.executeQueryObject(q))==null)
	    media = this.setDefault();
	return(media);	
    }

    
   /**
    * This function will create a default Media object
    * when the results of a media query count is 0
    *
    * @param  n/a
    * @return vo_media
    *
    */
    private vo_media setDefault()
    {
        TpjConfig config = (TpjConfig)daemon.lookup(CONFIG);
	vo_media  media  = new vo_media();
        media.mediaId      =0;
        media.mediaFile  = config.defaultMediaFile();
        media.mediaArtist= config.defaultMediaArtist();
        media.mediaTitle = config.defaultMediaTitle();
        media.mediaSource= "UPLOAD";            
        media.mediaStatus= "Upload in Progress";
        return(media);
    }
}
