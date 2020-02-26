package common;


public interface TpjConstants
{
    //-------------------------------------------------------------
    //	Services and such
    //-------------------------------------------------------------
    public final String LOG          = "EventLog Service";
    public final String CONFIG       = "Configuration Object";
    public final String CONTAINER    = "EntityContainer Object";
    public final String BEBATCH      = "Broadcast Post Process";
    public final String MSCHAN       = "Media Server Channel";
    public final String ACCOUNT_LOG  = "AccountLog Service";
    public final String SESSION      = "User Session Object";
    public final String USER_SESSION = "UserSession Object";
    public final String IVR_SESSION  = "IvrSession Object";

    public final String EO_NODE_SESSION = "eo_Node_session Object";

    //----------------------------------------------------------------
    // Virtual Channel constants
    //----------------------------------------------------------------
    public final String VCHAN        = "Virtual Channel";
    public final String BILLING      = "SPN Billing";
    public final String EMAIL        = "Email Channel";
    public final String IVR          = "IVR Channel";
    public final String FTP          = "FTP Channel";
    public final String QBUS         = "QBUS Channel";
    public final String SOAP         = "SOAP Channel";
    public final String SSH          = "SSH Channel";
    public final String REST         = "REST Channel";

    //--------------------------------------------------------------
    // SMTP Properties
    //--------------------------------------------------------------
    public final String SMTP  	     = "SMTP Properties";

    //--------------------------------------------------------------
    // Account Log Events
    //-------------------------------------------------------------- 
    public final String EV_LOGIN		= "Login Event";
    public final String EV_LOGOFF		= "Logoff Event";
    public final String EV_MASQUERADE		= "Root Masquerade";
    public final String EV_LOG_ONLY		= "Log String Only";
    public final String EV_EMAIL_COMPLETE	= "Email Complete";
    public final String EV_EMAIL_ERROR		= "Email Errors";
    public final String EV_PUB_REQUEST		= "Publish Request";
    public final String EV_PUB_COMPLETE		= "Publish Complete";
    public final String EV_PUB_ERROR		= "Publish Errors";
    public final String EV_PUB_APPROVE		= "Publish Approved";
    public final String EV_PUB_START		= "Publish Start";
    public final String EV_NEW_USER		= "New User";
    public final String EV_CHANGE_USER		= "Modified User";
    public final String EV_NEW_ACCOUNT		= "New Account";
    public final String EV_SPN_ALARM		= "Service Alarm";
    public final String EV_ACCESS_ALARM		= "Access Voilation";

    //----------------------------------------------------------------
    // User Permissions for all tpj functions are defined here
    //----------------------------------------------------------------
    //
    //---------------------------------
    // User/Account Permissions
    //---------------------------------
    public final String CAN_USER_ADMIN		= "canUserAdmin";
    public final String CAN_MANAGE_ACCOUNT	= "canAccountAdmin";
    public final String CAN_CREATEDELETE_ACCOUNT= "canCreateDeleteAdmin";
    //---------------------------------
    // Application Node Permissions
    //---------------------------------
    public final String CAN_USE_APPNODE		= "canUseAppNode";
    public final String CAN_NODE_PUBLISH	= "canNodePublish";
    //---------------------------------
    // CMS Permissions
    //---------------------------------
    public final String CAN_USE_CMS		= "canUseCms";
    public final String CAN_EDIT_CONTENTRULES	= "canEditContentRules";
    public final String CAN_EDIT_TEMPLATES	= "canEditTemplates";
    public final String CAN_CMS_PUBLISH		= "canCmsPublish";
    public final String CAN_CMS_AUTHOR		= "canCmsAuthor";
    public final String CAN_CMS_ADMIN		= "canCmsAdmin";
    //---------------------------------
    // Group Messaging Permissions
    //---------------------------------
    public final String CAN_USE_GROUPMESSAGING	= "canUseGroupMessaging";
    public final String CAN_EDIT_TOPICS		= "canEditTopics";

    //--------------------------------------------------------------
    // Stuff passed in QMessages
    //--------------------------------------------------------------
    public final String REMOTE_ADDR  		= "REMOTE_ADDR";
    public final String QUERY_STRING 		= "QUERY_STRING";
    public final String SESSION_ID   		= "SessionId";
    public final String STATUS       		= "STATUS";
    public final String REQUIRES_PERMISSION 	= "REQUIRES_PERMISSION";
    public final String DEFAULT_PERMISSION 	= "No Permission required";
    public final String STATUS_MESSAGE 		= "STATUS_MESSAGE";
    public final String STATUS_REFERER 		= "Referer";

    //----------------------------------------------------------------
    // Values for STATUS
    //----------------------------------------------------------------
    public final String STATUS_OK    		= "OK";
    public final String STATUS_NOLOGIN		="NOLOGIN";
    public final String STATUS_ERROR 		= "ERROR";
    public final String STATUS_ACTIVE 		= "ACTIVE";
    public final String STATUS_INACTIVE 	= "INACTIVE";
}
