package me.MuchDan.TimedLeaderboard.Util;

import me.MuchDan.TimedLeaderboard.TimedLeaderboard;
import org.bukkit.entity.Player;

public class ActivePlayer extends Timer{
    private Player player;
    private String set;

    public ActivePlayer(TimedLeaderboard plugin) {
        super(plugin);
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public Player getPlayer(){
        return player;
    }
    public void setSet(String set){
        this.set = set;
    }
    public String getSet(){
        return set;
    }
}
