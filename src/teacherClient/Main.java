package teacherClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Main
{
	
	private static String USER_NAME = "";
	private static String PASSWORD = "";
	private static String RECIPIENT = "";
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException 
	{
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);
	
	    byte[] b = new byte[2048];
	    int length;
	
	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }
	
	    is.close();
	    os.close();
	}
	
	public static void main(String[] args) 
	{
	    String from = USER_NAME;
	    String pass = PASSWORD;
	    String[] to = { RECIPIENT };
	    String subject = "MassPass";
	    String imageUrl = "http://www.barcodes4.me/barcode/c39/Jan18552.png";
	    String destinationFile = "image.png";

	    try 
	    {
	    	saveImage(imageUrl, destinationFile);
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    }
	    
	    sendFromGMail(from, pass, to, subject);
	}
	
	private static void sendFromGMail(String from, String pass, String[] to, String subject) 
	{
	    Properties props = System.getProperties();
	    String host = "smtp.gmail.com";
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", from);
	    props.put("mail.smtp.password", pass);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    MimeBodyPart imagePart = new MimeBodyPart();
	    MimeMultipart content = new MimeMultipart();
	    
	    try 
	    {
	    	imagePart.attachFile("image.png");
	    } 
	    catch (IOException e) 
	    {
	    	e.printStackTrace();
	    } 
	    catch (MessagingException e) 
	    {
	    	e.printStackTrace();
	    }
	    
	    try 
	    {
	        message.setFrom(new InternetAddress(from));
	        InternetAddress[] toAddress = new InternetAddress[to.length];
	
	        // To get the array of addresses
	        for( int i = 0; i < to.length; i++ ) 
	        {
	            toAddress[i] = new InternetAddress(to[i]);
	        }
	
	        for( int i = 0; i < toAddress.length; i++) 
	        {
	            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	        }
	
	        message.setSubject(subject);
	        content.addBodyPart(imagePart);
	        message.setContent(content);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect(host, from, pass);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (AddressException ae) 
	    {
	        ae.printStackTrace();
	    }
	    catch (MessagingException me) 
	    {
	        me.printStackTrace();
	    }
	}
}