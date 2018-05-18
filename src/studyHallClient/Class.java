package studyHallClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Class 
{
	private ArrayList<Student> students;
	private String name;
	public String period;
	
	public Class(String name, String period)
	{
		File classFile = new File(name+"_"+period+".txt");
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
			students = new ArrayList<Student>();
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
}
