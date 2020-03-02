/* eo_media.java
 *
 *****************************************************************************
 * History:
 * ========
 * 
 *  
 *****************************************************************************
 * 
 */ 
package model;

import java.lang.*;
import java.sql.*;
import java.util.*;

import com.qkernel.*;
import com.qkernel.json.*;
import common.TpjConstants;
import common.TpjSql;
import daemon.TpjConfig;

/************************************************************
 * An entity object for the db table media
 * 
 * 
 * @author  Initially created by EntityObjectBuilder
 * @version $Revision$ 
 ************************************************************
 */
public class eo_media extends eo_media_gen implements TpjConstants
{

    /*************************************************************
     * Returns Media objects by userId (it's the jukebox catalog)
     *
     * @param  int       jukebox id
     * @return JSONArray list of media objects 
     *************************************************************
     */
    public JSONArray findByUserId(int id)
    {
        String query=
         "SELECT media.mediaId,"+
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
	       "FROM media, upload "+
           "WHERE media.mediaStatus='ACTIVE' "+
             "AND media.userId=upload.userId "+
             "AND upload.uploadStatus='COMPLETE' "+
             "AND media.userId="+id;

	JSONArray ja = new JSONArray();
	ja = this.executeQueryJSONArray(query,"mapMediaList");
        if(ja.length() == 0)
	    ja = setDefaultJSON();
        return(ja);
    }


    /*************************************************************
     * Returns Media objects by userId (it's the jukebox catalog)
     *
     * @param  int       jukebox id
     * @return JSONArray list of media objects 
     *************************************************************
     */
    public vo_media findNextInQueue(int id)
    {
        String query=
         "SELECT media.mediaId,"+
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
	       "FROM media, upload "+
           "WHERE media.mediaStatus='ACTIVE' "+
             "AND media.userId=upload.userId "+
             "AND upload.uploadStatus='COMPLETE' "+
             "AND media.userId="+id;

        return(this.executeQueryObject(query));
    }

    /*************************************************************
     * Returns currently playing for a jukebox
     *
     * @param  int       jukebox id
     * @return vo_media  media 
     *************************************************************
     */
    public vo_media findCurrentlyPlaying(int id)
    {
        vo_media m= null;
        String   q= "";
	
	q ="SELECT media.mediaId,"+
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

        if ((m=this.executeQueryObject(q))==null)
	    m = setDefault();
        return(m);
    }

    
   /**
    *
    * This function maps an SQL row to a JSONObject for Media
    * it is called on each row.
    *
    * @param ResultSet  that contains the record to be mapped
    * @param ArrayList  of JSONObject's 
    * @return void
    *
    */
    public void mapMediaList(ResultSet rs, ArrayList<JSONObject> al) throws SQLException
    {
        JSONObject jo  = new JSONObject();

        jo.put("mediaId"      , rs.getInt("mediaId"));
	jo.put("userId"       , rs.getInt("userId"));
	jo.put("mediaFile"    , rs.getString("mediaFile"));
	jo.put("mediaSource"  , rs.getString("mediaSource"));
	jo.put("mediaArtist"  , rs.getString("mediaArtist"));
	jo.put("mediaTitle"   , rs.getString("mediaTitle"));
	jo.put("mediaYear"    , rs.getString("mediaYear"));
	jo.put("mediaDuration", rs.getString("mediaDuration"));
	jo.put("mediaCreated" , rs.getString("mediaCreated"));
	jo.put("mediaModified", rs.getString("mediaModified"));
	jo.put("mediaStatus"  , rs.getString("mediaStatus"));

        al.add(jo);
     }


    /*********************************************************
     * This function will create a default Media object
     * when the results of a media query count is 0
     *
     * @param  n/a
     * @return JSONArray default object
     *********************************************************
     */
    private JSONArray setDefaultJSON()
    {
        TpjConfig config = (TpjConfig)daemon.lookup(CONFIG);
	JSONArray  ja = new JSONArray();
        JSONObject jo = new JSONObject();
	
        jo.put("mediaId", "0");
        jo.put("mediaFile",   config.defaultMediaFile());
        jo.put("mediaArtist", config.defaultMediaArtist());
        jo.put("mediaTitle",  config.defaultMediaTitle());
        jo.put("mediaSource", "UPLOAD");            
        jo.put("mediaStatus", "Upload in Progress");
        ja.put(jo);
        return(ja);
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
    
   /**
    * executeQueryJSONArray
    * 
    * create JSONArray and ArrayList of JSONObjects mapped by 
    * mapperMethod on query then return the JSONArray.
    *
    * @param String query 
    * @param String mapper method 
    *
    * @return JSONArray
    */
    @SuppressWarnings({"unchecked"})
    
    public JSONArray executeQueryJSONArray(String q, String mapperMethod)
    {
	JSONArray ja = new JSONArray();
	try{
	ArrayList<JSONObject> al = executeQueryList(q, mapperMethod);
	al.forEach(jo->ja.put(jo));
        }
	catch(Exception e){daemon.log(e);}
        return(ja);
    }    
}
