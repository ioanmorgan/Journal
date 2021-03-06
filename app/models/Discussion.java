package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Discussion extends Model {

    public int discussion_ID;
    public Date comment_Date;    
    public String comment;     
    
    @ManyToOne
    public Reviews recievers_ID;
    public Reviews senders_ID;
    
    @ManyToOne
    public Articles article_ID;
    
    
    
    public Discussion(int discussion_ID, Reviews reviewers_ID, Articles article_ID, Date comment_Date, String comment) {
        this.discussion_ID= discussion_ID;
        this.recievers_ID = reviewers_ID;
        this.senders_ID = reviewers_ID;
        this.article_ID = article_ID;
        this.comment_Date = comment_Date;
        this.comment = comment;
        
    }
 
}
