import java.util.ArrayList;
import java.util.Random;

public class ColorSequence extends GameModes {
	ArrayList<String> sequence = new ArrayList<>();
	private Random rand = new Random();
	private String[] colors = { "red", "green", "blue", "yellow" };

	ColorSequence(SerialPortHandle sp, Player player,String gameMode) {
		super(sp, player, gameMode);
		// TODO Auto-generated constructor stub
		for (int i = 0; i < 3; i++) {
			sequence.add(colors[rand.nextInt(colors.length)]);
		}
	}

	@Override
	void runGameLogic() {
		// TODO Auto-generated method stub
		for (String color : sequence) {
			sp.printLine("LED:" + color);
			try {
				Thread.sleep(1000); // pause for LED reaction
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		 // Turn off LEDs after every full sequence display
	    sp.printLine("LED:OFF");
	    
		for (String pressed : sequence) {
			String input = sp.readLine();
			if (!input.equalsIgnoreCase(pressed)) {
				misses++;
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
