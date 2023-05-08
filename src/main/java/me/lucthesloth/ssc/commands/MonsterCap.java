package me.lucthesloth.ssc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class MonsterCap implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player){
            Collection<Entity> temp = player.getLocation().getNearbyEntitiesByType(Slime.class, 128);
            player.sendPlainMessage(String.format("%d slimes.",
                    (long) temp.size()));
            player.sendPlainMessage(String.format("%d", player.getWorld().getHighestBlockYAt(player.getLocation().getBlockX(), player.getLocation().getBlockZ())));
            return true;
        }
        return false;
    }
}
