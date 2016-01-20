package nl.lang2619.whitelistmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import net.minecraft.server.MinecraftServer;
import nl.lang2619.whitelistmod.config.ConfigHandler;
import nl.lang2619.whitelistmod.config.ModConfig;
import nl.lang2619.whitelistmod.proxies.CommonProxy;
import nl.lang2619.whitelistmod.sql.ModMySQL;
import nl.lang2619.whitelistmod.text.ModText;
import nl.lang2619.whitelistmod.twitch.ModTwitch;
import nl.lang2619.whitelistmod.twitch.TwitchConfig;
import nl.lang2619.whitelistmod.utils.ModInfo;

import java.io.File;

/**
 * Created by Tim on 7/9/2014.
 */
@Mod(modid = ModInfo.ID, version = ModInfo.VERSION, name = ModInfo.NAME, acceptableRemoteVersions = "*")

public class Whitelist {
    @SidedProxy(clientSide = "nl.lang2619.whitelistmod.proxies.ClientProxy", serverSide = "nl.lang2619.whitelistmod.proxies.CommonProxy")
    public static CommonProxy proxy;

    public static String path;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        path = event.getModConfigurationDirectory().getAbsolutePath() + File.separator;
        ConfigHandler.init(path);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {


    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.registerTickHandlers();
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartedEvent event) {
        System.out.println("Running Whitelist");
        if (ModConfig.toggle.equalsIgnoreCase("mysql")) {
            ModMySQL.checkWhitelist();
        } else if (ModConfig.toggle.equalsIgnoreCase("twitch")) {
            ModTwitch.checkWhitelist();
        } else if (ModConfig.toggle.equalsIgnoreCase("text")) {
            ModText.checkWhitelist();
        } else {
            System.out.println("No function selected.");
        }
        System.out.println("Finished Whitelist run.");
    }

}
