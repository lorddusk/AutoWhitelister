package nl.lang2619.whitelistmod.proxies;

import cpw.mods.fml.common.FMLCommonHandler;
import nl.lang2619.whitelistmod.utils.TickHandler;
//import nl.lang2619.whitelistmod.sql.IWhitelistHandler;

/**
 * Created by Tim on 7/10/2014.
 */
public class CommonProxy {
    public void registerTickHandlers(){
        FMLCommonHandler.instance().bus().register(new TickHandler());
    }
}
