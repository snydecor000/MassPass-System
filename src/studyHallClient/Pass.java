package studyHallClient;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Pass 
{
	protected enum PassStatus
	{
		Verified, Unknown, FailedDay, 
		FailedMonth, FailedPeriod, FailedEncryption;
		
		public String toString()
		{
			switch(this)
			{
			case Verified:
				return "This pass is verified";
			case Unknown:
				return "This pass has not been tested";
			case FailedDay:
				return "This pass is for the wrong day";
			case FailedMonth:
				return "This pass is for the wrong month";
			case FailedPeriod:
				return "This pass is for the wrong period";
			case FailedEncryption:
				return "This pass is fake";
			default:
				return "This is a really bad pass";
			}
		}
	}
	private String barcode;
	private String month;
	private String day;
	private String location;
	private String period;
	private String encryption;
	private PassStatus passStatus;
	
	public Pass(String b)
	{
		parsePass(b);
		passStatus = PassStatus.Unknown;
	}
	
	public String getPeriod()
	{
		return period;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public boolean setBarcode(String b)
	{
		passStatus = PassStatus.Unknown;
		return parsePass(b);
	}
	
	public String getBarcode()
	{
		return barcode;
	}
	
	public PassStatus getPassStatus()
	{
		return passStatus;
	}
	
	public boolean verifyPass(String currentPeriod)
	{
		DateTimeFormatter currentDayF = DateTimeFormatter.ofPattern("dd");
		DateTimeFormatter currentMonthF = DateTimeFormatter.ofPattern("MM");
		LocalDate localDate = LocalDate.now();
		String currentDayS = currentDayF.format(localDate);
		String currentMonthS = currentMonthF.format(localDate);
		
		int key = Integer.parseInt(currentDayS+currentMonthS);
		key = key - Integer.parseInt(currentDayS);
		byte[] bytesOfMessage = ByteBuffer.allocate(4).putInt(key).array();
		MessageDigest md;
		byte[] thedigest = {0,0,0,0};
		try
		{
			md = MessageDigest.getInstance("MD5");
			thedigest = md.digest(bytesOfMessage);
		}
		catch (NoSuchAlgorithmException e)
		{
			System.exit(0);
		}
		
		if(Integer.parseInt(encryption)!=thedigest[0])
		{
			passStatus = PassStatus.FailedEncryption;
			return false;
		}
		else if(!period.equals(currentPeriod))
		{
			passStatus = PassStatus.FailedPeriod;
			return false;
		}
		else if(!month.equals(currentMonthS))
		{
			passStatus = PassStatus.FailedMonth;
			return false;
		}
		else if(!day.equals(currentDayS))
		{
			passStatus = PassStatus.FailedDay;
			return false;
		}
		else
		{
			passStatus = PassStatus.Verified;
			return true;
		}
	}
	
	private boolean parsePass(String b)
	{
		if(b.matches("^\\d{10,11}$"))
		{
			barcode = b;
			month = b.substring(0, 2);
			day = b.substring(2, 4);
			location = b.substring(4, 7);
			period = b.substring(7, 8);
			encryption = b.substring(8);
			return true;
		}
		else
		{
			barcode = "";
			month = "";
			day = "";
			location = "";
			period = "";
			encryption = "";
			return false;
		}
	}
}
