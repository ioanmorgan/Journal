package controllers;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;

import models.*;
import play.*;
import play.data.Upload;
import play.data.validation.Required;
import play.db.jpa.GenericModel.JPAQuery;
import play.mvc.*;

public class Article extends Controller {

	@Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    }
	
    //TODO change error messages to plays flash messages
	
	/**
	 * renders the index.html page
	 */
    public static void index() {
    	List<Articles> articles = Articles.all().fetch();
    	render(articles);
    }
	
    public static void show(long id) {
    	Articles article = Articles.findById(id);
		Revisions latestRev = getLatestRevision(article);
		if(latestRev.revision_number > 0){
			int previous_revision_number = latestRev.revision_number -1; 
	        render(latestRev, article, previous_revision_number);
		} else {
			render(latestRev, article);
		}
	}
    
    public static Revisions getLatestRevision(Articles article){
		List<Revisions> revisions = Revisions.find("article_ID", article).fetch();

		int highestRevision = 0;
		for(int x = 0; x < revisions.size(); x++){
			Revisions revision = revisions.get(x);
			if(revision.revision_number > highestRevision){
				highestRevision = revision.revision_number;
			}
		}
		Revisions latestRev = Revisions.find("revision_number", highestRevision).first();
    	return latestRev;
    }
    
    public static void showRevision(long id, int revision_num) {
    	Articles article = Articles.findById(id);
		Revisions revision = Revisions.find("revision_number", revision_num).first();
        render("article/show.html", revision, article);
	}
       
    /**
     * For adding new articles.  If no params are given it renders and empty form, if title and article params are given it adds
     * the new article
     * @param title Title of the article 
     * @param tags any associated key words (optional)
     * @param authors any additional authors to give credit too (optional)
     * @param discription Article abstract
     * @param article the file containing the article
     */
    public static void newArticle(@Required String title, String tags, String authors,@Required String discription,@Required Upload article){

    	//TODO - tags and authors, need to be able to parse them and split them up
    	
    	//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
    	String responce = "";
    	if(title == null || uploads.isEmpty()){
    		responce = "Please chouse a title and upload youre article";
    	} else {
        	if(File_managment.upload(uploads, "public\\files\\unpublished\\")){
        		Date date = new Date();
        		String url = "public\\files\\unpublished\\" + uploads.get(0).getFileName();
        		
        		Users user = Security.getConnectedUser();
          		
        		Articles art = new Articles(user , title, null);  
        		Revisions rev = new Revisions((int) (Revisions.count()+1), art, date, discription, 1, url);   
        		//Save the article and the revision
        		rev.save();
        		art.save();
        		
        		responce = title + " was sucessfully uploaded";
        	}else{
        		responce = "There was an issue uploading youre article, please try again later.";
        	}
    	}
		render("article/new.html", responce);
    }
      
    /**
     * Edits articles
     * @param article the article to edit
     */
    public static void edit(@Required long id){
    	if(id == 0){
    		String responce = "No article was selected to edit, would you like to make one?";
    		render("article/new.html", responce);
    	} else {
    		String responce = " ";
    		Articles article = Articles.findById(id);
    		Revisions revision = getLatestRevision(article);
    		render(article, revision, responce);
    	}
    }
    
    public static void submitEdit(@Required String id, @Required String title, String tags, String authors,@Required String discription,@Required Upload article){
    	//TODO - tags and authors, need to be able to parse them and split them up
    	
    	//get the uploaded file parts and check a title is given
    	List<Upload> uploads  = (List<Upload>) request.current().args.get("__UPLOADS");
    	String responce = "";
    	if(title == null || uploads.isEmpty()){
    		responce = "Please chouse a title and upload youre article";
    	} else {
        	if(File_managment.upload(uploads, "public\\files\\unpublished\\")){
        		Date date = new Date();
        		String url = "public\\files\\unpublished\\" + uploads.get(0).getFileName();
        		
        		Users user = Security.getConnectedUser();
          		
        		Articles art = new Articles(user , title, null);  
        		Revisions rev = new Revisions((int) (Revisions.count()+1), art, date, discription, 1, url);   
        		//Save the article and the revision
        		rev.save();
        		art.save();
        		
        		responce = title + " was sucessfully uploaded";
        	}else{
        		responce = "There was an issue uploading youre article, please try again later.";
        	}
    	}
		render("article/new.html", responce);
    }
   
    
    //TODO - basic html file already made, 
    /**
     * deletes articles
     * @param id the id of the article to delete
     * @param article the path to the article to delete
     */
    public static void delete(String id, String article){
    	int article_ID = Integer.getInteger(id);
    	Articles.delete("article_ID", article_ID);
		File_managment.delete(article);
		render();
    }
 
}
