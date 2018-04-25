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
	private String recipients;
	private String password;
	private String username;
	
	public Emailer(String from, String pass, String to)
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
		
		InternetAddress toAddress = new InternetAddress();
		toAddress = new InternetAddress(recipients);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		
		makeBarcode(month,day,period,room);
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
			break;
		case 2:
			month = "February";
			break;
		case 3:
			month = "March";
			break;
		case 4:
			month = "April";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "July";
			break;
		case 8:
			month = "August";
			break;
		case 9:
			month = "September";
			break;
		case 10:
			month = "October";
			break;
		case 11:
			month = "November";
			break;
		case 12:
			month = "December";
			break;
		}
		
		MimeBodyPart text = new MimeBodyPart();
		text.setText("You have been summoned to " + r + " during period " + p + "/" + (p+5) + " on " + month + ", " + d);
		return text;
	}
	
	private void makeBarcode(int m, int d, int p, String r) throws IOException
	{
		String month = "";
		switch(m)
		{
		case 1:
			month = "Jan";
			break;
		case 2:
			month = "Feb";
			break;
		case 3:
			month = "Mar";
			break;
		case 4:
			month = "Apr";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "Jun";
			break;
		case 7:
			month = "Jul";
			break;
		case 8:
			month = "Aug";
			break;
		case 9:
			month = "Sep";
			break;
		case 10:
			month = "Oct";
			break;
		case 11:
			month = "Nov";
			break;
		case 12:
			month = "Dec";
			break;
		}
		
		String day = Integer.toString(d);
		if(day.length() == 1)
		{
			day = "0" + day;
		}
		//String URL = "http://bwipjs-api.metafloor.com/?bcid=code128&text=" + month + day + "." + p + "." + r + "&scaleX=2&includetext";
		String imageUrl = "http://www.barcodes4.me/barcode/c39/" + month + day + r + ".png";
		saveImage(imageUrl, "image.png");
	}
	
	private void saveImage(String imageUrl, String destinationFile) throws IOException 
	{
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);
		System.out.println("Ow");
	
		byte[] b = new byte[2048];
		int length;
	
		while ((length = is.read(b)) != -1)
		{
			os.write(b, 0, length);
		}
		
		is.close();
		os.close();
	}
}
