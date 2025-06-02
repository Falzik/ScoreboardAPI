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
    
    /**
     * Constructs a new GlobalScoreBoard.
     * 
     * @param id The unique ID of this scoreboard
     * @param title The title of the scoreboard
     */
    public GlobalScoreBoard(String id, String title) {
        super(title);
        this.id = id;
        this.players = new HashSet<>();
        this.autoAddPlayers = false;
    }
    
    /**
     * Gets the ID of this scoreboard.
     * 
     * @return The ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Checks if players are automatically added to this scoreboard when they join.
     * 
     * @return True if players are auto-added, false otherwise
     */
    public boolean isAutoAddPlayers() {
        return autoAddPlayers;
    }
    
    /**
     * Sets whether players should be automatically added to this scoreboard when they join.
     * 
     * @param autoAddPlayers True to auto-add players, false otherwise
     */
    public void setAutoAddPlayers(boolean autoAddPlayers) {
        this.autoAddPlayers = autoAddPlayers;
    }
    
    /**
     * Adds a player to this scoreboard.
     * 
     * @param player The player to add
     */
    public void addPlayer(Player player) {
        if (!players.contains(player.getUniqueId())) {
            players.add(player.getUniqueId());
            player.setScoreboard(bukkit);
        }
    }
    
    /**
     * Removes a player from this scoreboard.
     * 
     * @param player The player to remove
     */
    public void removePlayer(Player player) {
        if (players.remove(player.getUniqueId())) {
            // Reset to main scoreboard
            player.setScoreboard(player.getServer().getScoreboardManager().getMainScoreboard());
        }
    }
    
    /**
     * Checks if a player is in this scoreboard.
     * 
     * @param player The player to check
     * @return True if the player is in this scoreboard, false otherwise
     */
    public boolean hasPlayer(Player player) {
        return players.contains(player.getUniqueId());
    }
    
    /**
     * Gets all players in this scoreboard.
     * 
     * @return Set of player UUIDs
     */
    public Set<UUID> getPlayers() {
        return new HashSet<>(players);
    }
    
    @Override
    public void update() {
        updateLines();
        // No need to refresh all players as the scoreboard is updated automatically
    }
    
    @Override
    public void destroy() {
        // Reset all players to the main scoreboard
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.setScoreboard(player.getServer().getScoreboardManager().getMainScoreboard());
            }
        }
        players.clear();
    }
    
    /**
     * Updates the scoreboard for a specific player.
     * 
     * @param player The player to update the scoreboard for
     */
    public void updateForPlayer(Player player) {
        if (hasPlayer(player)) {
            player.setScoreboard(bukkit);
        }
    }
    
    /**
     * Updates the scoreboard for all players.
     */
    public void updateForAllPlayers() {
        for (UUID uuid : players) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                player.setScoreboard(bukkit);
            }
        }
    }
}