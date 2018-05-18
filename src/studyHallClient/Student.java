package studyHallClient;

public class Student 
{
	private String studentID;
	private String name;
	private String location;
	private String timeIn;
	private String timeOut;
	
	public Student(String id, String n, String loc, String tIn, String tOut)
	{
		studentID = id;
		name = n;
		location = loc;
		timeIn = tIn;
		timeOut = tOut;
	}
	
	public Student()
	{
		studentID = "";
		name = "";
		location = "";
		timeIn = "";
		timeOut = "";
	}
	
	public Student(String id, String n)
	{
		studentID = id;
		name = n;
		location = "";
		timeIn = "";
		timeOut = "";
	}
	
	public String getIDNumber()
	{
		return studentID;
	}
	
	public void setIDNumber(String id)
	{
		studentID = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public String getTimeOut()
	{
		return timeOut;
	}
	
	public void setTimeOut(String tOut)
	{
		timeOut = tOut;
	}
	
	public String getTimeIn()
	{
		return timeIn;
	}
	
	public void setTimeIn(String tIn)
	{
		timeIn = tIn;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String loc)
	{
		location = loc;
	}
}
