package me.shwepsss.xyzplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

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

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player p = event.getPlayer();
        Bukkit.broadcastMessage(p.getName()+" left the server at the XYZ coordinates: " + getPLocation(p));
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent event){
        Player p = event.getEntity().getPlayer();
        p.sendMessage("You died at the XYZ coordinates: " + getPLocation(p));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("xyz")) {
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (args.length == 0) {
                    p.chat(getPLocation(p));
                }
                else if(args.length == 2 && args[0].equals("add")){
                    File playerFile = new File(getDataFolder() + "/playerdata", p.getUniqueId() + ".yml");
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(playerFile);
                    data.set(args[1], getPLocation(p));
                    saveData(data, playerFile);
                    p.sendMessage(ChatColor.GREEN + "The XYZ coordinates has been saved as: " + args[1]);
                }
                else if(args[0].equals("list")){
                    File playerFile = new File(getDataFolder() + "/playerdata", p.getUniqueId() + ".yml");
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(playerFile);
                    if (args.length == 1) {
                        if (data.getKeys(false).isEmpty()){
                            p.sendMessage("List is empty use /xyz add {name} to add waypoints");
                        }
                        else {
                            for (String key : data.getKeys(false)) {
                                Object value = data.get(key);
                                p.sendMessage(key + ": " + value);
                            }
                        }
                    }else if(args.length == 2){
                        p.sendMessage(args[1]+ ": " + data.get(args[1]));
                    }
                }

                else if(args.length == 2 && args[0].equals("delete")){
                    File playerFile = new File(getDataFolder() + "/playerdata", p.getUniqueId() + ".yml");
                    YamlConfiguration data = YamlConfiguration.loadConfiguration(playerFile);
                    if (args[1].equals("all")){
                        for (String key : data.getKeys(false)) {
                            data.set(key, null);
                            saveData(data, playerFile);
                        }
                        p.sendMessage(ChatColor.RED + "All waypoints has been removed");
                    }
                        else{
                        for (String key : data.getKeys(false)) {
                            if (key.equals(args[1])){
                                data.set(key, null);
                                saveData(data, playerFile);
                                p.sendMessage(ChatColor.RED + "The XYZ coordinates for the name: "+ args[1] +" has been deleted");
                            }
                        }
                    }
                }
                else if(args.length == 1){
                    Player player = Bukkit.getServer().getPlayer(args[0]);
                    p.sendMessage(getPLocation(player));

                }
            }
        }
        return true;
    }
    void saveData(YamlConfiguration data, File playerFile){
        try {
            data.save(playerFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getPLocation(Player p){
        return Math.round(p.getLocation().getX()) + ", " + Math.round(p.getLocation().getY()) + ", " + Math.round(p.getLocation().getZ());
    }

    @Override
    public void onDisable() {

    }
}
