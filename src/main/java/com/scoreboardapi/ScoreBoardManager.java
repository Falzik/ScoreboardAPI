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


public class ScoreBoardManager {
    
    private final ScoreBoardPlugin plugin;
    private final Map<UUID, PlayerScoreBoard> playerScoreBoards;
    private final Map<String, GlobalScoreBoard> globalScoreBoards;

    public ScoreBoardManager(ScoreBoardPlugin plugin) {
        this.plugin = plugin;
        this.playerScoreBoards = new HashMap<>();
        this.globalScoreBoards = new HashMap<>();
    }

    public PlayerScoreBoard createPlayerScoreBoard(Player player, String title) {
        PlayerScoreBoard scoreBoard = new PlayerScoreBoard(player, title);
        playerScoreBoards.put(player.getUniqueId(), scoreBoard);
        return scoreBoard;
    }


    public Set<String> getGlobalScoreBoardIds() {
        return globalScoreBoards.keySet();
    }
    

    public PlayerScoreBoard getPlayerScoreBoard(Player player) {
        return playerScoreBoards.get(player.getUniqueId());
    }

    public GlobalScoreBoard createGlobalScoreBoard(String id, String title) {
        GlobalScoreBoard scoreBoard = new GlobalScoreBoard(id, title);
        globalScoreBoards.put(id, scoreBoard);
        return scoreBoard;
    }

    public GlobalScoreBoard getGlobalScoreBoard(String id) {
        return globalScoreBoards.get(id);
    }

    public void removePlayerScoreBoard(Player player) {
        PlayerScoreBoard scoreBoard = playerScoreBoards.remove(player.getUniqueId());
        if (scoreBoard != null) {
            scoreBoard.destroy();
        }
    }

    public void removeGlobalScoreBoard(String id) {
        GlobalScoreBoard scoreBoard = globalScoreBoards.remove(id);
        if (scoreBoard != null) {
            scoreBoard.destroy();
        }
    }

    public void cleanUp() {
        for (PlayerScoreBoard scoreBoard : playerScoreBoards.values()) {
            scoreBoard.destroy();
        }
        playerScoreBoards.clear();

        for (GlobalScoreBoard scoreBoard : globalScoreBoards.values()) {
            scoreBoard.destroy();
        }
        globalScoreBoards.clear();
    }
}