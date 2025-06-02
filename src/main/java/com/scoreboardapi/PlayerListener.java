package com.scoreboardapi;

import com.scoreboardapi.scoreboard.GlobalScoreBoard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    
    private final ScoreBoardPlugin plugin;

    public PlayerListener(ScoreBoardPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (String id : plugin.getScoreBoardManager().getGlobalScoreBoardIds()) {
            GlobalScoreBoard globalScoreBoard = plugin.getScoreBoardManager().getGlobalScoreBoard(id);
            if (globalScoreBoard.isAutoAddPlayers()) {
                globalScoreBoard.addPlayer(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        plugin.getScoreBoardManager().removePlayerScoreBoard(player);

        for (String id : plugin.getScoreBoardManager().getGlobalScoreBoardIds()) {
            GlobalScoreBoard globalScoreBoard = plugin.getScoreBoardManager().getGlobalScoreBoard(id);
            globalScoreBoard.removePlayer(player);
        }
    }
}