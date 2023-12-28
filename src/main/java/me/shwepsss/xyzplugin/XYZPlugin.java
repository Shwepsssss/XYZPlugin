package me.shwepsss.xyzplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class XYZPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

            getServer().getPluginManager().registerEvents(this, this);
        }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
    Player p = event.getPlayer();
    p.setPlayerListName(p.getName()+" "+getPLocation(p));


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("xyz")) {
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (args.length == 0) {
                    p.chat(getPLocation(p));

                }else if(args.length == 1){
                    Player player = Bukkit.getServer().getPlayer(args[0]);
                    p.sendMessage(getPLocation(player));



                }
            }




        }





        return true;
    }

    public String getPLocation(Player p){
        return Math.round(p.getLocation().getX()) + ", " + Math.round(p.getLocation().getY()) + ", " + Math.round(p.getLocation().getZ());
    }

    @Override
    public void onDisable() {

    }
}
