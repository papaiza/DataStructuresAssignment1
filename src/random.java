
public class random
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		mystery(3);
	}
	public static void mystery(int n){
		if(n <= 0) return;
		mystery(n -1);
		System.out.print((2*n + 1) + "");
		mystery(n -1);
	}

}
