
import java.util.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;



public class SetiTargets
{
	public static int NUM_HOURS = 12;
	public static int NUM_MINUTES = NUM_HOURS * 60;
	public static double DISH_LAT = 40.8172439;//;
	public static double DISH_LON = -121.4698372;
	public static double DISH_FOV = 4.0;
	public static double DISH_AZ_MIN = 180.0;
	public static double DISH_AZ_MAX = 180.0;
	public static double DISH_ELEV_MIN = 45.0;
	public static double DISH_ELEV_MAX = 90.0;


    public double observer_lat;
    public double observer_long;

public static void main(String args[]) 
{
    String inputFile = args[0];//set to args
    String configFile = args[1];
    String line;
    int lineIndex;
    int lineNum = 0;
    int index = 0;

    Target[] targetList = new Target[400];

    String[] temp = new String[20];

    if(configFile != null)
    {
        try 
        {
            Scanner conf = new Scanner(new File(configFile));
            
            
            DISH_LAT = conf.nextDouble();
            System.out.printf("%.3f ", DISH_LAT);
            conf.nextLine();
            DISH_LON = conf.nextDouble();
            conf.nextLine();
            DISH_FOV = conf.nextDouble();
            conf.nextLine();
            DISH_AZ_MAX = conf.nextDouble();
            conf.nextLine();
            DISH_AZ_MIN = conf.nextDouble();
            conf.nextLine();
            DISH_ELEV_MAX = conf.nextDouble();
            conf.nextLine();
            DISH_ELEV_MIN = conf.nextDouble();
            conf.nextLine();
            NUM_HOURS = conf.nextInt();
            NUM_MINUTES = NUM_HOURS * 60;
            
            conf.close();
        }
        catch(IOException e) 
        {
          e.printStackTrace();
        }
    }

    try 
    {
        Scanner in = new Scanner(new File(inputFile));
        in.nextLine();
        in.nextLine();
        

        while(in.hasNextLine()) 
        {
            line = in.nextLine().trim();

    		String[] tokens = line.split(",", 20);
    		int temp_ID = Integer.parseInt(tokens[0]);
            String temp_NAME = tokens[2];
            double temp_RA = Double.parseDouble(tokens[3]);
            double temp_DEC = Double.parseDouble(tokens[4]);
            
            Target newTar = new Target(temp_NAME, temp_RA, temp_DEC, 1440);

            targetList[index++] = newTar;

            lineNum++;
        }
        in.close();
    }
    catch(IOException e) 
    {
      e.printStackTrace();
    }

    Time t = new Time();

    System.out.printf("\n\nUTC = %s\n", t.getTime());
    System.out.printf("Mean Sidereal Time %f\n\n", t.getMeanSiderealTime(t,DISH_LON));


    int i = 0;

    while( targetList[i] != null)
    {
        targetList[i].findRiseSet(t, DISH_LAT, DISH_LON);
        targetList[i].printTarget();
        i++;
    }

  } 
}