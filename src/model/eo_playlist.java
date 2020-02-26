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
import java.lang.Math;

import com.qkernel.*;
import common.TpjSql;
import common.TpjConstants;

/************************************************************
 * An entity object for the db table playlist
 * 
 * 
 * @author  Initially created by EntityObjectBuilder
 * @version 200223  
 ************************************************************
 */
public class eo_playlist extends eo_playlist_gen imlements TpjConstants
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
        ArrayList<vo_playlist> pl      = this.findUserPlayList(id);
        int                    c       = pl.size();
	vo_playlist            playlist= pl.indexOf(0);

	if(c > 1)
        {
            this.delete(playlist.playlistId);
            
            for(int i=1; i<c; i++ )
            {
		pl.indexOf(i-1) = pl.indexOf(i);
		pl.indexOf(i-1).playlistOrder =i;
            }
            playlist.playlistModified= TpjSql.now();
            playlist.playlistStatus  = 'PLAYING';
            this.updateUserPlaylist(pl);
        }
        else if(c ==1)
        {
            ArrayList<vo_media> medias = this.getJukeboxCatalog(id);
            int rand   = (int)Math.random() * medias.size() -1;
            playlist.playlistOrder   = 1;
            playlist.mediaId         = medias.indexOf(rand).mediaId;
            playlist.userId          = id;
            playlist.playlistUserId  = id;
            playlist.playlistModified=TpjSql.now();
            playlist.playlistStatus  = 'PLAYING';
            this.update(playlist);   
        }
        else
        {
            ArrayList<vo_media> medias = this.getJukeboxCatalog(id);
            int rand   = (int)Math.random() * medias.size() -1;	    
            playlist.playlistOrder   = 1;
            playlist.mediaId         = medias.indexOf(rand).mediaId;
            playlist.userId          = id;
            playlist.playlistUserId  = id;
            playlist.playlistCreated = TpjSql.now();
            playlist.playlistModified= TpjSql.now();
            playlist.playlistStatus  = 'PLAYING';
            this.insert(playlist);
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
        String q="SELECT playlistId,"+
                   "userId,"+
                   "mediaId,".
                   "playlistUserId,"+
                   "playlistOrder,"+
                   "playlistCreated,"+
                   "playlistModified,"+
                   "playlistStatus "+                      		               
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
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.userId+" ";
        col+="ELSE userId END, ";
        query+=col; 
        
        col = "mediaId = CASE ";
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.mediaId+" ";
        col+="ELSE mediaId END, ";
        query+=col; 
	
        col = "playlistUserId = CASE ";
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.playlistUserId+" ";
        col+="ELSE playlistUserId END, ";
        query+=col;
	
        col = "playlistStatus = CASE ";
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.playlistStatus+" ";
        col+="ELSE playlistStatus END, ";
        query+=col; 
	
        col = "playlistOrder = CASE ";
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.playlistOrder+" ";
        col+="ELSE playlistOrder END, ";
        query+=col; 
	
        col = "playlistModified = CASE ";
        pl.forEach(m)-> col+="WHEN playlistId="+m.playlistId+" THEN  "+m.playlistModified+" ";
        col+="ELSE playlistModified END, ";
        query+=col; 
	
	where= "WHERE ";
        pl.forEach(m)-> where+="playlistId="+m.playlistId+" OR ";
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
	
        return((ArrayList<vo_media>)executeQueryList(q));	
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
	vo_media media = null;
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

	if((media=(vo_media)executeQueryObject(q))==null)
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
        media.mediaFile  = config.defaultMediaFile());
        media.mediaArtist= config.defaultMediaArtist());
        media.mediaTitle = config.defaultMediaTitle());
        media.mediaSource= "UPLOAD";            
        media.mediaStatus= "Upload in Progress";
        return(media);
    }
}
