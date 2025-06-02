package com.scoreboardapi;

import org.bukkit.plugin.java.JavaPlugin;

public class ScoreBoardPlugin extends JavaPlugin {
    
    private static ScoreBoardPlugin instance;
    private ScoreBoardManager scoreBoardManager;
    
    @Override
    public void onEnable() {
        instance = this;
        scoreBoardManager = new ScoreBoardManager(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getLogger().info("ScoreBoard API has been enabled!");
    }
    
    @Override
    public void onDisable() {
        if (scoreBoardManager != null) {
            scoreBoardManager.cleanUp();
        }
        
        getLogger().info("ScoreBoard API has been disabled!");
    }

    public static ScoreBoardPlugin getInstance() {
        return instance;
    }
    

    public ScoreBoardManager getScoreBoardManager() {
        return scoreBoardManager;
    }
}