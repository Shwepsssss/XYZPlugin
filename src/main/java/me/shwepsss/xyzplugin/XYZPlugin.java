package me.shwepsss.xyzplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class XYZPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("xyz")) {
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (args.length == 0) {
                    getPLocation(p);

                }else if(args.length == 1){
                    Player player = Bukkit.getServer().getPlayer(args[0]);
                    p.sendMessage(player.getName()+" XYZ Location is: "+Math.round(player.getLocation().getX()) + ", " + Math.round(player.getLocation().getY()) + ", " + Math.round(player.getLocation().getZ()));


                }
            }




        }


        return true;
    }

    public void getPLocation(Player p){
        p.chat(p.getName()+" XYZ Location is: "+Math.round(p.getLocation().getX()) + ", " + Math.round(p.getLocation().getY()) + ", " + Math.round(p.getLocation().getZ()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
