package com.github.mamizu0312.simplespawn;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleSpawn extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("setspawn").setExecutor(this);
        getCommand("spawn").setExecutor(this);
        getLogger().info("SimpleSpawn Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleSpawn Disabled.");
    }
    String prefix = getConfig().getString("prefix");

    public boolean onComamnd(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("setspawn")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(prefix + "§aSimpleSpawnはコンソールでは使用できません。");
                return true;
            }
                Player p = (Player) sender;
                if (!(sender.hasPermission("SimpleSpawn.setspawn"))) {
                    sender.sendMessage(prefix + "§4あなたには権限がありません");
                }
                getConfig().set("spawn.world", p.getLocation().getWorld().getName());
                getConfig().set("spawn.x", p.getLocation().getX());
                getConfig().set("spawn.y", p.getLocation().getY());
                getConfig().set("spawn.z", p.getLocation().getZ());
                saveConfig();
                reloadConfig();
                p.sendMessage(prefix + "スポーン地点を現在立っている場所に設定しました。");
                return true;

        }

        if(label.equalsIgnoreCase("spawn")) {
            if(args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "§aSimpleSpawnはコンソールでは使用できません。");
                    return true;
                }
                Player p = (Player) sender;
                if (!(sender.hasPermission("SimpleSpawn.spawn"))) {
                    sender.sendMessage(prefix + "§4あなたには権限がありません！");
                    return true;
                }
                World w = getServer().getWorld(getConfig().getString("spawn.world"));
                double x = getConfig().getDouble("spawn.x");
                double y = getConfig().getDouble("spawn.y");
                double z = getConfig().getDouble("spawn.z");
                p.teleport(new Location(w, x, y, z));
                sender.sendMessage(prefix + "spawnにワープしました。");
                return true;
            } else {
                sender.sendMessage(prefix + "使い方が間違っています: /spawn");
                return true;
            }
        }
        return false;
    }
}
