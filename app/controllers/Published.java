package controllers;

import java.util.List;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

import models.*;

@With(Secure.class)
public class Published extends Controller {

    @Before
    static void setConnectedUser() {
        Security.setConnectedUser();
    	System.out.println("User is admin: " + Security.isAdmin());
    	System.out.println("User is auditor: " + Security.isAuditor());
    	System.out.println("User is reviewer: " + Security.isReviewer());
    	System.out.println("User is editor: " + Security.isEditor());
    }
    public static void unpublishedShow(){
        /*add user login check*/
        /*add month check for tw0 months to force these to be chosen */
    	List unpublished = Articles.find("published", false).fetch();
        render ("unpublished/index.html", unpublished);
    } 
    
    public static void unpublishedreviewedShow(){
        /**Shows a list of possible unpublished articles that could be published 
         * seen only by editor
         * Needs author to review at three articles first 
         * As well as the article recieving at least three reviews, with their score shown.
         * A button that says publish that adds the article into the next journal.
         */ 
    }
}