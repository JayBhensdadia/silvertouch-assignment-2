import java.math.BigInteger;
import java.util.Random;

public class Main{
	
	public int random01Generator() {

		Random rand = new Random();
		return rand.nextInt(2); 
	}

	public void random06Generator(){

		Random rand = new Random();
		int val = 7;
		while (val >= 7) {

			String res = "";
			for (int i = 0; i < 3; i++) 
				res += 
				String.valueOf(random01Generator());		 

			BigInteger bg = new BigInteger(res,2);
						
			val = bg.intValue();
		}
		
		System.out.println(val);
	}
	
	
	public static void main(String[] args){
		Main r = new Main();
		r.random06Generator();
	}
}
