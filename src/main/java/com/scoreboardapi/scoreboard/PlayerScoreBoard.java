package com.scoreboardapi.scoreboard;

import org.bukkit.entity.Player;

/**
 * ScoreBoard implementation for a single player.
 */
public class PlayerScoreBoard extends AbstractScoreBoard {
    
    private final Player player;
    private boolean showing;
    
    /**
     * Constructs a new PlayerScoreBoard.
     * 
     * @param player The player this scoreboard is for
     * @param title The title of the scoreboard
     */
    public PlayerScoreBoard(Player player, String title) {
        super(title);
        this.player = player;
        this.showing = false;
    }
    
    /**
     * Gets the player this scoreboard is for.
     * 
     * @return The player
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Checks if the scoreboard is currently showing to the player.
     * 
     * @return True if showing, false otherwise
     */
    public boolean isShowing() {
        return showing;
    }
    
    /**
     * Shows the scoreboard to the player.
     */
    public void show() {
        if (!showing) {
            player.setScoreboard(bukkit);
            showing = true;
        }
    }
    
    /**
     * Hides the scoreboard from the player.
     */
    public void hide() {
        if (showing) {
            player.setScoreboard(player.getServer().getScoreboardManager().getMainScoreboard());
            showing = false;
        }
    }
    
    @Override
    public void update() {
        updateLines();
        if (showing) {
            // Refresh the scoreboard
            show();
        }
    }
    
    @Override
    public void destroy() {
        hide();
    }
    
    /**
     * Sets a player statistic on the scoreboard.
     * 
     * @param label The label for the statistic
     * @param value The value of the statistic
     * @param index The index to place it at
     */
    public void setPlayerStat(String label, Object value, int index) {
        setLine(index, label + ": " + value);
    }
    
    /**
     * Sets multiple player statistics on the scoreboard.
     * 
     * @param stats Array of label and value pairs
     * @param startIndex The starting index
     */
    public void setPlayerStats(Object[][] stats, int startIndex) {
        int index = startIndex;
        for (Object[] stat : stats) {
            setPlayerStat((String) stat[0], stat[1], index++);
        }
    }
    
    /**
     * Updates a player statistic on the scoreboard.
     * 
     * @param label The label for the statistic
     * @param value The new value of the statistic
     * @param index The index of the statistic
     */
    public void updatePlayerStat(String label, Object value, int index) {
        updateLine(index, label + ": " + value);
    }
    
    /**
     * Updates multiple player statistics on the scoreboard.
     * 
     * @param stats Array of label and value pairs
     * @param startIndex The starting index
     */
    public void updatePlayerStats(Object[][] stats, int startIndex) {
        int index = startIndex;
        for (Object[] stat : stats) {
            updatePlayerStat((String) stat[0], stat[1], index++);
        }
    }
}