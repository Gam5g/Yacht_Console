package Yacht;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    public static final String FONT_CYAN = "";//"\u001B[36m";
    public static final String RESET = "";//"\u001B[0m";
    public static final String FONT_RED = "";//"\u001B[31m";
    
    static GameManager instance;
    
    public static GameManager GetInstance() {
    	if (instance == null) {
    		instance = new GameManager();
    		instance.Instantiate();
    	}
    	return instance;
    }
    
    enum GameState {
    	Ready, Play, End
    	
    }
    
    GameState state;
    
    public boolean IsGamePlay() {
    	return state == GameState.Play;
    	
    }
    
    public void GameStart() {
    	state = GameState.Play;
    	
    }
    
    List<Player> players;
    
    int playerIdx;
    
    public Player GetNowPlayer() {
    	return players.get(playerIdx);
    	
    }
    
    int turnCount;
    
    public void Instantiate() {
    	players = new ArrayList<Player>();
    	playerIdx = 0;
    	turnCount = 0;
    	
    }
    
    public void AddPlayer(Player e) {
    	players.add(e);
    }
    
    public void EndTurn() {
        playerIdx++;
        
        if (playerIdx == players.size())
        {
            playerIdx = 0;
            turnCount++;
        }
        
        if (turnCount > 11)
        	state = GameState.End;
    	
    }

    public void PrintWinner() {
        System.out.println("최종 승자는 바로 " + Player.GetWinnderName(players) +"님 입니다.");
    }

	
	public static void PrintFontCyanln(String key) {
        System.out.println(GameManager.FONT_CYAN + key + GameManager.RESET);
        
	}
	
	public static void PrintFontCyan(String key) {
        System.out.print(GameManager.FONT_CYAN + key + GameManager.RESET);
        
	}
	
	public static void PrintFontRedln(String key) {
        System.out.println(GameManager.FONT_CYAN + key + GameManager.RESET);
        
	}

}