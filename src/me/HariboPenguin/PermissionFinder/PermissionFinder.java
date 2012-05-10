package me.HariboPenguin.PermissionFinder;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionFinder extends JavaPlugin {
    
    public FindCommand cmdExecutor = new FindCommand(this);
    
    public String prefix = ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "PermissionFinder" + ChatColor.DARK_PURPLE + "] ";
    
    @Override
    public void onEnable() {
        
        getCommand("findperms").setExecutor(cmdExecutor);
        
    }
    
}
