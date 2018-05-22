package studyHallClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Class 
{
	private ArrayList<Student> students;
	private String name;
	private String period;
	private File classFile;
	
	public Class()
	{
		name = "";
		period = "";
	}
	
	public Class(String n, String p)
	{
		name = n;
		period = p;
		classFile = new File(name+"_"+period+".csv");
		students = new ArrayList<Student>();
		if(!classFile.exists())
		{
			try
			{
				classFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				Scanner in = new Scanner(classFile);
				while(in.hasNextLine())
				{
					String line = in.nextLine();
					Student s = new Student();
					String[] splitLine = line.split(",");
					if(splitLine.length > 0)
					{
						s.setIDNumber(splitLine[0]);
						if(splitLine.length > 1)
						{
							s.setName(splitLine[1]);
							if(splitLine.length > 2)
							{
								s.setTimeOut(splitLine[2]);
								if(splitLine.length > 3)
								{
									s.setLocation(splitLine[3]);
									if(splitLine.length > 4)
									{
										s.setTimeIn(splitLine[4]);
									}
								}
							}
						}
						students.add(s);
					}
				}
				in.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Student> getStudents()
	{
		return students;
	}
	
	public void addStudent(Student s)
	{
		students.add(s);
		saveStudents();
	}
	
	public void saveStudents()
	{
		try
		{
			PrintWriter pw = new PrintWriter(classFile);
			for (Student s : students)
			{
				pw.println(s.toString());
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public Student getStudent(String idNumber)
	{
		Student student = null;
		for(Student s : students)
		{
			if(s.getIDNumber().equals(idNumber))
			{
				student = s;
			}
		}
		return student;
	}
	
	public String getPeriod()
	{
		return period;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFullName()
	{
		return (name + "_" + period);
	}
	
	public String toString()
	{
		return name + "," + period + ",";
	}
}
