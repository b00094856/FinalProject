import java.util.Scanner;
public class Driver {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// initialize connection 
		SerialPortHandle serial = new SerialPortHandle("COM3");
		
		System.out.print("Enter player name: ");
      String playerName = in.nextLine();
      Player player = new Player(playerName, 0);
		//Selecting game mode
		System.out.println("Select game mode: 1. Speed Test 2. Color Sequence");
		int choice = in.nextInt();
		in.nextLine();
		GameModes Mode;
		while (true) {
			switch (choice) {
			case 1 -> Mode = new SpeedTest(serial, player, "SpeedTest");
			case 2 -> Mode = new ColorSequence(serial, player, "ColorSequence");
			default -> {
				System.out.println("Invalid choice. Enter Again");
				choice = in.nextInt();
				continue;
			}
			}
			break;
		}
    		//Game start
		if (Mode != null) {
			Mode.play();
		}
	}
}
