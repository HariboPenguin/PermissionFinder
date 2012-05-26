package me.HariboPenguin.PermissionFinder;

import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionFinder extends JavaPlugin {

    public FindCommand cmdExecutor = new FindCommand(this);
    public DumpCommand dumpExecutor = new DumpCommand(this);
    public String prefix = ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "PermissionFinder" + ChatColor.DARK_PURPLE + "] ";

    @Override
    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }

        getCommand("findperms").setExecutor(cmdExecutor);
        getCommand("dumpperms").setExecutor(dumpExecutor);

    }
}
