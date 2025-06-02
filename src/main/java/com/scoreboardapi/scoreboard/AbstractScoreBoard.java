package com.scoreboardapi.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Abstract implementation of the ScoreBoard interface.
 */
public abstract class AbstractScoreBoard implements ScoreBoard {
    
    protected String title;
    protected List<String> lines;
    protected Scoreboard bukkit;
    protected Objective objective;
    
    /**
     * Constructs a new AbstractScoreBoard.
     * 
     * @param title The title of the scoreboard
     */
    public AbstractScoreBoard(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.lines = new ArrayList<>();
        this.bukkit = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = bukkit.registerNewObjective("scoreboard", "dummy", this.title);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public void setTitle(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.objective.setDisplayName(this.title);
    }
    
    @Override
    public List<String> getLines() {
        return new ArrayList<>(lines);
    }
    
    @Override
    public void setLines(List<String> lines) {
        this.lines.clear();
        for (String line : lines) {
            this.lines.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        updateLines();
    }
    
    @Override
    public void setLine(int index, String text) {
        // Ensure the list has enough entries
        while (lines.size() <= index) {
            lines.add("");
        }
        
        lines.set(index, ChatColor.translateAlternateColorCodes('&', text));
        updateLines();
    }
    
    @Override
    public void updateLines(List<String> lines) {
        setLines(lines);
        update();
    }
    
    @Override
    public void updateLine(int index, String text) {
        setLine(index, text);
        update();
    }
    
    /**
     * Updates the lines of the scoreboard.
     */
    protected void updateLines() {
        // Clear existing scores
        for (String entry : bukkit.getEntries()) {
            bukkit.resetScores(entry);
        }
        
        // Add new scores
        int score = lines.size();
        for (String line : lines) {
            if (!line.isEmpty()) {
                // Use unique entry for each line to avoid duplicates
                String entry = getUniqueEntry(line);
                Score lineScore = objective.getScore(entry);
                lineScore.setScore(score);
            }
            score--;
        }
    }
    
    /**
     * Gets a unique entry for the scoreboard.
     * 
     * @param text The text for the entry
     * @return A unique entry
     */
    protected String getUniqueEntry(String text) {
        // If text is longer than 40 characters, truncate it to prevent errors
        if (text.length() > 40) {
            text = text.substring(0, 40);
        }
        
        // Make entry unique by adding color codes
        ChatColor[] colors = ChatColor.values();
        StringBuilder uniqueText = new StringBuilder();
        
        for (int i = 0; i < Math.min(16, text.length()); i++) {
            uniqueText.append(colors[i % colors.length]).append(text.charAt(i));
        }
        
        return uniqueText.toString();
    }
    
    @Override
    public abstract void update();
    
    @Override
    public abstract void destroy();
}