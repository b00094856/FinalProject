
public abstract class GameModes {
	SerialPortHandle sp;
	int score = 0;
	int misses = 0;
	Player player;
	String gameMode = "";

	

	
	GameModes(SerialPortHandle sp, Player player, String gameMod) {
		this.sp = sp;
		this.player = player;
		this.gameMode = gameMode;
	}
	final void play() {
		countdown();
		applyAccelerationBonus();
		calculateScore();
		
	}
	void countdown() { // Ai-assisted
		long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < 30000 && misses < 3) {
            runGameLogic();
        }

	}
	void calculateScore() {
		player.score = score; 
		System.out.println("Final Score : " + score);
		if(gameMode.equals("SpeedTest")) {
			LeaderBoard ranking = new LeaderBoard("SpeedTest.txt");
			ranking.UpdateRanking(player);
			ranking.configRanking();
		}
		else if(gameMode.equals("ColorSequence")) {
			LeaderBoard ranking = new LeaderBoard("ColorSequence.txt");
			ranking.UpdateRanking(player);
			ranking.configRanking();
		}

		
	
	}
	void applyAccelerationBonus() {
	    int accelBonus = getAcceleration();
	    score = score + accelBonus;
	    System.out.println("Accelerometer bonus applied: " + accelBonus);
	}

	int getAcceleration() {
		sp.printLine("READ_ACCEL"); // Signal Arduino
        String Data = sp.readLine();
        String[] xyzaccel = Data.split(",");
        int xaxis =  Integer.parseInt(xyzaccel[0]);
        int yaxis = Integer.parseInt(xyzaccel[1]);
        int zaxis = Integer.parseInt(xyzaccel[2]);
        int accelscore = xaxis + yaxis + zaxis;
        if (accelscore > 1800) return 200;
        else if (accelscore > 1600) return 100;
        return 0;

        		

	}
	abstract void runGameLogic();
   
}
