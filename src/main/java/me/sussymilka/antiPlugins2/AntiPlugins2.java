package me.sussymilka.antiPlugins2;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.Date;

public class AntiPlugins2 extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AntiPlugins2 enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("AntiPlugins2 disabled");
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        // Get the command part only (first token) and normalize
        String message = event.getMessage();
        if (message == null || message.isEmpty()) return;

        String command = message.split(" ")[0].toLowerCase(); // e.g. "/pl" or "/plugins"

        if (command.equals("/pl") || command.equals("/plugins")) {
            // Cancel execution (note the correct method name: setCancelled)
            event.setCancelled(true);

            String playerName = event.getPlayer().getName();
            String reason = "Using /pl or /plugins";
            Date expires = new Date(System.currentTimeMillis() + 60L * 60L * 1000L); // 1 hour from now

            // Add a timed ban to the server ban list
            Bukkit.getBanList(BanList.Type.NAME).addBan(playerName, reason + " - banned for 1 hour", expires, getName());

            // Kick the player with a message
            event.getPlayer().kickPlayer(ChatColor.RED + "Masz bana na godzine za uzycie komendy /plugins");

            getLogger().info(playerName + " dostal bana na godzine za uzycie komendy  " + command);
        }
    }
}
