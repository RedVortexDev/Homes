package me.redvortex.homes;

import lombok.Getter;
import me.redvortex.homes.commands.HomeCommand;
import me.redvortex.homes.commands.SetHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Homes extends JavaPlugin {
    @Getter
    private static Homes instance;
    @Override
    public void onEnable() {

        instance = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("home").setExecutor(new HomeCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
