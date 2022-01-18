package me.kolterdyx.chemicalwarfare.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CWTabCompleter implements TabCompleter {

    public ArrayList<String> mainArgs = new ArrayList<>();
    public ArrayList<String> tierArgs = new ArrayList<>();
    public ArrayList<String> gasArgs = new ArrayList<>();

    public ArrayList<String> gasses = new ArrayList<>();
    public ArrayList<String> duration = new ArrayList<>();

    private Plugin plugin;

    public CWTabCompleter(Plugin plugin){
        this.plugin = plugin;
        mainArgs.addAll(List.of(new String[]{"mask", "canister", "clear", "mustard", "chlorine", "tear", "reload"}));
        tierArgs.addAll(List.of(new String[]{"1", "2", "3", "4", "<tier>"}));
        gasArgs.addAll(List.of(new String[]{"1", "2", "3", "[gas id]"}));
        gasses.addAll(List.of(new String[]{"mustard", "chlorine", "tear"}));
        duration.addAll(List.of(new String[]{"[seconds]"}));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {


        if (command.getName().equalsIgnoreCase("gas")) {
            switch (args.length){
                case 1:
                    return mainArgs;
                case 2:
                    if (args[0].equalsIgnoreCase("clear")){
                        return null;
                    }
                    return tierArgs;
                case 3:
                    if (gasses.contains(args[0])){
                        return duration;
                    } else {
                        return gasArgs;
                    }
            }
        }

        return null;
    }
}
