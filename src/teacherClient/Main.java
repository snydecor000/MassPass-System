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
	/*private static String USER_NAME = "cghsmasspass";
	private static String PASSWORD = "MP#1";
	private static String RECIPIENT = "";*/
	
	private JLabel l;
	private JComboBox monthList;
	private TextField txt2;
	private TextField txt;
	private JLabel l2;
	private JLabel l3;
	private TextField txt3;
	private JLabel l4;
	private JButton b;
	private JButton b2;
	private JLabel l5;
	private JLabel l6;
	private TextField txt4;

	
	private JButton qb;
	private JLabel ql;
	private JLabel ql2;
	private JLabel ql3;
	private JLabel ql4;
	private JButton rb;
	private JLabel rl;
	private JLabel rl2;
	private JLabel rl3;
	private JLabel rl4;
	private TextField rtxt;
	private TextField rtxt2;
	
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
		
		txt = new TextField("0");
		txt.setPreferredSize(new Dimension(200,21));
		panel.add(txt);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(42,20));
		panel.add(l6);
		
		txt2 = new TextField("");
		txt2.setPreferredSize(new Dimension(70,21));
		panel.add(txt2);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(125,20));
		panel.add(l6);
		
		txt4 = new TextField("snydecor000");
		txt4.setPreferredSize(new Dimension(30,21));
		panel.add(txt4);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(600,20));
		panel.add(l6);
		
		l4 = new JLabel("Classroom # or Name");
		l4.setPreferredSize(new Dimension(200,20));
		panel.add(l4);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(400,50));
		panel.add(l6);
		
		txt3 = new TextField("");
		txt3.setPreferredSize(new Dimension(30,21));
		panel.add(txt3);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(100,30));
		panel.add(l6);
		
		b = new JButton("Send Pass");
		b.addActionListener(this);
		panel.add(b);
		
		b2 = new JButton("Reset");
		b2.addActionListener(this);
		panel.add(b2);
		
		/*l3 = new JLabel("Pounds: ");
		panel.add(l3);
		
		l4 = new JLabel("");
		panel.add(l4);
		
		b = new JButton("Buddon");
		b.addActionListener(this);
		panel.add(b);
		
		b2 = new JButton("Exit");
		b2.addActionListener(this);
		panel.add(b2);
		
		ql = new JLabel("Dollars to Zars");
		ql.setPreferredSize(new Dimension(200,50));
		panel.add(ql);
		
		ql2 = new JLabel("Dollars: ");
		panel.add(ql2);
		
		qtxt = new TextField("$$$$");
		qtxt.setPreferredSize(new Dimension(200,50));
		panel.add(qtxt);
		
		ql3 = new JLabel("Zars: ");
		panel.add(ql3);
		
		ql4 = new JLabel("");
		panel.add(ql4);
		
		qb = new JButton("Buddon");
		qb.addActionListener(this);
		panel.add(qb);
		
		rl = new JLabel("Salary: ");
		panel.add(rl);
		
		rtxt = new TextField("$$$$");
		rtxt.setPreferredSize(new Dimension(200,50));
		panel.add(rtxt);
		
		rl2 = new JLabel("Games: ");
		panel.add(rl2);
		
		rtxt2 = new TextField("#Games");
		rtxt2.setPreferredSize(new Dimension(200,50));
		panel.add(rtxt2);
		
		rb = new JButton("Buddon");
		rb.addActionListener(this);
		panel.add(rb);
		
		rl3 = new JLabel("$ Per Game: ");
		panel.add(rl3);*/
		
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
		if(e.getSource() == b2)
		{
			String empty = "";
			monthList.setSelectedIndex(0);
			txt.setText("0");
			txt2.setText(empty);
			txt4.setText("snydecor000");
			txt3.setText(empty);
			//System.exit(0);
		}
		if(e.getSource() == b)
		{
			
		}
	}
}

