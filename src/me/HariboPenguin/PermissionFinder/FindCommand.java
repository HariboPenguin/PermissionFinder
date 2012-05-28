package me.HariboPenguin.PermissionFinder;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public class FindCommand implements CommandExecutor {

    public PermissionFinder plugin;

    public FindCommand(PermissionFinder instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

        if (sender.hasPermission("permissionfinder.findperms")) {

            if (args.length >= 1) {

                if (getPlugin(args[0]) != null) {
                    Plugin enteredPlugin = getPlugin(args[0]);

                    List permList = enteredPlugin.getDescription().getPermissions();

                    if (permList.isEmpty()) {
                        sender.sendMessage(plugin.prefix + ChatColor.RED + "No permission nodes were found for that plugin");
                    } else {

                        int listSize = permList.size();
                        int counter = 0;

                        sender.sendMessage(ChatColor.DARK_PURPLE + "---------- " + ChatColor.GOLD + "Permission nodes found for " + enteredPlugin.getName() + ChatColor.DARK_PURPLE + " ----------");

                        while (counter < listSize) {
                            Permission permissionNode = (Permission) permList.get(counter);

                            if (args.length == 2) {

                                String permissionToFind = args[1];

                                if (permissionNode.getName().toLowerCase().contains(permissionToFind.toLowerCase()) || permissionNode.getDescription().toLowerCase().contains(permissionToFind.toLowerCase())) {
                                    sender.sendMessage(ChatColor.RED + permissionNode.getName() + ChatColor.GOLD + " - " + ChatColor.GRAY + permissionNode.getDescription());
                                }

                            } else if (args.length == 1) {

                                sender.sendMessage(ChatColor.RED + permissionNode.getName() + ChatColor.GOLD + " - " + ChatColor.GRAY + permissionNode.getDescription());

                            }

                            counter++;
                        }
                        return true;
                    }

                } else {
                    sender.sendMessage(plugin.prefix + ChatColor.RED + "Plugin is not enabled!");
                    return true;
                }

            } else {
                sender.sendMessage(plugin.prefix + ChatColor.RED + "Correct usage is: /findperms [Plugin Name] <Search Terms>");
            }

        } else {
            sender.sendMessage(plugin.prefix + ChatColor.RED + "You do not have permission for this command!");
        }

        return true;
    }

    public Plugin getPlugin(String pluginName) {

        for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
            if (pl.getDescription().getName().equalsIgnoreCase(pluginName)) {
                return pl;
            }
        }
        return null;
    }
}
