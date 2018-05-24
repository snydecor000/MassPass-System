package studyHallClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Classes 
{
	private ArrayList<Class> classes;
	private File classesFile;
	private Comparator<Class> ClassPeriodComparator = new Comparator<Class>()
	{
		public int compare(Class c1, Class c2)
		{
		   int ClassPer1 = Integer.parseInt(c1.getPeriod());
		   int ClassPer2 = Integer.parseInt(c2.getPeriod());
		   return ClassPer1-ClassPer2;
	    }
	};
	
	public Classes()
	{
		classesFile = new File("classes.csv");
		classes = new ArrayList<Class>();
		if(!classesFile.exists())
		{
			try
			{
				classesFile.createNewFile();
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
				Scanner in = new Scanner(classesFile);
				while(in.hasNextLine())
				{
					String line = in.nextLine();
					String[] splitLine = line.split(",");
					classes.add(new Class(splitLine[0],splitLine[1]));
				}
				//this.sortClasses();
				in.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void addClass(String n, String p)
	{
		classes.add(new Class(n,p));
		saveClasses();
	}
	
	private void saveClasses()
	{
		try
		{
			PrintWriter pw = new PrintWriter(classesFile);
			for (Class c : classes)
			{
				pw.println(c.toString());
			}
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/*public Class getClass(String fullName)
	{
		Class cl = new Class();
		for(Class c : classes)
		{
			if(c.getFullName().equals(fullName))
			{
				cl = c;
			}
		}
		return cl;
	}*/
	
	public Class getClass(String period)
	{
		Class cl = null;
		for(Class c : classes)
		{
			if(c.getPeriod().equals(period))
			{
				cl = c;
			}
		}
		return cl;
	}
	
	private void sortClasses()
	{
		classes.sort(ClassPeriodComparator);
	}
	
	public ArrayList<Class> getClasses()
	{
		return classes;
	}
}
