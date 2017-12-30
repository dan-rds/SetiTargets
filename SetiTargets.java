
import java.util.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;



public class SetiTargets
{
	public static int NUM_HOURS = 12;
    public static int NUM_TARGETS = 400;
	public static int NUM_MINUTES = NUM_HOURS * 60;
    public static int START_TARGET_LINE = 0;
	public static double DISH_LAT = 40.8172439;//;
	public static double DISH_LON = -121.4698372;
	public static double DISH_AZ_MIN;
	public static double DISH_AZ_MAX;
	public static double DISH_ELEV_MIN = 45.0;
	public static double DISH_ELEV_MAX = 90.0;


    public static void configure(String configFile)
    {
        if(configFile != null)
        {
            try 
            {
                Scanner conf = new Scanner(new File(configFile));
                
                DISH_LAT = conf.nextDouble();
                conf.nextLine();
                DISH_LON = conf.nextDouble();
                conf.nextLine();
                DISH_AZ_MAX = conf.nextDouble();
                conf.nextLine();
                DISH_AZ_MIN = conf.nextDouble();
                conf.nextLine();
                DISH_ELEV_MAX = conf.nextDouble();
                conf.nextLine();
                DISH_ELEV_MIN = conf.nextDouble();
                conf.nextLine();
                NUM_TARGETS = conf.nextInt();
                conf.nextLine();
                START_TARGET_LINE = conf.nextInt();
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
    }

    public static Target[] readTargetsFromFile(String inputFile)
    {
        Target[] out = new Target[NUM_TARGETS];
        int index = 0;
        try 
        {
            Scanner in = new Scanner(new File(inputFile));

            for(int i = 0; i < START_TARGET_LINE +2; i++) in.nextLine();

            while(in.hasNextLine()) 
            {
                String line = in.nextLine().trim();

                String[] tokens = line.split("\"+,+\"|\"", 16);
                String temp_NAME = tokens[3];

                //for(int i = 0; i < 10; i++) System.out.printf("%d [%s]\n", i, tokens[i]);
                double temp_RA = Double.parseDouble(tokens[4]);
                double temp_DEC = Double.parseDouble(tokens[5]);
                
                Target newTar = new Target(temp_NAME, temp_RA, temp_DEC);

                out[index++] = newTar;
                if(index >= NUM_TARGETS) break;
            }
            in.close();
        }
        catch(IOException e) 
        {
          e.printStackTrace();
        }


        return out;

    }

public static void main(String args[]) 
{
    String inputFile = "targets.txt";
    String configFile = "config.txt";

    configure(configFile);

    Target[] targetList = readTargetsFromFile(inputFile);

    Time t = new Time();

    System.out.printf("\nUTC = %s\n", t.getTime());
    System.out.printf("Mean Sidereal Time %f\n", t.getMeanSiderealTime(t,DISH_LON));
    System.out.printf("\nDish:\n\tLat, Long = [%.3f, %.3f]\n\tAzimuth Range = [%.3f - %.3f]\n\tElevation Range = [%.3f - %.3f]\n", 
        DISH_LAT, DISH_LON, DISH_AZ_MIN, DISH_AZ_MAX, DISH_ELEV_MIN, DISH_ELEV_MAX);
    int i = 0;

    while( i < targetList.length && targetList[i] != null)
    {
        targetList[i].findRiseSet(t, DISH_LAT, DISH_LON);
        targetList[i].printTarget();
        i++;
    }

  } 
}