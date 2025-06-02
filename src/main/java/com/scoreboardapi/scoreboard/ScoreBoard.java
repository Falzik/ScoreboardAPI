package com.scoreboardapi.scoreboard;

import java.util.List;

import org.bukkit.entity.Player;

public interface ScoreBoard {
    

    String getTitle();
    

    void setTitle(String title);

    List<String> getLines();

    void setLines(List<String> lines);

    void setLine(int index, String text);

    void update();

    void updateLines(List<String> lines);

    void updateLine(int index, String text);

    void destroy();
}