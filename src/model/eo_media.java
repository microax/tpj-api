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


/************************************************************
 * An entity object for the db table media
 * 
 * 
 * @author  Initially created by EntityObjectBuilder
 * @version $Revision$ 
 ************************************************************
 */
public class eo_media extends eo_media_gen
{

    /*********************************************************
     * Returns a Media by userId (it's the jukebox catalog)
     *
     * @param  $id
     * @return $medias
     *********************************************************
     */
    public function findByUserId($id)
    {
        $query="SELECT media.mediaId,".
                      "media.userId,".
                      "media.mediaFile,".
                      "media.mediaSource,".
                      "media.mediaArtist,".
                      "media.mediaTitle,".
                      "media.mediaYear,".
                      "media.mediaDuration,".
                      "media.mediaCreated,".
                      "media.mediaModified,".
                      "media.mediaStatus ".                      		               
	       "FROM media, upload ".
           "WHERE media.mediaStatus='ACTIVE' ".
             "AND media.userId=upload.userId ".
             "AND upload.uploadStatus='COMPLETE' ".
             "AND media.userId=".$id;

		return(this.executeQueryJSONArray(q,"mapBroadcastList"));	

        return($this->getMedias($this->selectDB($query, "Media")));
    }


   /**
    *
    * This function maps an SQL row to a JSONObject for broadcastList
    * it is called on each row.
    *
    * @param ResultSet  that contains the record to be mapped
    * @param ArrayList  of JSONObject's 
    * @return void
    *
    */
    public void mapBroadcastList(ResultSet rs, ArrayList<JSONObject> al) throws SQLException
    {
        JSONObject jo  = new JSONObject();

        jo.put("channelName"       , rs.getString("chan_name"));
	jo.put("channelDescription", rs.getString("chan_description"));
	jo.put("roomId"            , rs.getString("room_id"));
	jo.put("broadcastId"       , rs.getString("bcast_id"));
	jo.put("broadcastStartDate", rs.getString("bcast_start_date"));

        al.add(jo);
     }


    /*********************************************************
     * This function will create a default Media object
     * when the results of a media query count is 0
     *
     * @param  $medias
     * @return $medias
     *********************************************************
     */
    private function getMedias($medias)
    {
        if(count($medias) == 0)
        {
            $medias[0] = new Media();
            $medias[0]->mediaid     = 0;
            $medias[0]->mediaFile   = defaultMediaFile();
            $medias[0]->mediaArtist = defaultMediaArtist();
            $medias[0]->mediaTitle  = defaultMediaTitle();
            $medias[0]->mediaSource = "UPLOAD";            
            $medias[0]->mediaStatus = "Upload in Progress";
        }
        return($medias);
    }       
    
}

