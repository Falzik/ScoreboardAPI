package com.scoreboardapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Listener for player-related events.
 */
public class PlayerListener implements Listener {
    
    private final ScoreBoardPlugin plugin;
    
    /**
     * Constructs a new PlayerListener.
     * 
     * @param plugin The plugin instance
     */
    public PlayerListener(ScoreBoardPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Handles player join events.
     * 
     * @param event The player join event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Add player to existing global scoreboards that have autoAdd enabled
        for (String id : plugin.getScoreBoardManager().getGlobalScoreBoardIds()) {
            GlobalScoreBoard globalScoreBoard = plugin.getScoreBoardManager().getGlobalScoreBoard(id);
            if (globalScoreBoard.isAutoAddPlayers()) {
                globalScoreBoard.addPlayer(player);
            }
        }
    }
    
    /**
     * Handles player quit events.
     * 
     * @param event The player quit event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        // Remove player's scoreboard
        plugin.getScoreBoardManager().removePlayerScoreBoard(player);
        
        // Remove player from all global scoreboards
        for (String id : plugin.getScoreBoardManager().getGlobalScoreBoardIds()) {
            GlobalScoreBoard globalScoreBoard = plugin.getScoreBoardManager().getGlobalScoreBoard(id);
            globalScoreBoard.removePlayer(player);
        }
    }
}