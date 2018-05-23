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
//		if(timeOut.isEmpty() && location.isEmpty())
//		{
//			status = StudentStatus.CheckedIn;
//		}
//		else
//		{
//			status = StudentStatus.CheckedOut;
//		}
	}
	
	public Student()
	{
		studentID = "";
		name = "";
		location = "Here";
		timeIn = "";
		timeOut = "";
		//status = StudentStatus.CheckedIn;
	}
	
	public Student(String id, String n)
	{
		studentID = id;
		name = n;
		location = "Here";
		timeIn = "";
		timeOut = "";
		//status = StudentStatus.CheckedIn;
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
		if(location.matches("^\\d{3}$"))
		{
			status = StudentStatus.CheckedOut;
		}
	}
	
	public void givePass(String barcode)
	{
		pass = new Pass(barcode);
	}
	
	public void giveEmptyPass()
	{
		pass = null;
	}
	
 	public String process(String time, String period)
	{
		if(pass != null)
		{
			if(location.equals("Here") && timeOut.isEmpty() && (pass.verifyPass(period)))
			{
				this.setLocation(pass.getLocation());
				this.setTimeOut(time);
				this.setTimeIn("");
			}
			else if(location.equals("Here") && (!timeOut.isEmpty()) && (!timeIn.isEmpty()) && (pass.verifyPass(period)))
			{
				this.setTimeOut(time);
				this.setLocation(pass.getLocation());
				this.setTimeIn("");
			}
			return pass.getPassStatus().toString();
		}
		else
		{
			if((!location.equals("Here")) && (!timeOut.isEmpty()) && (timeIn.isEmpty()))
			{
				this.setLocation("Here");
				this.setTimeIn(time);
			}
			else
			{
				this.setTimeOut("");
				this.setTimeIn("");
			}
			return "";
		}
	}

	public String toString()
	{
		return studentID + "," + name + "," + timeOut + "," + location + "," + timeIn + ",";
	}

}
