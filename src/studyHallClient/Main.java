package studyHallClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Main extends Frame implements ActionListener
{
	private JFrame frame;
	private JLabel l;
	private JTextField studentID;
	private JLabel l2;
	private JTextField classID;
	private JLabel l3;
	private JComboBox<String> periods;
	private LinkedHashMap<String,String> students;
    DefaultTableModel model = new DefaultTableModel()
    {
    	@Override
    	public boolean isCellEditable(int row, int column)
    	{
    		return column == 1;
    	}
    };
	
	public static LinkedHashMap<String, String> getStudentList() throws IOException
	{
		LinkedHashMap<String,String> students = new LinkedHashMap<String,String>();
		File file = new File("students.txt");
		if(file.exists())
		{
			Scanner in = new Scanner(file);
			while(in.hasNextLine())
			{
				String line = in.nextLine();
				if(!line.isEmpty())
				{
					students.put(line.substring(0, line.indexOf('#')), 
							line.substring(line.indexOf('#')+1));
				}
			}
			in.close();
			return students;
		}
		else
		{
			file.createNewFile();
			return new LinkedHashMap<String,String>();
		}
	}
	
	private String verifyPass(String barcode)
	{
		String k = barcode.substring(8);
		DateTimeFormatter currentDayF = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter currentMonthF = DateTimeFormatter.ofPattern("MM");
		LocalDate localDate = LocalDate.now();
		String currentDayS = currentDayF.format(localDate);
		String currentMonthS = currentMonthF.format(localDate);
		
		int key = Integer.parseInt(currentDayS+currentMonthS);
		key = key - Integer.parseInt(currentDayS);
		
		byte[] bytesOfMessage = ByteBuffer.allocate(4).putInt(key).array();
		MessageDigest md;
		byte[] thedigest = {0,0,0,0};
		try
		{
			md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);
		}
		catch (NoSuchAlgorithmException e)
		{
			JOptionPane.showMessageDialog(frame, "this went very wrong");
			System.exit(0);
		}
		
		
		if(Integer.parseInt(k)!=thedigest[0])
		{
			return "Pass can only be used on the designated day";
		}
		else if(!periods.getSelectedItem().toString().equals(Character.toString(barcode.charAt(7))))
		{
			return "Pass can only be used on the designated period";
		}
		else
		{
			return "";
		}
	}
	
	public int getRow(DefaultTableModel m, String rowName)
	{
		int row = -1;
		for(int x = 0; x < model.getRowCount(); x++)
		{
			if(model.getValueAt(x, 0).equals(rowName))
			{
				row = x;
			}
		}
		return row;
	}
	
	public String getDay()
	{
		DateTimeFormatter currentDayF = DateTimeFormatter.ofPattern("dd");
		LocalDate localDate = LocalDate.now();
		return(currentDayF.format(localDate));
	}
	
	public String getMonth()
	{
		DateTimeFormatter currentMonthF = DateTimeFormatter.ofPattern("MM");
		LocalDate localDate = LocalDate.now();
		return (currentMonthF.format(localDate));
	}
	
	public String getTime()
	{
		DateTimeFormatter currentTimeF = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime localDateTime = LocalDateTime.now();
		return (currentTimeF.format(localDateTime));
	}
	
	public void saveStudents() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("students.txt", "UTF-8");
		for(int x = 0; x < model.getRowCount();x++)
		{
			writer.println(model.getValueAt(x, 0)+"#"+model.getValueAt(x, 1)+"#"+model.getValueAt(x, 2)+"#"+model.getValueAt(x, 3)+"#"+model.getValueAt(x, 4));
		}
		writer.close();
	}
	
	public Main() throws IOException
	{
		students = getStudentList();
		
		JFrame frame = new JFrame("MassPass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel inputs = new JPanel();
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.X_AXIS));
		panel.add(inputs);
		
		JPanel textFields = new JPanel();
		textFields.setLayout(new BorderLayout());
	
		GridBagConstraints c;
		JPanel top = new JPanel();
		top.setLayout(new GridBagLayout());
		//top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		
		l = new JLabel("Student ID:  ");
		top.add(l);
		studentID = new JTextField();
		studentID.setText("");
		studentID.addActionListener(this);
		//studentID.setPreferredSize(new Dimension(200,20));
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		top.add(studentID, c);
		textFields.add(top, BorderLayout.NORTH);

		JPanel bottom = new JPanel();
		bottom.setLayout(new GridBagLayout());
		//bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
