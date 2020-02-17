/* eo_user_gen.java
 *
 * THE FOLLOWING CODE IS AUTO GENERATED BY GENDB SCRIPT 
 * !!!!!!!!!!!!  DO NOT MODIFY THIS FILE !!!!!!!!!!!
 */
package model;

import java.lang.*;
import java.sql.*;
import java.util.*;

import com.qkernel.*;


@SuppressWarnings({"unchecked", "fallthrough", "serial" })
/************************************************************
 * An entity object for the db table user
 * 
 * 
 * @author Initially created by EntityObjectBuilder
 * @version $Revision$ 
 ************************************************************
 */
public abstract class eo_user_gen extends EntityObject
{

    public final static String FIELD_USERID = "userId";
    public final static String FIELD_ACCOUNTID = "accountId";
    public final static String FIELD_USERNAME = "userName";
    public final static String FIELD_USERPASSWORD = "userPassword";
    public final static String FIELD_USERPASSCODE = "userPasscode";
    public final static String FIELD_USERFIRSTNAME = "userFirstName";
    public final static String FIELD_USERLASTNAME = "userLastName";
    public final static String FIELD_USERISJUKEBOX = "userIsJukebox";
    public final static String FIELD_USERNICKNAME = "userNickName";
    public final static String FIELD_USERLIKES = "userLikes";
    public final static String FIELD_USERWORKPLACE = "userWorkplace";
    public final static String FIELD_USERWORKHOURS = "userWorkHours";
    public final static String FIELD_USERPHOTO = "userPhoto";
    public final static String FIELD_USERLONGITUDE = "userLongitude";
    public final static String FIELD_USERLATITUDE = "userLatitude";
    public final static String FIELD_USERLASTLOGIN = "userLastLogin";
    public final static String FIELD_USERCREATED = "userCreated";
    public final static String FIELD_USERMODIFIED = "userModified";
    public final static String FIELD_USERSTATUS = "userStatus";

    /****************************************************
     * find(): This method implements find by primary key
     * key for the table user.
     * If it doesn't find the object in the existing 
     * cache, it loads the necessary data from the db.
     *
     * @param id The primary key value for the record we
     *  searching for
     * @return a vo_user object, representing
     *  the record
     ****************************************************
     */
    public vo_user find(int id)
    {
        vo_user e = findInCache(id);

        if(e == null)
        {
            // Try to load cache first
            load(id);
            return(findInCache(id));
        }
        else 
        {
            // return cache value
            return(e);
        }
    }

    /*****************************************************
     * This method checks the cache for the requested item
     *
     * @param id the primary key of the record we're 
     *  looking for
     *
     * @return a vo_user that represents the 
     *  record or null, if it doesn't exist
     *
     *****************************************************
     */
    public vo_user findInCache(int id)
    {
        vo_user e = null;
        e = (vo_user)entityCache.get(new Integer(id));
        return(e);
    }

    /****************************************************
     * find(): This method implements find by primary key
     * key for the table user.
     * If it doesn't find the object in the existing 
     * cache, it loads the necessary data from the db.
     *
     * @param id The primary key value for the record we
     *  searching for
     * @return a vo_user object, representing
     *  the record
     ****************************************************
     */
    public vo_user find(String id)
    {
        vo_user e = findInCache(id);

        if(e == null)
        {
            // Try to load cache first
            load(id);
            return(findInCache(id));
        }
        else 
        {
            // return cache value
            return(e);
        }
    }

    /*****************************************************
     * This method checks the cache for the requested item
     *
     * @param id the primary key of the record we're 
     *  looking for
     *
     * @return a vo_user that represents the 
     *  record or null, if it doesn't exist
     *
     *****************************************************
     */
    public vo_user findInCache(String id)
    {
        vo_user e = null;
        e = (vo_user)entityCache.get(id);
        return(e);
    }

    /*****************************************************
     * This function returns an ArrayList  of all Value 
     * Objects in this Entity Object
     * @return an ArrayList of all the vo_user 
     *  objects
     *
     *****************************************************
     */
    public ArrayList find()
    {
        String query = "SELECT * FROM user ";
        ArrayList al = new ArrayList();
        executeQuery(query, "setEntity", al);
        return al;
    }

    /*****************************************************
     * This function returns an ArrayList  of all Value 
     * Objects based on query
     * @return an ArrayList of all the vo_user 
     *  objects
     *
     *****************************************************
     */
    public ArrayList executeQueryList(String query)
    {
        ArrayList al = new ArrayList();
        executeQuery(query, "setEntity", al);
        return al;
    }

