package com.scoreboardapi.scoreboard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * ScoreBoard implementation for multiple players.
 */
public class GlobalScoreBoard extends AbstractScoreBoard {
    
    private final String id;
    private final Set<UUID> players;
    private boolean autoAddPlayers;
    

    public GlobalScoreBoard(String id, String title) {
        super(title);
        this.id = id;
        this.players = new HashSet<>();
        this.autoAddPlayers = false;
    }
    

    public String getId() {
        return id;
    }
    

    public boolean isAutoAddPlayers() {
        return autoAddPlayers;
    }
    

    public void setAutoAddPlayers(boolean autoAddPlayers) {
        this.autoAddPlayers = autoAddPlayers;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player.getUniqueId())) {
            players.add(player.getUniqueId());
            player.setScoreboard(bukkit);
        }
    }
    

    public void removePlayer(Player player) {
        if (players.remove(player.getUniqueId())) {
            // Reset to main scoreboard
            player.setScoreboard(player.getServer().getScoreboardManager().getMainScoreboard());
        }
    }
    

    public boolean hasPlayer(Player player) {
        return players.contains(player.getUniqueId());
    }
    

    public Set<UUID> getPlayers() {
        return new HashSet<>(players);
    }
    
    @Override
    public void update() {
        updateLines();
    }
    
    @Override
    public void destroy() {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.setScoreboard(player.getServer().getScoreboardManager().getMainScoreboard());
            }
        }
        players.clear();
    }
    

    public void updateForPlayer(Player player) {
        if (hasPlayer(player)) {
            player.setScoreboard(bukkit);
        }
    }

    public void updateForAllPlayers() {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.setScoreboard(bukkit);
            }
        }
    }
}