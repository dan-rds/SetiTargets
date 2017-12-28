public class Position
{
	public double az;
	public double el;
	public Time t;

	Position(double AZ, double EL, Time T)
	{
		az = AZ;
		el = EL;
		t = T;
	}

	void printPos()
	{
		System.out.printf("[ %.4f, %.4f ] \t\t%s\n", az , el, t.getTime());
	}
}