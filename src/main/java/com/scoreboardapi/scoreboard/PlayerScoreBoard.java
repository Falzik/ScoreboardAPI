package com.scoreboardapi.scoreboard;

import org.bukkit.entity.Hanging;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerScoreBoard extends AbstractScoreBoard {
    
    private final Player player;

    private boolean showing;
    private boolean save = false;

    private static final HashMap<UUID, PlayerScoreBoard> playerScoreBoard = new HashMap<>();
    

    public PlayerScoreBoard(Player player, String title) {
        super(title);
        this.player = player;
        this.showing = false;
    }

    public PlayerScoreBoard(Player player, String title, boolean save) {
        super(title);
        this.player = player;
        this.showing = false;

        setSave(save);
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
        playerScoreBoard.put(player.getUniqueId(), this);
    }

    public static PlayerScoreBoard getBoardBy(Player player) {
        if(!playerScoreBoard.containsKey(player.getUniqueId())) return null;
        return playerScoreBoard.get(player.getUniqueId());
    }

    public Player getPlayer() {
        return player;
    }
    

    public boolean isShowing() {
        return showing;
    }

    public void show() {
        if (!showing) {
            player.setScoreboard(bukkit);
            showing = true;
        }
    }

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
            show();
        }
    }
    
    @Override
    public void destroy() {
        hide();
    }

    public void setPlayerStat(String label, Object value, int index) {
        setLine(index, label + ": " + value);
    }
    

    public void setPlayerStats(Object[][] stats, int startIndex) {
        int index = startIndex;
        for (Object[] stat : stats) {
            setPlayerStat((String) stat[0], stat[1], index++);
        }
    }
    

    public void updatePlayerStat(String label, Object value, int index) {
        updateLine(index, label + ": " + value);
    }

    public void updatePlayerStats(Object[][] stats, int startIndex) {
        int index = startIndex;
        for (Object[] stat : stats) {
            updatePlayerStat((String) stat[0], stat[1], index++);
        }
    }
}