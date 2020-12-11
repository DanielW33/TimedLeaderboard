package me.MuchDan.TimedLeaderboard.Util;

import me.MuchDan.TimedLeaderboard.TimedLeaderboard;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer {
    private int TaskID;
    private int runtime;
    private TimedLeaderboard plugin;

    public Timer(TimedLeaderboard plugin){
        this.plugin = plugin;
    }

    public void StartTimer(){
        runtime = 0;
        BukkitScheduler Scheduler = Bukkit.getServer().getScheduler();
        TaskID = Scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                runtime++;
            }
        }, 20L,20L);
    }
    public void EndTimer(){
        Bukkit.getScheduler().cancelTask(TaskID);
    }
    public int getRuntime(){
        return runtime;
    }
}
