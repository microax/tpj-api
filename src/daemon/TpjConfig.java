package daemon;
//
// TpjConfig.java        TPJ Application Node Configuration Object
// ----------------------------------------------------------------------------
// History:
// --------
// 02/10/20 M. Gill     Initial creation.
// ----------------------------------------------------------------------------
//
import java.lang.*;
import java.util.*;
import java.io.*;
import com.qkernel.*;
import common.TpjConstants;

@SuppressWarnings("unchecked")

public final class TpjConfig extends Config implements TpjConstants
{
    //--------------------------------------------------------------------------------
    // METHOD   putROMConfig()
    //
    // PURPOSE: Create default ROM daemon configuration.
    // INPUT:   None.
    // RETURN:  None.
    //--------------------------------------------------------------------------------

    public void putROMConfig()
    {
	super.putROMConfig();
	
        putString("defaultMediaFile",  "rock lobster.mp3");
        putString("defaultMediaArtist","The B-52's");
        putString("defaultMediaTitle", "Rock Lobster");
        putString("mp3PlayerUrl",
		  "http://thepeoplesjukebox.com/mp3player/mp3player.php");

	putInt("songSelectMax", 2);
    }

    public String defaultMediaFile()
    {
	return(getString("defaultMediaFile")); 
    }
    public String defaultMediaArtist()
    {
        return(getString("defaultMediaArtist")); 	
    }
    public String defaultMediaTitle()
    {
        return(getString("defaultMediaTitle"));
    }
    public String getPlayerUrl(int id)
    {
        return(getString("mp3PlayerUrl")+"?jukeboxId="+id);
    }
    public int getSongSelectMax()
    {
        return(getInt("songSelectMax"));
    }
    
    //--------------------------------------------------------------------------------
    // METHOD   TpjConfig()
    //
    // PURPOSE: Constructor ...Reads params from properties file...loads
    //		default ROM configuration for properties that may not be defined.
    //
    // INPUT:   1) Daemon
    //		2) Commandline argvs
    //
    // RETURN:  N/A
    //--------------------------------------------------------------------------------
    public TpjConfig( Daemon daemon, String[] argvs )
    {
	super(daemon, argvs);
    }
}
