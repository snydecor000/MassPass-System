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
		if(location.matches("^\\d{3}$"))
		{
			if(Integer.parseInt(location) >= 900)
			{
				switch(Integer.parseInt(location))
				{
				case 900:
					location= "Attendance Office";
					break;
				case 901:
					location= "Guidance Office";
					break;
				case 902:
					location= "Main Office";
					break;
				case 903:
					location= "Deans Office";
					break;
				case 904:
					location= "Athletic Office";
					break;
				case 905:
					location= "Media Center";
					break;
				case 906:
					location= "Music Practice Rooms";
					break;
				case 907:
					location= "West Gym";
					break;
				case 908:
					location= "East Gym";
					break;
				case 909:
					location= "Student Activity Center";
					break;
				}
			}
		}
		return location;
	}
	
	public void setLocation(String loc)
	{
		if(loc.matches("^\\d{3}$"))
		{
			switch(Integer.parseInt(loc))
			{
			case 900:
				loc= "Attendance Office";
				break;
			case 901:
				loc= "Guidance Office";
				break;
			case 902:
				loc= "Main Office";
				break;
			case 903:
				loc= "Deans Office";
				break;
			case 904:
				loc= "Athletic Office";
				break;
			case 905:
				loc= "Media Center";
				break;
			case 906:
				loc= "Music Practice Rooms";
				break;
			case 907:
				loc= "West Gym";
				break;
			case 908:
				loc= "East Gym";
				break;
			case 909:
				loc= "Student Activity Center";
				break;
			}
		}
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
