//Not Tested, Spigot 1.15.2, latest Holographic displays supports 1.15.2
package me.MuchDan.TimedLeaderboard;

import me.MuchDan.TimedLeaderboard.Util.ActivePlayer;
import me.MuchDan.TimedLeaderboard.Util.IO;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TimedLeaderboard extends JavaPlugin {
    private IO StartEndData;
    private IO PlayerData;
    private List<ActivePlayer> Active;
    
    @Override
    public void onEnable(){
        if(this.getServer().getPluginManager().getPlugin("HolographicDisplays") != null){
            this.getLogger().log(Level.INFO, "Successfully Hooked into Holographic Displays");
        }
        else{
            this.getLogger().log(Level.SEVERE, "Failed to Hook into Holographic Displays");
            this.getServer().getPluginManager().disablePlugin(this);
        }
        
        Active = new ArrayList<>();
        
        StartEndData = new IO(this,"StartEndData.yml");
        StartEndData.getConfig().options().copyDefaults(false);
        StartEndData.saveDefaultConfig();

        PlayerData = new IO(this, "PlayerData.yml");
        PlayerData.getConfig().options().copyDefaults(false);
        PlayerData.saveDefaultConfig();

    }
    public IO getStartEndData(){
        return StartEndData;
    }
    public IO getPlayerData(){
        return PlayerData;
    }
    public List<ActivePlayer> getActive(){
        return Active;
    }
}
