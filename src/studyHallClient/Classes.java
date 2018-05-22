package studyHallClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Classes 
{
	private ArrayList<Class> classes;
	private File classesFile;
	
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
	
	public ArrayList<Class> getClasses()
	{
		return classes;
	}
}
