package com.scoreboardapi.scoreboard;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public abstract class AbstractScoreBoard implements ScoreBoard {
    
    protected String title;
    protected List<String> lines;
    protected Scoreboard bukkit;
    protected Objective objective;

    private static final List<String> UNIQUE_SUFFIXES = Arrays.asList(
            "§r", "§f", "§l", "§o", "§n", "§m", "§k", "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7"
    );

    private final Map<String, String> entryCache = new HashMap<>();

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
            if(line.equalsIgnoreCase(" ")) {
                line = UNIQUE_SUFFIXES.get(ThreadLocalRandom.current().nextInt(UNIQUE_SUFFIXES.size()));
            }
            this.lines.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        updateLines();
    }
    
    @Override
    public void setLine(int index, String text) {
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

    protected void updateLines() {
        entryCache.clear();

        for (String entry : bukkit.getEntries()) {
            bukkit.resetScores(entry);
        }

        int score = lines.size();
        for (String line : lines) {
            if (!line.isEmpty()) {
                String entry = getUniqueEntry(line);
                Score lineScore = objective.getScore(entry);
                lineScore.setScore(score);
            }
            score--;
        }
    }

    protected String getUniqueEntry(String text) {
        if (text.length() > 40) {
            text = text.substring(0, 40);
        }

        if (entryCache.containsKey(text)) {
            return entryCache.get(text);
        }

        for (String suffix : UNIQUE_SUFFIXES) {
            String attempt = text + suffix;
            if (!bukkit.getEntries().contains(attempt)) {
                entryCache.put(text, attempt);
                return attempt;
            }
        }

        return text;
    }

    public Scoreboard getBukkitScoreboard() {
        return bukkit;
    }
    
    @Override
    public abstract void update();
    
    @Override
    public abstract void destroy();
}