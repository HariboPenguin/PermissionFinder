package uk.co.HariboPenguin.PermissionFinder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

public class DumpCommand implements CommandExecutor {

    public PermissionFinder plugin;

    public DumpCommand(PermissionFinder instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {

        if (sender.hasPermission("permissionfinder.dumpperms")) {

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("all")) {
                    List<Plugin> noPermPlugins = new ArrayList<Plugin>();
                    List<Plugin> successfulDumpPlugins = new ArrayList<Plugin>();

                    for (Plugin pl : Bukkit.getServer().getPluginManager().getPlugins()) {
                        List permList = pl.getDescription().getPermissions();

                        if (permList.isEmpty()) {
                            noPermPlugins.add(pl);
                        } else {

                            int listSize = permList.size();
                            int counter = 0;

                            plugin.getDataFolder().mkdir();

                            File dumpFile = new File(plugin.getDataFolder().getPath() + File.separatorChar + pl.getName() + "-perms.txt");

                            try {
                                dumpFile.createNewFile();

                                BufferedWriter dumpOut = new BufferedWriter(new FileWriter(dumpFile));

                                dumpOut.write("---------- Permission nodes for " + pl.getName() + " ----------");

                                while (counter < listSize) {
                                    Permission permissionNode = (Permission) permList.get(counter);

                                    if (args.length == 1) {

                                        dumpOut.newLine();
                                        dumpOut.write(permissionNode.getName() + " - " + permissionNode.getDescription());

                                        counter++;

                                    }
                                }

                                dumpOut.close();

                            } catch (IOException ex) {
                                Logger.getLogger(DumpCommand.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            successfulDumpPlugins.add(pl);

                        }
                    }
                    
                    StringBuilder successfulList = new StringBuilder();
                    
                    int successPluginListSize = successfulDumpPlugins.size();
                    int counter = 0;
                    
                    while (counter < successPluginListSize) {
                        Plugin pl = successfulDumpPlugins.get(counter);
                        
                        if (successfulList.length() > 0) {
                            successfulList.append(ChatColor.WHITE + ", ");
                        }
                        
                        successfulList.append(ChatColor.GREEN + pl.getName());
                        
                        pl.getName();
                        
                        counter++;
                    }
                    
                    StringBuilder unsuccessfulList = new StringBuilder();
                    
                    int noSuccessPluginListSize = noPermPlugins.size();
                    int counter2 = 0;
                    
                    while (counter2 < noSuccessPluginListSize) {
                        Plugin pl = noPermPlugins.get(counter2);
                        
                        if (unsuccessfulList.length() > 0) {
                            unsuccessfulList.append(ChatColor.WHITE + ", ");
                        }
                        
                        unsuccessfulList.append(ChatColor.RED + pl.getName());
                        
                        pl.getName();
                        
                        counter2++;   
                    }
                    
                    sender.sendMessage(plugin.prefix + ChatColor.GREEN + "The following plugin's permission nodes have been successfully dumped to text files: " + successfulList);
                    sender.sendMessage(plugin.prefix + ChatColor.RED + "Permission nodes could not be found for the following plugins: " + unsuccessfulList);
                    
                    return true;


                } else {

                    if (getPlugin(args[0]) != null) {
                        Plugin enteredPlugin = getPlugin(args[0]);

                        List permList = enteredPlugin.getDescription().getPermissions();

                        if (permList.isEmpty()) {
                            sender.sendMessage(plugin.prefix + ChatColor.RED + "No permission nodes were found for that plugin");
                        } else {

                            int listSize = permList.size();
                            int counter = 0;

                            plugin.getDataFolder().mkdir();
                            File dumpFile = new File(plugin.getDataFolder().getPath() + File.separatorChar + enteredPlugin.getName() + "-perms.txt");
                            try {
                                dumpFile.createNewFile();

                                BufferedWriter dumpOut = new BufferedWriter(new FileWriter(dumpFile));

                                dumpOut.write("---------- Permission nodes for " + enteredPlugin.getName() + " ----------");

                                while (counter < listSize) {
                                    Permission permissionNode = (Permission) permList.get(counter);

                                    if (args.length == 1) {

                                        dumpOut.newLine();
                                        dumpOut.write(permissionNode.getName() + " - " + permissionNode.getDescription());

                                        counter++;

                                    }
                                }

                                dumpOut.close();
                                sender.sendMessage(plugin.prefix + ChatColor.GREEN + "Permission nodes successfully dumped to: " + dumpFile.getPath());

                            } catch (IOException ex) {
                                Logger.getLogger(DumpCommand.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        sender.sendMessage(plugin.prefix + ChatColor.RED + "Plugin is not enabled!");
                    }
                }
                
            } else {
                sender.sendMessage(plugin.prefix + ChatColor.RED + "Command usage is: /dumpperms [Plugin Name | all]");
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