//		bottom.setMaximumSize(new Dimension(300,20));
		l2 = new JLabel("Pass Info:  ");
		bottom.add(l2);
		classID = new JTextField();
		//classID.setPreferredSize(new Dimension(35,20));
		classID.addActionListener(this);
		//classID.setPreferredSize(new Dimension(200,20));
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		bottom.add(classID, c);
		textFields.add(bottom, BorderLayout.SOUTH);

		inputs.add(textFields);
		
		JPanel options = new JPanel();
		l3 = new JLabel("Current Period:  ");
		options.add(l3);
		periods = new JComboBox<String>();
		periods.addItem("1");
		periods.addItem("2");
		periods.addItem("3");
		periods.addItem("4");
		periods.addItem("5");
		options.add(periods);
		inputs.add(options);

        JTable table = new JTable(model);
        table.removeEditor();
        
        model.addColumn("Student ID");
        model.addColumn("Name");
        model.addColumn("Time Out");
        model.addColumn("Location");
        model.addColumn("Time In");
        
        for(String idNum : students.keySet())
        {
        	model.addRow(new Object[] {idNum, students.get(idNum)});
        }
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table);
        panel.add(pane);
		
		//panel.setPreferredSize(new Dimension(600,200));
		Container cp = frame.getContentPane();
		cp.add(panel, BorderLayout.LINE_START);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, 
	InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Main m = new Main();
		Pass p = new Pass("05182341125");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getSource().equals(studentID))
		{
			if(e.getActionCommand().length()>=8)
			{
				if(getRow(model,studentID.getText()) != -1)
				{
					if(!model.getValueAt(getRow(model,studentID.getText()), 2).toString().isEmpty())
					{
						model.setValueAt(getTime(), getRow(model,studentID.getText()), 4);
						studentID.setText(" ");
						studentID.setText("");
						classID.setText(" ");
						classID.setText("");
					}
				}
			}
		}
		if(e.getSource().equals(classID))
		{
			if(e.getActionCommand().length()>=10)
			{
				if(verifyPass(classID.getText()).isEmpty())
				{
					if(getRow(model,studentID.getText()) != -1)
					{
						System.out.println(model.getValueAt(getRow(model,studentID.getText()), 1).toString());
						if(model.getValueAt(getRow(model,studentID.getText()), 1).toString().isEmpty())
						{
							model.setValueAt(JOptionPane.showInputDialog("Enter in First and Last Name"), 
									getRow(model,studentID.getText()), 1);
						}
						else
						{
							model.setValueAt((getTime()), getRow(model,studentID.getText()), 2);
						}
						model.setValueAt(classID.getText().substring(4,7), getRow(model,studentID.getText()), 3);
					}
					else
					{
						model.addRow(new Object[] {studentID.getText(), 
								JOptionPane.showInputDialog("Enter in First and Last Name"), 
								getTime(), classID.getText().substring(4, 7)});
					}
					System.out.println("VERIFIED");
					studentID.setText(" ");
					studentID.setText("");
					classID.setText(" ");
					classID.setText("");
					studentID.requestFocus();
					try
					{
						saveStudents();
					}
					catch (FileNotFoundException | UnsupportedEncodingException e1)
					{
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(this , (verifyPass(classID.getText())));
					studentID.setText(" ");
					studentID.setText("");
					classID.setText(" ");
					classID.setText("");
				}
			}
		}
	}
}
