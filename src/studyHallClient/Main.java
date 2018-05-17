package studyHallClient;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class Main extends Frame implements ActionListener
{
	private JFrame frame;
	private JLabel l;
	private JTextField studentID;
	private JLabel l2;
	private JTextField classID;
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
					students.put(line.substring(0, line.indexOf('#')), line.substring(line.indexOf('#')+1));
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
	
	private boolean verifyPass(String k) 
	{
		DateTimeFormatter currentDayF = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter currentMonthF = DateTimeFormatter.ofPattern("MM");
		LocalDate localDate = LocalDate.now();
		String currentDayS = currentDayF.format(localDate);
		String currentMonthS = currentMonthF.format(localDate);
		
		int key = Integer.parseInt(currentDayS+currentMonthS);
		
		key = key - Integer.parseInt(currentDayS);
		
		return (Integer.parseInt(k)==key);
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
			writer.println(model.getValueAt(x, 0)+"#"+model.getValueAt(x, 1));
		}
		writer.close();
	}
	
	public Main() throws IOException
	{
		students = getStudentList();
		
		JFrame frame = new JFrame("MassPass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		Box top = Box.createHorizontalBox();
		top.setMaximumSize(new Dimension(300,20));
		
		l = new JLabel("Student ID:  ");
		top.add(l);
		
		studentID = new JTextField();
		studentID.setText("");
		studentID.addActionListener(this);
		top.add(studentID);
		
		Box middle = Box.createHorizontalBox();
		middle.setMaximumSize(new Dimension(300,20));
		
		l2 = new JLabel("Pass Info:  ");
		middle.add(l2);
		
		classID = new JTextField();
		classID.setPreferredSize(new Dimension(35,20));
		classID.addActionListener(this);
		middle.add(classID);
		
		panel.add(top);
		panel.add(middle);
		

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
		
		panel.setPreferredSize(new Dimension(600,200));
		Container cp = frame.getContentPane();
		cp.add(panel, BorderLayout.LINE_START);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, 
	InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		Main m = new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getSource().equals(studentID))
		{
			if(e.getActionCommand().length()>=8)
			{
				classID.requestFocus();
			}
		}
		if(e.getSource().equals(classID))
		{
			if(e.getActionCommand().length()>=10)
			{
				if(verifyPass(classID.getText().substring(8,12)))
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
								JOptionPane.showInputDialog("Enter in First and Last Name"), getTime(), classID.getText().substring(5, 10)});
					}
					System.out.println("VERIFIED");
					studentID.setText(" ");
					studentID.setText("");
					classID.setText(" ");
					classID.setText("");
					
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
					studentID.setText(" ");
					studentID.setText("");
					classID.setText(" ");
					classID.setText("");
					JOptionPane.showMessageDialog(this , "Incorrect Inputs");
				}
			}
		}
	}
}
