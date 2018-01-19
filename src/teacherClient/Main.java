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
	private static String USER_NAME = "cghsmasspass";
	private static String PASSWORD = "MP#1";
	private static String RECIPIENT = "";
	
	public static void main(String[] args) 
	{
	    String from = USER_NAME;
	    String pass = PASSWORD;
	    String[] to = { RECIPIENT };

	    Emailer e = new Emailer(from,pass,to);
	    
	    e.sendBarcodeEmail(month, day, period, room);
	}
}