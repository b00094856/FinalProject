public abstract class GameModes {
	SerialPortHandle sp;
	int score = 0;
	int misses = 0;
	Player player;
	String gameMode;
	
	GameModes(SerialPortHandle sp, Player player, String gameMode) {
		this.sp = sp;
		this.player = player;
		this.gameMode = gameMode;
	}
	// Core of the template design pattern
	final void play() {
		getReady();
		countdown();
		calculateScore();
		endRound();
	}




	//Signals start of game
	void getReady()
	{
	    System.out.println("GET READY");
	    sp.printLine("FLASH");
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// game runs for 30 secs or if the player has made 3 mistakes
	void countdown() {
		long startTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - startTime) < 30000 && misses < 3) {
			runGameLogic();
		}
	}
	//used for calculating score and updating leaderboard
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
	//end the game	
	void endRound() {
		System.out.println("TIME IS UP");
		sp.printLine("FLASH");
	    try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sp.printLine("LED:OFF");
	}
	// needs to implemented in children
	abstract void runGameLogic();
}
