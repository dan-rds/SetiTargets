
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
	public static double DISH_LAT = 40.8172439;//;
	public static double DISH_LON = -121.4698372;
	public static double DISH_AZ_MIN ;
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
            in.nextLine();
            in.nextLine();
            
           // String del = "\","

            while(in.hasNextLine()) 
            {
                String line = in.nextLine().trim();

                String[] tokens = line.split("[\"+,|,+\"]", 16);
                // for (int i = 0; i < tokens.length; i++)
                // {
                //     System.out.printf(" i = %d  %s\n", i,  tokens[i]);
                // }
                // //int temp_ID = Integer.parseInt(tokens[0]);
                String temp_NAME = tokens[7];
               
                double temp_RA = Double.parseDouble(tokens[10]);
                double temp_DEC = Double.parseDouble(tokens[13]);
                
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
    String inputFile = args[0];//set to args
    String configFile = args[1];
    String line;
    int lineIndex;
    int index = 0;

    configure(configFile);

    Target[] targetList = readTargetsFromFile(inputFile);

    

    Time t = new Time();

    System.out.printf("\n\nUTC = %s\n", t.getTime());
    System.out.printf("Mean Sidereal Time %f\n\n", t.getMeanSiderealTime(t,DISH_LON));


    int i = 0;

    while( i < targetList.length && targetList[i] != null)
    {
        targetList[i].findRiseSet(t, DISH_LAT, DISH_LON);
        targetList[i].printTarget();
        i++;
    }

  } 
}