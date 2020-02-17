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
import com.qkernel.crypto.bcrypt.*;
import com.qkernel.crypto.bytes.*;

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

    /*********************************************************
     * Returns a User for $username and $password
     *
     * @param  string $username
     * @param  string $password 
     * @return array  $user
     *********************************************************
     */
    public vo_user findUserLogin(String username, String password)
    {
	//        String username        = $this->sqlSafe($username);

        String  q = "SELECT * FROM user WHERE userName='"+username+"' AND userStatus = 'ACTIVE'"; 
        vo_user u = executeQueryObject(q);
        if(u != null)
        {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), u.userPassword);
            if(!result.verified)
                u = null;
        }
        return(u);
    }
}

