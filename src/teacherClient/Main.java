/**
 * Cory Snyder
**/
package teacherClient;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/*import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;*/

public class Main extends Frame implements ActionListener 
{
	private static String USER_NAME = "masspasscghs";
	private static String PASSWORD = "MP#1";
	private static String RECIPIENT;
	
	private JLabel l;
	private JComboBox monthList;
	private TextField studentID;
	private TextField day;
	private JLabel l2;
	private JLabel l3;
	private TextField classroom;
	private JLabel l4;
	private JButton b;
	private JButton b2;
	private JLabel l5;
	private JLabel l6;
	private TextField emailUsername;
	private TextField period;
	private JLabel l7;
	
	private String[] months = {"(Month)","January","February","March","April",
							"May","June","July","August","September",
							"October","November","December"};
	
	public Main()
	{
		JFrame frame = new JFrame("MassPass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		l = new JLabel("Date of Pass");
		l.setPreferredSize(new Dimension(200,30));
		panel.add(l);
		
		l3 = new JLabel("Student ID #");
		l3.setPreferredSize(new Dimension(200,30));
		panel.add(l3);
		
		l5 = new JLabel("Student Email Username");
		l5.setPreferredSize(new Dimension(200,20));
		panel.add(l5);
		
		monthList = new JComboBox(months);
		monthList.setSelectedIndex(0);
		monthList.addActionListener(this);
		panel.add(monthList);
		
		l2 = new JLabel("Day");
		panel.add(l2);
		
		day = new TextField("0");
		day.setPreferredSize(new Dimension(200,21));
		panel.add(day);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(42,20));
		panel.add(l6);
		
		studentID = new TextField("");
		studentID.setPreferredSize(new Dimension(70,21));
		panel.add(studentID);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(125,20));
		panel.add(l6);
		
		emailUsername = new TextField("");
		emailUsername.setPreferredSize(new Dimension(100,21));
		panel.add(emailUsername);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(600,20));
		panel.add(l6);
		
		l4 = new JLabel("Classroom # or Name");
		l4.setPreferredSize(new Dimension(200,20));
		panel.add(l4);
		
		l7 = new JLabel("Period");
		l7.setPreferredSize(new Dimension(200,20));
		panel.add(l7);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(200,20));
		panel.add(l6);
		
		classroom = new TextField("");
		classroom.setPreferredSize(new Dimension(50,21));
		panel.add(classroom);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(150,20));
		panel.add(l6);
		
		period = new TextField("");
		period.setPreferredSize(new Dimension(20,21));
		panel.add(period);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(100,20));
		panel.add(l6);
		
		b = new JButton("Send Pass");
		b.addActionListener(this);
		panel.add(b);
		
		b2 = new JButton("Reset");
		b2.addActionListener(this);
		panel.add(b2);
		
		panel.setPreferredSize(new Dimension(625,500));
		Container cp = frame.getContentPane();
		cp.add(panel, BorderLayout.LINE_START);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) 
	{
		Main m = new Main();
	}
	/*}
	    String from = USER_NAME;
	    String pass = PASSWORD;
	    String[] to = { RECIPIENT };

	    Emailer e = new Emailer(from,pass,to);
	    
	    e.sendBarcodeEmail(month, day, period, room);
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(b2))
		{
			monthList.setSelectedIndex(0);
			day.setText("0");
			studentID.setText(" ");
			studentID.setText("");
			emailUsername.setText(" ");
			emailUsername.setText("");
			classroom.setText(" ");
			classroom.setText("");
			
		}
		if(e.getSource().equals(b))
		{
			RECIPIENT = emailUsername.getText() + "@students.centergrove.k12.in.us";
		    Emailer email = new Emailer(USER_NAME,PASSWORD,RECIPIENT);
		    try {
				email.sendBarcodeEmail(monthList.getSelectedIndex(), Integer.parseInt(day.getText()), 
						Integer.parseInt(period.getText()), classroom.getText());
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (MessagingException e1) {
				e1.printStackTrace();
			}
		}
	}
}

