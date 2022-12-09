package disconnectcommand.disconnectcommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisconnectCommand extends JavaPlugin implements CommandExecutor {


    FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic


        config.addDefault("disconnectmessage", "&a&lDisconnected");
        config.addDefault("reloadmessage", "&a&lReloaded");
        config.addDefault("nopermissionmessage", "&a&lYou don't have the required permission to use this command");
        config.options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("dcreload"))
        {
            if (sender.hasPermission("disconnect.reload"))
            {
                reloadConfig();
                sender.sendMessage(color(config.get("reloadmessage").toString()));
            }
            else
            {
                sender.sendMessage(color(config.get("nopermissionmessage").toString()));
            }
        }
        else if (command.getName().equals("disconnect"))
        {
            if (sender.hasPermission("disconnect.use"))
            {
                Bukkit.getPlayer(sender.getName()).kickPlayer(color(config.get("disconnectmessage").toString()));
            }
            else
            {
                sender.sendMessage(color(config.get("nopermissionmessage").toString()));
            }
        }

        return super.onCommand(sender, command, label, args);
    }



    @Override
    public void reloadConfig() {
        super.reloadConfig();

        saveDefaultConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }


    public String color(String text)
    {
        String chatcolor = ChatColor.translateAlternateColorCodes('&', text);
        return chatcolor;
    }
}
