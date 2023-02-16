package dk.nydt.sell;

import dk.nydt.sell.utils.Config;
import me.leoko.advancedban.AdvancedLicense;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Sell extends JavaPlugin {

    public static Sell instance;
    private static PluginManager pluginManager;
    public static Config material, config, multiplier, license;
    public static FileConfiguration materialYML, configYML, multiplierYML, licenseYML;
    public static Economy econ = null;
    private String licenses;
    private boolean access = false;
    @Override
    public void onEnable() {
        pluginManager = getServer().getPluginManager();
        instance = this;

        //LICENSE.YML
        if (!(new File(getDataFolder(), "license.yml")).exists())
            saveResource("license.yml", false);

        license = new Config(this, null, "license.yml");
        licenseYML = license.getConfig();

        licenses = licenseYML.getString("License");
        if(!new AdvancedLicense(licenses, "https://license.cutekat.dk/verify.php", this).debug().register()) return;
        access = true;

        //CONFIG.YML
        if (!(new File(getDataFolder(), "config.yml")).exists())
            saveResource("config.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        //MATERIAL.YML
        if (!(new File(getDataFolder(), "material.yml")).exists())
            saveResource("material.yml", false);

        material = new Config(this, null, "material.yml");
        materialYML = material.getConfig();

        //MATERIAL.YML
        if (!(new File(getDataFolder(), "multiplier.yml")).exists())
            saveResource("multiplier.yml", false);

        multiplier = new Config(this, null, "multiplier.yml");
        multiplierYML = multiplier.getConfig();

        //COMMANDS
        getCommand("Sell").setExecutor(new dk.nydt.sell.commands.Sell());

        //SETUP ECON
        setupEconomyPlugin();

    }

    public void setupEconomyPlugin() {
        if (!setupEconomy() ) {
            Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.getLogger().severe(String.format(String.valueOf(getServer().getPluginManager().getPlugin("Vault"))));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupEconomy();
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        if (access) {
            config.saveConfig();
            material.saveConfig();
            multiplier.saveConfig();
            license.saveConfig();
        }
        license.saveConfig();
    }
}
