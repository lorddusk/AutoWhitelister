package nl.lang2619.whitelistmod.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import nl.lang2619.whitelistmod.sql.DatabaseConfig;
import nl.lang2619.whitelistmod.sql.ModMySQL;
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

        if (tick == (DatabaseConfig.time * 20 * 60)) {
            System.out.println("Running Whitelist");
            if (!TwitchConfig.toggle) {
                ModMySQL.checkWhitelist();
            } else {
                ModTwitch.checkWhitelist();
            }
            System.out.println("Finished Whitelist run.");
            tick = 0;
        }
    }
}