package uk.co.HariboPenguin.PermissionFinder;

import java.io.IOException;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionFinder extends JavaPlugin {
    
    public static Permission perms = null;

    public FindCommand cmdExecutor = new FindCommand(this);
    public DumpCommand dumpExecutor = new DumpCommand(this);
    public AddCommand addExecutor = new AddCommand(this);
    public String prefix = ChatColor.DARK_PURPLE + "[" + ChatColor.GOLD + "PermissionFinder" + ChatColor.DARK_PURPLE + "] ";

    @Override
    public void onEnable() {

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        
        setupPermissions();

        getCommand("findperms").setExecutor(cmdExecutor);
        getCommand("dumpperms").setExecutor(dumpExecutor);
        getCommand("addperms").setExecutor(addExecutor);

    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
