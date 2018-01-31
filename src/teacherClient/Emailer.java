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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Emailer 
{
	private String recipients[];
	private String password;
	private String username;
	
	public Emailer(String from, String pass, String[] to)
	{
	    username = from;
	    password = pass;
	    recipients = to;
	}
	
	public void sendBarcodeEmail(int month, int day, int period, String room) throws IOException, MessagingException 
	{
		Properties props = System.getProperties();
	    String host = "smtp.gmail.com";
	    
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
	    
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    MimeBodyPart imagePart = new MimeBodyPart();
	    MimeMultipart content = new MimeMultipart();
	    
	    imagePart.attachFile("image.png");
	    
	    message.setFrom(new InternetAddress(username));
	    
        InternetAddress[] toAddress = new InternetAddress[recipients.length];

        for( int x = 0; x < recipients.length; x++ ) 
        {
            toAddress[x] = new InternetAddress(recipients[x]);
        }

        for( int i = 0; i < toAddress.length; i++) 
        {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }

        message.setSubject("CG Mass Pass");
        content.addBodyPart(createTextBody(month,day,period,room));
        content.addBodyPart(imagePart);
        message.setContent(content);
        
        Transport transport = session.getTransport("smtp");
        transport.connect(host, username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
	}
	
	private MimeBodyPart createTextBody(int m, int d, int p, String r) throws MessagingException
	{
		String month = "";
		switch(m)
		{
		case 1:
			month = "January";
		case 2:
			month = "February";
		case 3:
			month = "March";
		case 4:
			month = "April";
		case 5:
			month = "May";
		case 6:
			month = "June";
		case 7:
			month = "July";
		case 8:
			month = "August";
		case 9:
			month = "September";
		case 10:
			month = "October";
		case 11:
			month = "November";
		case 12:
			month = "December";
		}
		
		MimeBodyPart text = new MimeBodyPart();
		text.setText("You have been summoned to the " + r + " during period " + p + "/" + p+5 + " on " + month + ", " + d);
		return text;
	}
	
	private void makeBarcode(int m, int d, int p, String r) throws IOException
	{
		String month = "";
		switch(m)
		{
		case 1:
			month = "Jan";
		case 2:
			month = "Feb";
		case 3:
			month = "Mar";
		case 4:
			month = "Apr";
		case 5:
			month = "May";
		case 6:
			month = "Jun";
		case 7:
			month = "Jul";
		case 8:
			month = "Aug";
		case 9:
			month = "Sep";
		case 10:
			month = "Oct";
		case 11:
			month = "Nov";
		case 12:
			month = "Dec";
		}
		
		String day = Integer.toString(d);
		if(day.length() == 1)
		{
			day = "0" + day;
		}
		String URL = "http://bwipjs-api.metafloor.com/?bcid=code128&text=" + month + day + "." + p + "." + r + "&scaleX=2&includetext";
		String imageUrl = "http://www.barcodes4.me/barcode/c39/" + month + day + r + ".png";
		saveImage(URL, "image.png");
	}
	
	private void saveImage(String imageUrl, String destinationFile) throws IOException 
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
}
