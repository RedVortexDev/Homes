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

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class HomeCommand implements CommandExecutor {
    public final HashMap<UUID, Long> cooldowns;
    public HomeCommand() {
        this.cooldowns = new HashMap<>();
    }

    public void teleport(Player player) {
        if (Homes.getInstance().getConfig().getLocation("homes." + player.getUniqueId()) != null) {
            player.teleport(Homes.getInstance().getConfig().getLocation("homes." + player.getUniqueId()));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F);
            player.sendActionBar(Component.text("§bTeleported to your home!"));
        } else {
            player.sendActionBar(Component.text("§cYou do not have a home!"));
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5f, 0f);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player player) {

            if (!this.cooldowns.containsKey(player.getUniqueId())) {
                cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                teleport(player);
            } else {
                long timeElapsed = System.currentTimeMillis() - cooldowns.get(player.getUniqueId());

                if (timeElapsed >= Homes.getInstance().getConfig().getInt("homeCooldown") * 1000L) {
                    this.cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
                    teleport(player);
                } else {
                    player.sendActionBar(Component.text("§cYou're on cooldown for another §f" + (((Homes.getInstance().getConfig().getInt("homeCooldown") * 1000L) - timeElapsed) / 1000) + " §cseconds!"));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_DIDGERIDOO, 5f, 0f);
                }
            }

        }
        return true;
    }
}
