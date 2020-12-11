package me.MuchDan.TimedLeaderboard.Util;

import me.MuchDan.TimedLeaderboard.TimedLeaderboard;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class IO {
    private TimedLeaderboard plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private String FileName;

    public IO(TimedLeaderboard plugin, String FileName) {
        this.plugin = plugin;
        saveDefaultConfig();

        this.FileName = FileName;
    }

    public void reloadConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), FileName);
        }
        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource(FileName);
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults((defaultConfig));
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null) {
            return;
        }
        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save data config.", e);
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), FileName);
        }

        if (!this.configFile.exists()) {
            this.plugin.saveResource(FileName, false);
        }
    }
}