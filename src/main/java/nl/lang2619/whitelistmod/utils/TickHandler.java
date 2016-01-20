package nl.lang2619.whitelistmod.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import nl.lang2619.whitelistmod.config.ModConfig;
import nl.lang2619.whitelistmod.sql.DatabaseConfig;
import nl.lang2619.whitelistmod.sql.ModMySQL;
import nl.lang2619.whitelistmod.text.ModText;
import nl.lang2619.whitelistmod.twitch.ModTwitch;
import nl.lang2619.whitelistmod.twitch.TwitchConfig;

import java.util.EnumSet;

/**
 * Created by Tim on 7/10/2014.
 */

public class TickHandler {

    int tick = 0;

    @SubscribeEvent
    public void tickWhiteList(TickEvent.ServerTickEvent event) {
        tick++;

        if (tick == (ModConfig.time * 20 * 60)) {
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
            tick = 0;
        }
    }
}