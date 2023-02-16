package dk.nydt.sell.utils;

import dk.nydt.sell.Sell;
import org.bukkit.entity.Player;

public class Multiplier {

    public static int getMultiplier(Player p) {
        return Sell.multiplierYML.getInt(p.getUniqueId()+".Multiplier", 1);
    }

    public static void setMultiplier(Player p, int multiplier) {
        Sell.multiplierYML.set(p.getUniqueId()+".Multiplier", multiplier);
        Sell.multiplier.saveConfig();
    }

    public static void addMultiplier(Player p, int multiplier) {
        int multiplierNow = Sell.multiplierYML.getInt(p.getUniqueId()+".Multiplier", 1);
        Sell.multiplierYML.set(p.getUniqueId()+".Multiplier", (multiplierNow+multiplier));
        Sell.multiplier.saveConfig();
    }

    public static void remMultiplier(Player p, int multiplier) {
        int multiplierNow = Sell.multiplierYML.getInt(p.getUniqueId()+".Multiplier", 1);
        Sell.multiplierYML.set(p.getUniqueId()+".Multiplier", (multiplierNow-multiplier));
        Sell.multiplier.saveConfig();
    }
    public static int getGlobalMultiplier() {
        return Sell.configYML.getInt("Global Multiplier", 0);
    }

    public static void setGlobalMultiplier(int multiplier) {
        Sell.configYML.set("Global Multiplier", multiplier);
        Sell.config.saveConfig();
    }

    public static void addGlobalMultiplier(int multiplier) {
        int multiplierNow = Sell.multiplierYML.getInt("Global Multiplier", 0);
        Sell.configYML.set("Global Multiplier", (multiplierNow+multiplier));
        Sell.multiplier.saveConfig();
    }

    public static void remGlobalMultiplier (int multiplier) {
        int multiplierNow = Sell.configYML.getInt("Global Multiplier", 0);
        Sell.configYML.set("Global Multiplier", (multiplierNow-multiplier));
        Sell.config.saveConfig();
    }

    public static int getRankMultiplier(Player p) {
        return Sell.multiplierYML.getInt(p.getUniqueId()+".RankMultiplier", 0);
    }

    public static void setRankMultiplier(Player p, int multiplier) {
        Sell.multiplierYML.set(p.getUniqueId()+".RankMultiplier", multiplier);
        Sell.multiplier.saveConfig();
    }

    public static void addRankMultiplier(Player p, int multiplier) {
        int multiplierNow = Sell.multiplierYML.getInt(p.getUniqueId()+".RankMultiplier", 0);
        Sell.multiplierYML.set(p.getUniqueId()+".RankMultiplier", (multiplierNow+multiplier));
        Sell.multiplier.saveConfig();
    }

    public static void remRankMultiplier(Player p, int multiplier) {
        int multiplierNow = Sell.multiplierYML.getInt(p.getUniqueId()+".multiplier", 0);
        Sell.multiplierYML.set(p.getUniqueId()+".RankMultiplier", (multiplierNow-multiplier));
        Sell.multiplier.saveConfig();
    }

}