    /*****************************************************
     * This function returns an ArrayList  of all Value 
     * Objects based on query
     * @return an ArrayList of all the vo_user 
     *  objects
     *
     *****************************************************
     */
    public ArrayList executeQueryList(String query, String mapper)
    {
        ArrayList al = new ArrayList();
        executeQuery(query, mapper, al);
        return al;
    }

    /****************************************************
     * executeQueryObject(): This method implements 
     * executeQuery for the table user.
     * -- and returns vo_user  
     * @param query string
     * @return a vo_user object
     ****************************************************
     */
    public vo_user executeQueryObject(String query)
    {
        Vector vec = new Vector();
        executeQuery(query, "setEntityV", vec );
        Enumeration enm = vec.elements();
        if(enm.hasMoreElements())
            return((vo_user)enm.nextElement());
        else
            return(null);
    }

    /****************************************************
     * executeQueryObject(): This method implements 
     * executeQuery for the table user.
     * -- and returns vo_user  
     * @param query string
     * @return a vo_user object
     ****************************************************
     */
    public vo_user executeQueryObject(String query, String mapper)
    {
        Vector vec = new Vector();
        executeQuery(query, mapper, vec );
        Enumeration enm = vec.elements();
        if(enm.hasMoreElements())
            return((vo_user)enm.nextElement());
        else
            return(null);
    }

    /*****************************************************
     * This function takes the current row in a ResultSet,
     * creates a vo_user object with the data, 
     * @param rs A result set of a query on the user table
     *****************************************************
     */
     public void setEntity(ResultSet rs, ArrayList al) throws SQLException
     {
         vo_user e = entityRow(rs);
         al.add(e);
     }

    /*****************************************************
     * This function takes the current row in a ResultSet,
     * creates a vo_user object with the data, 
     * @param rs A result set of a query on the user table
     *****************************************************
     */
     public void setEntityV(ResultSet rs, Vector vec) throws SQLException
     {
         vo_user e = entityRow(rs);
         vec.addElement(e);
     }

    /*****************************************************
     * This function is similar to the setEntity() method.
     * It takes the current row in a ResultSet,
     * creates a vo_user object with the data, 
     * but instead of placing it in the cache,
     * it places it in the ArrayList that is passed in.
    *
     * @param al An ArrayList to place the resulting vo_user object into.
     * @param rs A result set of a query on the user table
     * @see #setEntity()
     ****************************************************
     */
    public void setEntityC(ResultSet rs) throws SQLException
    {
        vo_user e = entityRow(rs);
        entityCache.put(new Integer(e.userId), e);
     }

    /****************************************************
     * This function maps an SQL row to a Value Object
     *
     * @param rs a ResultSet that contains the record to be mapped
     * @return a vo_user value object
     ****************************************************
     */
    public vo_user entityRow(ResultSet rs) throws SQLException
    {
        vo_user e = new vo_user();
        e.userId= rs.getInt(FIELD_USERID);
        e.accountId= rs.getInt(FIELD_ACCOUNTID);
        e.userName= rs.getString(FIELD_USERNAME);
        e.userPassword= rs.getString(FIELD_USERPASSWORD);
        e.userPasscode= rs.getString(FIELD_USERPASSCODE);
        e.userFirstName= rs.getString(FIELD_USERFIRSTNAME);
        e.userLastName= rs.getString(FIELD_USERLASTNAME);
        e.userIsJukebox= rs.getString(FIELD_USERISJUKEBOX);
        e.userNickName= rs.getString(FIELD_USERNICKNAME);
        e.userLikes= rs.getString(FIELD_USERLIKES);
        e.userWorkplace= rs.getString(FIELD_USERWORKPLACE);
        e.userWorkHours= rs.getString(FIELD_USERWORKHOURS);
        e.userPhoto= rs.getString(FIELD_USERPHOTO);
        e.userLongitude= rs.getString(FIELD_USERLONGITUDE);
        e.userLatitude= rs.getString(FIELD_USERLATITUDE);
        e.userLastLogin= rs.getString(FIELD_USERLASTLOGIN);
        e.userCreated= rs.getString(FIELD_USERCREATED);
        e.userModified= rs.getString(FIELD_USERMODIFIED);
        e.userStatus= rs.getString(FIELD_USERSTATUS);
        return(e);
    }

