package me.redvortex.homes.commands;

import me.redvortex.homes.Homes;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {

        if (sender instanceof Player player) {
            Location playerLocation = player.getLocation();
            Homes.getInstance().getConfig().set("homes." + player.getUniqueId(), playerLocation);
            Homes.getInstance().saveConfig();

            player.sendActionBar(Component.text("§aSet your home!"));
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5f, 2f);
        }

        return true;
    }
}
