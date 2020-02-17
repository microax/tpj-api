package common;
/**
 * TpjSql -- Common SQL functions
 *
 * History:
 * --------
 * 02-10-20 mgill  Initial Creation. 
 */
import java.lang.*;
import java.util.*;
import java.io.*;
import java.text.*;
import com.qkernel.*;
import com.qkernel.json.*;
/**
 * {@code SpnSql} 
 * <p>
 * Methods:
 * --------
 * now()
 * randomUUID();
 *
 */
public class TpjSql extends Object implements TpjConstants
{  
   /**
    * now
    *
    * returns sql formatted String for postgres timestamp
    * 
    * @param  void
    * @return String time 
    */
    public String now()
    {
        Date curDate = new Date();
        String TIME_FORMAT   = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
	return(sdf.format(curDate));
    }

   /**
    * randomUUID
    *
    * returns sql formatted UUID String 
    * 
    * @param  void
    * @return String UUID
    */
    public String randomUUID()
    {
        UUID uuid = UUID.randomUUID();
	return(uuid.toString());
    }

    public TpjSql()
    { 

    }
}
