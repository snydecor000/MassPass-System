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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class StudyHallMassPass extends Frame implements ActionListener
{
	private JLabel l;
	private JTextField studentID;
	private JLabel l2;
	private JTextField classID;
	private JLabel l3;
	private JComboBox<String> periods;
	private JButton resetStudents;
	private JButton addClass;
	JTabbedPane tabbedPane;
	Classes classes;
	ArrayList<JTable> tables;
	
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
	
	public void updateRow(Class c, DefaultTableModel model, String rowName)
	{
		model.setValueAt(c.getStudent(rowName).getTimeOut(), getRow(model,rowName), 2);
		model.setValueAt(c.getStudent(rowName).getLocation(), getRow(model,rowName), 3);
		model.setValueAt(c.getStudent(rowName).getTimeIn(), getRow(model,rowName), 4);
	}
	
	public int getRow(DefaultTableModel model, String rowName)
	{
		int row = -2;
		for(int x = 0; x < model.getRowCount(); x++)
		{
			if(model.getValueAt(x, 0).equals(rowName))
			{
				row = x;
			}
		}
		return row;
	}
	
	public StudyHallMassPass() throws IOException
	{
		tables = new ArrayList<JTable>();
		for(int x = 1;x < 11;x++)
		{
			DefaultTableModel model = new DefaultTableModel()
		    {
		    	@Override
		    	public boolean isCellEditable(int row, int column)
		    	{
		    		return column == 1;
		    	}
		    };
	        model.addColumn("Student ID");
	        model.addColumn("Name");
	        model.addColumn("Time Out");
	        model.addColumn("Location");
	        model.addColumn("Time In");
	        JTable j = new JTable(model);
		    tables.add(j);
		}
		
		tabbedPane = new JTabbedPane();
		classes = new Classes();
		
		for(Class c : classes.getClasses())
		{
			JPanel j = new JPanel();
			j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
			l3 = new JLabel(c.getName());
			j.add(l3);
			j.add(new JScrollPane(tables.get(Integer.parseInt(c.getPeriod())-1)));
			tabbedPane.add(c.getPeriod(),j);
		}
		
		for(Class c : classes.getClasses())
		{
			c.sortStudents();
			DefaultTableModel model = (DefaultTableModel)tables.get(Integer.parseInt(c.getPeriod())-1).getModel();
			for(Student s : c.getStudents())
			{
				model.addRow(new Object[] {s.getIDNumber(),s.getName(),s.getTimeOut(),s.getLocation(),s.getTimeIn()});
			}
		}
		
		JFrame frame = new JFrame("MassPass");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel inputs = new JPanel();
		inputs.setLayout(new BoxLayout(inputs, BoxLayout.X_AXIS));
		panel.add(inputs);
		
		JPanel textFields = new JPanel();
		textFields.setLayout(new BorderLayout());
		
		JPanel options = new JPanel();
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
	
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
		//bottom.setMaximumSize(new Dimension(300,20));
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
		
		resetStudents = new JButton("Reset Student Information");
		resetStudents.addActionListener(this);
		options.add(resetStudents);
		
		addClass = new JButton("Add New Class");
		addClass.addActionListener(this);
		options.add(addClass);
		
		inputs.add(options);

		panel.add(tabbedPane);
		
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
		StudyHallMassPass m = new StudyHallMassPass();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println(e.getActionCommand());
		if(e.getSource().equals(studentID))
		{
			String per = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
			Class cl = classes.getClass(per);
			Student stu = cl.getStudent(studentID.getText());
			DefaultTableModel model = (DefaultTableModel) tables.get(Integer.parseInt(per)-1).getModel();
			
			if(studentID.getText().matches("^\\d{8}$"))
			{
				if(stu != null)
				{
					stu.giveEmptyPass();
					stu.process(getTime(), per);
					updateRow(cl, model,stu.getIDNumber());
					cl.saveStudents();
					studentID.setText(" ");
					studentID.setText("");
					classID.setText(" ");
					classID.setText("");
					studentID.requestFocus();
				}
			}
		}
		if(e.getSource().equals(classID))
		{
			if(classID.getText().length()>=10)
			{
				if(classID.getText().matches("^\\d{10,11}$") && studentID.getText().matches("^\\d{8}$"))
				{
					String per = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
					Class cl = classes.getClass(per);
					
					if(cl != null)
					{
						Student stu = cl.getStudent(studentID.getText());
						DefaultTableModel model = (DefaultTableModel) tables.get(Integer.parseInt(per)-1).getModel();
						
						if(stu != null)
						{
							if(stu.getLocation().equals("Here"))
							{
								stu.givePass(classID.getText());
								String status = stu.process(getTime(), per);
								if(status.isEmpty()||status.equals("This pass is verified"))
								{
									updateRow(cl, model, studentID.getText());
									cl.saveStudents();
								}
								else
								{
									JOptionPane.showMessageDialog(this,status);
								}
							}
						}
						else
						{
							stu = new Student(studentID.getText(),JOptionPane.showInputDialog("Enter in First and Last Name"));
							cl.addStudent(stu);
							stu.givePass(classID.getText());
							stu.process(getTime(), per);
							cl.saveStudents();
							//it doesnt actually set stu to have proper values??
							model.addRow(new Object[] {studentID.getText(), 
									stu.getName(), getTime(), stu.getLocation(),
									stu.getTimeIn()});
							updateRow(cl,model,studentID.getText());
						}
						studentID.setText(" ");
						studentID.setText("");
						classID.setText(" ");
						classID.setText("");
						studentID.requestFocus();
					}
				}
			}
		}
		if(e.getSource().equals(resetStudents))
		{
			String per = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
			Class cl = classes.getClass(per);
			DefaultTableModel model = (DefaultTableModel) tables.get(Integer.parseInt(per)-1).getModel();
			cl.resetStudentInfo();
			
			for(Student s : cl.getStudents())
			{
				updateRow(cl, model, s.getIDNumber());
			}
			cl.saveStudents();
		}
		if(e.getSource().equals(addClass))
		{
			String className = JOptionPane.showInputDialog("Enter in Class Name");
			Object[] options = new Object[]{"1","2","3","4","5","6","7","8","9","10"};
			int classPeriod = JOptionPane.showOptionDialog(this, "Select a Period", "", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null)+1;
			boolean ok = true;
			for(Class c : classes.getClasses())
			{
				if(c.getPeriod().equals(Integer.toString(classPeriod)))
				{
					ok = false;
				}
			}
			if(ok)
			{
				classes.addClass(className, Integer.toString(classPeriod));
				Class c = classes.getClass(Integer.toString(classPeriod));
				
				JPanel j = new JPanel();
				j.setLayout(new BoxLayout(j, BoxLayout.Y_AXIS));
				l3 = new JLabel(c.getName());
				j.add(l3);
				j.add(new JScrollPane(tables.get(Integer.parseInt(c.getPeriod())-1)));
				tabbedPane.add(c.getPeriod(),j);
				
				DefaultTableModel model = (DefaultTableModel)tables.get(Integer.parseInt(c.getPeriod())-1).getModel();
				for(Student s : c.getStudents())
				{
					model.addRow(new Object[] {s.getIDNumber(),s.getName(),s.getTimeOut(),s.getLocation(),s.getTimeIn()});
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "This Period Already Exists");
			}
		}
	}
}
