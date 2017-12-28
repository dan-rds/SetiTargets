import java.util.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Time{

	public static final double RAD_TO_DEG = 180/Math.PI;       // convert radians to degrees
    public static final double DEG_TO_RAD = Math.PI/180;
    public static final double ATA_LAT = 40.8172439;
    public static final double ATA_LON = -121.4698327;
    public static final double ATA_ELEV = 986; //meters

	public Calendar startDate;

	
	Time()
	{
		startDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}

	Time(Time START)
	{
		startDate =  (Calendar)START.startDate.clone();
	}

	void printTime()
	{
		double year       = startDate.get(Calendar.YEAR);
	    double month      = startDate.get(Calendar.MONTH);
	    double day        = startDate.get(Calendar.DAY_OF_MONTH);
	    double hour       = startDate.get(Calendar.HOUR_OF_DAY);
	   	double  minute     = startDate.get(Calendar.MINUTE);
	   	double  second     = startDate.get(Calendar.SECOND);
	    System.out.printf("\n%.0f/%.0f/%.0f : %.0f:%.0f:%.0f\n", year, month, day, hour, minute, second);
	}
	String getTime()
	{
		double year       = startDate.get(Calendar.YEAR);
	    double month      = startDate.get(Calendar.MONTH);
	    double day        = startDate.get(Calendar.DAY_OF_MONTH);
	    double hour       = startDate.get(Calendar.HOUR_OF_DAY);
	   double  minute     = startDate.get(Calendar.MINUTE);
	   double  second     = startDate.get(Calendar.SECOND);
	   String out = String.format("%.0f/%.0f/%.0f : %.0f:%.0f:%.0f", year, month, day, hour, minute, second);
	   return out;
	    //return (year + " " + month + " " + day + " " + hour + " " + minute + " " + second);
	}
	double getMeanSiderealTime(Time t ,double lon)
	{
		Calendar cal 	= t.startDate;
		double year       = cal.get(Calendar.YEAR);
	    double month      = cal.get(Calendar.MONTH);
	    double day        = cal.get(Calendar.DAY_OF_MONTH);
	    double hour       = cal.get(Calendar.HOUR_OF_DAY);
	   	double  minute     = cal.get(Calendar.MINUTE);
	   	double  second     = cal.get(Calendar.SECOND);

	    if ((month == 1)||(month == 2))
	    {
	        year  = year - 1;
	        month = month + 12;
	    }
	   // printTime();
	    double a = Math.floor(year/100);
	    double b = 2 - a + Math.floor(a/4);
	    double c = Math.floor(365.25*year);
	    double d = Math.floor(30.6001*(month+2));
	    
	    // days since J2000.0
	    double jd = b + c + d - 730550.5 + day + (hour + minute/60 + second/3600)/24.0;
	    //jd = 6390.228;
	    
	    // julian centuries since J2000.0
	    double jt = jd/36525.0;
	    
	    // mean sidereal time
	    double mst = 280.46061837 + 360.98564736629*jd + 0.000387933*jt*jt - jt*jt*jt/38710000 +lon;
	    
	    if (mst > 0.0)
	    {
	        while (mst > 360.0)
	        mst = mst - 360.0;
	    }
	    else
	    {
	        while (mst < 0.0)
	        mst = mst + 360.0;
	    }
	   // printTime();
	   // double tempH = Math.floor((mst) /15);
	   // double tempM = ((mst/15) % 1) * 60;
	    //System.out.printf("\n mst = %.5f  :  %.0f : %.3f\n", mst, tempH, tempM); 
	    return mst;
	}
	    


}