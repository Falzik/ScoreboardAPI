package com.scoreboardapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.scoreboardapi.scoreboard.GlobalScoreBoard;
import com.scoreboardapi.scoreboard.PlayerScoreBoard;
import com.scoreboardapi.scoreboard.ScoreBoard;

/**
 * Manager class for handling all scoreboards.
 */
public class ScoreBoardManager {
    
    private final ScoreBoardPlugin plugin;
    private final Map<UUID, PlayerScoreBoard> playerScoreBoards;
    private final Map<String, GlobalScoreBoard> globalScoreBoards;
    
    /**
     * Constructs a new ScoreBoardManager.
     * 
     * @param plugin The plugin instance
     */
    public ScoreBoardManager(ScoreBoardPlugin plugin) {
        this.plugin = plugin;
        this.playerScoreBoards = new HashMap<>();
        this.globalScoreBoards = new HashMap<>();
    }
    
    /**
     * Creates a new player scoreboard.
     * 
     * @param player The player to create the scoreboard for
     * @param title The title of the scoreboard
     * @return The created player scoreboard
     */
    public PlayerScoreBoard createPlayerScoreBoard(Player player, String title) {
        PlayerScoreBoard scoreBoard = new PlayerScoreBoard(player, title);
        playerScoreBoards.put(player.getUniqueId(), scoreBoard);
        return scoreBoard;
    }

    /**
     * Returns a set of all global scoreboard IDs.
     *
     * @return Set of global scoreboard IDs
     */
    public Set<String> getGlobalScoreBoardIds() {
        return globalScoreBoards.keySet();
    }
    
    /**
     * Gets the player scoreboard for the specified player.
     * 
     * @param player The player to get the scoreboard for
     * @return The player's scoreboard, or null if not found
     */
    public PlayerScoreBoard getPlayerScoreBoard(Player player) {
        return playerScoreBoards.get(player.getUniqueId());
    }
    
    /**
     * Creates a new global scoreboard.
     * 
     * @param id The ID of the scoreboard
     * @param title The title of the scoreboard
     * @return The created global scoreboard
     */
    public GlobalScoreBoard createGlobalScoreBoard(String id, String title) {
        GlobalScoreBoard scoreBoard = new GlobalScoreBoard(id, title);
        globalScoreBoards.put(id, scoreBoard);
        return scoreBoard;
    }
    
    /**
     * Gets a global scoreboard by its ID.
     * 
     * @param id The ID of the scoreboard
     * @return The global scoreboard, or null if not found
     */
    public GlobalScoreBoard getGlobalScoreBoard(String id) {
        return globalScoreBoards.get(id);
    }
    
    /**
     * Removes a player scoreboard.
     * 
     * @param player The player whose scoreboard should be removed
     */
    public void removePlayerScoreBoard(Player player) {
        PlayerScoreBoard scoreBoard = playerScoreBoards.remove(player.getUniqueId());
        if (scoreBoard != null) {
            scoreBoard.destroy();
        }
    }
    
    /**
     * Removes a global scoreboard.
     * 
     * @param id The ID of the scoreboard to remove
     */
    public void removeGlobalScoreBoard(String id) {
        GlobalScoreBoard scoreBoard = globalScoreBoards.remove(id);
        if (scoreBoard != null) {
            scoreBoard.destroy();
        }
    }
    
    /**
     * Cleans up all scoreboards.
     */
    public void cleanUp() {
        // Clean up player scoreboards
        for (PlayerScoreBoard scoreBoard : playerScoreBoards.values()) {
            scoreBoard.destroy();
        }
        playerScoreBoards.clear();
        
        // Clean up global scoreboards
        for (GlobalScoreBoard scoreBoard : globalScoreBoards.values()) {
            scoreBoard.destroy();
        }
        globalScoreBoards.clear();
    }
}