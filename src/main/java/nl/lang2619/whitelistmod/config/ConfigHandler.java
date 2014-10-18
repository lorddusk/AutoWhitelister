package nl.lang2619.whitelistmod.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Tim on 7/10/2014.
 */
public class ConfigHandler {

    public static Configuration config;

    public static void init(String filePath){
        ModConfig.init(new File(filePath+"whitelistmod.cfg"));
    }
}
