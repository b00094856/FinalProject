
import java.util.Random;
public class SpeedTest extends GameModes {
	
	int responseTime= 1000; 
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
		long start = System.currentTimeMillis();
        String input = sp.readLine().trim();
        long reactiontime = System.currentTimeMillis() - start;

        if (input.trim().equalsIgnoreCase(color) && reactiontime <= responseTime) {
            score = score + 100;
            responseTime = Math.max(300, responseTime - 50);
            
        } else {
            misses++;
            sp.printLine("BUZZER_ON");
        }

	}

}
