import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
public class ColorSequence extends GameModes {
	ArrayList<String> sequence = new ArrayList<>();
	private Random rand = new Random();
	private String[] colors = { "red", "green", "blue", "yellow" }; // available colors
	ColorSequence(SerialPortHandle sp, Player player, String gameMode) {
		super(sp, player, gameMode);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < 3; i++) { // make game more difficult
			sequence.add(colors[rand.nextInt(colors.length)]); 		}
	}
	
	@Override
	void runGameLogic() {
		// TODO Auto-generated method stub
		//for configuring the sequence
		for (String color : sequence) {
		    sp.printLine("LED:OFF");
		    try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sp.printLine("LED:" + color);
			try {
				Thread.sleep(1000); // pause to give time for LED reaction
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		 // Turn off LEDs after every full sequence display
	    sp.printLine("LED:ON");
	  	// used to check the player’s button presses
		for (String pressed : sequence) {
			String input = sp.readLine();
			if (!input.equalsIgnoreCase(pressed)) {
				misses++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sp.printLine("BUZZER_ON");
				System.out.println("Wrong input! Misses: " + misses);
				if (misses >= 3)
					break;
				return;
			} else {
				score += 100;
			}
		}
		if (misses < 3) {
			System.out.println("Sequence complete!");
			sequence.add(colors[rand.nextInt(colors.length)]);
		}
	}
	
}
