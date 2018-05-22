package studyHallClient;

public class Student 
{
	protected enum StudentStatus
	{
		CheckedIn, CheckedOut;
	}
	private StudentStatus status;
	private String studentID;
	private String name;
	private String location;
	private String timeIn;
	private String timeOut;
	private Pass pass;
	
	public Student(String id, String n, String loc, String tIn, String tOut)
	{
		studentID = id;
		name = n;
		location = loc;
		timeIn = tIn;
		timeOut = tOut;
		if(timeOut.isEmpty() && location.isEmpty())
		{
			status = StudentStatus.CheckedIn;
		}
		else
		{
			status = StudentStatus.CheckedOut;
		}
	}
	
	public Student()
	{
		studentID = "";
		name = "";
		location = "";
		timeIn = "";
		timeOut = "";
		status = StudentStatus.CheckedIn;
	}
	
	public Student(String id, String n)
	{
		studentID = id;
		name = n;
		location = "";
		timeIn = "";
		timeOut = "";
		status = StudentStatus.CheckedIn;
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
		if(status.equals(StudentStatus.CheckedIn))
		{
			return "Here";
		}
		else
		{
			return location;
		}
	}
	
	public void setLocation(String loc)
	{
		location = loc;
	}
	
	public void givePass(String barcode)
	{
		pass = new Pass(barcode);
	}
	
	public void giveEmptyPass()
	{
		pass = null;
	}
	
 	public void process(String time, String period)
	{
		if(pass != null)
		{
			if(status.equals(StudentStatus.CheckedIn) && location.isEmpty() && timeOut.isEmpty() && (pass.verifyPass(period)))
			{
				status = StudentStatus.CheckedOut;
				this.setLocation(pass.getLocation());
				this.setTimeOut(time);
			}
			else if(status.equals(StudentStatus.CheckedIn) && (timeIn.isEmpty()) && (location.isEmpty()) && (timeOut.isEmpty()))
			{
				status = StudentStatus.CheckedOut;
				this.setLocation(pass.getLocation());
				this.setTimeOut(time);
				this.setTimeIn("");
			}
		}
		else
		{
			if(status.equals(StudentStatus.CheckedOut) && (!location.isEmpty()) && (!timeOut.isEmpty()))
			{
				status = StudentStatus.CheckedIn;
				this.setTimeIn(time);
			}
			else
			{
				this.setLocation("");
				this.setTimeOut("");
				this.setTimeIn("");
			}
		}
	}

	public String toString()
	{
		return studentID + "," + name + "," + timeOut + "," + location + "," + timeIn + ",";
	}

}
