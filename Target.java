import java.util.Calendar;
import java.util.Date;
public class Target
{
	public static final double RAD_TO_DEG = 180/Math.PI;       // convert radians to degrees
    public static final double DEG_TO_RAD = Math.PI/180;

	public String name;
	public double ra;
	public double dec;
	public Position rise;
	public Position set;
	public Position startPos;
	public boolean everInView;
	public boolean isUpNow;


	Target(String NAME, double RA, double DEC)
	{
		name = NAME;
		ra = RA;
		dec = DEC;
		startPos = null;
		rise = null;
		set = null;
		isUpNow = false;
		everInView = false;

	}

	void findRiseSet(Time start, double lat, double lon)
	{
		Time curr_Time = new Time(start);
		boolean isUpFlag = false;
		Position curr_pos = calcAzEl(curr_Time, lat, lon);
		startPos = curr_pos;

		if(inView(curr_pos))
		{	
			isUpNow = true;
		}

		for(int i = 0; i < SetiTargets.NUM_MINUTES; i++)
		{
			if(!isUpFlag && inView(curr_pos))
	        {
	            rise = curr_pos;
	            rise.t = new Time(curr_Time);
	            everInView = true;
	            isUpFlag = true;
	        }

	        else if (isUpFlag && !inView(curr_pos))
	        {
	            set = curr_pos;
	            set.t = new Time(curr_Time);
	            everInView = true;
	            isUpFlag = false;
	        }
			curr_Time.startDate.add(Calendar.MINUTE, 1);
			curr_pos = calcAzEl(curr_Time, lat, lon);
		}
	}

	boolean inView(Position p)
	{
		boolean azInView = (SetiTargets.DISH_AZ_MIN < p.az && SetiTargets.DISH_AZ_MAX > p.az);
		boolean elInView = (SetiTargets.DISH_ELEV_MIN < p.el && SetiTargets.DISH_ELEV_MAX > p.el); 
	    return (azInView && elInView);
	} 
	
	void printTarget()
	{
		System.out.printf("\n%s     %s\n\tra, dec =  [ %f , %f ]\n", name, (isUpNow)? "is up.": "is NOT up.",  ra, dec);
		System.out.printf("\taz, el  = ");
		startPos.printPos();
		if(everInView)
		{
			if(rise != null)
			{
				System.out.printf("\tRises > ");
				rise.printPos();
			}
			if(set != null)
			{
				System.out.printf("\tSets <  ");
				set.printPos();
			}
		}
		else System.out.printf("\tNever in view\n");
		
	}


	Position calcAzEl(Time t, double lat, double lon )
	{
    	double newRA = ra * 15.0;

	    // compute hour angle in degrees
	    double ha = t.getMeanSiderealTime( t, lon) - newRA;

	    if (ha < 0) ha = ha + 360;

	    // convert degrees to radians
	    ha  *= DEG_TO_RAD;
	    double tempDec = dec * DEG_TO_RAD;
	    lat *= DEG_TO_RAD;
	    
	    // compute altitude in radians
	    double sin_alt = Math.sin(tempDec) * Math.sin(lat) + Math.cos(tempDec) * Math.cos(lat) * Math.cos(ha);
	    double alt     =  Math.asin(sin_alt);
	    
	    // compute azimuth in radians
	    double cos_az = ( Math.sin(tempDec) -  Math.sin(alt)* Math.sin(lat))/( Math.cos(alt)* Math.cos(lat));
	    double az     =  Math.acos(cos_az);
	    
	    // convert radians to degrees
	    double el = alt * RAD_TO_DEG;
	    az  = az * RAD_TO_DEG;
	    
	    // choose hemisphere
	    if ( Math.sin(ha) > 0) az  = 360 - az;
	    
	    Position out_pos = new Position(az, el, t);
	  
	    return out_pos;
	}

}