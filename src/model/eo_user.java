/* eo_user.java
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
import common.TpjSql;

/************************************************************
 * An entity object for the db table user
 * 
 * 
 * @author  Initially created by EntityObjectBuilder
 * @version $Revision$ 
 ************************************************************
 */
public class eo_user extends eo_user_gen
{

   /**
    * Returns a vo_user for username and password
    *
    * @param  string  username
    * @param  string  password 
    * @return vo_user user
    *
    */
    public vo_user findUserLogin(String username, String password)
    {
	username  = SqlSafe.sqlSafe(username);
        String  q = "SELECT * FROM user WHERE userName='"+username+"' AND userStatus = 'ACTIVE'"; 
        vo_user u = executeQueryObject(q);
        if(u != null)
        {
	    if(!TpjSql.bcryptVerify(password, u.userPassword))
		u = null;
        }
        return(u);
    }

   /**
    * Returns a vo_user for username and passcode
    *
    * @param  string  username
    * @param  string  passcode 
    * @return vo_user user
    *
    */
    public vo_user findByUserPasscode(String username, String passcode)
    {
	username  = SqlSafe.sqlSafe(username);
	passcode  = SqlSafe.sqlSafe(passcode);
	
        String  q = "SELECT * FROM user WHERE userName='"+
	            username+"' AND userPasscode='"+
	            passcode+"' AND userStatus = 'ACTIVE'";
	
        return(executeQueryObject(q));
    }

}

