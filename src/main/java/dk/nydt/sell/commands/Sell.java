package dk.nydt.sell.commands;

import dk.nydt.sell.utils.Color;
import dk.nydt.sell.utils.Multiplier;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static dk.nydt.sell.Sell.*;

public class Sell implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission(configYML.getString("permissions.reload"))) {
                    material.reloadConfig();
                    materialYML = material.getConfig();
                    config.reloadConfig();
                    configYML = config.getConfig();
                    multiplier.reloadConfig();
                    multiplierYML = multiplier.getConfig();
                    player.sendMessage(Color.getColored(configYML.getString("messages.reload_success")));
                } else {
                    player.sendMessage(Color.getColored(configYML.getString("messages.reload_deny")));
                }
                return true;
            } else {
                player.sendMessage(Color.getColored(configYML.getString("messages.argument_error")));
            }
        }
        int multiplier = Multiplier.getMultiplier(player);
        int rankMultiplier = Multiplier.getRankMultiplier(player);
        int globalMultiplier = Multiplier.getGlobalMultiplier();
        if (rankMultiplier == 0) {
            if (globalMultiplier == 0) {
                globalMultiplier = 1;
            }
        }
        int totalMultiplier = (multiplier * (globalMultiplier + rankMultiplier));
        Inventory inventory = player.getInventory();
        int totalSellPrice = 0;

        for (ItemStack item : inventory.getContents()) {
            if (item == null) {
                continue;
            }
            Material itemType = item.getType();
            short itemData = item.getDurability();
            if (materialYML.contains("Sellables." + itemType.name())) {
                ConfigurationSection sellableSection = materialYML.getConfigurationSection("Sellables." + itemType.name());
                if (sellableSection.contains("data") && sellableSection.getInt("data") != itemData) {
                    continue;
                }
                int quantity = item.getAmount();
                int sellPrice = multiplier * (quantity * sellableSection.getInt("price"));
                player.getInventory().removeItem(item);
                totalSellPrice += sellPrice;
            }
        }
        if (!(totalSellPrice > 0)) {
            player.sendMessage(Color.getColored(configYML.getString("messages.sell_fail")));
            return true;
        }
        econ.depositPlayer(player, totalSellPrice);
        for (String s : configYML.getStringList("messages.sell_success")) {
            s = s.replace("%multiplier%", String.valueOf(totalMultiplier));
            s = s.replace("%price%", String.valueOf(totalSellPrice));
            player.sendMessage(Color.getColored(s));
        }
        return true;
    }
}
