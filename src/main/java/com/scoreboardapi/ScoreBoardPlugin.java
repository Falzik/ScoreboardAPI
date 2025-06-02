package com.scoreboardapi;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for the ScoreBoard API.
 */
public class ScoreBoardPlugin extends JavaPlugin {
    
    private static ScoreBoardPlugin instance;
    private ScoreBoardManager scoreBoardManager;
    
    @Override
    public void onEnable() {
        instance = this;
        scoreBoardManager = new ScoreBoardManager(this);
        
        // Register events
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        
        getLogger().info("ScoreBoard API has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Clean up resources
        if (scoreBoardManager != null) {
            scoreBoardManager.cleanUp();
        }
        
        getLogger().info("ScoreBoard API has been disabled!");
    }
    
    /**
     * Gets the instance of the plugin.
     * 
     * @return The plugin instance
     */
    public static ScoreBoardPlugin getInstance() {
        return instance;
    }
    
    /**
     * Gets the ScoreBoard manager.
     * 
     * @return The ScoreBoard manager
     */
    public ScoreBoardManager getScoreBoardManager() {
        return scoreBoardManager;
    }
}