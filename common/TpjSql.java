package common;
/**
 * TpjSql -- Common SQL functions
 *
 * History:
 * --------
 * 02-20-20 mgill  Add bcryptVerify()
 * 02-10-20 mgill  Initial Creation. 
 */
import java.lang.*;
import java.util.*;
import java.io.*;
import java.text.*;
import com.qkernel.*;
import com.qkernel.json.*;
import com.qkernel.crypto.bcrypt.*;
import com.qkernel.crypto.bytes.*;

/**
 * {@code TpjSql} 
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
    public static String now()
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
    public static String  randomUUID()
    {
        UUID uuid = UUID.randomUUID();
	return(uuid.toString());
    }

   /**
    * verifies a bcrypt string is equal a compared string
    *
    * @param  String  str to verify
    * @param  String  bcrypt str to check against
    *
    * @return boolean true= verified
    *
    */
    public static boolean  bcryptVerify(String str, String bcryptStr)
    {
	boolean verified = false;
	
        BCrypt.Result result = BCrypt.verifyer().verify(str.toCharArray(), bcryptStr);
        if(result.verified)
            verified = true;
	
        return(verified);
    }
    
    public TpjSql()
    { 

    }
}
