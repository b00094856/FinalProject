import java.util.Random;
import java.lang.Math;
public class SpeedTest extends GameModes {
	
	int responseTime= 4000; // 4 sec to respond initially
	Random random = new Random();
	SpeedTest(SerialPortHandle sp, Player player, String gameMode) {
		super(sp, player, gameMode);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void runGameLogic() {
		// TODO Auto-generated method stub
		String[] LEDs = {"red" , "green" , "blue" , "yellow"};
		String color = LEDs[random.nextInt(4)];
		sp.printLine("LED:" + color);
//Measuring player reaction time
		long start = System.currentTimeMillis();
      String input = sp.readLine().trim();
      long reactiontime = System.currentTimeMillis() - start;
      if (input.equalsIgnoreCase(color) && reactiontime <= responseTime) {
   	   int bonus = getAcceleration();
          score = score + 100 + bonus;
          System.out.println("Bonus = " + bonus);
          responseTime = Math.max(500, responseTime - 100); // Make game more diffuclt
        
      } else {
          misses++;
          try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          sp.printLine("BUZZER_ON");
      }
	    //sp.printLine("LED:OFF");
	    //sp.printLine("FLASH");
	}
// used to add a bonus based on accelerometer dara
	int getAcceleration() {
	    sp.printLine("READ_ACCEL"); // Request accel data
	    while (true) {
	        String data = sp.readLine();
	        // Check if data looks like accelerometer reading (e.g., contains 2 commas)
	        if (data == null) continue; 
	        if (data.chars().filter(ch -> ch == ',').count() == 2) {
	            try {
	                String[] xyzaccel = data.split(",");
	                int xaxis = Integer.parseInt(xyzaccel[0].trim());
	                int yaxis = Integer.parseInt(xyzaccel[1].trim());
	                int zaxis = Integer.parseInt(xyzaccel[2].trim());
	                Double accelscore = Math.sqrt((xaxis*xaxis) + (yaxis*yaxis) + (zaxis*zaxis));
	                if (accelscore > 1000) return 200;
	                else if (accelscore > 700) return 100;
	                else return 0;
	            } 
catch (NumberFormatException e) {
	                continue;
	            }
	           
	        }
	      
	    }
	}
}
