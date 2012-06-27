package uk.co.HariboPenguin.PermissionFinder;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public class AddCommand implements CommandExecutor {

    public PermissionFinder plugin;

    public AddCommand(PermissionFinder instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
        // /addperms [plugin] [player]
        if (sender.hasPermission("permissionfinder.addperms")) {

            if (args.length == 2) {

                if (getPlugin(args[0]) != null) {

                    if (plugin.getServer().getPlayer(args[1]) != null) {
                        
                        Plugin enteredPlugin = getPlugin(args[0]);
                        Player enteredPlayer = plugin.getServer().getPlayer(args[1]);
                        
                        List permList = enteredPlugin.getDescription().getPermissions();
                        
                        if (!permList.isEmpty()) {
                            
                            int listSize = permList.size();
                            int counter = 0;
                            
                            while (counter < listSize) {
                                
                                Permission permissionNode = (Permission) permList.get(counter);
                                
                                PermissionFinder.perms.playerAdd(enteredPlayer, permissionNode.getName());
                                sender.sendMessage(plugin.prefix + ChatColor.GREEN + permissionNode.getName() + "added to " + enteredPlayer.getName());
                                
                                counter++;
                            }
                            
                        } else {
                            sender.sendMessage(plugin.prefix + ChatColor.RED + "No permission nodes were found for that plugin");
                            return true;
                        }
                        
                    } else if (plugin.getServer().getOfflinePlayer(args[1]) != null) {
                        
                        Plugin enteredPlugin = getPlugin(args[0]);
                        Player enteredPlayer = plugin.getServer().getOfflinePlayer(args[1]).getPlayer();
                        
                        List permList = enteredPlugin.getDescription().getPermissions();
                        
                        if (!permList.isEmpty()) {
                            
                            int listSize = permList.size();
                            int counter = 0;
                            
                            while (counter < listSize) {
                                
                                Permission permissionNode = (Permission) permList.get(counter);
                                
                                PermissionFinder.perms.playerAdd(enteredPlayer, permissionNode.getName());
                                sender.sendMessage(plugin.prefix + ChatColor.GREEN + permissionNode.getName() + "added to " + enteredPlayer.getName());
                                
                                counter++;
                            }
                            
                        } else {
                            sender.sendMessage(plugin.prefix + ChatColor.RED + "No permission nodes were found for that plugin");
                            return true;
                        }
                        
                    } else {
                        sender.sendMessage(plugin.prefix + ChatColor.RED + "Player has not joined the server before");
                        return true;
                    }

                } else {
                    sender.sendMessage(plugin.prefix + ChatColor.RED + "Plugin is not enabled");
                    return true;
                }

            } else {
                sender.sendMessage(plugin.prefix + ChatColor.RED + "Incorrect Parameters! Correct usage: /addperms [Plugin] [Player]");
                return true;
            }

        } else {
            sender.sendMessage(plugin.prefix + ChatColor.RED + "You don't have permission for this command!");
            return true;
        }

        return false;
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
