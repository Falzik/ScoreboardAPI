package com.scoreboardapi.scoreboard;

import java.util.List;

import org.bukkit.entity.Player;

/**
 * Interface for all scoreboard types.
 */
public interface ScoreBoard {
    
    /**
     * Gets the title of the scoreboard.
     * 
     * @return The title
     */
    String getTitle();
    
    /**
     * Sets the title of the scoreboard.
     * 
     * @param title The new title
     */
    void setTitle(String title);
    
    /**
     * Gets the lines of the scoreboard.
     * 
     * @return The lines
     */
    List<String> getLines();
    
    /**
     * Sets the lines of the scoreboard.
     * 
     * @param lines The new lines
     */
    void setLines(List<String> lines);
    
    /**
     * Sets a specific line of the scoreboard.
     * 
     * @param index The index of the line (0-based)
     * @param text The text for the line
     */
    void setLine(int index, String text);
    
    /**
     * Updates the scoreboard for all applicable players.
     */
    void update();
    
    /**
     * Updates the scoreboard with new lines.
     * 
     * @param lines The new lines to display
     */
    void updateLines(List<String> lines);
    
    /**
     * Updates a specific line on the scoreboard.
     * 
     * @param index The index of the line to update
     * @param text The new text for the line
     */
    void updateLine(int index, String text);
    
    /**
     * Destroys the scoreboard, cleaning up resources.
     */
    void destroy();
}