import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class LeaderBoard {
	ArrayList<Player> ranking;
	String fileName;

	LeaderBoard(String fileName) {
		this.fileName = fileName;
		ranking = new ArrayList<>();
		loadRankings(); // Load saved rankings
	}

	void UpdateRanking(Player P) {
	    boolean updated = false;
	    for (int i = 0; i < ranking.size(); i++) {
	        Player existing = ranking.get(i);
	        if (existing.name.equalsIgnoreCase(P.name)) {
	            if (P.score > existing.score) {
	                ranking.set(i, P);  // Replace with better score
	                System.out.println("Updated score for " + P.name);
	            } else {
	                System.out.println("Score not improved for " + P.name);
	            }
	            updated = true;
	            break;
	        }
	    }

	    if (!updated) {
	        ranking.add(P);
	        System.out.println("New player added to leaderboard: " + P.name);
	    }

	    ranking.sort(Comparator.comparingInt(p -> -p.score));
	    saveRankings();
	}

	 void configRanking() {
	        System.out.println("---Rankings for (" + fileName + ") ---");
	        for (int i = 0; i < ranking.size(); i++) {
	            System.out.println((i + 1) + ". " + ranking.get(i).name + " " + ranking.get(i).score);
	        }
	    }

	void saveRankings() {
		try {
			FileOutputStream fout = new FileOutputStream(fileName);
			for (Player p : ranking) {
				String line = p.name + " " + p.score + "\n";
				fout.write(line.getBytes());
			}
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void loadRankings() { // Ai -assisted
		ranking.clear();
		try {
			FileInputStream fin = new FileInputStream(fileName);
			int i;
			StringBuilder builder = new StringBuilder();
			while ((i = fin.read()) != -1) {
				builder.append((char) i);
			}
			String[] lines = builder.toString().split("\\n"); // Ai-assisted
			for (String line : lines) {
				String[] parts = line.trim().split(" ");
				if (parts.length == 2) {
					ranking.add(new Player(parts[0], Integer.parseInt(parts[1])));
				}

			}
			fin.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
