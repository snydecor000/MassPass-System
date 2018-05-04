/**
 * Cory Snyder
**/
package teacherClient;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.mail.MessagingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
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
import java.net.URISyntaxException;
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
	private JTextPane instructions;
	private JLabel l8;
	
	private String[] months = {"(Month)","January","February","March","April",
							"May","June","July","August","September",
							"October","November","December"};
	
	public Main()
	{
		JFrame frame = new JFrame("MassPass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		l = new JLabel("Date of Pass");
		l.setPreferredSize(new Dimension(125,30));
		panel.add(l);
		
		l5 = new JLabel("Student Email Username");
		l5.setPreferredSize(new Dimension(150,20));
		panel.add(l5);
		
		l4 = new JLabel("Classroom # or Name");
		l4.setPreferredSize(new Dimension(125,20));
		panel.add(l4);
		
		l7 = new JLabel("Period");
		l7.setPreferredSize(new Dimension(50,20));
		panel.add(l7);
		
		date = new JXDatePicker();
		date.setDate(new Date());
		panel.add(date);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(31-date.getWidth(),20));
		panel.add(l6);
		
		emailUsername = new TextField("");
		emailUsername.setPreferredSize(new Dimension(100,21));
		panel.add(emailUsername);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(60,20));
		panel.add(l6);
		
		classroom = new TextField("");
		classroom.setPreferredSize(new Dimension(50,21));
		panel.add(classroom);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(50,20));
		panel.add(l6);
		
		period = new TextField("");
		period.setPreferredSize(new Dimension(20,21));
		panel.add(period);
		
		b = new JButton("Send Pass");
		b.addActionListener(this);
		panel.add(b);
		
		b2 = new JButton("Reset");
		b2.addActionListener(this);
		panel.add(b2);
		
		l6 = new JLabel("");
		l6.setPreferredSize(new Dimension(250,20));
		panel.add(l6);
		
		l8 = new JLabel("Instructions:");
		Font font = new Font("Courier", Font.BOLD,12);
		l8.setFont(font);
		l8.setPreferredSize(new Dimension(475,20));
		panel.add(l8);
		
		instructions = new JTextPane();
		instructions.setEditable(false);
		instructions.setText("1)Select the date the pass will be used on\n"
				+ "2)Enter in the student's email username (Ex. snydecor000)\n"
				+ "3)Enter in the classroom number the student will travel to\n"
				+ "*See table below if location does not have a number*\n"
				+ "4)Enter in the period number the pass will be valid (1-5)\n"
				+ "5)Confirm the information and then click the 'Send Pass' button\n"
				+ "6)The 'Reset' button clears out all text fields\n"
				+ "\n"
				+ "\n"
				+ "Attendance Office = 900\n"
				+ "Guidance Office = 901\n"
				+ "Main Office = 902\n"
				+ "Deans Office = 903\n"
				+ "Athletic Office = 904\n"
				+ "Media Center = 905\n"
				+ "Music Practice Rooms = 906\n"
				+ "West Gym = 907\n"
				+ "East Gym = 908\n"
				+ "Student Activity Center = 909\n");
		panel.add(instructions);
		
		panel.setPreferredSize(new Dimension(500,500));
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
			date.setDate(new Date());
			emailUsername.setText(" ");
			emailUsername.setText("");
			classroom.setText(" ");
			classroom.setText("");
			period.setText(" ");
			period.setText("");
			
		}
		if(e.getSource().equals(b))
		{
			if(!(emailUsername.getText().isEmpty()) && 
					!(period.getText().isEmpty()) && 
					!(classroom.getText().isEmpty()))
			{
				recipient = emailUsername.getText() + "@students.centergrove.k12.in.us";
				Emailer email = new Emailer(username,password,recipient);
				try
				{
					email.sendBarcodeEmail((date.getDate().getMonth()+1), date.getDate().getDate(), 
							Integer.parseInt(period.getText()), classroom.getText());
				}
				catch (NumberFormatException | IOException | MessagingException e1)
				{
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "There was an error");
					try
					{
						restartApplication();
					}
					catch (URISyntaxException | IOException e2)
					{
						e2.printStackTrace();
					}
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Incorrect Inputs");
			}
		}
	}
	
	public void restartApplication() throws URISyntaxException, IOException
	{
	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
	  final File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

	  /* is it a jar file? */
	  if(!currentJar.getName().endsWith(".jar"))
	    return;

	  /* Build command: java -jar application.jar */
	  final ArrayList<String> command = new ArrayList<String>();
	  command.add(javaBin);
	  command.add("-jar");
	  command.add(currentJar.getPath());

	  final ProcessBuilder builder = new ProcessBuilder(command);
	  builder.start();
	  System.exit(0);
	}
}

