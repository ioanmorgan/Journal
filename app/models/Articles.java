package models;

import java.util.Date;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
 
@Entity
public class Articles extends Model {

    public String title;
	public Boolean published;
    
    @ManyToOne
    public Users user;
	
	@ManyToMany(mappedBy="articles") 
	public List<Tag> tags;
    
    public Articles(Users user, String title, List<Tag> tags){
        this.user = user;
        this.title = title;
		this.tags = tags;
    }
 
}