    /****************************************************
     * This method inserts a new record into the table,
     * but DOES NOT return the new ID or a reference object
     * @param vo_user a value object to be written
     *  to the DB
     * @see #insert2()
     ****************************************************
     */
    public void insert(vo_user e){
        StringBuffer qsb=new StringBuffer();
        qsb.append("INSERT INTO user(");
        qsb.append(FIELD_ACCOUNTID+",");
        qsb.append(FIELD_USERNAME+",");
        qsb.append(FIELD_USERPASSWORD+",");
        qsb.append(FIELD_USERPASSCODE+",");
        qsb.append(FIELD_USERFIRSTNAME+",");
        qsb.append(FIELD_USERLASTNAME+",");
        qsb.append(FIELD_USERISJUKEBOX+",");
        qsb.append(FIELD_USERNICKNAME+",");
        qsb.append(FIELD_USERLIKES+",");
        qsb.append(FIELD_USERWORKPLACE+",");
        qsb.append(FIELD_USERWORKHOURS+",");
        qsb.append(FIELD_USERPHOTO+",");
        qsb.append(FIELD_USERLONGITUDE+",");
        qsb.append(FIELD_USERLATITUDE+",");
        qsb.append(FIELD_USERLASTLOGIN+",");
        qsb.append(FIELD_USERCREATED+",");
        qsb.append(FIELD_USERMODIFIED+",");
        qsb.append(FIELD_USERSTATUS+")");
        qsb.append("VALUES(");
        qsb.append("").append(e.accountId).append(",");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPassword)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPasscode)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userFirstName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userIsJukebox)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userNickName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLikes)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkplace)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkHours)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPhoto)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLongitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLatitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastLogin)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userCreated)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userModified)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userStatus)).append("')");


        executeUpdate(qsb.toString());
    }

    /****************************************************
     * This method inserts a new record into the table,
     * using the local entityQueue
     * @param vo_user a value object to be written
     *  to the DB
     * @see #insert2()
     ****************************************************
     */
    public void insertQueue(vo_user e){
        StringBuffer qsb=new StringBuffer();
        qsb.append("INSERT INTO user(");
        qsb.append(FIELD_ACCOUNTID+",");
        qsb.append(FIELD_USERNAME+",");
        qsb.append(FIELD_USERPASSWORD+",");
        qsb.append(FIELD_USERPASSCODE+",");
        qsb.append(FIELD_USERFIRSTNAME+",");
        qsb.append(FIELD_USERLASTNAME+",");
        qsb.append(FIELD_USERISJUKEBOX+",");
        qsb.append(FIELD_USERNICKNAME+",");
        qsb.append(FIELD_USERLIKES+",");
        qsb.append(FIELD_USERWORKPLACE+",");
        qsb.append(FIELD_USERWORKHOURS+",");
        qsb.append(FIELD_USERPHOTO+",");
        qsb.append(FIELD_USERLONGITUDE+",");
        qsb.append(FIELD_USERLATITUDE+",");
        qsb.append(FIELD_USERLASTLOGIN+",");
        qsb.append(FIELD_USERCREATED+",");
        qsb.append(FIELD_USERMODIFIED+",");
        qsb.append(FIELD_USERSTATUS+")");
        qsb.append("VALUES(");
        qsb.append("").append(e.accountId).append(",");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPassword)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPasscode)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userFirstName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userIsJukebox)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userNickName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLikes)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkplace)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkHours)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPhoto)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLongitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLatitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastLogin)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userCreated)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userModified)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userStatus)).append("')");


        entityQueue.put(qsb.toString());
    }

    /****************************************************
     * This method inserts a new record into the table,
     * AND returns a reference object with the new
     * auto-generated PK;
     *
     * @param vo_user a value object to be written to the DB
      * @return a vo_user object that represents
     *  the original object, with the addition of the primary key
     * @see #insert()
     ****************************************************
     */
    public vo_user insert2(vo_user e)
    {
        vo_user e1=(vo_user) e.clone();

        StringBuffer qsb=new StringBuffer();
        qsb.append("INSERT INTO user(");
        qsb.append(FIELD_ACCOUNTID+",");
        qsb.append(FIELD_USERNAME+",");
        qsb.append(FIELD_USERPASSWORD+",");
        qsb.append(FIELD_USERPASSCODE+",");
        qsb.append(FIELD_USERFIRSTNAME+",");
        qsb.append(FIELD_USERLASTNAME+",");
        qsb.append(FIELD_USERISJUKEBOX+",");
        qsb.append(FIELD_USERNICKNAME+",");
        qsb.append(FIELD_USERLIKES+",");
        qsb.append(FIELD_USERWORKPLACE+",");
        qsb.append(FIELD_USERWORKHOURS+",");
        qsb.append(FIELD_USERPHOTO+",");
        qsb.append(FIELD_USERLONGITUDE+",");
        qsb.append(FIELD_USERLATITUDE+",");
        qsb.append(FIELD_USERLASTLOGIN+",");
        qsb.append(FIELD_USERCREATED+",");
        qsb.append(FIELD_USERMODIFIED+",");
        qsb.append(FIELD_USERSTATUS+")");
        qsb.append("VALUES(");
        qsb.append("").append(e.accountId).append(",");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPassword)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPasscode)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userFirstName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userIsJukebox)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userNickName)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLikes)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkplace)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userWorkHours)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userPhoto)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLongitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLatitude)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userLastLogin)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userCreated)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userModified)).append("',");
        qsb.append("'").append(SqlSafe.sqlSafe( e.userStatus)).append("')");


        try
        {
            Connection conn    = entityDbPool.getConnection();
            Statement stmt     = conn.createStatement();
            stmt.executeUpdate(qsb.toString());
            //------------------------------------------------------------------
            // Retrieve new AUTO_INCREMENT userId 
            //------------------------------------------------------------------
            ResultSet r = stmt.executeQuery("SELECT LAST_INSERT_ID() AS cId");
            r.next();
            e1.userId= r.getInt("cId");
            stmt.close();
            entityDbPool.freeConnection(conn);
        }
        catch(Exception ept)
        {
            daemon.eventLog.sendMessage(ept);
        }
        return(e1);
    }

    /****************************************************
     * This function updates the record in the database
     * who's primary key matches the 
     * userId value of the passed-in 
     * vo_user object.
     *
     * @param e a vo_user object
     ****************************************************
     */
    public void update(vo_user e)
    {
        StringBuffer qsb=new StringBuffer();
        qsb.append("UPDATE user ").append("SET ");
        qsb.append(FIELD_ACCOUNTID+"=").append(e.accountId).append(",");
        qsb.append(FIELD_USERNAME+"='").append(SqlSafe.sqlSafe( e.userName)).append("',");
        qsb.append(FIELD_USERPASSWORD+"='").append(SqlSafe.sqlSafe( e.userPassword)).append("',");
        qsb.append(FIELD_USERPASSCODE+"='").append(SqlSafe.sqlSafe( e.userPasscode)).append("',");
        qsb.append(FIELD_USERFIRSTNAME+"='").append(SqlSafe.sqlSafe( e.userFirstName)).append("',");
        qsb.append(FIELD_USERLASTNAME+"='").append(SqlSafe.sqlSafe( e.userLastName)).append("',");
        qsb.append(FIELD_USERISJUKEBOX+"='").append(SqlSafe.sqlSafe( e.userIsJukebox)).append("',");
        qsb.append(FIELD_USERNICKNAME+"='").append(SqlSafe.sqlSafe( e.userNickName)).append("',");
        qsb.append(FIELD_USERLIKES+"='").append(SqlSafe.sqlSafe( e.userLikes)).append("',");
        qsb.append(FIELD_USERWORKPLACE+"='").append(SqlSafe.sqlSafe( e.userWorkplace)).append("',");
        qsb.append(FIELD_USERWORKHOURS+"='").append(SqlSafe.sqlSafe( e.userWorkHours)).append("',");
        qsb.append(FIELD_USERPHOTO+"='").append(SqlSafe.sqlSafe( e.userPhoto)).append("',");
        qsb.append(FIELD_USERLONGITUDE+"='").append(SqlSafe.sqlSafe( e.userLongitude)).append("',");
        qsb.append(FIELD_USERLATITUDE+"='").append(SqlSafe.sqlSafe( e.userLatitude)).append("',");
        qsb.append(FIELD_USERLASTLOGIN+"='").append(SqlSafe.sqlSafe( e.userLastLogin)).append("',");
        qsb.append(FIELD_USERCREATED+"='").append(SqlSafe.sqlSafe( e.userCreated)).append("',");
        qsb.append(FIELD_USERMODIFIED+"='").append(SqlSafe.sqlSafe( e.userModified)).append("',");
        qsb.append(FIELD_USERSTATUS+"='").append(SqlSafe.sqlSafe( e.userStatus)).append("'");
        qsb.append(" WHERE ");
                qsb.append("userId="+e.userId);

        executeUpdate(qsb.toString());
    }

    /****************************************************
     * This function updates the record in the database
     * who's primary key matches the 
     * userId value of the passed-in 
     * vo_user object.
     *
     * @param e a vo_user object
     ****************************************************
     */
    public void updateQueue(vo_user e)
    {
        StringBuffer qsb=new StringBuffer();
        qsb.append("UPDATE user ").append("SET ");
        qsb.append(FIELD_ACCOUNTID+"=").append(e.accountId).append(",");
        qsb.append(FIELD_USERNAME+"='").append(SqlSafe.sqlSafe( e.userName)).append("',");
        qsb.append(FIELD_USERPASSWORD+"='").append(SqlSafe.sqlSafe( e.userPassword)).append("',");
        qsb.append(FIELD_USERPASSCODE+"='").append(SqlSafe.sqlSafe( e.userPasscode)).append("',");
        qsb.append(FIELD_USERFIRSTNAME+"='").append(SqlSafe.sqlSafe( e.userFirstName)).append("',");
        qsb.append(FIELD_USERLASTNAME+"='").append(SqlSafe.sqlSafe( e.userLastName)).append("',");
        qsb.append(FIELD_USERISJUKEBOX+"='").append(SqlSafe.sqlSafe( e.userIsJukebox)).append("',");
        qsb.append(FIELD_USERNICKNAME+"='").append(SqlSafe.sqlSafe( e.userNickName)).append("',");
        qsb.append(FIELD_USERLIKES+"='").append(SqlSafe.sqlSafe( e.userLikes)).append("',");
        qsb.append(FIELD_USERWORKPLACE+"='").append(SqlSafe.sqlSafe( e.userWorkplace)).append("',");
        qsb.append(FIELD_USERWORKHOURS+"='").append(SqlSafe.sqlSafe( e.userWorkHours)).append("',");
        qsb.append(FIELD_USERPHOTO+"='").append(SqlSafe.sqlSafe( e.userPhoto)).append("',");
        qsb.append(FIELD_USERLONGITUDE+"='").append(SqlSafe.sqlSafe( e.userLongitude)).append("',");
        qsb.append(FIELD_USERLATITUDE+"='").append(SqlSafe.sqlSafe( e.userLatitude)).append("',");
        qsb.append(FIELD_USERLASTLOGIN+"='").append(SqlSafe.sqlSafe( e.userLastLogin)).append("',");
        qsb.append(FIELD_USERCREATED+"='").append(SqlSafe.sqlSafe( e.userCreated)).append("',");
        qsb.append(FIELD_USERMODIFIED+"='").append(SqlSafe.sqlSafe( e.userModified)).append("',");
        qsb.append(FIELD_USERSTATUS+"='").append(SqlSafe.sqlSafe( e.userStatus)).append("'");
        qsb.append(" WHERE ");
                qsb.append("userId="+e.userId);

        entityQueue.put(qsb.toString());
    }

    /****************************************************
     * delete(id): This method deletes the object in the
     * who's primary ket matches the value passed in
     * 
     * @param id a Int representing the primary key
     ****************************************************
     */
    public void delete(int id)
    {
        String query;
        query="DELETE FROM user WHERE userId="+id;
        executeUpdate(query);
    }

    /****************************************************
     * This is the default load method that is called by
     * the container at startup
     ****************************************************
     */
    public void load()
    {
         //This is empty for now

    }

    /****************************************************
     * This function loads an individual record into the
     * cache
     *
     * @param id The primary key to use for record lookup
     ****************************************************
     */
    public void load(int id)
    {
     String qs=
         "SELECT * FROM user WHERE userId="+id;

     executeQuery(qs,"setEntityC");
    }

    /****************************************************
     * This function loads an individual record into the
     * cache
     *
     * @param id The  key to use for record lookup
     ****************************************************
     */
    public void load(String id)
    {
         //Override this method to implement cache scheme

    }

    /****************************************************
     * This function returns the number of records
     * in the table represented by this entity. 
     * 
     * @return The number of records in the table
     ****************************************************
     */
    public int getCount()
    {
        int count=0;
        String qs=
            "SELECT COUNT(userId) AS Count FROM user ";
        ResultSet r=executeQuery(qs);
        try
        {
            r.next();
            count=r.getInt("Count");
            closeResultSet(r);
        }
        catch(Exception e)
         {
            daemon.event_log.sendMessage("Can't retrieve ResultSet because: "+e.getMessage());
        }
        return(count);
    }

    /****************************************************
     * This function is called by the daemon when this 
     * Entity Object is initialized
     * Sub-class should invoke super.init() then:
     * entityCache.start(t)
     * useLocalEntityQueue() (if you want a local queue)
     ****************************************************
     */
    public void init()
    {
        entityQueue.start();          // Start Global Entity Queue
        //entityCache.start(30);      // Start Entity Cache
        //useLocalEntityQueue();      // Set and Start Local Entity Queue
    }

}

