/* vo_faq_gen 
 *
 * THE FOLLOWING CODE IS AUTO GENERATED BY GENDB SCRIPT 
 * !!!!!!!!!!!!  DO NOT MODIFY THIS FILE !!!!!!!!!!!
 */
package model;

import com.qkernel.*;

@SuppressWarnings({"unchecked", "fallthrough", "serial" })
/************************************************************
 * A ValueObject for the db table faq
 * @author Initially created by ValueObjectBuilder
 * @version $Revision$ 
 ************************************************************
 */
public abstract class vo_faq_gen extends CloneableObject
{

    /** faqId database type:INT */
    public int faqId=0;

    /** faqQuestion database type:BLOB */
    public String faqQuestion="";

    /** faqAnswer database type:BLOB */
    public String faqAnswer="";

    /** faqOrder database type:INT */
    public int faqOrder=0;

    /** faqCreated database type:DATETIME */
    public String faqCreated="";

    /** faqModified database type:DATETIME */
    public String faqModified="";

    /** faqStatus database type:VARCHAR */
    public String faqStatus="";

    /**
     * Gets a string representation of the requested field, or an empty string if not available
     * @param field - field name being requested (matching defined field name in the entity object)
     * @return String representation of the field requested
     */
    public String getString(String field)
    {
        if(field.equals(eo_faq.FIELD_FAQID)) {return Integer.toString(faqId);}
        if(field.equals(eo_faq.FIELD_FAQQUESTION)) {return faqQuestion==null ? "" : faqQuestion;}
        if(field.equals(eo_faq.FIELD_FAQANSWER)) {return faqAnswer==null ? "" : faqAnswer;}
        if(field.equals(eo_faq.FIELD_FAQORDER)) {return Integer.toString(faqOrder);}
        if(field.equals(eo_faq.FIELD_FAQCREATED)) {return faqCreated==null ? "" : faqCreated;}
        if(field.equals(eo_faq.FIELD_FAQMODIFIED)) {return faqModified==null ? "" : faqModified;}
        if(field.equals(eo_faq.FIELD_FAQSTATUS)) {return faqStatus==null ? "" : faqStatus;}
        return "";
    }

    /**
     * Sets the field value if it is available in the given Hashtable
     * @param values - list of fields and their associated values to be set
     */
    public void setFields(QMessage values)
    {
        if(values.containsKey(eo_faq.FIELD_FAQID)) { faqId = values.getInt(eo_faq.FIELD_FAQID);}
        if(values.containsKey(eo_faq.FIELD_FAQQUESTION)) { faqQuestion = values.getString(eo_faq.FIELD_FAQQUESTION);}
        if(values.containsKey(eo_faq.FIELD_FAQANSWER)) { faqAnswer = values.getString(eo_faq.FIELD_FAQANSWER);}
        if(values.containsKey(eo_faq.FIELD_FAQORDER)) { faqOrder = values.getInt(eo_faq.FIELD_FAQORDER);}
        if(values.containsKey(eo_faq.FIELD_FAQCREATED)) { faqCreated = values.getString(eo_faq.FIELD_FAQCREATED);}
        if(values.containsKey(eo_faq.FIELD_FAQMODIFIED)) { faqModified = values.getString(eo_faq.FIELD_FAQMODIFIED);}
        if(values.containsKey(eo_faq.FIELD_FAQSTATUS)) { faqStatus = values.getString(eo_faq.FIELD_FAQSTATUS);}
    }

}

