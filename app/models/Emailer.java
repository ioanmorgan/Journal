package models;

import java.io.File;
import java.net.URL;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import play.db.jpa.Model;
import play.libs.Mail;

public class Emailer  extends Model {

	public static void sendNewsletterTo(String emailAddress, File fileURL) throws EmailException{	
		HtmlEmail email = new HtmlEmail();
		email.addTo(emailAddress);
		email.setFrom("aca08iam@sheffield.ac.uk", "User");
		email.setSubject("Journal Newsletter");
		// embed content
		String cid = email.embed(fileURL);
		// set the html message
		email.setHtmlMsg("<html>Zenexity logo - <img src=\"cid:"+cid+"\"></html>");
		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages, too bad :(");
		Mail.send(email); 
	}
}
