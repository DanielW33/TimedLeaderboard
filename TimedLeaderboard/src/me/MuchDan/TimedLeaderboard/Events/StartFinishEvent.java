package me.MuchDan.TimedLeaderboard.Events;

import me.MuchDan.TimedLeaderboard.TimedLeaderboard;
import me.MuchDan.TimedLeaderboard.Util.ActivePlayer;
import me.MuchDan.TimedLeaderboard.Util.IO;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class StartFinishEvent implements Listener {
    private TimedLeaderboard plugin;
    private IO StartEndData;

    public StartFinishEvent(TimedLeaderboard plugin){
        this.plugin = plugin;
        StartEndData = plugin.getStartEndData();
    }
    @EventHandler
    public void StartFinish(PlayerInteractEvent Event){
        if(Event.getAction().equals(Action.PHYSICAL)){
            if(Event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE){
                Player player = Event.getPlayer();
                Location loc = player.getLocation();
                double px = loc.getX(), py = loc.getY(), pz = loc.getZ();

                StartEndData.getConfig().getConfigurationSection("PreSets").getKeys(false).forEach(set ->{
                    double x = ((int)px == (int)StartEndData.getConfig().getDouble("Presets." + set + ".start.x"))
                            ? StartEndData.getConfig().getDouble("Presets." + set + ".start.x")
                            : StartEndData.getConfig().getDouble("Presets." + set + ".end.x");

                    double y = ((int)px == (int)StartEndData.getConfig().getDouble("Presets." + set + ".start.y"))
                            ? StartEndData.getConfig().getDouble("Presets." + set + ".start.y")
                            : StartEndData.getConfig().getDouble("Presets." + set + ".end.y");

                    double z = ((int)px == (int)StartEndData.getConfig().getDouble("Presets." + set + ".start.z"))
                            ? StartEndData.getConfig().getDouble("Presets." + set + ".start.z")
                            : StartEndData.getConfig().getDouble("Presets." + set + ".end.z");

                    if((int)x == (int) px && (int)y == (int) py && (int)z == (int) pz){
                        String se = ((int)x == (int)StartEndData.getConfig().getDouble("Presets." + set + ".start.x"))
                                ? "start" : "end";

                        StartorEnd(player, se, set);
                    }
                });
            }
        }
    }
    private void StartorEnd(Player player, String se, String set){
        if(se.equalsIgnoreCase("start")){
            StartTimer(player, set);
        }
        else{
            EndTimer(player);
        }
    }
    private void StartTimer(Player player, String set){
        List<ActivePlayer> Active = plugin.getActive();
        Active.forEach(APlayers ->{
            if(APlayers.getPlayer().equals(player)) {
                Active.remove(APlayers);
            }
        });
        ActivePlayer pl = new ActivePlayer(plugin);
        pl.setPlayer(player);
        pl.setSet(set);
        pl.StartTimer();
    }
    private void EndTimer(Player player){
        List<ActivePlayer> Active = plugin.getActive();
        int Time;

        for(ActivePlayer pl : Active){
            if(pl.getPlayer().equals(player)) {
                Time = pl.getRuntime();
                pl.EndTimer();
                setTime(Time, pl);
                Active.remove(pl);
            }
        }
    }
    private void setTime(int Time, ActivePlayer Apl){
        IO PlayerData = plugin.getPlayerData();
        PlayerData.getConfig().set("Leaderboards." + Apl.getSet() + Apl.getPlayer().getName() + ".Time", Time);
        PlayerData.saveConfig();
    }
}
