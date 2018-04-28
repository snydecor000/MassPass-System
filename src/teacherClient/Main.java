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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.JXDatePicker;

import javafx.scene.control.DatePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

public class Main extends Frame implements ActionListener 
{
	private static String username;
	private static String password;
	private static String recipient;
	
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
	private JXDatePicker date;
	
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
		
		/*monthList = new JComboBox(months);
		monthList.setSelectedIndex(0);
		monthList.addActionListener(this);
		panel.add(monthList);
		
		l2 = new JLabel("Day");
		panel.add(l2);
		
		day = new TextField("0");
		day.setPreferredSize(new Dimension(200,21));
		panel.add(day);*/
		
		date = new JXDatePicker();
		date.setDate(new Date());
		panel.add(date);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(52,20));
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
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, FileNotFoundException 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Scanner in = new Scanner(new File("account.txt"));
		ArrayList<String> account = new ArrayList<String>();
		while (in.hasNextLine())
		{
			account.add(in.nextLine());
		}
		username = account.get(0);
		password = account.get(1);
		Main m = new Main();

	}
	
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
			period.setText(" ");
			period.setText("");
			
		}
		if(e.getSource().equals(b))
		{
			recipient = emailUsername.getText() + "@students.centergrove.k12.in.us";
			Emailer email = new Emailer(username,password,recipient);
			try
			{
				email.sendBarcodeEmail((date.getDate().getMonth()+1), date.getDate().getDate(), 
						Integer.parseInt(period.getText()), classroom.getText());
			} 
			catch (NumberFormatException e1) 
			{
				e1.printStackTrace();
			}
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
			catch (MessagingException e1) 
			{
				e1.printStackTrace();
			}
		}
	}
}

